package ru.gubber.portfoliohistory.purchasedasset.dto;

import ru.gubber.portfoliohistory.purchasedasset.model.PurchasedAsset;

public class PurchasedAssetMapper {
    public PurchasedAssetFullDto toPurchasedAssetFullDto(PurchasedAsset purchasedAsset) {
        return new PurchasedAssetFullDto(
                purchasedAsset.getCode(),
                purchasedAsset.getAssetType().name(),
                purchasedAsset.getAmount(),
                purchasedAsset.getPurchasePrice(),
                purchasedAsset.getCurrentPrice()
        );
    }
}
