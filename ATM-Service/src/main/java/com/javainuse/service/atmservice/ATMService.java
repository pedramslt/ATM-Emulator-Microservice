package com.javainuse.service.atmservice;

import org.springframework.http.ResponseEntity;

import javax.servlet.http.HttpServletRequest;
import java.math.BigDecimal;

public interface ATMService {

    ResponseEntity<String> getBalance(String session);

    ResponseEntity<String> deposit(String session, BigDecimal amount);

    ResponseEntity<String> withdraw(String session, BigDecimal amount);

    ResponseEntity<String> exit(HttpServletRequest request);
}
