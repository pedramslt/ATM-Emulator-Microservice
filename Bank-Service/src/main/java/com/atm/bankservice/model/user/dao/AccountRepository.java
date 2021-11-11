package com.atm.bankservice.model.user.dao;

import com.atm.bankservice.model.account.entity.Account;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AccountRepository extends JpaRepository<Account, Integer> {

    Optional<Account> findByCardNo(String cardNo);
}
