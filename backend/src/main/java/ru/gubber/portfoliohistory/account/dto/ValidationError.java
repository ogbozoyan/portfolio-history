package ru.gubber.portfoliohistory.account.dto;

import ru.gubber.portfoliohistory.common.utils.FieldValidationError;

import java.util.List;

public class ValidationError extends BaseResponce {

    public ValidationError(ResponceStatus status, String errorMessage, List<FieldValidationError> responce) {
        super(status, errorMessage, responce);
    }
}
