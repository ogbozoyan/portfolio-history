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
import ru.gubber.portfoliohistory.account.service.UpdateStatus;
import ru.gubber.portfoliohistory.account.service.UpdatingResult;

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
    private AccountMapper mapper = new AccountMapper();

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
        UpdatingResult updatingResult = new UpdatingResult(uuid3, UpdateStatus.SUCCESSFULLY);
        Mockito.when(mockAccountService.updateAccount(uuid3.toString(),"БКС3", "БКС", "03")).thenReturn(updatingResult);

        accountController.updateAccount(new IncomeFullAccountDto("f21c831f-9807-4de5-88c7-61cfe33e1c47", "БКС3", "БКС", "03"));
        verify(mockAccountService).updateAccount(uuid3.toString(),"БКС3", "БКС", "03");
    }

    @Test
    @DisplayName("При обновлении некорректных данных счета - idDto.isEmpty- возвращается ошибка валидации")
    void updateAccount_whenIdDtoIsEmpty_thenReturnValidationError() {
        List<FieldValidationError> responce = new ArrayList<>();
        responce.add(new FieldValidationError("id","Поле не может быть пустым"));
        ValidationError responceError = new ValidationError(ResponceStatus.ERROR, "Не правильный запрос", responce);
        ValidationError result = (ValidationError) accountController.updateAccount(new IncomeFullAccountDto("", "БКС3", "БКС", "03"));
        Assertions.assertEquals(responceError.getErrorMessage(), result.getErrorMessage());
    }

    @Test
    @DisplayName("При обновлении данных счета возвращается корректный UUID")
    void updateAccount_whenDtoIsValid_thenReturnUUID() {
        UUID uuid3 = UUID.fromString("f21c831f-9807-4de5-88c7-61cfe33e1c47");
        UpdatingResult updatingResult = new UpdatingResult(uuid3, UpdateStatus.SUCCESSFULLY);

        Mockito.when(mockAccountService.updateAccount(anyString(), anyString(), anyString(), anyString())).thenReturn(updatingResult);

        IdOutcomeAccountDto responceResult = (IdOutcomeAccountDto) accountController.updateAccount(new IncomeFullAccountDto(uuid3.toString(), "БКС3", "БКС", "03"));
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
        ValidationError result = (ValidationError) accountController.updateAccount(new IncomeFullAccountDto("f21c831f-9807-4de5-88c7-61cfe33e1c50", "БКС3", "БКС", "БКС3"));
        Assertions.assertEquals(validationError.getErrorMessage(), result.getErrorMessage());
    }

    @Test
    @DisplayName("При значении статуса UNSUCCESSFULLY, полученного из сервиса, возвращается ответ - Error")
    void updateAccount_whenUUINotEqualsIdDto_thenReturnError() {
        UUID uuid3 = UUID.fromString("f21c831f-9807-4de5-88c7-61cfe33e1c47");
        UpdatingResult updatingResult = new UpdatingResult(uuid3, UpdateStatus.UNSUCCESSFULLY);

        Mockito.when(mockAccountService.updateAccount(anyString(), anyString(), anyString(), anyString())).thenReturn(updatingResult);
        ValidationError validationError = new ValidationError(ResponceStatus.WARN,
                String.format("В системе уже зарегистрирован счёт %s у брокера %s", "04", "БКС"), null);
        ValidationError result = (ValidationError) accountController.updateAccount(new IncomeFullAccountDto(uuid3.toString(), "a", "БКС", "04"));
        Assertions.assertEquals(validationError.getErrorMessage(), result.getErrorMessage());
    }

    @Test
    @DisplayName("Вызывается сервис")
    void deleteAccount_thenUseService() {
        UUID uuid3 = UUID.fromString("f21c831f-9807-4de5-88c7-61cfe33e1c47");

        Mockito.when(mockAccountService.deleteAccount(uuid3.toString())).thenReturn(uuid3);

        accountController.deleteAccount(new IdIncomeAccountDto(uuid3.toString()));

        verify(mockAccountService).deleteAccount(uuid3.toString());
    }

    @Test
    @DisplayName("При удалении счета со значением - idDto.isEmpty- возвращается ошибка валидации")
    void deleteAccount_whenIdDtoIsEmpty_thenReturnValidationError() {
        List<FieldValidationError> responce = new ArrayList<>();
        responce.add(new FieldValidationError("id","Поле не может быть пустым"));
        ValidationError responceError = new ValidationError(ResponceStatus.ERROR, "Не правильный запрос", responce);
        ValidationError result = (ValidationError) accountController.deleteAccount(new IdIncomeAccountDto(""));
        Assertions.assertEquals(responceError.getErrorMessage(), result.getErrorMessage());
    }

    @Test
    @DisplayName("При удалении счета при получении из сервиса значения null возвращается Error")
    void deleteAccount_whenUUIDisNull_thenReturnError() {
        Mockito.when(mockAccountService.deleteAccount(anyString())).thenReturn(null);
        ValidationError validationError = new ValidationError(ResponceStatus.WARN,
                String.format("Нет счёта с идентификатором %s", "f21c831f-9807-4de5-88c7-61cfe33e1c50"), null);
        ValidationError result = (ValidationError) accountController.deleteAccount(new IdIncomeAccountDto("f21c831f-9807-4de5-88c7-61cfe33e1c50"));
        Assertions.assertEquals(validationError.getErrorMessage(), result.getErrorMessage());
    }

    @Test
    @DisplayName("Вызывается сервис")
    void getAccountsList_thenUseService() {
        List<Account> accounts = new ArrayList<>();
        accounts.add(validAccount1);
        accounts.add(validAccount2);
        Mockito.when(mockAccountService.getAccountsList()).thenReturn(accounts);
        accountController.getAccountsList();

        verify(mockAccountService).getAccountsList();
    }

    @Test
    @DisplayName("При получении списка счетов получаем список dto")
    void getAccountsList_thenReturnDtoList() {
        List<Account> accounts = new ArrayList<>();
        accounts.add(validAccount1);
        accounts.add(validAccount2);
        Mockito.when(mockAccountService.getAccountsList()).thenReturn(accounts);
        List<AccountDto> dtos = accounts.stream().map(mapper::toAccountDto).toList();

        OutcomeAccountDto responce = (OutcomeAccountDto)accountController.getAccountsList();
        List<AccountDto> resultDtos = (List<AccountDto>) responce.getResponce();

        Assertions.assertEquals(dtos.size(), resultDtos.size());
    }

    @Test
    @DisplayName("При получении списка счетов - dtos.isEmpty- возвращается ошибка валидации")
    void getAccountsList_whenIdDtoIsEmpty_thenReturnValidationError() {
        ValidationError responceError = new ValidationError(ResponceStatus.ERROR, "Список счетов пуст.", null);
        ValidationError result = (ValidationError) accountController.getAccountsList();
        Assertions.assertEquals(responceError.getErrorMessage(), result.getErrorMessage());
    }

    @Test
    @DisplayName("При получении информации о счете получает dto")
    void getAccountsInfo_thenReturnDto() {
        Mockito.when(mockAccountService.getAccountsInfo(anyString())).thenReturn(validAccount1);

        BaseResponce accountsInfo = accountController.getAccountsInfo(new IdIncomeAccountDto(validAccount1.getId().toString()));
        AccountDto responce = (AccountDto) accountsInfo.getResponce();

        Assertions.assertAll(
                () -> {
                    Assertions.assertEquals(validAccount1.getId().toString(), responce.id());
                    Assertions.assertEquals(validAccount1.getName(), responce.name());
                    Assertions.assertEquals(validAccount1.getBroker(), responce.broker());
                    Assertions.assertEquals(validAccount1.getNumber(), responce.number());
                }
        );
    }

    @Test
    @DisplayName("При получении информации о счете со значением - idDto.isEmpty- возвращается ошибка валидации")
    void getAccountsInfo_whenIdDtoIsEmpty_thenReturnValidationError() {
        List<FieldValidationError> responce = new ArrayList<>();
        responce.add(new FieldValidationError("id","Поле не может быть пустым"));
        ValidationError responceError = new ValidationError(ResponceStatus.ERROR, "Не правильный запрос", responce);
        ValidationError result = (ValidationError) accountController.getAccountsInfo(new IdIncomeAccountDto(""));
        Assertions.assertEquals(responceError.getErrorMessage(), result.getErrorMessage());
    }

    @Test
    @DisplayName("При получении информации о счете при получении из сервиса значения null возвращается Error")
    void getAccountsInfo_whenUUIDisNull_thenReturnError() {
        Mockito.when(mockAccountService.getAccountsInfo(anyString())).thenReturn(null);
        ValidationError validationError = new ValidationError(ResponceStatus.WARN,
                String.format("Нет счёта с идентификатором %s", "f21c831f-9807-4de5-88c7-61cfe33e1c50"), null);
        ValidationError result = (ValidationError) accountController.getAccountsInfo(new IdIncomeAccountDto("f21c831f-9807-4de5-88c7-61cfe33e1c50"));
        Assertions.assertEquals(validationError.getErrorMessage(), result.getErrorMessage());
    }
}