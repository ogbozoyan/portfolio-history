package ru.gubber.portfoliohistory.purchasedasset.controller;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.gubber.portfoliohistory.purchasedasset.dto.OutcomePurchasedAssetDto;
import ru.gubber.portfoliohistory.purchasedasset.dto.PurchasedAssetFullDto;
import ru.gubber.portfoliohistory.purchasedasset.dto.PurchasedAssetMapper;
import ru.gubber.portfoliohistory.purchasedasset.model.AssetType;
import ru.gubber.portfoliohistory.purchasedasset.model.PurchasedAsset;
import ru.gubber.portfoliohistory.purchasedasset.service.PurchasedAssetService;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class PurchasedAssetControllerTest {
    @InjectMocks
    private PurchasedAssetController purchasedAssetController;
    @Mock
    private PurchasedAssetService mockPurchasedAssetService;
    private final PurchasedAssetMapper mapper = new PurchasedAssetMapper();
    UUID uuid = UUID.fromString("f99b9e41-4753-43ad-89cd-1874c3a35c89");
    UUID accountUuid = UUID.fromString("f99b9e41-4753-43ad-89cd-1874c3a35c89");
    PurchasedAsset validAsset1 = new PurchasedAsset(uuid, "RUR", AssetType.CURRENCY, 100.0, 1.0, 1.0, accountUuid);
    UUID uuid2 = UUID.fromString("f99b9e41-4753-43ad-89cd-1874c3a35c33");
    UUID accountUuid2 = UUID.fromString("f99b9e41-4753-43ad-89cd-1874c3a35c32");
    PurchasedAsset validAsset2 = new PurchasedAsset(uuid2, "RUR", AssetType.CURRENCY, 100.0, 1.0, 1.0, accountUuid2);

    @Test
    @DisplayName("Вызывается сервис")
    void getPurchasedAssetsList_thenUseService() {
        List<PurchasedAsset> purchasedAssets = new ArrayList<>();
        purchasedAssets.add(validAsset1);
        purchasedAssets.add(validAsset2);
        Mockito.when(mockPurchasedAssetService.getPurchasedAssetsList()).thenReturn(purchasedAssets);
        purchasedAssetController.getPurchasedAssetsList();

        verify(mockPurchasedAssetService).getPurchasedAssetsList();
    }

    @Test
    @DisplayName("При получении списка активов получаем список dto")
    void getPurchasedAssetsList_thenReturnDtoList() {
        List<PurchasedAsset> purchasedAssets = new ArrayList<>();
        purchasedAssets.add(validAsset1);
        purchasedAssets.add(validAsset2);
        Mockito.when(mockPurchasedAssetService.getPurchasedAssetsList()).thenReturn(purchasedAssets);
        List<PurchasedAssetFullDto> dtos = purchasedAssets.stream().map(mapper::toPurchasedAssetFullDto).toList();

        OutcomePurchasedAssetDto response = (OutcomePurchasedAssetDto) purchasedAssetController.getPurchasedAssetsList();
        List<PurchasedAssetFullDto> resultDtos = (List<PurchasedAssetFullDto>) response.getResponse();

        Assertions.assertEquals(dtos.size(), resultDtos.size());
    }
}