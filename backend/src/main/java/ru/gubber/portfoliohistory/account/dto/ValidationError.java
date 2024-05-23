package ru.gubber.portfoliohistory.account.dto;

import java.util.List;

public class ValidationError extends BaseResponse {

    public ValidationError(ResponceStatus status, String errorMessage, List<FieldValidationError> responce) {
        super(status, errorMessage, responce);
    }
}
