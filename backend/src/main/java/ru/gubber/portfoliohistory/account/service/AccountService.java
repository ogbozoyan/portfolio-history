package ru.gubber.portfoliohistory.account.service;

import ru.gubber.portfoliohistory.account.model.Account;

import java.util.List;
import java.util.UUID;

public interface AccountService {
    UUID createAccount(String name, String broker, String number);

    UpdatingResult updateAccount(UUID id, String name, String broker, String number);

    UUID deleteAccount(UUID id);

    List<Account> getAccountsList();

    Account getAccountsInfo(String id);

    boolean accountExists(UUID accountUuid);

    double changeCurrentBalance(UUID accountUuid, double amount);
}
