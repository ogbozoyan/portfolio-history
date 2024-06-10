package ru.gubber.portfoliohistory.operation.controller;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.gubber.portfoliohistory.common.dto.ResponseId;
import ru.gubber.portfoliohistory.common.dto.ResponseStatus;
import ru.gubber.portfoliohistory.account.dto.ValidationError;
import ru.gubber.portfoliohistory.common.dto.SuccessResponseDto;
import ru.gubber.portfoliohistory.common.utils.FieldValidationError;
import ru.gubber.portfoliohistory.operation.dto.IncomeOperationDto;
import ru.gubber.portfoliohistory.operation.service.OperationService;
import ru.gubber.portfoliohistory.operation.service.OperationStatus;
import ru.gubber.portfoliohistory.operation.service.WithdrawalResult;

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
    @DisplayName("При вводе некорректных данных счета- accountId.isEmpty возвращается ошибка валидации")
    void replenishAccount_whenAccountIdIsEmpty_thenReturnValidationError() {
        List<FieldValidationError> response = new ArrayList<>();
        response.add(new FieldValidationError("accountId","Поле не может быть пустым"));
        ValidationError responseError = new ValidationError(ResponseStatus.ERROR, "Не правильный запрос", response);
        ValidationError result = (ValidationError) operationController.replenishAccount(new IncomeOperationDto("", amount));
        Assertions.assertEquals(responseError.getErrorMessage(), result.getErrorMessage());
    }

    @Test
    @DisplayName("При вводе некорректных данных счета- amount null - возвращается ошибка валидации")
    void replenishAccount_whenAmountIsNull_thenReturnValidationError() {
        List<FieldValidationError> response = new ArrayList<>();
        response.add(new FieldValidationError("amount","Поле не может быть пустым"));
        ValidationError responseError = new ValidationError(ResponseStatus.ERROR, "Не правильный запрос", response);
        ValidationError result = (ValidationError) operationController.replenishAccount(new IncomeOperationDto(accoundUUID.toString(), null));
        Assertions.assertEquals(responseError.getErrorMessage(), result.getErrorMessage());
    }

    @Test
    @DisplayName("Вызывается сервис")
    void replenishAccount_thenUseService() {
        Mockito.when(mockOperationService.replenishAccount(any(), any())).thenReturn(operationUUID);

        operationController.replenishAccount(new IncomeOperationDto(accoundUUID.toString(), amount));
        verify(mockOperationService).replenishAccount(any(), any());
    }

    @Test
    @DisplayName("При корректных значениях dto возвращается ответ с корректным uuid")
    void replenishAccount_thenReturnUUID() {
        Mockito.when(mockOperationService.replenishAccount(any(), any())).thenReturn(operationUUID);

        SuccessResponseDto<ResponseId> outcomeOperationDto = (SuccessResponseDto<ResponseId>) operationController.replenishAccount(new IncomeOperationDto(accoundUUID.toString(), amount));
        ResponseId result = (ResponseId) outcomeOperationDto.getResponse();

        Assertions.assertEquals(operationUUID, result.id());
    }

    @Test
    @DisplayName("При условии что из сервис возвращается ответ null - тогда вернуть Error ")
    void replenishAccount_whenUUIDisNull_thenReturnError() {
        Mockito.when(mockOperationService.replenishAccount(any(), any())).thenReturn(null);
        ValidationError validationError = new ValidationError(ResponseStatus.WARN,
                String.format("На счет %s не удалось добавить актив", accoundUUID.toString()), null);
        ValidationError resultError = (ValidationError) operationController.replenishAccount(new IncomeOperationDto(accoundUUID.toString(), amount));
        Assertions.assertEquals(validationError.getErrorMessage(), resultError.getErrorMessage());
    }

    @Test
    @DisplayName("Вывод средств - при вводе некорректных данных счета- accountId.isEmpty возвращается ошибка валидации")
    void withdrawFromAccount_whenAccountIdIsEmpty_thenReturnValidationError() {
        List<FieldValidationError> response = new ArrayList<>();
        response.add(new FieldValidationError("accountId","Поле не может быть пустым"));
        ValidationError responseError = new ValidationError(ResponseStatus.ERROR, "Не правильный запрос", response);
        ValidationError result = (ValidationError) operationController.withdrawFromAccount(new IncomeOperationDto("", amount));
        Assertions.assertEquals(responseError.getErrorMessage(), result.getErrorMessage());
    }

    @Test
    @DisplayName("Вывод средств - при вводе некорректных данных счета- accountId.isEmpty возвращается ошибка валидации")
    void withdrawFromAccount_whenAmountIsNull_thenReturnValidationError() {
        List<FieldValidationError> response = new ArrayList<>();
        response.add(new FieldValidationError("amount","Поле не может быть пустым"));
        ValidationError responseError = new ValidationError(ResponseStatus.ERROR, "Не правильный запрос", response);
        ValidationError result = (ValidationError) operationController.withdrawFromAccount(new IncomeOperationDto(accoundUUID.toString(), null));
        Assertions.assertEquals(responseError.getErrorMessage(), result.getErrorMessage());
    }

    @Test
    @DisplayName("Вызывается сервис")
    void withdrawFromAccount_thenUseService() {
        WithdrawalResult withdrawalResult = new WithdrawalResult(operationUUID, OperationStatus.SUCCESSFULLY);
        Mockito.when(mockOperationService.withdrawFromAccount(anyString(), any())).thenReturn(withdrawalResult);

        operationController.withdrawFromAccount(new IncomeOperationDto(accoundUUID.toString(), amount));
        verify(mockOperationService).withdrawFromAccount(accoundUUID.toString(), amount);
    }

    @Test
    @DisplayName("При выводе средств при корректных значениях dto возвращается ответ с корректным uuid")
    void withdrawFromAccount_thenReturnUUID() {
        WithdrawalResult withdrawalResult = new WithdrawalResult(operationUUID, OperationStatus.SUCCESSFULLY);

        Mockito.when(mockOperationService.withdrawFromAccount(anyString(), any())).thenReturn(withdrawalResult);

        SuccessResponseDto<ResponseId> outcomeOperationDto = (SuccessResponseDto<ResponseId>) operationController.withdrawFromAccount(new IncomeOperationDto(accoundUUID.toString(), amount));
        ResponseId result = (ResponseId) outcomeOperationDto.getResponse();

        Assertions.assertEquals(operationUUID, result.id());
    }

    @Test
    @DisplayName("При выводе средств при условии что из сервис возвращается ответ ITEM_NOT_FOUND - тогда вернуть Error ")
    void withdrawFromAccount_whenITEM_NOT_FOUND_thenReturnError() {
        WithdrawalResult withdrawalResult = new WithdrawalResult(operationUUID, OperationStatus.ITEM_NOT_FOUND);
        Mockito.when(mockOperationService.withdrawFromAccount(anyString(), any())).thenReturn(withdrawalResult);
        ValidationError validationError = new ValidationError(ResponseStatus.ERROR,
                String.format("Нет счёта с идентификатором %s", accoundUUID.toString()), null);
        ValidationError resultError = (ValidationError) operationController.withdrawFromAccount(new IncomeOperationDto(accoundUUID.toString(), amount));
        Assertions.assertEquals(validationError.getErrorMessage(), resultError.getErrorMessage());
    }

    @Test
    @DisplayName("При выводе средств при условии что из сервис возвращается ответ NOT_ENOUGH_FUNDS - тогда вернуть Error ")
    void withdrawFromAccount_whenNOT_ENOUGH_FUNDS_thenReturnError() {
        WithdrawalResult withdrawalResult = new WithdrawalResult(operationUUID, OperationStatus.NOT_ENOUGH_FUNDS);
        Mockito.when(mockOperationService.withdrawFromAccount(anyString(), any())).thenReturn(withdrawalResult);
        ValidationError validationError = new ValidationError(ResponseStatus.ERROR,
                String.format("На счете не достаточно средств", accoundUUID.toString()), null);
        ValidationError resultError = (ValidationError) operationController.withdrawFromAccount(new IncomeOperationDto(accoundUUID.toString(), amount));
        Assertions.assertEquals(validationError.getErrorMessage(), resultError.getErrorMessage());
    }
}