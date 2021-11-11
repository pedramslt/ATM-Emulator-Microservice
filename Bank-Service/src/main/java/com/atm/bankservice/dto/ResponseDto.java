package com.atm.bankservice.dto;

/**
 * general service response;
 *
 * @param <T>
 */
public class ResponseDto<T> {

    private String errorMessage;
    boolean success;
    private T data;

    public String getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }
}
