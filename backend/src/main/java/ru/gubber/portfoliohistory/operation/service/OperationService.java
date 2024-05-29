package ru.gubber.portfoliohistory.operation.service;

import ru.gubber.portfoliohistory.account.dto.IdOutcomeAccountDto;

import java.util.UUID;

public interface OperationService {

    UUID replenishAccount(String accountId, Double amount);
}
