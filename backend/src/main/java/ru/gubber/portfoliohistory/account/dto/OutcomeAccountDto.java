package ru.gubber.portfoliohistory.account.dto;

public class OutcomeAccountDto extends BaseResponce {

    public OutcomeAccountDto(String responce) {
        super(ResponceStatus.SUCCESS, null, responce);
    }
}