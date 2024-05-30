package ru.gubber.portfoliohistory.account.service;

import ru.gubber.portfoliohistory.account.model.Account;

import java.util.List;
import java.util.UUID;

public interface AccountService {
    UUID createAccount(String name, String broker, String number);

    UpdatingResult updateAccount(String id, String name, String broker, String number);

    UUID deleteAccount(String id);

    List<Account> getAccountsList();

    Account getAccountsInfo(String id);

    boolean accountExists(UUID accountUuid);

    double setCurrentBalance(UUID accountUuid, double amount);
}
