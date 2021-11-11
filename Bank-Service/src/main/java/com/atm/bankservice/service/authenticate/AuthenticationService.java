package com.atm.bankservice.service.authenticate;

import com.atm.bankservice.model.account.dto.LoginDto;

public interface AuthenticationService {

    /**
     * checks if card number is valid or not
     *
     * @param session
     * @param cardNumber
     * @return true of false
     */
    Boolean verifyAccount(String session, String cardNumber);

    /**
     * checks if user credentials is correct or not.
     *
     * @param session
     * @param loginDto
     * @return successful or failed massage.
     * @throws Exception
     */
    String authenticate(String session, LoginDto loginDto);

    /**
     * delete the session user was working.
     *
     * @param session
     * @return successful or failed massage.
     */
    String deleteSession(String session);
}
