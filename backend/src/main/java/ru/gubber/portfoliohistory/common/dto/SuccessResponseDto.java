package ru.gubber.portfoliohistory.common.dto;

import ru.gubber.portfoliohistory.account.dto.BaseResponse;
import ru.gubber.portfoliohistory.account.dto.ResponseStatus;

public class SuccessResponseDto<T> extends BaseResponse {
    public SuccessResponseDto(T response) {
        super(ResponseStatus.SUCCESS, null, response);
    }
}
