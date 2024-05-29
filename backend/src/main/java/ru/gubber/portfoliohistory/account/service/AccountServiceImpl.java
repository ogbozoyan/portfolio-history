package ru.gubber.portfoliohistory.account.service;

import org.springframework.stereotype.Service;
import ru.gubber.portfoliohistory.account.model.Account;
import ru.gubber.portfoliohistory.account.repository.AccountRepository;
import ru.gubber.portfoliohistory.common.utils.StringUtils;

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
        String newName = StringUtils.cut(name, 50);
        String newBroker = StringUtils.cut(broker, 100);
        String newNumber = StringUtils.cut(number, 50);
        Account newAccount = repository.save(new Account(UUID.randomUUID(), newName, newBroker, newNumber));
        return newAccount.getId();
    }

    @Override
    public UpdatingResult updateAccount(String id, String name, String broker, String number) {
        UUID uuid = UUID.fromString(id);
        Optional<Account> optionalAccount = repository.findById(uuid);
        if (optionalAccount.isEmpty()) {
            return new UpdatingResult(uuid, UpdateStatus.ITEM_NOT_FOUND);
        }
        Account account = optionalAccount.get();
        Optional<Account> byBrokerAndNumber = repository.findByBrokerAndNumber(broker, number);
        if ((byBrokerAndNumber.isPresent()) && (!byBrokerAndNumber.get().getId().equals(uuid))) {
            return new UpdatingResult(uuid, UpdateStatus.UNSUCCESSFULLY);
        } else {
            String newName = StringUtils.cut(name, 50);
            String newBroker = StringUtils.cut(broker, 100);
            String newNumber = StringUtils.cut(number, 50);
            account.setName(newName);
            account.setBroker(newBroker);
            account.setNumber(newNumber);
            repository.save(account);
            return new UpdatingResult(uuid, UpdateStatus.SUCCESSFULLY);
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

    @Override
    public boolean accountExists(UUID accountUuid) {
        return repository.existsById(accountUuid);
    }

}
