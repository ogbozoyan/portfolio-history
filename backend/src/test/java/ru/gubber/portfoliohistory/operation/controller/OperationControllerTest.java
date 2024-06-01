package ru.gubber.portfoliohistory.operation.controller;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.gubber.portfoliohistory.account.dto.ResponseStatus;
import ru.gubber.portfoliohistory.account.dto.ValidationError;
import ru.gubber.portfoliohistory.common.utils.FieldValidationError;
import ru.gubber.portfoliohistory.operation.dto.OperationDto;
import ru.gubber.portfoliohistory.operation.dto.OutcomeOperationDto;
import ru.gubber.portfoliohistory.operation.dto.ResultOperationId;
import ru.gubber.portfoliohistory.operation.service.OperationService;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class OperationControllerTest {
    @InjectMocks
    OperationController operationController;
    @Mock
    OperationService mockOperationService;
    UUID operationUUID = UUID.fromString("f99b9e41-4753-43ad-89cd-1874c3a35c90");
    UUID accoundUUID = UUID.fromString("f99b9e41-4753-43ad-89cd-1874c3a35c40");
    double amount = 205.1;

    @Test
    @DisplayName("При сохранении некорректных данных счета- accountId.isEmpty возвращается ошибка валидации")
    void replenishAccount_whenAccountIdIsEmpty_thenReturnValidationError() {
        List<FieldValidationError> response = new ArrayList<>();
        response.add(new FieldValidationError("accountId","Поле не может быть пустым"));
        ValidationError responseError = new ValidationError(ResponseStatus.ERROR, "Не правильный запрос", response);
        ValidationError result = (ValidationError) operationController.replenishAccount(new OperationDto("", amount));
        Assertions.assertEquals(responseError.getErrorMessage(), result.getErrorMessage());
    }

    @Test
    @DisplayName("При сохранении некорректных данных счета- amount null - возвращается ошибка валидации")
    void replenishAccount_whenAmountIsNull_thenReturnValidationError() {
        List<FieldValidationError> response = new ArrayList<>();
        response.add(new FieldValidationError("amount","Поле не может быть пустым"));
        ValidationError responseError = new ValidationError(ResponseStatus.ERROR, "Не правильный запрос", response);
        ValidationError result = (ValidationError) operationController.replenishAccount(new OperationDto(accoundUUID.toString(), null));
        Assertions.assertEquals(responseError.getErrorMessage(), result.getErrorMessage());
    }

    @Test
    @DisplayName("Вызывается сервис")
    void replenishAccount_thenUseService() {
        Mockito.when(mockOperationService.replenishAccount(anyString(), any())).thenReturn(operationUUID);

        operationController.replenishAccount(new OperationDto(accoundUUID.toString(), amount));
        verify(mockOperationService).replenishAccount(anyString(), any());
    }

    @Test
    @DisplayName("При корректных значениях dto возвращается ответ с корректным uuid")
    void replenishAccount_thenReturnUUID() {
        Mockito.when(mockOperationService.replenishAccount(anyString(), any())).thenReturn(operationUUID);

        OutcomeOperationDto outcomeOperationDto = (OutcomeOperationDto) operationController.replenishAccount(new OperationDto(accoundUUID.toString(), amount));
        ResultOperationId result = (ResultOperationId) outcomeOperationDto.getResponse();

        Assertions.assertEquals(operationUUID, result.id());
    }

    @Test
    @DisplayName("При условии что из сервис возвращается ответ null - тогда вернуть Error ")
    void replenishAccount_whenUUIDisNull_thenReturnError() {
        Mockito.when(mockOperationService.replenishAccount(anyString(), any())).thenReturn(null);
        ValidationError validationError = new ValidationError(ResponseStatus.WARN,
                String.format("На счет %s не удалось добавить актив", accoundUUID.toString()), null);
        ValidationError resultError = (ValidationError) operationController.replenishAccount(new OperationDto(accoundUUID.toString(), amount));
        Assertions.assertEquals(validationError.getErrorMessage(), resultError.getErrorMessage());
    }
}