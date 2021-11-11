package com.atm.bankservice.service.account;

import com.atm.bankservice.dto.ResponseDto;
import com.atm.bankservice.model.account.dto.AccountDto;


public interface AccountService {

    /**
     * @param session : String  request session id
     * @return entered card account data
     * @throws Exception
     */
    ResponseDto<AccountDto> getAccount(String session) throws Exception;
}
