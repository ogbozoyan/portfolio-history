package ru.gubber.portfoliohistory.account.dto;

public class BaseResponse {
    private final ResponceStatus status;
    private String errorMessage;
    private Object response;

    public BaseResponse(ResponceStatus status, String errorMessage, Object response) {
        this.status = status;
        this.errorMessage = errorMessage;
        this.response = response;
    }

    @Override
    public String toString() {
        return "BaseResponse{" +
                "status=" + status +
                ", errorMessage='" + errorMessage + '\'' +
                ", response=" + response +
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

    public Object getResponse() {
        return response;
    }

    public void setResponse(Object response) {
        this.response = response;
    }
}
