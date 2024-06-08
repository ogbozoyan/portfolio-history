package ru.gubber.portfoliohistory.purchasedasset.dto;

public record PurchasedAssetFullDto(
        String code,
        String assetType,
        double amount,
        double purchasePrice,
        double currentPrice
) {}
