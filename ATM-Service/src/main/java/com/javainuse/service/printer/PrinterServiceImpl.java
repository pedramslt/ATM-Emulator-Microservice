package com.javainuse.service.printer;

import com.javainuse.externalservices.bankservice.dto.AccountDto;
import com.javainuse.externalservices.bankservice.dto.ResponseDto;
import com.javainuse.externalservices.bankservice.service.BankService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.UnavailableException;
import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.Objects;


@Service
@Slf4j
public class PrinterServiceImpl implements PrinterService {

    @Autowired
    BankService bankService;

    @Override
    public String printBalanceReceipt(HttpServletRequest request) {
        String serviceErrorMassage = "printBalanceReceipt service error : ";
        try {
            ResponseDto<AccountDto> responseDto = bankService.getAccountDetails(request.getSession().getId());
            if (Objects.nonNull(responseDto)) {
                if (responseDto.isSuccess()) {
                    AccountDto accountDto = responseDto.getData();
                    String amount = accountDto.getBalance().toPlainString();
                    String today = new Date().toString();
                    log.info("print ".concat(accountDto.getAccountNo()).concat(" ").concat("balance successfully"));
                    return today.concat(" ").concat("account balance is : ").concat(amount);
                } else {
                    log.info(serviceErrorMassage.concat(responseDto.getErrorMessage()));
                    return responseDto.getErrorMessage();
                }
            } else throw new UnavailableException("");
        } catch (UnavailableException e) {
            log.error(serviceErrorMassage.concat(e.getMessage()));
            return "SERVICE_TEMPORARY_UNAVAILABLE";
        } catch (Exception e) {
            log.error(serviceErrorMassage.concat(e.getMessage()));
            return "UNEXPECTED ERROR HAPPENED";
        }
    }

}
