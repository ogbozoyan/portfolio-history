package ru.gubber.portfoliohistory.account.dto;

import ru.gubber.portfoliohistory.common.dto.BaseResponse;
import ru.gubber.portfoliohistory.common.dto.ResponseId;
import ru.gubber.portfoliohistory.common.dto.ResponseStatus;

public class IdOutcomeAccountDto extends BaseResponse {

    public IdOutcomeAccountDto(ResponseId response) {
        super(ResponseStatus.SUCCESS, null, response);
    }


}