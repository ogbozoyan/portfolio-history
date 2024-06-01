package ru.gubber.portfoliohistory.purchasedasset.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.gubber.portfoliohistory.purchasedasset.model.PurchasedAsset;

import java.util.Optional;
import java.util.UUID;

public interface PurchasedAssetRepository extends JpaRepository<PurchasedAsset, UUID> {
    Optional<PurchasedAsset> findByAccountIdAndCode(UUID accountId, String code);
}
