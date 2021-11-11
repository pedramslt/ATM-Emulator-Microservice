package com.atm.bankservice.mysql;

import com.atm.bankservice.model.account.dao.UserRepository;
import com.atm.bankservice.model.account.entity.Account;
import com.atm.bankservice.model.user.dao.AccountRepository;
import com.atm.bankservice.model.user.entity.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;


@SpringBootTest
class MySqlConTests {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    AccountRepository accountRepository;

    @Autowired
    PasswordEncoder passwordEncoder;

    private final String nationalCode = "123456789";
    private final String fingerPrint = "unit-test";
    private final String cardNumber = "1234-1234-1234-1234";
    private final String pin = "1234";

    @BeforeEach
    public void setUp() {
        deleteIfExist();
        User user = saveUser();
        Account account = saveAccount(user);
        updateUser(user, account);
    }

    @Test
    void testCon() {

        User user = userRepository.findByNationalCode(nationalCode);
        assertNotNull(user);
        assertNotNull(user.getFirstName());
        assertNotNull(user.getLastName());
        assertNotNull(user.getMobileNumber());
        assertNotNull(user.getFirstName());
        assertTrue(passwordEncoder.matches(fingerPrint, user.getFingerPrint()));
        assertEquals(user.getNationalCode(), nationalCode);

        Optional<Account> account = accountRepository.findByCardNo(cardNumber);
        assertTrue(account.isPresent());
        assertNotNull(account.get());
        assertEquals(account.get().getUser().getId(), user.getId());
        assertTrue(passwordEncoder.matches(pin, account.get().getPin()));
        assertTrue(account.get().isActive());
    }

    private User saveUser() {
        User user = new User();
        user.setFirstName("unit");
        user.setLastName("test");
        user.setMobileNumber("001858-85-858");
        user.setNationalCode(nationalCode);
        user.setFingerPrint(passwordEncoder.encode(fingerPrint));
        user.setNationalCode(nationalCode);

        userRepository.save(user);
        return user;
    }

    private Account saveAccount(User user) {
        Account account = new Account();
        account.setPin(passwordEncoder.encode(pin));
        account.setAccountNo("123456789-123456789");
        account.setCardNo(cardNumber);
        account.setBalance(new BigDecimal(BigInteger.TEN));
        account.setActive(true);
        account.setCreatedAt(new Date());
        account.setUser(user);

        accountRepository.save(account);
        return account;
    }

    private void updateUser(User user, Account account) {
        List<Account> accounts = new ArrayList<>(Collections.singletonList(account));
        user.setAccountList(accounts);

        userRepository.save(user);
    }

    private void deleteIfExist() {
        Optional<Account> account = accountRepository.findByCardNo(cardNumber);
        account.ifPresent(value -> accountRepository.delete(value));
        User user = userRepository.findByNationalCode(nationalCode);
        if (Objects.nonNull(user)) userRepository.delete(user);
    }

}
