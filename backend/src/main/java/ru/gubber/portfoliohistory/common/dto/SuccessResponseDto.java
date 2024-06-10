package ru.gubber.portfoliohistory.common.dto;

public class SuccessResponseDto<T> extends BaseResponse {
    public SuccessResponseDto(T response) {
        super(ResponseStatus.SUCCESS, null, response);
    }
}
