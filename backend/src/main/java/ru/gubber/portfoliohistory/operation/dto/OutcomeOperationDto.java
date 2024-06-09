package ru.gubber.portfoliohistory.operation.dto;

import ru.gubber.portfoliohistory.common.dto.BaseResponse;
import ru.gubber.portfoliohistory.common.dto.ResponseId;
import ru.gubber.portfoliohistory.common.dto.ResponseStatus;

public class OutcomeOperationDto extends BaseResponse {
    public OutcomeOperationDto(ResponseId response) {
        super(ResponseStatus.SUCCESS, null, response);
    }
}
