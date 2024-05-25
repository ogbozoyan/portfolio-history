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
    private final AccountMapper mapper = new AccountMapper();

    public AccountController(AccountService accountService) {
        this.accountService = accountService;
    }

    @PostMapping("/api/v1/create-account")
    public BaseResponse createAccount(@RequestBody IncomeAccountDto dto) {
        log.info("Получен запрос на добавление счета {}.", dto.name());
        List<FieldValidationError> invalidField = new ArrayList<>();
        invalidField.add(ValidationUtils.validateStringField(dto.name()));
        invalidField.add(ValidationUtils.validateStringField(dto.broker()));
        invalidField.add(ValidationUtils.validateStringField(dto.number()));
        List<FieldValidationError> validationErrors = invalidField.stream().filter(Objects::nonNull).toList();
        if (!validationErrors.isEmpty()) {
            return new ValidationError(ResponseStatus.ERROR, "Не правильный запрос", validationErrors);
        }
        UUID uuid = accountService.createAccount(dto.name(), dto.broker(), dto.number());
        if (uuid != null) {
            return new IdOutcomeAccountDto(new ResponseId(uuid));
        } else {
            return new ValidationError(ResponseStatus.WARN,
                    String.format("В системе уже зарегистрирован счёт %s у брокера %s", dto.number(), dto.broker()), null);
        }
    }

    @PostMapping("/api/v1/update-account")
    public BaseResponse updateAccount(@RequestBody IncomeFullAccountDto dto) {
        log.info("Получен запрос на обновление счета {}.", dto.name());
        List<FieldValidationError> invalidField = new ArrayList<>();
        invalidField.add(ValidationUtils.validateStringField(dto.id()));
        invalidField.add(ValidationUtils.validateStringField(dto.name()));
        invalidField.add(ValidationUtils.validateStringField(dto.broker()));
        invalidField.add(ValidationUtils.validateStringField(dto.number()));
        List<FieldValidationError> validationErrors = invalidField.stream().filter(Objects::nonNull).toList();
        if (!validationErrors.isEmpty()) {
                return new ValidationError(ResponseStatus.ERROR, "Не правильный запрос", validationErrors);
            }
        UpdatingResult updatingResult = accountService.updateAccount(dto.id(), dto.name(), dto.broker(), dto.number());
        switch (updatingResult.status()) {
            case UNSUCCESSFULLY -> {
                return new ValidationError(ResponseStatus.WARN,
                        String.format("В системе уже зарегистрирован счёт %s у брокера %s", dto.number(), dto.broker()), null);
            }
            case ITEM_NOT_FOUND -> {
                return new ValidationError(ResponseStatus.WARN,
                        String.format("Нет счёта с идентификатором %s", dto.id()), null);
            }
            default -> {
                return   new IdOutcomeAccountDto(new ResponseId(updatingResult.uuid()));
            }

        }
    }

    @PostMapping("/api/v1/delete-account")
    public BaseResponse deleteAccount(@RequestBody IdIncomeAccountDto dto) {
        log.info("Получен запрос на удаление счета {}.", dto.id());
        List<FieldValidationError> invalidField = new ArrayList<>();
        invalidField.add(ValidationUtils.validateStringField(dto.id()));
        List<FieldValidationError> validationErrors = invalidField.stream().filter(Objects::nonNull).toList();
        if (!validationErrors.isEmpty()) {
            return new ValidationError(ResponseStatus.ERROR, "Не правильный запрос", validationErrors);
        }
        UUID uuid = accountService.deleteAccount(dto.id());
        if (uuid != null) {
            return new IdOutcomeAccountDto(new ResponseId(uuid));
        } else {
            return new ValidationError(ResponseStatus.WARN,
                    String.format("Нет счёта с идентификатором %s", dto.id()), null);
        }
    }

    @PostMapping("/api/v1/get-accounts-list")
    public BaseResponse getAccountsList() {
        log.info("Получен запрос на предоставление списка всех счетов.");
        List<AccountDto> dtos = accountService.getAccountsList().stream().map(mapper::toAccountDto).collect(Collectors.toList());
        return new OutcomeAccountDto(dtos);
    }

    @PostMapping("/api/v1/get-accounts-info")
    public BaseResponse getAccountsInfo(@RequestBody IdIncomeAccountDto dto) {
        log.info("Получен запрос на предоставление информации о счете с Id {}", dto.id());
        List<FieldValidationError> invalidField = new ArrayList<>();
        invalidField.add(ValidationUtils.validateStringField(dto.id()));
        List<FieldValidationError> validationErrors = invalidField.stream().filter(Objects::nonNull).toList();
        if (!validationErrors.isEmpty()) {
            return new ValidationError(ResponseStatus.ERROR, "Не правильный запрос", validationErrors);
        }
        Account accountsInfo = accountService.getAccountsInfo(dto.id());
        if (accountsInfo != null) {
            AccountDto result = mapper.toAccountDto(accountsInfo);
            return new BaseResponse(ResponseStatus.SUCCESS, null, result);
        } else {
            return new ValidationError(ResponseStatus.WARN,
                    String.format("Нет счёта с идентификатором %s", dto.id()), null);
        }
    }
}

