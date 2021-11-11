package com.javainuse.externalservices.bankservice.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/**
 * User data transfer object
 */

@JsonIgnoreProperties(ignoreUnknown = true)
public class UserDto {

    private int id;
    private String firstName;
    private String lastName;
    private String mobileNumber;
    /**
     * user finger print hashed string
     */
    private String fingerPrint;
    private String nationalCode;

    public UserDto() {
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


}
