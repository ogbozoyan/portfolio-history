package ru.gubber.portfoliohistory.account.dto;

public class IdOutcomeAccountDto extends BaseResponse {

    public IdOutcomeAccountDto(ResponceId responce) {
        super(ResponceStatus.SUCCESS, null, responce);
    }


}