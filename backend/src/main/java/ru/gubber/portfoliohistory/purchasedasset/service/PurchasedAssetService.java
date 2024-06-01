package ru.gubber.portfoliohistory.purchasedasset.service;

import java.util.UUID;

public interface PurchasedAssetService {
    void purchaseAsset(UUID accountId, String code, Double amount);
}
