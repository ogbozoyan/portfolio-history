package ru.gubber.portfoliohistory.account.controller;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.gubber.portfoliohistory.account.dto.*;
import ru.gubber.portfoliohistory.account.model.Account;
import ru.gubber.portfoliohistory.account.service.AccountService;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class AccountControllerTest {
    @InjectMocks
    private AccountController accountController;
    @Mock
    private AccountService mockAccountService;
    private UUID uuid1 = UUID.fromString("f21c831f-9807-4de5-88c7-61cfe33e1c46");
    private UUID uuid2 = UUID.fromString("b1acc051-ea56-4ffc-a249-67010f8dd132");
    private Account validAccount1 = new Account(uuid1, "БКС1", "БКС", "01");
    private Account validAccount2 = new Account(uuid2, "БКС2", "БКС", "02");

    @Test
    @DisplayName("Вызывается сервис")
    void createAccount_thenUseService() {
        UUID uuid3 = UUID.fromString("f21c831f-9807-4de5-88c7-61cfe33e1c47");
        Account account3 = new Account(uuid3, "БКС3", "БКС", "03");

        Mockito.when(mockAccountService.createAccount("БКС3", "БКС", "03")).thenReturn(account3.getId());

        accountController.createAccount(new IncomeAccountDto("БКС3", "БКС", "03"));
        verify(mockAccountService).createAccount("БКС3", "БКС", "03");
    }

    @Test
    @DisplayName("Возвращается ответ с корректным UUID")
    void createAccount_thenReturnUUID() {
        UUID uuid3 = UUID.fromString("f21c831f-9807-4de5-88c7-61cfe33e1c47");
        Account account3 = new Account(uuid3, "БКС3", "БКС", "03");

        Mockito.when(mockAccountService.createAccount("БКС3", "БКС", "03")).thenReturn(account3.getId());

        IdOutcomeAccountDto responceResult = (IdOutcomeAccountDto) accountController.createAccount(new IncomeAccountDto("БКС3", "БКС", "03"));
        ResponceId responceId = (ResponceId)responceResult.getResponce();
        UUID resultUUID = responceId.id();
        Assertions.assertEquals(account3.getId(), resultUUID);
    }

    @Test
    @DisplayName("При получении значения null из сервиса озвращается ответ - Error")
    void createAccount_whenUUIDisNull_thenReturnError() {
        Mockito.when(mockAccountService.createAccount(anyString(), anyString(), anyString())).thenReturn(null);
        ValidationError validationError = new ValidationError(ResponceStatus.WARN,
                String.format("В системе уже зарегистрирован счёт %s у брокера %s", "БКС3", "БКС"), null);
        ValidationError result = (ValidationError) accountController.createAccount(new IncomeAccountDto("БКС3", "БКС", "БКС3"));
        Assertions.assertEquals(validationError.getErrorMessage(), result.getErrorMessage());
    }

    @Test
    @DisplayName("При сохранении некорректных данных счета- name.isEmpty возвращается ошибка валидации")
    void createAccount_whenNameIsEmpty_thenReturnValidationError() {
        List<FieldValidationError> responce = new ArrayList<>();
        responce.add(new FieldValidationError("name","Поле не может быть пустым"));
        ValidationError responceError = new ValidationError(ResponceStatus.ERROR, "Не правильный запрос", responce);
        ValidationError result = (ValidationError) accountController.createAccount(new IncomeAccountDto("", "БКС", "03"));
        Assertions.assertEquals(responceError.getErrorMessage(), result.getErrorMessage());
    }

    @Test
    @DisplayName("При сохранении некорректных данных счета - broker.isEmpty возвращается ошибка валидации")
    void createAccount_whenBrokerIsEmpty_thenReturnValidationError() {
        List<FieldValidationError> responce = new ArrayList<>();
        responce.add(new FieldValidationError("broker","Поле не может быть пустым"));
        ValidationError responceError = new ValidationError(ResponceStatus.ERROR, "Не правильный запрос", responce);
        ValidationError result = (ValidationError) accountController.createAccount(new IncomeAccountDto("БКС3", "", "03"));
        Assertions.assertEquals(responceError.getErrorMessage(), result.getErrorMessage());
    }

    @Test
    @DisplayName("При сохранении некорректных данных счета - number.isEmpty возвращается ошибка валидации")
    void createAccount_whenNumberIsEmpty_thenReturnValidationError() {
        List<FieldValidationError> responce = new ArrayList<>();
        responce.add(new FieldValidationError("number","Поле не может быть пустым"));
        ValidationError responceError = new ValidationError(ResponceStatus.ERROR, "Не правильный запрос", responce);
        ValidationError result = (ValidationError) accountController.createAccount(new IncomeAccountDto("БКС3", "БКС", ""));
        Assertions.assertEquals(responceError.getErrorMessage(), result.getErrorMessage());
    }

    @Test
    @DisplayName("Вызывается сервис")
    void updateAccount_thenUseService() {
        UUID uuid3 = UUID.fromString("f21c831f-9807-4de5-88c7-61cfe33e1c47");
        Account account3 = new Account(uuid3, "БКС3", "БКС", "03");

        Mockito.when(mockAccountService.updateAccount(uuid3.toString(),"БКС3", "БКС", "03")).thenReturn(account3.getId());

        accountController.updateAccount(new IdIncomeAccountDto("f21c831f-9807-4de5-88c7-61cfe33e1c47"), new IncomeAccountDto("БКС3", "БКС", "03"));
        verify(mockAccountService).updateAccount(uuid3.toString(),"БКС3", "БКС", "03");
    }

    @Test
    @DisplayName("При обновлении некорректных данных счета - idDto.isEmpty- возвращается ошибка валидации")
    void updateAccount_whenIdDtoIsEmpty_thenReturnValidationError() {
        List<FieldValidationError> responce = new ArrayList<>();
        responce.add(new FieldValidationError("id","Поле не может быть пустым"));
        ValidationError responceError = new ValidationError(ResponceStatus.ERROR, "Не правильный запрос", responce);
        ValidationError result = (ValidationError) accountController.updateAccount(new IdIncomeAccountDto(""), new IncomeAccountDto("БКС3", "БКС", "03"));
        Assertions.assertEquals(responceError.getErrorMessage(), result.getErrorMessage());
    }

    @Test
    @DisplayName("При обновлении данных счета возвращается корректный UUID")
    void updateAccount_whenDtoIsValid_thenReturnUUID() {
        UUID uuid3 = UUID.fromString("f21c831f-9807-4de5-88c7-61cfe33e1c47");

        Mockito.when(mockAccountService.updateAccount(anyString(), anyString(), anyString(), anyString())).thenReturn(uuid3);

        IdOutcomeAccountDto responceResult = (IdOutcomeAccountDto) accountController.updateAccount(new IdIncomeAccountDto(uuid3.toString()), new IncomeAccountDto("БКС3", "БКС", "03"));
        ResponceId responce = (ResponceId) responceResult.getResponce();
        UUID resultUUID = responce.id();

        Assertions.assertEquals(uuid3, resultUUID);
    }

    @Test
    @DisplayName("При обновлении данных счета при получении из сервиса значения null возвращается Error")
    void updateAccount_whenUUIDisNull_thenReturnError() {
        Mockito.when(mockAccountService.updateAccount(anyString(), anyString(), anyString(), anyString())).thenReturn(null);
        ValidationError validationError = new ValidationError(ResponceStatus.WARN,
                String.format("Нет счёта с идентификатором %s", "f21c831f-9807-4de5-88c7-61cfe33e1c50"), null);
        ValidationError result = (ValidationError) accountController.updateAccount(new IdIncomeAccountDto("f21c831f-9807-4de5-88c7-61cfe33e1c50"), new IncomeAccountDto("БКС3", "БКС", "БКС3"));
        Assertions.assertEquals(validationError.getErrorMessage(), result.getErrorMessage());
    }
    /*@Test
    void deleteAccount() {
    }*/

    /*@Test
    void getAccountsList() {
    }*/
}