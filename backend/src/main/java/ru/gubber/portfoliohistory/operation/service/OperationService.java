package ru.gubber.portfoliohistory.operation.service;

import java.util.UUID;

public interface OperationService {

    UUID replenishAccount(UUID accountId, Double amount);

    WithdrawalResult withdrawFromAccount(String accountId, Double amount);
}
