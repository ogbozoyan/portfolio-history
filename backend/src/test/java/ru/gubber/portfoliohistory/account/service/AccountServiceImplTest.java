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

import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class AccountServiceImplTest {
    @InjectMocks
    AccountServiceImpl accountService;
    @Mock
    AccountRepository mockRepository;
    private UUID uuid1 = UUID.fromString("f21c831f-9807-4de5-88c7-61cfe33e1c46");
    private Account validAccount1 = new Account(uuid1, "БКС1", "БКС", "01");

    @Test
    @DisplayName("При создании счета с именем и номером, которые уже существуют у другого счета в базе, получение null.")
    void createAccount_thenReturnUUID() {
        Mockito.when(mockRepository.existsByNameAndNumber(anyString(), anyString())).thenReturn(true);

        UUID uuid = accountService.createAccount("БКС1", "БКС", "01");

        Assertions.assertEquals(null, uuid);
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

    /*@Test
    void deleteAccount() {
    }*/

    /*@Test
    void getAccountsList() {
    }*/

    /*@Test
    void getAccountsInfo() {
    }*/
}