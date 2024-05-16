package ru.gubber.portfoliohistory.account.service;

import java.util.UUID;

public interface AccountService {
    UUID createAccount(String name, String broker, String number);

    UUID updateAccount(String id, String name, String broker, String number);
}
