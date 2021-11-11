package com.javainuse.service.printer;

import javax.servlet.http.HttpServletRequest;

public interface PrinterService {

    String printBalanceReceipt(HttpServletRequest request);
}
