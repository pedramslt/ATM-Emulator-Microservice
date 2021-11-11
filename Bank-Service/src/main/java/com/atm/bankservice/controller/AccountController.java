package com.atm.bankservice.controller;

import com.atm.bankservice.dto.ResponseDto;
import com.atm.bankservice.model.account.dto.AccountDto;
import com.atm.bankservice.service.account.AccountService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin
@Slf4j
@RequestMapping("/api/accounts")
public class AccountController {

    @Autowired
    AccountService accountService;

    /**
     * get account details
     *
     * @param session
     * @return account data transfer object
     * @throws Exception
     */
    @GetMapping("")
    public ResponseEntity<ResponseDto<AccountDto>> getAccount(@RequestParam String session) throws Exception {
        log.info("getAccount request called");
        return new ResponseEntity<>(accountService.getAccount(session), HttpStatus.OK);
    }

}
