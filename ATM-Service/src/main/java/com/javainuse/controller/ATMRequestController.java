package com.javainuse.controller;

import com.javainuse.service.atmservice.ATMService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.math.BigDecimal;

@RestController
@CrossOrigin
@Slf4j
@RequestMapping("/api/account")
public class ATMRequestController {

    @Autowired
    private ATMService atmService;

    /**
     * @param request : servlet request
     * @return entered card number account balance
     * @throws Exception
     */
    @GetMapping("")
    public ResponseEntity<String> getBalance(HttpServletRequest request) {
        log.info("verify request call");
        return atmService.getBalance(request.getRequestedSessionId());
    }

    /**
     * @param request : servlet request
     * @param amount  : money amount to deposit to account.
     * @return successful or failed to deposit entered amount
     * @throws Exception
     */
    @PostMapping("/deposit")
    public ResponseEntity<String> deposit(HttpServletRequest request, @RequestParam BigDecimal amount) {
        log.info("deposit request call");
        return atmService.deposit(request.getRequestedSessionId(), amount);
    }

    /**
     * @param request : servlet request
     * @param amount  : money amount to withdraw from account.
     * @return successful or failed to withdraw entered amount
     * @throws Exception
     */
    @PostMapping("/withdraw")
    public ResponseEntity<String> withdraw(HttpServletRequest request, @RequestParam BigDecimal amount) {
        log.info("withdraw request call");
        return atmService.withdraw(request.getRequestedSessionId(), amount);
    }

    /**
     * logout user and exit card
     *
     * @param request : servlet request
     * @return successful or failed message
     * @throws Exception
     */
    @GetMapping("exit")
    public ResponseEntity<String> exit(HttpServletRequest request) {
        log.info("exit request call");
        return atmService.exit(request);
    }

}