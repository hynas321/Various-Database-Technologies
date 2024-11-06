package org.example.Services;

import org.example.Entities.Account;
import org.example.Entities.Account.AccountType;
import org.example.Repositories.AccountRepository;
import org.example.Services.Interfaces.IAccountService;

import java.util.List;
import java.util.UUID;

public class AccountService implements IAccountService {
    private final AccountRepository accountRepository;

    public AccountService(AccountRepository accountRepository) {
        this.accountRepository = accountRepository;
    }

    @Override
    public Account createAccount(String email, String password, boolean isAdmin) {
        try {
            if (accountRepository.getAll().stream().anyMatch(u -> u.getEmail().equals(email))) {
                return null;
            }

            AccountType accountType = isAdmin ? AccountType.ADMIN : AccountType.USER;
            Account account = new Account(email, password, accountType);
            accountRepository.create(account);

            return account;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public boolean deleteAccount(UUID accountId) {
        try {
            Account account = accountRepository.getById(accountId);
            if (account == null) {
                return false;
            }

            accountRepository.delete(account);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public boolean updateAccount(Account account) {
        try {
            Account existingAccount = accountRepository.getById(account.getId());
            if (existingAccount == null) {
                return false;
            }

            accountRepository.update(account);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public Account getAccountById(UUID accountId) {
        return accountRepository.getById(accountId);
    }

    @Override
    public List<Account> getAllAccounts() {
        return accountRepository.getAll();
    }
}
