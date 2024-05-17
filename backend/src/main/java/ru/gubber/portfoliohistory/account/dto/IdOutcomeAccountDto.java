package ru.gubber.portfoliohistory.account.dto;

public class IdOutcomeAccountDto extends BaseResponce {

    public IdOutcomeAccountDto(ResponceId responce) {
        super(ResponceStatus.SUCCESS, null, responce);
    }
}