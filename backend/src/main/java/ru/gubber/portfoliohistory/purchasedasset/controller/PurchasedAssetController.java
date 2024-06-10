package ru.gubber.portfoliohistory.purchasedasset.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import ru.gubber.portfoliohistory.account.controller.AccountController;
import ru.gubber.portfoliohistory.account.dto.ValidationError;
import ru.gubber.portfoliohistory.common.dto.BaseResponse;
import ru.gubber.portfoliohistory.common.dto.RequestAccountIdDto;
import ru.gubber.portfoliohistory.common.dto.ResponseStatus;
import ru.gubber.portfoliohistory.common.dto.SuccessResponseDto;
import ru.gubber.portfoliohistory.common.utils.FieldValidationError;
import ru.gubber.portfoliohistory.common.utils.ValidationUtils;
import ru.gubber.portfoliohistory.purchasedasset.dto.PurchasedAssetFullDto;
import ru.gubber.portfoliohistory.purchasedasset.dto.PurchasedAssetMapper;
import ru.gubber.portfoliohistory.purchasedasset.service.PurchasedAssetService;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

@RestController
public class PurchasedAssetController {
    private final PurchasedAssetService purchasedAssetService;
    private final Logger log = LoggerFactory.getLogger(AccountController.class);

    public PurchasedAssetController(PurchasedAssetService purchasedAssetService) {
        this.purchasedAssetService = purchasedAssetService;
    }

    @PostMapping("/api/v1/get-purchased-assets-list")
    public BaseResponse getPurchasedAssetsList(@RequestBody RequestAccountIdDto dto) {
        log.info("Получен запрос на предоставление списка всех активов.");
        List<FieldValidationError> invalidField = new ArrayList<>();
        invalidField.add(ValidationUtils.validateUUID(dto.id(), "id"));
        List<FieldValidationError> validationErrors = invalidField.stream().filter(Objects::nonNull).toList();
        if (!validationErrors.isEmpty()) {
            log.info("Ошибка валидации. Не правильный запрос");
            return new ValidationError(ResponseStatus.ERROR, "Не правильный запрос", validationErrors);
        }
        List<PurchasedAssetFullDto> dtos = purchasedAssetService.getPurchasedAssetsList(UUID.fromString(dto.id())).stream()
                .map(PurchasedAssetMapper::toPurchasedAssetFullDto)
                .toList();
        log.info("Список активов получен.");
        return new SuccessResponseDto<>(dtos);
    }
}
