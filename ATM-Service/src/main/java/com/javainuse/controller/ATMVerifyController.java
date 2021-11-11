package com.javainuse.controller;

import com.javainuse.dto.LoginDto;
import com.javainuse.externalservices.bankservice.service.BankService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@RestController
@CrossOrigin
@RequestMapping("/api/verify")
public class ATMVerifyController {

    @Autowired
    private BankService bankService;

    /**
     * verify user and open a session if entered card number is valid
     *
     * @param request    : servlet request
     * @param cardNumber : user card number
     * @return true for successful and false for failed
     * @throws Exception
     */
    @GetMapping("")
    public ResponseEntity<String> verify(HttpServletRequest request, @RequestParam String cardNumber) throws Exception {
        return new ResponseEntity<>(bankService.verify(request.getSession().getId(), cardNumber), HttpStatus.OK);
    }

    /**
     * checks if user entered pin or finger print is correct
     *
     * @param request  : servlet request
     * @param loginDto
     * @return proper massage
     * @throws Exception
     */
    @PostMapping("authenticate")
    public ResponseEntity<String> authenticate(HttpServletRequest request, @RequestBody LoginDto loginDto) throws Exception {
        return new ResponseEntity<>(bankService.authenticate(request.getRequestedSessionId(), loginDto), HttpStatus.OK);
    }
}