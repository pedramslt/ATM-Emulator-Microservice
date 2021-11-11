package com.atm.bankservice.controller;

import com.atm.bankservice.service.transaction.TransactionService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;

@RestController
@CrossOrigin("*")
@Slf4j
@RequestMapping("/api/transactions")
public class TransactionController {

    @Autowired
    private TransactionService transactionService;

    /**
     * get account balance
     *
     * @param session : request session id
     * @return entered car balance
     */
    @GetMapping("")
    public ResponseEntity<String> getBalance(@RequestParam String session) {
        log.info("getBalance request called");
        return new ResponseEntity<>(transactionService.getBalance(session), HttpStatus.OK);
    }

    /**
     * deposit money to balance
     *
     * @param session : request session id
     * @param amount  : money amount to deposit
     * @return successful or failed massage
     */
    @PostMapping("/deposit")
    public ResponseEntity<String> deposit(@RequestParam String session, @RequestParam BigDecimal amount) {
        log.info("deposit request called");
        return new ResponseEntity<>(transactionService.deposit(session, amount), HttpStatus.OK);
    }

    /**
     * withdraw money from balance
     *
     * @param session : request session id
     * @param amount  : money amount to withdraw
     * @return successful or failed massage
     * @
     */
    @PostMapping("/withdraw")
    public ResponseEntity<String> withdraw(@RequestParam String session, @RequestParam BigDecimal amount) {
        log.info("withdraw request called");
        return new ResponseEntity<>(transactionService.withdraw(session, amount), HttpStatus.OK);
    }
}