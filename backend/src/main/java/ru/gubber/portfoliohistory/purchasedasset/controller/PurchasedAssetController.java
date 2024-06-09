package ru.gubber.portfoliohistory.purchasedasset.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.gubber.portfoliohistory.account.controller.AccountController;
import ru.gubber.portfoliohistory.account.dto.BaseResponse;
import ru.gubber.portfoliohistory.purchasedasset.dto.OutcomePurchasedAssetDto;
import ru.gubber.portfoliohistory.purchasedasset.dto.PurchasedAssetFullDto;
import ru.gubber.portfoliohistory.purchasedasset.dto.PurchasedAssetMapper;
import ru.gubber.portfoliohistory.purchasedasset.service.PurchasedAssetService;

import java.util.List;

@RestController
public class PurchasedAssetController {
    private final PurchasedAssetService purchasedAssetService;
    private final Logger log = LoggerFactory.getLogger(AccountController.class);

    public PurchasedAssetController(PurchasedAssetService purchasedAssetService) {
        this.purchasedAssetService = purchasedAssetService;
    }

    @PostMapping("/api/v1/get-purchased-assets-list")
    public BaseResponse getPurchasedAssetsList() {
        log.info("Получен запрос на предоставление списка всех активов.");
        List<PurchasedAssetFullDto> dtos = purchasedAssetService.getPurchasedAssetsList().stream()
                .map(PurchasedAssetMapper::toPurchasedAssetFullDto)
                .toList();
        log.info("Список активов получен.");
        return new OutcomePurchasedAssetDto(dtos);
    }
}
