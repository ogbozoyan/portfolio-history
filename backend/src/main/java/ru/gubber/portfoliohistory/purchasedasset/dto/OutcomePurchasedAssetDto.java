package ru.gubber.portfoliohistory.purchasedasset.dto;

import ru.gubber.portfoliohistory.account.dto.BaseResponse;
import ru.gubber.portfoliohistory.account.dto.ResponseStatus;

import java.util.List;

public class OutcomePurchasedAssetDto extends BaseResponse {
    public OutcomePurchasedAssetDto(List<PurchasedAssetFullDto> response) {
        super(ResponseStatus.SUCCESS, null, response);
    }
}
