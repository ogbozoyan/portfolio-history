package ru.gubber.portfoliohistory.account.dto;

public class BaseResponce {
    private final ResponceStatus status;
    private String errorMessage;
    private Object responce;

    public BaseResponce(ResponceStatus status, String errorMessage, Object responce) {
        this.status = status;
        this.errorMessage = errorMessage;
        this.responce = responce;
    }

    @Override
    public String toString() {
        return "BaseResponce{" +
                "status=" + status +
                ", errorMessage='" + errorMessage + '\'' +
                ", responce=" + responce +
                '}';
    }

    public ResponceStatus getStatus() {
        return status;
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
