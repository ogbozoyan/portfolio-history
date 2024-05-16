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
            if (dto.name().isEmpty()) {
                responce.add(new FieldValidationError("name","Поле не может быть пустым"));
            }
            if (dto.broker().isEmpty()) {
                responce.add(new FieldValidationError("broker","Поле не может быть пустым"));
            }
            if (dto.number().isEmpty()) {
                responce.add(new FieldValidationError("number","Поле не может быть пустым"));
            }
            return new ValidationError(ResponceStatus.ERROR, "Не правильный запрос", responce);
        }
        UUID uuid = accountService.createAccount(dto.name(), dto.broker(), dto.number());
        if (uuid != null) {
            return new OutcomeAccountDto(new ResponceId(uuid));
        } else {
            return new ValidationError(ResponceStatus.WARN, String.format("В системе уже зарегистрирован счёт %s у брокера %s", dto.number(), dto.broker()), null);
        }

    }
}