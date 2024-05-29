package ru.gubber.portfoliohistory.operation.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "operations")
public class Operation {
    @Id
    private UUID id;
    private LocalDateTime creatTime;
    private UUID accountId;
    private String assetCode;
    private OperationType operationType;
    private double count;
    private double unitPrice;

    public Operation() {
    }

    public Operation(UUID id,
                     LocalDateTime creatTime,
                     UUID accountId,
                     String assetCode,
                     OperationType operationType,
                     double count,
                     double unitPrice) {
        this.id = id;
        this.creatTime = creatTime;
        this.accountId = accountId;
        this.assetCode = assetCode;
        this.operationType = operationType;
        this.count = count;
        this.unitPrice = unitPrice;
    }

    public UUID getId() {
        return id;
    }

    public LocalDateTime getCreatTime() {
        return creatTime;
    }

    public void setCreatTime(LocalDateTime creatTime) {
        this.creatTime = creatTime;
    }

    public UUID getAccountId() {
        return accountId;
    }

    public void setAccountId(UUID accountId) {
        this.accountId = accountId;
    }

    public String getAssetCode() {
        return assetCode;
    }

    public void setAssetCode(String assetCode) {
        this.assetCode = assetCode;
    }

    public OperationType getOperationType() {
        return operationType;
    }

    public void setOperationType(OperationType operationType) {
        this.operationType = operationType;
    }

    public double getCount() {
        return count;
    }

    public void setCount(double count) {
        this.count = count;
    }

    public double getUnitPrice() {
        return unitPrice;
    }

    public void setUnitPrice(double unitPrice) {
        this.unitPrice = unitPrice;
    }

    @Override
    public String toString() {
        return "Operation{" +
                "id=" + id +
                ", creatTime=" + creatTime +
                ", accountId=" + accountId +
                ", assetCode='" + assetCode + '\'' +
                ", operationType=" + operationType +
                ", count=" + count +
                ", unitPrice=" + unitPrice +
                '}';
    }
}
