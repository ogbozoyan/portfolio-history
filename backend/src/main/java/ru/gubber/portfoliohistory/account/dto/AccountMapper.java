package ru.gubber.portfoliohistory.account.dto;

import ru.gubber.portfoliohistory.account.model.Account;

public class AccountMapper {
    public static AccountDto toAccountDto(Account account) {
        return new AccountDto(account.getId().toString(),
                account.getName(),
                account.getBroker(),
                account.getNumber(),
                account.getCurrentBalance());
    }
}
