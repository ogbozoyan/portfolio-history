package ru.gubber.portfoliohistory.asset.service;

import org.springframework.stereotype.Service;
import ru.gubber.portfoliohistory.asset.repository.PurchasedAssetRepository;

import java.util.UUID;

@Service
public class PurchasedAssetServiceImpl implements PurchasedAssetService {
    private final PurchasedAssetRepository repository;

    public PurchasedAssetServiceImpl(PurchasedAssetRepository repository) {
        this.repository = repository;
    }

    /*public void purchaseAsset(String code, Double amount) { //сюда параметр номер счета

    }*/
}
