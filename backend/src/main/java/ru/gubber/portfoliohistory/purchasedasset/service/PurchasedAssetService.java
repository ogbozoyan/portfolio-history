package ru.gubber.portfoliohistory.purchasedasset.service;

import ru.gubber.portfoliohistory.purchasedasset.dto.PurchasedAssetFullDto;
import ru.gubber.portfoliohistory.purchasedasset.model.PurchasedAsset;

import java.util.List;
import java.util.UUID;

public interface PurchasedAssetService {
    void purchaseAsset(UUID accountId, String code, Double amount);

    boolean sellAsset(UUID accountId, String code, Double amount);

    List<PurchasedAsset> getPurchasedAssetsList(UUID accountId);
}
