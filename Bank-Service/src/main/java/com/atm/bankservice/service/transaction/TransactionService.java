package com.atm.bankservice.service.transaction;

import java.math.BigDecimal;

public interface TransactionService {

    /**
     * get entered card balance.
     *
     * @param session : String request session id
     * @return account balance
     */
    String getBalance(String session);

    /**
     * deposits money into an account.
     *
     * @param session : String request session id
     * @param amount  : money amount to deposit
     * @return successful or fail massage.
     */
    String deposit(String session, BigDecimal amount);

    /**
     * withdraws money from an account.
     *
     * @param session : String request session id.
     * @param amount  : money amount to withdraw from account.
     * @return successful or fail massage.
     * @
     */
    String withdraw(String session, BigDecimal amount);
}
