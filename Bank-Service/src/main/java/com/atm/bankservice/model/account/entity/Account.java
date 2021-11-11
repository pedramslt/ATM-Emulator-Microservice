package com.atm.bankservice.model.account.entity;

import com.atm.bankservice.model.account.dto.AccountDto;
import com.atm.bankservice.model.user.entity.User;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Date;

@Entity
@Table(name = "ACCOUNT")
public class Account {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "accountSeqGen")
    @SequenceGenerator(name = "accountSeqGen", sequenceName = "ACCOUNT_SEQ_GEN", allocationSize = 1)
    private int id;

    private String pin;

    @Column(name = "ACCOUNT_NUMBER", unique = true)
    private String accountNo;

    @Column(name = "CARD_NUMBER", unique = true)
    private String cardNo;

    private BigDecimal balance;

    private boolean active;

    @ManyToOne(cascade = CascadeType.REMOVE)
    private User user;

    @Column(name = "CREATED_AT")
    private Date createdAt;

    @Column(name = "MODIFIED_AT")
    private Date modifiedAt;

    public Account() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getAccountNo() {
        return accountNo;
    }

    public void setAccountNo(String accountNo) {
        this.accountNo = accountNo;
    }

    public String getCardNo() {
        return cardNo;
    }

    public void setCardNo(String cardNo) {
        this.cardNo = cardNo;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public String getPin() {
        return pin;
    }

    public void setPin(String pin) {
        this.pin = pin;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    public Date getModifiedAt() {
        return modifiedAt;
    }

    public void setModifiedAt(Date modifiedAt) {
        this.modifiedAt = modifiedAt;
    }

    public BigDecimal getBalance() {
        return balance;
    }

    public void setBalance(BigDecimal balance) {
        this.balance = balance;
    }

    public AccountDto toDto() {

        AccountDto accountDto = new AccountDto();

        accountDto.setCardNo(cardNo);
        accountDto.setAccountNo(accountNo);
        accountDto.setActive(active);
        accountDto.setId(id);
        accountDto.setBalance(balance);
        accountDto.setUserDto(user.toDto());

        return accountDto;
    }
}
