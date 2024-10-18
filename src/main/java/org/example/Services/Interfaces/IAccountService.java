package org.example.Services.Interfaces;

import org.example.Entities.Account;

import java.util.List;
import java.util.Optional;

public interface IAccountService {
    Account createAccount(String email, String password, boolean isAdmin);
    boolean deleteAccount(String accountId);
    boolean updateAccount(Account account);
    Account getAccountById(String accountId);
    List<Account> getAllAccounts();
}