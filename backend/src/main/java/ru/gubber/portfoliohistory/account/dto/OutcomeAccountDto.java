package ru.gubber.portfoliohistory.account.dto;

import java.util.List;

public class OutcomeAccountDto extends BaseResponce {
    public OutcomeAccountDto(List<AccountDto> responce) {
        super(ResponceStatus.SUCCESS, null, responce);
    }
}
