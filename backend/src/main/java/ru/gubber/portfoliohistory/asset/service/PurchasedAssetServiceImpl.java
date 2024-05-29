package ru.gubber.portfoliohistory.asset.service;

import org.springframework.stereotype.Service;
import ru.gubber.portfoliohistory.asset.model.AssetType;
import ru.gubber.portfoliohistory.asset.model.PurchasedAsset;
import ru.gubber.portfoliohistory.asset.repository.PurchasedAssetRepository;

import java.util.Optional;
import java.util.UUID;

@Service
public class PurchasedAssetServiceImpl implements PurchasedAssetService {
    private final PurchasedAssetRepository repository;

    public PurchasedAssetServiceImpl(PurchasedAssetRepository repository) {
        this.repository = repository;
    }

    @Override
    public void purchaseAsset(UUID accountId, String code, Double amount) {
        Optional<PurchasedAsset> optionalPurchasedAsset = repository.findByAccountIdAndCode(accountId, code);
        if (optionalPurchasedAsset.isEmpty()) {
            repository.save(new PurchasedAsset(null, code, AssetType.CURRENCY, amount, 1.0, 1.0, accountId));
        } else {
            PurchasedAsset asset = optionalPurchasedAsset.get();
            asset.setAmount(asset.getAmount() + amount);
            repository.save(asset);
        }
    }
}
