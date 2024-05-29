package ru.gubber.portfoliohistory.asset.service;

import java.util.UUID;

public interface PurchasedAssetService {
    void purchaseAsset(UUID accountId, String code, Double amount);
}
