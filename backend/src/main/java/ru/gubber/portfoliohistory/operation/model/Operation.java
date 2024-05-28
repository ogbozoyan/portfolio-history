package ru.gubber.portfoliohistory.operation.model;

import ru.gubber.portfoliohistory.account.dto.IdOutcomeAccountDto;
import ru.gubber.portfoliohistory.asset.model.PurchasedAsset;

import java.time.LocalDateTime;

public class Operation {
    private LocalDateTime creatTime;
    private IdOutcomeAccountDto accountId;
    private PurchasedAsset purchasedAsset;
    private OperationType operationType;
    private double count;
    private double unitPrice;
}
