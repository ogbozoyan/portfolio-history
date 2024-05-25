package ru.gubber.portfoliohistory.account.dto;

public class IdOutcomeAccountDto extends BaseResponse {

    public IdOutcomeAccountDto(ResponseId responce) {
        super(ResponseStatus.SUCCESS, null, responce);
    }


}