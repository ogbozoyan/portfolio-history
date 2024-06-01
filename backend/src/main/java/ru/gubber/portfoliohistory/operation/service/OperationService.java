package ru.gubber.portfoliohistory.operation.service;

import java.util.UUID;

public interface OperationService {

    UUID replenishAccount(String accountId, Double amount);
}
