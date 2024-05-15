package ru.gubber.portfoliohistory.account.dto;

public class BaseResponce {
    String status;
    String errorMessage;
    Object responce;

    public BaseResponce(String status, String errorMessage, Object responce) {
        this.status = status;
        this.errorMessage = errorMessage;
        this.responce = responce;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }

    public Object getResponce() {
        return responce;
    }

    public void setResponce(Object responce) {
        this.responce = responce;
    }
}
