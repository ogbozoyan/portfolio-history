package ru.gubber.portfoliohistory.account.service;

import org.springframework.stereotype.Service;
import ru.gubber.portfoliohistory.account.dto.ResponceId;
import ru.gubber.portfoliohistory.account.model.Account;
import ru.gubber.portfoliohistory.account.repository.AccountRepository;

import java.util.UUID;

@Service
public class AccountServiceImpl implements AccountService {
    private final AccountRepository repository;

    public AccountServiceImpl(AccountRepository repository) {
        this.repository = repository;
    }

    @Override
    public String createAccount(String name, String broker, String number) {
        Account newAccount = repository.save(new Account(UUID.randomUUID(), name, broker, number));
        return new ResponceId(newAccount.getId().toString()).id();
    }
}
