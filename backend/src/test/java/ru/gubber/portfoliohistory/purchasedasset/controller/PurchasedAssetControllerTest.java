package ru.gubber.portfoliohistory.purchasedasset.controller;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.gubber.portfoliohistory.account.dto.ValidationError;
import ru.gubber.portfoliohistory.common.dto.RequestAccountIdDto;
import ru.gubber.portfoliohistory.common.dto.ResponseStatus;
import ru.gubber.portfoliohistory.common.dto.SuccessResponseDto;
import ru.gubber.portfoliohistory.common.utils.FieldValidationError;
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
    UUID uuid = UUID.fromString("f99b9e41-4753-43ad-89cd-1874c3a35c89");
    UUID accountUuid = UUID.fromString("f99b9e41-4753-43ad-89cd-1874c3a35c89");
    PurchasedAsset validAsset1 = new PurchasedAsset(uuid, "RUR", AssetType.CURRENCY, 100.0, 1.0, 1.0, accountUuid);
    UUID uuid2 = UUID.fromString("f99b9e41-4753-43ad-89cd-1874c3a35c33");
    UUID accountUuid2 = UUID.fromString("f99b9e41-4753-43ad-89cd-1874c3a35c32");
    PurchasedAsset validAsset2 = new PurchasedAsset(uuid2, "RUR", AssetType.CURRENCY, 100.0, 1.0, 1.0, accountUuid);

    @Test
    @DisplayName("Вызывается сервис")
    void getPurchasedAssetsList_thenUseService() {
        List<PurchasedAsset> purchasedAssets = new ArrayList<>();
        purchasedAssets.add(validAsset1);
        purchasedAssets.add(validAsset2);
        Mockito.when(mockPurchasedAssetService.getPurchasedAssetsList(accountUuid)).thenReturn(purchasedAssets);
        purchasedAssetController.getPurchasedAssetsList(new RequestAccountIdDto(accountUuid.toString()));

        verify(mockPurchasedAssetService).getPurchasedAssetsList(accountUuid);
    }

    @Test
    @DisplayName("При получении списка активов получаем список dto")
    void getPurchasedAssetsList_thenReturnDtoList() {
        List<PurchasedAsset> purchasedAssets = new ArrayList<>();
        purchasedAssets.add(validAsset1);
        purchasedAssets.add(validAsset2);
        Mockito.when(mockPurchasedAssetService.getPurchasedAssetsList(accountUuid)).thenReturn(purchasedAssets);
        List<PurchasedAssetFullDto> dtos = purchasedAssets.stream().map(PurchasedAssetMapper::toPurchasedAssetFullDto).toList();

        SuccessResponseDto<List<PurchasedAssetFullDto>> response = (SuccessResponseDto<List<PurchasedAssetFullDto>>) purchasedAssetController.getPurchasedAssetsList(
                new RequestAccountIdDto(accountUuid.toString())
        );
        List<PurchasedAssetFullDto> resultDtos = (List<PurchasedAssetFullDto>) response.getResponse();

        Assertions.assertEquals(dtos.size(), resultDtos.size());
    }

    @Test
    @DisplayName("При получении информации об активе со значением - idDto.isEmpty- возвращается ошибка валидации")
    void getPurchasedAssetsList_whenIdDtoIsEmpty_thenReturnDtoList() {
        List<FieldValidationError> response = new ArrayList<>();
        response.add(new FieldValidationError("id","Поле не может быть пустым"));
        ValidationError responseError = new ValidationError(ResponseStatus.ERROR, "Не правильный запрос", response);
        ValidationError result = (ValidationError) purchasedAssetController.getPurchasedAssetsList(new RequestAccountIdDto(""));
        Assertions.assertEquals(responseError.getErrorMessage(), result.getErrorMessage());
    }

    @Test
    @DisplayName("При невалидном значении uuid - возвращается ошибка валидации")
    void getPurchasedAssetsList_whenIdIsInvalid_thenReturnError() {
        List<FieldValidationError> response = new ArrayList<>();
        response.add(new FieldValidationError("id","Поле не может быть пустым"));
        ValidationError responseError = new ValidationError(ResponseStatus.ERROR, "Не правильный запрос. Не правильный формат UUID.", response);
        ValidationError result = (ValidationError) purchasedAssetController.getPurchasedAssetsList(new RequestAccountIdDto("невалидная строка"));
        Assertions.assertEquals(responseError.getErrorMessage(), result.getErrorMessage());
    }
}