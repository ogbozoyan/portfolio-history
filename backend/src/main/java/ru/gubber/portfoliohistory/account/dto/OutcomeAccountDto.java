package ru.gubber.portfoliohistory.account.dto;

import java.util.List;

public class OutcomeAccountDto extends BaseResponse {
    public OutcomeAccountDto(List<AccountDto> response) {
        super(ResponseStatus.SUCCESS, null, response);
    }
}
