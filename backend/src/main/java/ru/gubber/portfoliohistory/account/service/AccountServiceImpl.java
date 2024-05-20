package ru.gubber.portfoliohistory.account.service;

import org.springframework.stereotype.Service;
import ru.gubber.portfoliohistory.account.model.Account;
import ru.gubber.portfoliohistory.account.repository.AccountRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class AccountServiceImpl implements AccountService {
    private final AccountRepository repository;

    public AccountServiceImpl(AccountRepository repository) {
        this.repository = repository;
    }

    @Override
    public UUID createAccount(String name, String broker, String number) {
        if (repository.existsByNameAndNumber(name, number)) {
            return null;
        }
        Account newAccount = repository.save(new Account(UUID.randomUUID(), name, broker, number));
        return newAccount.getId();
    }

    @Override
    public UUID updateAccount(String id, String name, String broker, String number) {
        UUID uuid = UUID.fromString(id);
        if (!repository.existsById(uuid)) {
            return null;
        }
        UUID idByNameAndNumber = repository.findByNumber(number).getId();
        if (idByNameAndNumber != uuid) {
            return idByNameAndNumber;
        } else {
            repository.save(new Account(uuid, name, broker, number));
            return uuid;
        }
    }

    @Override
    public UUID deleteAccount(String id) {
        UUID uuid = UUID.fromString(id);
        if (!repository.existsById(uuid)) {
            return null;
        }
        repository.deleteById(uuid);
        return uuid;
    }

    @Override
    public List<Account> getAccountsList() {
        return repository.findAll();
    }

    @Override
    public Account getAccountsInfo(String id) {
        Optional<Account> optionalAccount = repository.findById(UUID.fromString(id));
        return optionalAccount.orElse(null);
    }
}
