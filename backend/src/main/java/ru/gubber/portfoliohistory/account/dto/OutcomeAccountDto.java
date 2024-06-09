package ru.gubber.portfoliohistory.account.dto;

import ru.gubber.portfoliohistory.common.dto.BaseResponse;
import ru.gubber.portfoliohistory.common.dto.ResponseStatus;

import java.util.List;

public class OutcomeAccountDto extends BaseResponse {
    public OutcomeAccountDto(List<AccountDto> response) {
        super(ResponseStatus.SUCCESS, null, response);
    }
}
