package org.example.Services.Interfaces;

import org.bson.types.ObjectId;
import org.example.Entities.Account;

import java.util.List;
import java.util.Optional;

public interface IAccountService {
    Account createAccount(String email, String password, boolean isAdmin);
    boolean deleteAccount(ObjectId accountId);
    boolean updateAccount(Account account);
    Account getAccountById(ObjectId accountId);
    List<Account> getAllAccounts();
}