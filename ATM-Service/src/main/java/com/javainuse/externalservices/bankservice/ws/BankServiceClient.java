package com.javainuse.externalservices.bankservice.ws;

import com.javainuse.dto.LoginDto;
import com.javainuse.externalservices.bankservice.dto.AccountDto;
import com.javainuse.externalservices.bankservice.dto.ResponseDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import java.math.BigDecimal;

@Component
@FeignClient(name = "BANK-SERVICE", path = "/api")
public interface BankServiceClient {

    @GetMapping("verify")
    String verify(@RequestParam String session, @RequestParam String cardNumber);

    @PostMapping("verify/authenticate")
    String authenticate(@RequestParam String session, @RequestBody LoginDto loginDto);

    @GetMapping("verify/exit")
    String exit(@RequestParam String session);

    @GetMapping("transactions/")
    String getBalance(@RequestParam String session);

    @PostMapping("transactions/deposit")
    String deposit(@RequestParam String session, @RequestParam BigDecimal amount);

    @PostMapping("transactions/withdraw")
    String withdraw(@RequestParam String session, @RequestParam BigDecimal amount);

    @GetMapping("accounts")
    ResponseDto<AccountDto> getAccountDetails(@RequestParam String session);

}
