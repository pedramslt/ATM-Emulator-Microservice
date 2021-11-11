package com.javainuse.externalservices.bankservice.service;

import com.javainuse.dto.LoginDto;
import com.javainuse.externalservices.bankservice.dto.AccountDto;
import com.javainuse.externalservices.bankservice.dto.ResponseDto;
import com.javainuse.externalservices.bankservice.ws.BankServiceClient;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
@Slf4j
public class BankServiceImpl implements BankService {

    @Autowired
    BankServiceClient bankServiceClient;

    @Override
    public String verify(String session, String cardNumber) {
        log.info("bank verify service called for : ".concat(cardNumber));
        return bankServiceClient.verify(session, cardNumber);
    }

    @Override
    public String authenticate(String session, LoginDto loginDto) {
        log.info("bank authenticate service called for : ".concat(loginDto.getCardNumber()));
        return bankServiceClient.authenticate(session, loginDto);
    }

    @Override
    public String exit(String session) {
        log.info("bank exit service called for : ".concat(session));
        return bankServiceClient.exit(session);
    }

    @Override
    public String getBalance(String session) {
        log.info("bank getBalance service called for : ".concat(session));
        return bankServiceClient.getBalance(session);
    }

    @Override
    public String deposit(String session, BigDecimal amount) {
        log.info("bank deposit service called for : ".concat(session).concat(" - amount : ").concat(amount.toPlainString()));
        return bankServiceClient.deposit(session, amount);
    }

    @Override
    public String withdraw(String session, BigDecimal amount) {
        log.info("bank withdraw service called for : ".concat(session).concat(" - amount : ").concat(amount.toPlainString()));
        return bankServiceClient.withdraw(session, amount);
    }

    @Override
    public ResponseDto<AccountDto> getAccountDetails(String session) {
        log.info("bank getAccountDetails service called for : ".concat(session));
        return bankServiceClient.getAccountDetails(session);
    }

}
