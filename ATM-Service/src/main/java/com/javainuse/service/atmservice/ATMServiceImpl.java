package com.javainuse.service.atmservice;

import com.javainuse.externalservices.bankservice.service.BankService;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixProperty;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.math.BigDecimal;

@Service
public class ATMServiceImpl implements ATMService {

    @Autowired
    private BankService bankService;

    @Override
    @HystrixCommand(fallbackMethod = "getBalanceFallback", commandProperties = {
            @HystrixProperty(name = "execution.isolation.thread.timeoutInMilliseconds", value = "2000")
    })
    public ResponseEntity<String> getBalance(String session) {
        return new ResponseEntity<>(bankService.getBalance(session), HttpStatus.OK);
    }

    @Override
    @HystrixCommand(fallbackMethod = "transactionFallback", commandProperties = {
            @HystrixProperty(name = "execution.isolation.thread.timeoutInMilliseconds", value = "2000")
    })
    public ResponseEntity<String> deposit(String session, BigDecimal amount) {
        return new ResponseEntity<>(bankService.deposit(session, amount), HttpStatus.OK);
    }

    @Override
    @HystrixCommand(fallbackMethod = "transactionFallback", commandProperties = {
            @HystrixProperty(name = "execution.isolation.thread.timeoutInMilliseconds", value = "2000")
    })
    public ResponseEntity<String> withdraw(String session, BigDecimal amount) {
        return new ResponseEntity<>(bankService.withdraw(session, amount), HttpStatus.OK);
    }

    @Override
    @HystrixCommand(fallbackMethod = "exitFallback", commandProperties = {
            @HystrixProperty(name = "execution.isolation.thread.timeoutInMilliseconds", value = "2000")
    })
    public ResponseEntity<String> exit(HttpServletRequest request) {
        try {
            String response = bankService.exit(request.getSession().getId());
            request.getSession().invalidate();
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>("PLEASE TRY AGAIN", HttpStatus.OK);
        }
    }

    private ResponseEntity<String> getBalanceFallback(String session) {
        return new ResponseEntity<>("please try again", HttpStatus.REQUEST_TIMEOUT);
    }

    private ResponseEntity<String> exitFallback(HttpServletRequest request) {
        return new ResponseEntity<>("please try again", HttpStatus.REQUEST_TIMEOUT);
    }

    private ResponseEntity<String> transactionFallback(String session, BigDecimal amount) {
        return new ResponseEntity<>("please try again", HttpStatus.REQUEST_TIMEOUT);
    }
}
