package ru.gubber.portfoliohistory.operation.service;

import org.springframework.stereotype.Service;
import ru.gubber.portfoliohistory.account.service.AccountService;
import ru.gubber.portfoliohistory.purchasedasset.service.PurchasedAssetService;
import ru.gubber.portfoliohistory.operation.model.Operation;
import ru.gubber.portfoliohistory.operation.model.OperationType;
import ru.gubber.portfoliohistory.operation.repository.OperationRepository;

import java.time.LocalDateTime;
import java.util.UUID;

@Service
public class OperationServiceImpl implements OperationService {
    private final OperationRepository repository;
    private final AccountService accountService;
    private final PurchasedAssetService purchasedAssetService;
    private static final String ASSET_CODE = "RUR";
    private static final double UNIT_PRICE = 1.0;

    public OperationServiceImpl(OperationRepository repository, AccountService accountService, PurchasedAssetService purchasedAssetService) {
        this.repository = repository;
        this.accountService = accountService;
        this.purchasedAssetService = purchasedAssetService;
    }

    @Override
    public UUID replenishAccount(String accountId, Double amount) {
        UUID accountUuid = UUID.fromString(accountId);
        if (accountService.accountExists(accountUuid)) {
            Operation operation = repository.save(new Operation(UUID.randomUUID(), LocalDateTime.now(), accountUuid, ASSET_CODE, OperationType.REPLENISHMENT, amount, UNIT_PRICE));
            purchasedAssetService.purchaseAsset(accountUuid, ASSET_CODE, amount);
            accountService.changeCurrentBalance(accountUuid, amount);
            return operation.getId();
        }
        return null;
    }

    @Override
    public WithdrawalResult withdrawFromAccount(String accountId, Double amount) {
        UUID accountUuid = UUID.fromString(accountId);
        if (accountService.accountExists(accountUuid)) {
            boolean sellAsset = purchasedAssetService.sellAsset(accountUuid, ASSET_CODE, amount);
            if (!sellAsset) {
                return new WithdrawalResult(accountUuid, OperationStatus.NOT_ENOUGH_FUNDS);
            }
            Operation operation = repository.save(new Operation(UUID.randomUUID(), LocalDateTime.now(), accountUuid, ASSET_CODE, OperationType.WITHDRAW, amount, UNIT_PRICE));
            accountService.changeCurrentBalance(accountUuid, amount * -1);
            return new WithdrawalResult(operation.getId(), OperationStatus.SUCCESSFULLY);
        }
        return new WithdrawalResult(accountUuid, OperationStatus.ITEM_NOT_FOUND);
    }
}
