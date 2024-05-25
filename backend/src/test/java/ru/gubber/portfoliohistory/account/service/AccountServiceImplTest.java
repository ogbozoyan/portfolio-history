package ru.gubber.portfoliohistory.account.service;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.gubber.portfoliohistory.account.model.Account;
import ru.gubber.portfoliohistory.account.repository.AccountRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class AccountServiceImplTest {
    @InjectMocks
    AccountServiceImpl accountService;
    @Mock
    AccountRepository mockRepository;
    private final UUID uuid1 = UUID.fromString("f21c831f-9807-4de5-88c7-61cfe33e1c46");
    private final Account validAccount1 = new Account(uuid1, "БКС1", "БКС", "01");

    @Test
    @DisplayName("При создании счета с именем и номером, которые уже существуют у другого счета в базе, получение null.")
    void createAccount_thenReturnUUID() {
        Mockito.when(mockRepository.existsByNameAndNumber(anyString(), anyString())).thenReturn(true);

        UUID uuid = accountService.createAccount("БКС1", "БКС", "01");

        Assertions.assertNull(uuid);
    }

    @Test
    @DisplayName("Успешное создание счета")
    void createAccount_whenNameAndNumberIsExists_thenReturnNull() {
        Mockito.when(mockRepository.save(any())).thenReturn(validAccount1);

        UUID uuid = accountService.createAccount("БКС1", "БКС", "01");

        verify(mockRepository).save(any());
        Assertions.assertEquals(uuid1, uuid);
    }

    @Test
    @DisplayName("При получении из репозитория значения OptionalIsEmpty вернуть ITEM_NOT_FOUND")
    void updateAccount_whenOptionalIsEmpty_thenReturnStatusITEM_NOT_FOUND() {
        Optional<Account> empty = Optional.empty();
        UpdatingResult updatingResult = new UpdatingResult(UUID.fromString("f21c831f-9807-4de5-88c7-61cfe33e1c46"), UpdateStatus.ITEM_NOT_FOUND);
        Mockito.when(mockRepository.findById(any())).thenReturn(empty);

        UpdatingResult result = accountService.updateAccount("f21c831f-9807-4de5-88c7-61cfe33e1c46", "БКС11", "БКС-", "011");

        Assertions.assertEquals(updatingResult.status(), result.status());
    }

    @Test
    @DisplayName("При получении из репозитория значения Optional isPresent вернуть UNSUCCESSFULLY")
    void updateAccount_whenOptionalIsPresent_thenReturnStatusUNSUCCESSFULLY() {
        Optional<Account> optionalAccount = Optional.of(validAccount1);
        UpdatingResult updatingResult = new UpdatingResult(UUID.fromString("f21c831f-9807-4de5-88c7-61cfe33e1c46"), UpdateStatus.UNSUCCESSFULLY);
        UUID uuid2 = UUID.fromString("f21c831f-9807-4de5-88c7-61cfe33e1c50");
        Account validAccountTemp = new Account(uuid2, "БКС1", "БКС", "01");


        Mockito.when(mockRepository.findById(any())).thenReturn(optionalAccount);
        Mockito.when(mockRepository.findByBrokerAndNumber(anyString(), anyString())).thenReturn(Optional.of(validAccountTemp));

        UpdatingResult result = accountService.updateAccount("f21c831f-9807-4de5-88c7-61cfe33e1c46", "БКС", "БКС", "01");

        Assertions.assertEquals(updatingResult.status(), result.status());
    }

    @Test
    @DisplayName("При получении из репозитория значения Optional isPresent вернуть SUCCESSFULLY")
    void updateAccount_whenOptionalIsPresent_thenReturnStatusSUCCESSFULLY() {
        Optional<Account> optionalAccount = Optional.of(validAccount1);
        UpdatingResult updatingResult = new UpdatingResult(UUID.fromString("f21c831f-9807-4de5-88c7-61cfe33e1c46"), UpdateStatus.SUCCESSFULLY);



        Mockito.when(mockRepository.findById(any())).thenReturn(optionalAccount);
        Mockito.when(mockRepository.findByBrokerAndNumber(anyString(), anyString())).thenReturn(optionalAccount);

        UpdatingResult result = accountService.updateAccount("f21c831f-9807-4de5-88c7-61cfe33e1c46", "БКС", "БКС", "01");

        Assertions.assertEquals(updatingResult.status(), result.status());
    }

    @Test
    @DisplayName("При заданом UUID счет не найден - вернуть null")
    void deleteAccount_whenAccountNotFound_thenReturnNull() {
        Mockito.when(mockRepository.existsById(any())).thenReturn(false);

        UUID uuid = accountService.deleteAccount("f21c831f-9807-4de5-88c7-61cfe33e1c90");

        Assertions.assertNull(uuid);
    }

    @Test
    @DisplayName("При заданом UUID счет найден (true) - вызывается метод репозитория")
    void deleteAccount_whenAccountIsFound_thenReturn() {
        Mockito.when(mockRepository.existsById(any())).thenReturn(true);

        accountService.deleteAccount("f21c831f-9807-4de5-88c7-61cfe33e1c90");
        verify(mockRepository, times(1)).deleteById(UUID.fromString("f21c831f-9807-4de5-88c7-61cfe33e1c90"));
    }

    @Test
    @DisplayName("Успешное получение списка счетов длиной 2.")
    void getAccountsList_thenReturnList() {
        List<Account> accounts = new ArrayList<>();
        UUID uuid2 = UUID.fromString("b1acc051-ea56-4ffc-a249-67010f8dd132");
        Account validAccount2 = new Account(uuid2, "БКС2", "БКС", "02");
        accounts.add(validAccount1);
        accounts.add(validAccount2);
        Mockito.when(mockRepository.findAll()).thenReturn(accounts);

        List<Account> accountsResult = accountService.getAccountsList();

        Assertions.assertEquals(accounts.size(), accountsResult.size());
    }

    @Test
    @DisplayName("Успешное получение пустого списка счетов при отсутствии счетов.")
    void getAccountsList_whenAccountsNotFound_thenReturnEmptyList() {
        List<Account> accounts = new ArrayList<>();
        Mockito.when(mockRepository.findAll()).thenReturn(accounts);

        List<Account> accountsResult = accountService.getAccountsList();

        Assertions.assertEquals(accounts.size(), accountsResult.size());
        Assertions.assertEquals(0, accountsResult.size());
    }

    @Test
    @DisplayName("Успешное получение информации о счете.")
    void getAccountsInfo_whenAccountIsFound_thenReturnAccount() {
        Optional<Account> optionalAccount = Optional.of(validAccount1);
        Mockito.when(mockRepository.findById(any())).thenReturn(optionalAccount);

        Account accountsInfo = accountService.getAccountsInfo(validAccount1.getId().toString());

        Assertions.assertAll(
                () -> {
                    assertEquals(validAccount1.getId(), accountsInfo.getId());
                    assertEquals(validAccount1.getName(), accountsInfo.getName());
                    assertEquals(validAccount1.getBroker(), accountsInfo.getBroker());
                    assertEquals(validAccount1.getNumber(), accountsInfo.getNumber());
                }
        );
    }
}