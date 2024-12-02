package org.example.Services;

import org.bson.types.ObjectId;
import org.example.Entities.Account;
import org.example.Entities.Admin;
import org.example.Entities.User;
import org.example.Repositories.EntityRepository;
import org.example.Services.Interfaces.IAccountService;

import java.util.List;

public class AccountService implements IAccountService {
    private final EntityRepository<Account> accountRepository;

    public AccountService(EntityRepository<Account> accountRepository) {
        this.accountRepository = accountRepository;
    }

    @Override
    public Account createAccount(String email, String password, boolean isAdmin) {
        try {
            if (accountRepository.getAll().stream().anyMatch(u -> u.getEmail().equals(email))) {
                return null;
            }

            Account account = isAdmin ? new Admin(email, password) : new User(email, password);
            accountRepository.create(account);

            return account;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public boolean deleteAccount(ObjectId accountId) {
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
    public Account getAccountById(ObjectId accountId) {
        return accountRepository.getById(accountId);
    }

    @Override
    public List<Account> getAllAccounts() {
        return accountRepository.getAll();
    }
}
