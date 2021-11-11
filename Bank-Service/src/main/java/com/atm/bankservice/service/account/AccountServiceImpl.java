package com.atm.bankservice.service.account;

import com.atm.bankservice.dto.ResponseDto;
import com.atm.bankservice.dto.SessionDto;
import com.atm.bankservice.model.account.dto.AccountDto;
import com.atm.bankservice.model.account.entity.Account;
import com.atm.bankservice.model.user.dao.AccountRepository;
import com.atm.bankservice.service.redis.RedisService;
import javassist.NotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.nio.file.AccessDeniedException;
import java.util.Objects;
import java.util.Optional;


@Service
@Slf4j
public class AccountServiceImpl implements AccountService {

    @Autowired
    AccountRepository accountRepository;
    @Autowired
    RedisService<SessionDto> redisService;


    @Override
    public ResponseDto<AccountDto> getAccount(String session) {
        ResponseDto<AccountDto> responseDto = new ResponseDto<>();
        try {
            SessionDto sessionDto = redisService.findByKey(session, SessionDto.class);
            if (Objects.isNull(sessionDto)) {
                throw new AccessDeniedException("");
            }
            Optional<Account> account = accountRepository.findByCardNo(sessionDto.getAccountDto().getCardNo());
            if (!account.isPresent()) {
                throw new NotFoundException("");
            } else {
                responseDto.setData(account.get().toDto());
                responseDto.setSuccess(true);
                responseDto.setErrorMessage("");
                return responseDto;
            }
        } catch (AccessDeniedException e) {
            log.error("getAccount service error : ACCESS_DENIED ".concat(session));
            log.error(e.getLocalizedMessage());
            return returnException("ACCESS_DENIED");
        } catch (NotFoundException e) {
            log.error("getAccount service error : ACCOUNT_NOT_FOUND ".concat(session));
            log.error(e.getLocalizedMessage());
            return returnException("ACCOUNT_NOT_FOUND");
        } catch (Exception e) {
            log.error("getAccount service error : UNEXPECTED_ERROR ".concat(session));
            log.error(e.getLocalizedMessage());
            return returnException("UNEXPECTED_ERROR");
        }
    }

    private ResponseDto<AccountDto> returnException(String massage) {
        ResponseDto<AccountDto> responseDto = new ResponseDto<>();
        responseDto.setData(new AccountDto());
        responseDto.setSuccess(false);
        responseDto.setErrorMessage(massage);
        return responseDto;
    }
}
