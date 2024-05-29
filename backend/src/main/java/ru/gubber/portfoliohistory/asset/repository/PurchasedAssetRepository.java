package ru.gubber.portfoliohistory.asset.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.gubber.portfoliohistory.asset.model.PurchasedAsset;

import java.util.UUID;

public interface PurchasedAssetRepository extends JpaRepository<PurchasedAsset, UUID> {
}
