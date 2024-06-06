package ru.gubber.portfoliohistory.operation.service;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.gubber.portfoliohistory.account.service.AccountService;
import ru.gubber.portfoliohistory.purchasedasset.service.PurchasedAssetService;
import ru.gubber.portfoliohistory.operation.model.Operation;
import ru.gubber.portfoliohistory.operation.repository.OperationRepository;

import java.util.UUID;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class OperationServiceImplTest {
    @InjectMocks
    OperationServiceImpl operationService;
    @Mock
    OperationRepository mockRepository;
    @Mock
    AccountService mockAccountService;
    @Mock
    PurchasedAssetService mockPurchasedAssetService;
    UUID accoundUUID = UUID.fromString("f99b9e41-4753-43ad-89cd-1874c3a35c40");
    @Captor
    ArgumentCaptor<Operation> operationArgumentCaptor;
    private static final String ASSET_CODE = "RUR";

    @Test
    @DisplayName("При существующем id счета возвращается uuid операции")
    public void replenishAccount_whenAccountExists_thenReturnOperationUUID() {
        double amount = 105.3;
        Mockito.when(mockAccountService.accountExists(any())).thenReturn(true);
        Mockito.when(mockRepository.save(any())).thenAnswer(invocationOnMock ->
                invocationOnMock.getArgument(0));

        UUID resultUuid = operationService.replenishAccount(accoundUUID.toString(), amount);
        verify(mockAccountService).changeCurrentBalance(accoundUUID, amount);
        verify(mockRepository).save(operationArgumentCaptor.capture());
        verify(mockPurchasedAssetService).purchaseAsset(accoundUUID, ASSET_CODE, amount);
        Assertions.assertEquals(operationArgumentCaptor.getValue().getId(), resultUuid);
    }

    @Test
    @DisplayName("При несуществующем id счета возвращается null")
    public void replenishAccount_whenAccountNotExists_thenReturnNull() {
        double amount = 105.3;
        Mockito.when(mockAccountService.accountExists(any())).thenReturn(false);

        UUID resultUuid = operationService.replenishAccount(accoundUUID.toString(), amount);
        Assertions.assertNull(resultUuid);
    }

    @Test
    @DisplayName("Вывод средств - при существующем id счета возвращается uuid операции")
    public void withdrawFromAccount_whenAccountExists_thenReturnOperationUUID() {
        double amount = 105.3;
        Mockito.when(mockAccountService.accountExists(any())).thenReturn(true);
        Mockito.when(mockPurchasedAssetService.sellAsset(any(), anyString(), anyDouble())).thenReturn(true);
        Mockito.when(mockRepository.save(any())).thenAnswer(invocationOnMock ->
                invocationOnMock.getArgument(0));

        WithdrawalResult withdrawalResult = operationService.withdrawFromAccount(accoundUUID.toString(), amount);
        verify(mockAccountService).changeCurrentBalance(accoundUUID, amount * -1);
        verify(mockPurchasedAssetService).sellAsset(accoundUUID, ASSET_CODE, amount);
        verify(mockRepository).save(operationArgumentCaptor.capture());
        Assertions.assertEquals(operationArgumentCaptor.getValue().getId(), withdrawalResult.uuid());
    }

    @Test
    @DisplayName("Вывод средств - при несуществующем id счета возвращается ITEM_NOT_FOUND")
    public void withdrawFromAccount_whenAccountNotExists_thenReturnITEM_NOT_FOUND() {
        double amount = 105.3;
        Mockito.when(mockAccountService.accountExists(any())).thenReturn(false);

        WithdrawalResult withdrawalResult = operationService.withdrawFromAccount(accoundUUID.toString(), amount);
        Assertions.assertEquals(OperationStatus.ITEM_NOT_FOUND.name(), withdrawalResult.status().name());
    }
}