package com.javainuse.controller;

import com.javainuse.service.printer.PrinterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

@RestController
@CrossOrigin
@RequestMapping("/api/receipts")
public class PrintRequestController {

    @Autowired
    private PrinterService printerService;

    /**
     * @param request : servlet request
     * @return get balance printing text
     * @throws Exception
     */
    @GetMapping("balance")
    public ResponseEntity<String> printGetBalanceReceipt(HttpServletRequest request) {
        return new ResponseEntity<>(printerService.printBalanceReceipt(request), HttpStatus.OK);
    }
}