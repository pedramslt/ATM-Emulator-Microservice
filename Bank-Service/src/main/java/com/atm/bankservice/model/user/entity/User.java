package com.atm.bankservice.model.user.entity;

import com.atm.bankservice.model.account.entity.Account;
import com.atm.bankservice.model.user.dto.UserDto;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "USER")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "userSeqGen")
    @SequenceGenerator(name = "userSeqGen", sequenceName = "USER_SEQ_GEN", allocationSize = 1)
    private int id;

    private String firstName;

    private String lastName;

    private String mobileNumber;

    private String fingerPrint;

    @Column(nullable = false, name = "NATIONAL_CODE", unique = true)
    private String nationalCode;

    @OneToMany(fetch = FetchType.LAZY)
    private List<Account> accountList = new ArrayList<>();

    public User() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getMobileNumber() {
        return mobileNumber;
    }

    public void setMobileNumber(String mobileNumber) {
        this.mobileNumber = mobileNumber;
    }

    public List<Account> getAccountList() {
        return accountList;
    }

    public void setAccountList(List<Account> accountList) {
        this.accountList = accountList;
    }

    public String getFingerPrint() {
        return fingerPrint;
    }

    public void setFingerPrint(String fingerPrint) {
        this.fingerPrint = fingerPrint;
    }

    public String getNationalCode() {
        return nationalCode;
    }

    public void setNationalCode(String nationalCode) {
        this.nationalCode = nationalCode;
    }

    public UserDto toDto() {

        UserDto userDto = new UserDto();
        userDto.setFirstName(firstName);
        userDto.setLastName(lastName);
        userDto.setId(id);
        userDto.setNationalCode(nationalCode);
        userDto.setFingerPrint(fingerPrint);

        return userDto;
    }
}
