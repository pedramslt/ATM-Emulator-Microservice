package com.atm.bankservice.dto;

import com.atm.bankservice.model.account.dto.AccountDto;

public class SessionDto {

    private AccountDto accountDto;
    private String sessionId;
    private int loginTryTimes;

    public SessionDto() {
    }

    public AccountDto getAccountDto() {
        return accountDto;
    }

    public void setAccountDto(AccountDto accountDto) {
        this.accountDto = accountDto;
    }

    public String getSessionId() {
        return sessionId;
    }

    public void setSessionId(String sessionId) {
        this.sessionId = sessionId;
    }

    public int getLoginTryTimes() {
        return loginTryTimes;
    }

    public void setLoginTryTimes(int loginTryTimes) {
        this.loginTryTimes = loginTryTimes;
    }
}
