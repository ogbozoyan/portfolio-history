package ru.gubber.portfoliohistory.account.dto;

public class IdOutcomeAccountDto extends BaseResponse {

    public IdOutcomeAccountDto(ResponseId response) {
        super(ResponseStatus.SUCCESS, null, response);
    }


}