package ru.gubber.portfoliohistory.account.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import ru.gubber.portfoliohistory.account.dto.*;
import ru.gubber.portfoliohistory.account.service.AccountService;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@RestController
public class AccountController {
    private final AccountService accountService;
    private final Logger log = LoggerFactory.getLogger(AccountController.class);

    public AccountController(AccountService accountService) {
        this.accountService = accountService;
    }

    @PostMapping("/api/v1/create-account")
    public BaseResponce createAccount(@RequestBody IncomeAccountDto dto) {
        log.info("Получен запрос на добавление счета {}.", dto.name());
        if (dto.name().isEmpty() || dto.broker().isEmpty() || dto.number().isEmpty()) {
            List<FieldValidationError> responce = new ArrayList<>();
            validationDto(dto.name(), dto.broker(), dto.number(), responce);
            return new ValidationError(ResponceStatus.ERROR, "Не правильный запрос", responce);
        }
        UUID uuid = accountService.createAccount(dto.name(), dto.broker(), dto.number());
        if (uuid != null) {
            return new OutcomeAccountDto(new ResponceId(uuid));
        } else {
            return new ValidationError(ResponceStatus.WARN,
                    String.format("В системе уже зарегистрирован счёт %s у брокера %s", dto.number(), dto.broker()), null);
        }
    }

    @PostMapping("/api/v1/update-account")
    public BaseResponce updateAccount(@RequestBody IncomeUpdateAccountDto dto) {
        log.info("Получен запрос на обновление счета {}.", dto.name());
        if (dto.id().isEmpty() || dto.name().isEmpty() || dto.broker().isEmpty() || dto.number().isEmpty()) {
            List<FieldValidationError> responce = new ArrayList<>();
            if (dto.id().isEmpty()) {
                responce.add(new FieldValidationError("id", "Поле не может быть пустым"));
            }
            validationDto(dto.name(), dto.broker(), dto.number(), responce);
            return new ValidationError(ResponceStatus.ERROR, "Не правильный запрос", responce);
        }
        UUID uuid = accountService.updateAccount(dto.id(), dto.name(), dto.broker(), dto.number());
        if (uuid != null) {
            if (uuid.toString().equals(dto.id())) {
                return new OutcomeAccountDto(new ResponceId(uuid));
            } else {
                return new ValidationError(ResponceStatus.WARN,
                        String.format("В системе уже зарегистрирован счёт %s у брокера %s", dto.number(), dto.broker()), null);
            }
        } else {
            return new ValidationError(ResponceStatus.WARN,
                    String.format("Нет счёта с идентификатором %s", dto.id()), null);
        }
    }

    private void validationDto(String name, String broker, String number, List<FieldValidationError> responce) {
        if (name.isEmpty()) {
            responce.add(new FieldValidationError("name","Поле не может быть пустым"));
        }
        if (broker.isEmpty()) {
            responce.add(new FieldValidationError("broker","Поле не может быть пустым"));
        }
        if (number.isEmpty()) {
            responce.add(new FieldValidationError("number","Поле не может быть пустым"));
        }
    }
}

