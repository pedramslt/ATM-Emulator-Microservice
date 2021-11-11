package com.atm.bankservice.service.authenticate;

import com.atm.bankservice.dto.SessionDto;
import com.atm.bankservice.enumeration.LoginType;
import com.atm.bankservice.model.account.dto.AccountDto;
import com.atm.bankservice.model.account.dto.LoginDto;
import com.atm.bankservice.model.account.entity.Account;
import com.atm.bankservice.model.user.dao.AccountRepository;
import com.atm.bankservice.service.redis.RedisService;
import javassist.NotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.naming.directory.InvalidAttributesException;
import java.util.Objects;
import java.util.Optional;

@Service
@Slf4j
public class AuthenticationServiceImpl implements AuthenticationService {

    private final AccountRepository accountRepository;
    private final PasswordEncoder passwordEncoder;
    private final RedisService<SessionDto> redisService;

    @Autowired
    public AuthenticationServiceImpl(AccountRepository accountRepository, PasswordEncoder passwordEncoder, RedisService<SessionDto> redisService) {
        this.accountRepository = accountRepository;
        this.passwordEncoder = passwordEncoder;
        this.redisService = redisService;
    }

    @Override
    public Boolean verifyAccount(String session, String cardNumber) {

        try {
            Optional<Account> account = accountRepository.findByCardNo(cardNumber);
            if (account.isPresent()) {
                if (account.get().isActive()) {
                    saveSessionDto(account.get(), session);
                    return true;
                } else {
                    throw new IllegalAccessException("");
                }
            } else {
                throw new NotFoundException("CARD_NUMBER ".concat(cardNumber).concat(" NOT_FOUND"));
            }
        } catch (NotFoundException e) {
            log.error("verifyAccount service not found error");
            log.error(e.getLocalizedMessage());
            return false;
        } catch (IllegalAccessException e) {
            log.error("verifyAccount service illegal access error");
            log.error(e.getLocalizedMessage());
            return false;
        } catch (Exception e) {
            log.error("verifyAccount service error");
            log.error(e.getLocalizedMessage());
            return false;
        }
    }

    private void saveSessionDto(Account account, String session) {
        AccountDto accountDto = account.toDto();
        SessionDto sessionDto = new SessionDto();
        sessionDto.setAccountDto(accountDto);
        sessionDto.setSessionId(session);
        redisService.save(session, sessionDto);
    }

    /**
     * there is two ways to authenticate  1. PIN code  2.FINGER_PRINT
     *
     * @param session
     * @param loginDto
     * @return
     */
    public String authenticate(String session, LoginDto loginDto) {
        try {
            SessionDto foundSessionDto = redisService.findByKey(session, SessionDto.class);
            if (foundSessionDto.getSessionId().equals(session)) {
                if (Objects.isNull(loginDto.getLoginType()) || loginDto.getLoginType().equals(LoginType.NONE)) {
                    throw new InvalidAttributesException("INVALID_LOGIN_TYPE");
                } else {
                    Optional<Account> account = accountRepository.findByCardNo(loginDto.getCardNumber());
                    if (account.isPresent()) {
                        if (loginDto.getLoginType().equals(LoginType.PIN)) {
                            if (passwordEncoder.matches(String.valueOf(loginDto.getPin()), String.valueOf(account.get().getPin()))) {
                                return "LOGGED_IN_SUCCESSFULLY";
                            } else {
                                SessionDto sessionDto = redisService.findByKey(foundSessionDto.getSessionId(), SessionDto.class);
                                if (Objects.nonNull(sessionDto) && sessionDto.getLoginTryTimes() >= 3) {
                                    return banAccount(account.get());
                                } else {
                                    if (Objects.isNull(sessionDto)) {
                                        sessionDto = new SessionDto();
                                        sessionDto.setLoginTryTimes(0);
                                    } else {
                                        sessionDto.setLoginTryTimes(sessionDto.getLoginTryTimes() + 1);
                                    }
                                }
                                saveSessionOnRedis(foundSessionDto.getSessionId(), sessionDto, account.get());
                                return "INCORRECT";
                            }
                        } else if (loginDto.getLoginType().equals(LoginType.FINGER_PRINT)) {
                            if (passwordEncoder.matches(loginDto.getFingerPrint(), account.get().getUser().getFingerPrint())) {
                                return "LOGGED_IN_SUCCESSFULLY";
                            } else {
                                SessionDto sessionDto = redisService.findByKey(foundSessionDto.getSessionId(), SessionDto.class);
                                if (Objects.nonNull(sessionDto) && sessionDto.getLoginTryTimes() >= 3) {
                                    return banAccount(account.get());
                                } else {
                                    if (Objects.isNull(sessionDto)) {
                                        sessionDto = new SessionDto();
                                        sessionDto.setLoginTryTimes(1);
                                    } else {
                                        sessionDto.setLoginTryTimes(sessionDto.getLoginTryTimes() + 1);
                                    }
                                }
                                saveSessionOnRedis(foundSessionDto.getSessionId(), sessionDto, account.get());
                                throw new BadCredentialsException("INVALID_CREDENTIALS");
                            }
                        }
                    } else {
                        throw new NotFoundException("ACCOUNT_NOT_FOUND");
                    }
                }
            } else {
                throw new IllegalStateException("INVALID_STATE");
            }
        } catch (DisabledException e) {
            log.error("authenticate service error");
            log.error(e.getLocalizedMessage());
            return "USER_DISABLED";
        } catch (BadCredentialsException e) {
            log.error("authenticate service bad credentials error");
            log.error(e.getLocalizedMessage());
            return "INVALID_CREDENTIALS";
        } catch (InvalidAttributesException e) {
            log.error("authenticate service error");
            log.error(e.getLocalizedMessage());
            return "INVALID_LOGIN_TYPE";
        } catch (NotFoundException e) {
            log.error("authenticate service error");
            log.error(e.getLocalizedMessage());
            return "ACCOUNT_NOT_FOUND";
        } catch (IllegalStateException e) {
            log.error("authenticate service illegal state error");
            log.error(e.getLocalizedMessage());
            return "INVALID_STATE";
        } catch (Exception e) {
            log.error("authenticate service error");
            log.error(e.getLocalizedMessage());
            return "UNEXPECTED_ERROR";
        }
        return null;
    }

    @Override
    public String deleteSession(String session) {
        redisService.delete(session);
        log.info("session ".concat(session).concat(" deleted from redis"));
        return "HAVE_A_GOOD_DAY";
    }

    private void saveSessionOnRedis(String sessionId, SessionDto sessionDto, Account account) {
        AccountDto accountDto = account.toDto();
        sessionDto.setAccountDto(accountDto);
        sessionDto.setSessionId(sessionId);
        redisService.save(sessionId, sessionDto);
    }

    private String banAccount(Account account) {
        account.setActive(false);
        accountRepository.save(account);
        log.info("accountId ".concat(String.valueOf(account.getId())).concat(" ").concat("banned"));
        return "ACCOUNT_DEACTIVATED";
    }

}
