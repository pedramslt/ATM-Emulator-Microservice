package com.atm.bankservice.controller;

import com.atm.bankservice.model.account.dto.LoginDto;
import com.atm.bankservice.service.authenticate.AuthenticationService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin
@Slf4j
@RequestMapping("/api/verify")
public class VerifyController {

    @Autowired
    AuthenticationService authenticationService;

    /**
     * verify user and open a session if the card entered is valid
     *
     * @param session    : request session id
     * @param cardNumber : user car number
     * @return true for successful and false for failed
     */
    @GetMapping("")
    public Boolean verifyCardNumber(@RequestParam String session, @RequestParam String cardNumber) {
        log.info("verifyCardNumber request called for : ".concat(cardNumber));
        return authenticationService.verifyAccount(session, cardNumber);
    }

    /**
     * checks if user entered pin or finger print is valid
     *
     * @param session  : request session id
     * @param loginDto : required login fields
     * @return successful or failed massage
     */
    @PostMapping("authenticate")
    public String authenticate(@RequestParam String session, @RequestBody LoginDto loginDto) {
        log.info("authenticate request called for : ".concat(loginDto.getCardNumber()));
        return authenticationService.authenticate(session, loginDto);
    }

    /**
     * close users session and allow atm to return the user card
     *
     * @param session : request session id
     * @return successful or failed massage
     */
    @GetMapping("exit")
    public String getCard(@RequestParam String session) {
        log.info("getCard request called for : ".concat(session));
        return authenticationService.deleteSession(session);
    }


}