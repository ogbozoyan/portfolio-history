package ru.gubber.portfoliohistory.operation.dto;

import ru.gubber.portfoliohistory.account.dto.BaseResponse;
import ru.gubber.portfoliohistory.account.dto.ResponseStatus;

public class OutcomeOperationDto extends BaseResponse {
    public OutcomeOperationDto(ResultOperationId  response) {
        super(ResponseStatus.SUCCESS, null, response);
    }
}
