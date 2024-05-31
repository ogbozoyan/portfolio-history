package ru.gubber.portfoliohistory.asset.service;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.gubber.portfoliohistory.asset.model.AssetType;
import ru.gubber.portfoliohistory.asset.model.PurchasedAsset;
import ru.gubber.portfoliohistory.asset.repository.PurchasedAssetRepository;

import java.util.Optional;
import java.util.UUID;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class PurchasedAssetServiceImplTest {
    @InjectMocks
    PurchasedAssetServiceImpl purchasedAssetService;
    @Mock
    PurchasedAssetRepository mockRepository;
    @Captor
    ArgumentCaptor<PurchasedAsset> assetArgumentCaptor;
    UUID uuid = UUID.fromString("f99b9e41-4753-43ad-89cd-1874c3a35c89");
    UUID accountUuid = UUID.fromString("f99b9e41-4753-43ad-89cd-1874c3a35c89");
    PurchasedAsset validAsset1 = new PurchasedAsset(uuid, "RUR", AssetType.CURRENCY, 100.0, 1.0, 1.0, accountUuid);

    @Test
    @DisplayName("Успешное сохранение приобретенного актива если его ещё не было")
    void purchaseAsset_whenAssetNotFound_thenSaveAsset() {
        Optional<PurchasedAsset> assetOptional = Optional.empty();
        Mockito.when(mockRepository.findByAccountIdAndCode(any(), anyString())).thenReturn(assetOptional);

        purchasedAssetService.purchaseAsset(accountUuid, "RUR", 100.0);
        verify(mockRepository).save(assetArgumentCaptor.capture());
        Assertions.assertAll(
                () -> {
                    Assertions.assertEquals(validAsset1.getCode(), assetArgumentCaptor.getValue().getCode());
                    Assertions.assertEquals(validAsset1.getAssetType(), assetArgumentCaptor.getValue().getAssetType());
                    Assertions.assertEquals(validAsset1.getAmount(), assetArgumentCaptor.getValue().getAmount());
                    Assertions.assertEquals(validAsset1.getPurchasePrice(), assetArgumentCaptor.getValue().getPurchasePrice());
                    Assertions.assertEquals(validAsset1.getCurrentPrice(), assetArgumentCaptor.getValue().getCurrentPrice());
                    Assertions.assertEquals(validAsset1.getAccountId(), assetArgumentCaptor.getValue().getAccountId());
                }
        );
    }

    @Test
    @DisplayName("Успешное увеличение  приобретенного актива если этот актив есть на счете")
    void purchaseAsset_whenAssetIsFound_thenSaveAsset() {
        UUID uuid1 = UUID.fromString("f99b9e41-4753-43ad-89cd-1874c3a35c90");
        UUID accountUuid2 = UUID.fromString("f99b9e41-4753-43ad-89cd-1874c3a35c91");
        PurchasedAsset validAsset = new PurchasedAsset(uuid1, "RUR", AssetType.CURRENCY, 100.0, 1.0, 1.0, accountUuid2);
        Optional<PurchasedAsset> assetOptional = Optional.of(validAsset);
        Mockito.when(mockRepository.findByAccountIdAndCode(any(), anyString())).thenReturn(assetOptional);

        purchasedAssetService.purchaseAsset(accountUuid2, "RUR", 100.0);
        verify(mockRepository).save(assetArgumentCaptor.capture());

        Assertions.assertEquals(200.0, assetArgumentCaptor.getValue().getAmount());
    }
}