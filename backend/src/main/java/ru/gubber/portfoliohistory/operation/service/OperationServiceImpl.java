package ru.gubber.portfoliohistory.operation.service;

import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class OperationServiceImpl implements OperationService {
    @Override
    public UUID replenishAccount(String accountId, Double amount) {
        UUID uuid = UUID.fromString(accountId);
        return null;
    }
}
