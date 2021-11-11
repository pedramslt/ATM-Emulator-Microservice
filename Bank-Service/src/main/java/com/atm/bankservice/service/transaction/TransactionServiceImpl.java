package com.atm.bankservice.service.transaction;

import com.atm.bankservice.dto.SessionDto;
import com.atm.bankservice.model.account.entity.Account;
import com.atm.bankservice.model.user.dao.AccountRepository;
import com.atm.bankservice.service.redis.RedisService;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixProperty;
import javassist.NotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Date;
import java.util.Optional;

@Service
@Slf4j
public class TransactionServiceImpl implements TransactionService {

    @Autowired
    RedisService<SessionDto> redisService;

    @Autowired
    AccountRepository accountRepository;

    @Override
    @HystrixCommand(fallbackMethod = "getBalanceFallback", commandProperties = {
            @HystrixProperty(name = "execution.isolation.thread.timeoutInMilliseconds", value = "2000")
    })
    public String getBalance(String session) {
        try {
            SessionDto sessionDto = getSessionFromRedis(session);
            Optional<Account> account = findAccount(sessionDto.getAccountDto().getCardNo());
            if (account.isPresent()) return String.valueOf(account.get().getBalance());
            else throw new NotFoundException("");
        } catch (NotFoundException e) {
            log.error("getBalance service error : ".concat(session));
            log.error(e.getLocalizedMessage());
            return "ACCOUNT NOT FOUND";
        } catch (Exception e) {
            log.error("getBalance service error : ".concat(session));
            log.error(e.getLocalizedMessage());
            return "UNEXPECTED ERROR";
        }
    }

    @Override
    @HystrixCommand(fallbackMethod = "transactionFallback", commandProperties = {
            @HystrixProperty(name = "execution.isolation.thread.timeoutInMilliseconds", value = "2000")
    })
    public String deposit(String session, BigDecimal amount) {
        try {
            SessionDto sessionDto = getSessionFromRedis(session);
            Optional<Account> account = findAccount(sessionDto.getAccountDto().getCardNo());
            if (account.isPresent()) {
                BigDecimal newBalance = account.get().getBalance().add(amount);
                account.get().setBalance(newBalance);
                account.get().setModifiedAt(new Date());
                accountRepository.save(account.get());
                return "DEPOSITED_SUCCESSFULLY";
            } else {
                throw new NotFoundException("ACCOUNT_NOT_FOUND");
            }
        } catch (NotFoundException e) {
            log.error("deposit service error : ".concat(session).concat(" ").concat(amount.toPlainString()));
            log.error(e.getLocalizedMessage());
            return "ACCOUNT_NOT_FOUND";
        } catch (Exception e) {
            log.error("deposit service error : ".concat(session).concat(" ").concat(amount.toPlainString()));
            log.error(e.getLocalizedMessage());
            return "UNEXPECTED ERROR";
        }
    }

    @Override
    @HystrixCommand(fallbackMethod = "transactionFallback", commandProperties = {
            @HystrixProperty(name = "execution.isolation.thread.timeoutInMilliseconds", value = "2000")
    })
    public String withdraw(String session, BigDecimal amount) {
        try {
            SessionDto sessionDto = getSessionFromRedis(session);
            Optional<Account> account = findAccount(sessionDto.getAccountDto().getCardNo());
            if (account.isPresent()) {
                if (account.get().getBalance().compareTo(amount) > 0) {
                    BigDecimal newBalance = account.get().getBalance().subtract(amount);
                    account.get().setBalance(newBalance);
                } else {
                    return "NOT_ENOUGH_MONEY";
                }
                account.get().setModifiedAt(new Date());
                accountRepository.save(account.get());
            } else {
                throw new NotFoundException("ACCOUNT_NOT_FOUND");
            }
        } catch (NotFoundException e) {
            log.error("withdraw service error : ".concat(session).concat(" ").concat(amount.toPlainString()));
            log.error(e.getLocalizedMessage());
            return "ACCOUNT_NOT_FOUND";
        } catch (Exception e) {
            log.error("withdraw service error : ".concat(session).concat(" ").concat(amount.toPlainString()));
            log.error(e.getLocalizedMessage());
            return "UNEXPECTED ERROR";
        }
        return null;
    }

    /**
     * @param session
     * @return session data transfer object saved on redis for the input session id.
     */
    private SessionDto getSessionFromRedis(String session) {
        return redisService.findByKey(session, SessionDto.class);
    }

    /**
     * find card number corresponding account
     *
     * @param cardNumber
     * @return account
     */
    private Optional<Account> findAccount(String cardNumber) {
        return accountRepository.findByCardNo(cardNumber);
    }

    private String getBalanceFallback(String session) {
        log.info("getBalanceFallback method called : ".concat(session));
        return "please try again";
    }

    private String transactionFallback(String session, BigDecimal amount) {
        log.info("transactionFallback method called : ".concat(session).concat(" - ").concat(amount.toPlainString()));
        return "please try again";
    }

}
