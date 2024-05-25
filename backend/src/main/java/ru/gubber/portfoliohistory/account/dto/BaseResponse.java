package ru.gubber.portfoliohistory.account.dto;

public class BaseResponse {
    private final ResponseStatus status;
    private final String errorMessage;
    private final Object response;

    public BaseResponse(ResponseStatus status, String errorMessage, Object response) {
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

    public String getErrorMessage() {
        return errorMessage;
    }

    public Object getResponse() {
        return response;
    }
}
