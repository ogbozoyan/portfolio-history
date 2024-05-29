package ru.gubber.portfoliohistory.operation.service;

import org.springframework.stereotype.Service;
import ru.gubber.portfoliohistory.account.service.AccountService;
import ru.gubber.portfoliohistory.operation.model.Operation;
import ru.gubber.portfoliohistory.operation.model.OperationType;
import ru.gubber.portfoliohistory.operation.repository.OperationRepository;

import java.time.LocalDateTime;
import java.util.UUID;

@Service
public class OperationServiceImpl implements OperationService {
    private final OperationRepository repository;
    private final AccountService accountService;
    private static final String assetCode = "RUR";

    public OperationServiceImpl(OperationRepository repository, AccountService accountService) {
        this.repository = repository;
        this.accountService = accountService;
    }

    @Override
    public UUID replenishAccount(String accountId, Double amount, Double unitPrice) {
        UUID accountUuid = UUID.fromString(accountId);
        if (accountService.accountExists(accountUuid)) {
            return repository.save(new Operation(null, LocalDateTime.now(), accountUuid, assetCode, OperationType.REPLENISHMENT, amount, unitPrice)).getAccountId();
        }
        return null;
    }
}
