package ru.gubber.portfoliohistory.purchasedasset.model;

import jakarta.persistence.*;

import java.util.UUID;

@Entity
@Table(name = "purchased_assets")
public class PurchasedAsset {
    @Id
    private UUID id;
    private String code;
    @Enumerated(EnumType.STRING)
    @Column(name = "asset_type")
    private AssetType assetType;
    private double amount;
    @Column(name = "purchase_price")
    private double purchasePrice;
    @Column(name = "current_price")
    private double currentPrice;
    @Column(name = "account_id")
    private UUID accountId;

    public PurchasedAsset() {
    }

    public PurchasedAsset(UUID id, String code,
                          AssetType assetType,
                          double amount,
                          double purchasePrice,
                          double currentPrice,
                          UUID accountId) {
        this.id = id;
        this.code = code;
        this.assetType = assetType;
        this.amount = amount;
        this.purchasePrice = purchasePrice;
        this.currentPrice = currentPrice;
        this.accountId = accountId;
    }

    public UUID getId() {
        return id;
    }
    
    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public AssetType getAssetType() {
        return assetType;
    }

    public void setAssetType(AssetType assetType) {
        this.assetType = assetType;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double count) {
        this.amount = count;
    }

    public double getPurchasePrice() {
        return purchasePrice;
    }

    public void setPurchasePrice(double purchasePrice) {
        this.purchasePrice = purchasePrice;
    }

    public double getCurrentPrice() {
        return currentPrice;
    }

    public void setCurrentPrice(double currentPrice) {
        this.currentPrice = currentPrice;
    }

    public UUID getAccountId() {
        return accountId;
    }

    public void setAccountId(UUID accountId) {
        this.accountId = accountId;
    }

    @Override
    public String toString() {
        return "Asset{" +
                "id=" + id +
                ", code='" + code + '\'' +
                ", assetType=" + assetType +
                ", amount=" + amount +
                ", purchasePrice=" + purchasePrice +
                ", currentPrice=" + currentPrice +
                ", accountId=" + accountId +
                '}';
    }
}
