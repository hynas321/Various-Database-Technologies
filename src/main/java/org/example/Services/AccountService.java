package org.example.Services;

import org.example.Entities.Account;
import org.example.Entities.Admin;
import org.example.Entities.User;
import org.example.Repositories.Interfaces.EntityRepository;
import org.example.Services.Interfaces.IAccountService;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.util.List;
import java.util.Optional;

public class AccountService implements IAccountService {
    private final EntityRepository<Account> accountRepository;
    private final Session session;

    public AccountService(EntityRepository<Account> accountRepository, Session session) {
        this.accountRepository = accountRepository;
        this.session = session;
    }

    @Override
    public Optional<Account> createAccount(String email, String password, boolean isAdmin) {
        Transaction transaction = session.beginTransaction();

        try {
            if (accountRepository.getAll().stream().anyMatch(u -> u.getEmail().equals(email))) {
                transaction.rollback();
                return Optional.empty();
            }

            Account account = isAdmin ? new Admin(email, password) : new User(email, password);
            accountRepository.create(account);
            transaction.commit();
            return Optional.of(account);
        } catch (Exception e) {
            transaction.rollback();
            e.printStackTrace();
            return Optional.empty();
        }
    }

    @Override
    public boolean deleteAccount(Long accountId) {
        Transaction transaction = session.beginTransaction();

        try {
            Account account = accountRepository.getById(accountId);
            if (account == null) {
                transaction.rollback();
                return false;
            }

            accountRepository.delete(account);
            transaction.commit();
            return true;
        } catch (Exception e) {
            transaction.rollback();
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public boolean updateAccount(Account account) {
        Transaction transaction = session.beginTransaction();

        try {
            Account existingAccount = accountRepository.getById(account.getId());
            if (existingAccount == null) {
                transaction.rollback();
                return false;
            }

            accountRepository.update(account);
            transaction.commit();
            return true;
        } catch (Exception e) {
            transaction.rollback();
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public Optional<Account> getAccountById(Long accountId) {
        return Optional.ofNullable(accountRepository.getById(accountId));
    }

    @Override
    public List<Account> getAllAccounts() {
        return accountRepository.getAll();
    }
}
