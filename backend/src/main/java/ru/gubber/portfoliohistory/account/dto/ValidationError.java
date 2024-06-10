package ru.gubber.portfoliohistory.account.dto;

import ru.gubber.portfoliohistory.common.dto.BaseResponse;
import ru.gubber.portfoliohistory.common.dto.ResponseStatus;
import ru.gubber.portfoliohistory.common.utils.FieldValidationError;

import java.util.List;

public class ValidationError extends BaseResponse {

    public ValidationError(ResponseStatus status, String errorMessage, List<FieldValidationError> response) {
        super(status, errorMessage, response);
    }
}
