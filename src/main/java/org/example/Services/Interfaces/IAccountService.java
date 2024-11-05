package org.example.Services.Interfaces;

import org.example.Entities.Account;

import java.util.List;
import java.util.UUID;

public interface IAccountService {
    Account createAccount(String email, String password, boolean isAdmin);
    boolean deleteAccount(UUID accountId);
    boolean updateAccount(Account account);
    Account getAccountById(UUID accountId);
    List<Account> getAllAccounts();
}
