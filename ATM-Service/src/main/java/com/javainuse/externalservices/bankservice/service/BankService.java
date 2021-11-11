package com.javainuse.externalservices.bankservice.service;

import com.javainuse.dto.LoginDto;
import com.javainuse.externalservices.bankservice.dto.AccountDto;
import com.javainuse.externalservices.bankservice.dto.ResponseDto;

import java.math.BigDecimal;

public interface BankService {

    String verify(String session, String cardNumber);

    String authenticate(String session, LoginDto loginDto);

    String exit(String session);

    String getBalance(String session);

    String deposit(String session, BigDecimal amount);

    String withdraw(String session, BigDecimal amount);

    ResponseDto<AccountDto> getAccountDetails(String session);
}
