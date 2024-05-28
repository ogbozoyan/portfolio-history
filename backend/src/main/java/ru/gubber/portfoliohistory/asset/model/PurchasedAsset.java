package ru.gubber.portfoliohistory.asset.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

import java.util.UUID;

@Entity
@Table(name = "purchased_assets")
public class PurchasedAsset {
    @Id
    private UUID id;
    private String code;
    private AssetType assetType;
    private double count;
    private double purchasePrice;
    private double currentPrice;

    public PurchasedAsset() {
    }

    public PurchasedAsset(UUID id, String code,
                          AssetType assetType,
                          double count,
                          double purchasePrice,
                          double currentPrice) {
        this.id = id;
        this.code = code;
        this.assetType = assetType;
        this.count = count;
        this.purchasePrice = purchasePrice;
        this.currentPrice = currentPrice;
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

    public double getCount() {
        return count;
    }

    public void setCount(double count) {
        this.count = count;
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

    @Override
    public String toString() {
        return "Asset{" +
                "id=" + id +
                ", code='" + code + '\'' +
                ", assetType=" + assetType +
                ", count=" + count +
                ", purchasePrice=" + purchasePrice +
                ", currentPrice=" + currentPrice +
                '}';
    }
}
