package ru.gubber.portfoliohistory.account.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import ru.gubber.portfoliohistory.account.dto.*;
import ru.gubber.portfoliohistory.account.model.Account;
import ru.gubber.portfoliohistory.account.service.AccountService;
import ru.gubber.portfoliohistory.account.service.UpdatingResult;
import ru.gubber.portfoliohistory.common.dto.BaseResponse;
import ru.gubber.portfoliohistory.common.dto.ResponseId;
import ru.gubber.portfoliohistory.common.dto.ResponseStatus;
import ru.gubber.portfoliohistory.common.dto.SuccessResponseDto;
import ru.gubber.portfoliohistory.common.utils.FieldValidationError;
import ru.gubber.portfoliohistory.common.utils.ValidationUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.UUID;
import java.util.stream.Collectors;

@RestController
public class AccountController {
    private final AccountService accountService;
    private final Logger log = LoggerFactory.getLogger(AccountController.class);
    private final String VALIDATION_ERROR = "Ошибка валидации. Не правильный запрос";

    public AccountController(AccountService accountService) {
        this.accountService = accountService;
    }

    @PostMapping("/api/v1/create-account")
    public BaseResponse createAccount(@RequestBody IncomeAccountDto dto) {
        log.info("Получен запрос на добавление счета {}.", dto.name());
        List<FieldValidationError> invalidField = new ArrayList<>();
        invalidField.add(ValidationUtils.validateStringField(dto.name(), "name"));
        invalidField.add(ValidationUtils.validateStringField(dto.broker(), "broker"));
        invalidField.add(ValidationUtils.validateStringField(dto.number(), "number"));
        List<FieldValidationError> validationErrors = invalidField.stream().filter(Objects::nonNull).toList();
        if (!validationErrors.isEmpty()) {
            log.info(VALIDATION_ERROR);
            return new ValidationError(ResponseStatus.ERROR, "Не правильный запрос", validationErrors);
        }
        UUID uuid = accountService.createAccount(dto.name(), dto.broker(), dto.number());
        if (uuid != null) {
            log.info("Зарегистрирован счет {} с ID {}", dto.name(), uuid);
            return new SuccessResponseDto<>(new ResponseId(uuid));
        } else {
            String errorMessage = String.format("В системе уже зарегистрирован счёт %s у брокера %s", dto.number(), dto.broker());
            log.info(errorMessage);
            return new ValidationError(ResponseStatus.WARN,
                    errorMessage, null);
        }
    }

    @PostMapping("/api/v1/update-account")
    public BaseResponse updateAccount(@RequestBody IncomeFullAccountDto dto) {
        log.info("Получен запрос на обновление счета {}.", dto.name());
        List<FieldValidationError> invalidField = new ArrayList<>();
        invalidField.add(ValidationUtils.validateStringField(dto.id(), "id"));
        invalidField.add(ValidationUtils.validateStringField(dto.name(), "name"));
        invalidField.add(ValidationUtils.validateStringField(dto.broker(), "broker"));
        invalidField.add(ValidationUtils.validateStringField(dto.number(), "number"));
        List<FieldValidationError> validationErrors = invalidField.stream().filter(Objects::nonNull).toList();
        if (!validationErrors.isEmpty()) {
            log.info(VALIDATION_ERROR);
            return new ValidationError(ResponseStatus.ERROR, "Не правильный запрос", validationErrors);
            }
        UpdatingResult updatingResult = accountService.updateAccount(dto.id(), dto.name(), dto.broker(), dto.number());
        switch (updatingResult.status()) {
            case UNSUCCESSFULLY -> {
                String errorMessage = String.format("В системе уже зарегистрирован счёт %s у брокера %s", dto.number(), dto.broker());
                log.info(errorMessage);
                return new ValidationError(ResponseStatus.WARN,
                        errorMessage, null);
            }
            case ITEM_NOT_FOUND -> {
                log.info("Нет счёта с идентификатором {}", dto.id());
                return new ValidationError(ResponseStatus.WARN,
                        String.format("Нет счёта с идентификатором %s", dto.id()), null);
            }
            default -> {
                log.info("Обновлен счет {}", dto.name());
                return   new SuccessResponseDto<>(new ResponseId(updatingResult.uuid()));
            }

        }
    }

    @PostMapping("/api/v1/delete-account")
    public BaseResponse deleteAccount(@RequestBody IdIncomeAccountDto dto) {
        log.info("Получен запрос на удаление счета {}.", dto.id());
        List<FieldValidationError> invalidField = new ArrayList<>();
        invalidField.add(ValidationUtils.validateStringField(dto.id(), "id"));
        List<FieldValidationError> validationErrors = invalidField.stream().filter(Objects::nonNull).toList();
        if (!validationErrors.isEmpty()) {
            log.info(VALIDATION_ERROR);
            return new ValidationError(ResponseStatus.ERROR, "Не правильный запрос", validationErrors);
        }
        UUID uuid = accountService.deleteAccount(dto.id());
        if (uuid != null) {
            log.info("Удален аккаунт с ID {}", dto.id());
            return new SuccessResponseDto<>(new ResponseId(uuid));
        } else {
            log.info("Не найден счёт с идентификатором {}", dto.id());
            return new ValidationError(ResponseStatus.WARN,
                    String.format("Нет счёта с идентификатором %s", dto.id()), null);
        }
    }

    @PostMapping("/api/v1/get-accounts-list")
    public BaseResponse getAccountsList() {
        log.info("Получен запрос на предоставление списка всех счетов.");
        List<AccountDto> dtos = accountService.getAccountsList().stream().map(AccountMapper::toAccountDto).collect(Collectors.toList());
        log.info("Список счетов получен.");
        return new SuccessResponseDto<>(dtos);
    }

    @PostMapping("/api/v1/get-accounts-info")
    public BaseResponse getAccountsInfo(@RequestBody IdIncomeAccountDto dto) {
        log.info("Получен запрос на предоставление информации о счете с Id {}", dto.id());
        List<FieldValidationError> invalidField = new ArrayList<>();
        invalidField.add(ValidationUtils.validateStringField(dto.id(), "id"));
        List<FieldValidationError> validationErrors = invalidField.stream().filter(Objects::nonNull).toList();
        if (!validationErrors.isEmpty()) {
            log.info(VALIDATION_ERROR);
            return new ValidationError(ResponseStatus.ERROR, "Не правильный запрос", validationErrors);
        }
        Account accountsInfo = accountService.getAccountsInfo(dto.id());
        if (accountsInfo != null) {
            AccountDto result = AccountMapper.toAccountDto(accountsInfo);
            log.info("Запрос успешно выполнен.");
            return new SuccessResponseDto<>(result);
        } else {
            log.info("Не был найден счёт с идентификатором {}", dto.id());
            return new ValidationError(ResponseStatus.WARN,
                    String.format("Нет счёта с идентификатором %s", dto.id()), null);
        }
    }
}

