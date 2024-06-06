package ru.gubber.portfoliohistory.operation.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import ru.gubber.portfoliohistory.account.controller.AccountController;
import ru.gubber.portfoliohistory.account.dto.*;
import ru.gubber.portfoliohistory.common.utils.FieldValidationError;
import ru.gubber.portfoliohistory.common.utils.ValidationUtils;
import ru.gubber.portfoliohistory.operation.dto.OperationDto;
import ru.gubber.portfoliohistory.operation.dto.OutcomeOperationDto;
import ru.gubber.portfoliohistory.operation.dto.ResultOperationId;
import ru.gubber.portfoliohistory.operation.service.OperationService;
import ru.gubber.portfoliohistory.operation.service.WithdrawalResult;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

@RestController
public class OperationController {
    private final OperationService operationService;
    private final Logger log = LoggerFactory.getLogger(AccountController.class);

    public OperationController(OperationService operationService) {
        this.operationService = operationService;
    }

    @PostMapping("/api/v1/replenish-account")
    public BaseResponse replenishAccount(@RequestBody OperationDto dto) {
        log.info("Получен запрос на пополнение счета {}.", dto.accountId());
        List<FieldValidationError> invalidField = new ArrayList<>();
        invalidField.add(ValidationUtils.validateStringField(dto.accountId(), "accountId"));
        invalidField.add(ValidationUtils.validateNumberField(dto.amount(), "amount"));
        List<FieldValidationError> validationErrors = invalidField.stream().filter(Objects::nonNull).toList();
        if (!validationErrors.isEmpty()) {
            log.info("Ошибка валидации - не правильный запрос.");
            return new ValidationError(ResponseStatus.ERROR, "Не правильный запрос", validationErrors);
        }
        UUID uuid = operationService.replenishAccount(dto.accountId(), dto.amount());
        if (uuid != null) {
            log.info("Запрос успешно выполнен.");
            return new OutcomeOperationDto(new ResultOperationId(uuid));
        } else {
            log.info("Не удалось выполнить запрос на добавление актива на счет {}", dto.accountId());
            return new ValidationError(ResponseStatus.WARN,
                    String.format("На счет %s не удалось добавить актив", dto.accountId()), null);
        }
    }

    @PostMapping("/api/v1/withdraw-from-account")
    public BaseResponse withdrawFromAccount(@RequestBody OperationDto dto) {
        log.info("Получен запрос на вывод средств со счета {}.", dto.accountId());
        List<FieldValidationError> invalidField = new ArrayList<>();
        invalidField.add(ValidationUtils.validateStringField(dto.accountId(), "accountId"));
        invalidField.add(ValidationUtils.validateNumberField(dto.amount(), "amount"));
        List<FieldValidationError> validationErrors = invalidField.stream().filter(Objects::nonNull).toList();
        if (!validationErrors.isEmpty()) {
            log.info("Ошибка валидации - не правильный запрос.");
            return new ValidationError(ResponseStatus.ERROR, "Не правильный запрос", validationErrors);
        }
        WithdrawalResult withdrawalResult = operationService.withdrawFromAccount(dto.accountId(), dto.amount());
        switch (withdrawalResult.status()) {
            case ITEM_NOT_FOUND -> {
                log.info("Нет счёта с идентификатором {}", dto.accountId());
                return new ValidationError(ResponseStatus.WARN,
                        String.format("Нет счёта с идентификатором %s", dto.accountId()), null);
            }
            case NOT_ENOUGH_FUNDS -> {
                log.info("Не удалось выполнить запрос на вывод средств со счета {}", dto.accountId());
                return new ValidationError(ResponseStatus.ERROR,
                        "На счете не достаточно средств", null);
            }
            default -> {
                log.info("Запрос на вывод средств успешно выполнен.");
                return new OutcomeOperationDto(new ResultOperationId(withdrawalResult.uuid()));
            }
        }
    }
}
