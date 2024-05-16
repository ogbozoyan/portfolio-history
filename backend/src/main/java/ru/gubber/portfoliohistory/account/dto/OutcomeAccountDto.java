package ru.gubber.portfoliohistory.account.dto;

public class OutcomeAccountDto extends BaseResponce {

    public OutcomeAccountDto(ResponceId responce) {
        super(ResponceStatus.SUCCESS, null, responce);
    }
}