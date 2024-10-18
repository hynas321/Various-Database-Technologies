package org.example.Mappers;

import org.bson.Document;
import org.example.Entities.Account;
import org.example.Entities.Admin;
import org.example.Entities.User;

import java.util.HashSet;

public class AccountMapper implements EntityMapper<Account> {

    public Document toDocument(Account account) {
        Document document = new Document("_id", account.getId())
                .append("email", account.getEmail())
                .append("password", account.getPassword())
                .append("postIds", account.getPostIds())
                .append("boardIds", account.getBoardIds());

        if (account instanceof User) {
            document.append("_class", "User");
        } else if (account instanceof Admin) {
            document.append("_class", "Admin");
        }

        return document;
    }

    public Account fromDocument(Document document) {
        String className = document.getString("_class");
        Account account;

        if ("User".equals(className)) {
            account = new User();
        } else if ("Admin".equals(className)) {
            account = new Admin();
        } else {
            throw new IllegalArgumentException("Unknown account type: " + className);
        }

        account.setId(document.getString("_id"));
        account.setEmail(document.getString("email"));
        account.setPassword(document.getString("password"));
        account.setPostIds(new HashSet<>(document.getList("postIds", String.class)));
        account.setBoardIds(new HashSet<>(document.getList("boardIds", String.class)));

        return account;
    }
}
