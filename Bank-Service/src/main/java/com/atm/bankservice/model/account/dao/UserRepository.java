package com.atm.bankservice.model.account.dao;

import com.atm.bankservice.model.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {

    User findByNationalCode(String nationalCode);
}
