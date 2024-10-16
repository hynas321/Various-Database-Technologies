package org.example.Services.Interfaces;

import org.example.Entities.Account;

import java.util.List;
import java.util.Optional;

public interface IAccountService {
    Optional<Account> createAccount(String email, String password, boolean isAdmin);
    boolean deleteAccount(Long userId);
    boolean updateAccount(Account account);
    Optional<Account> getAccountById(Long userId);
    List<Account> getAllAccounts();
}