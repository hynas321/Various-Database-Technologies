package org.example.Repositories;

import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;
import org.example.Entities.Account;
import org.example.Mappers.EntityMapper;

import java.util.ArrayList;
import java.util.List;

import static com.mongodb.client.model.Filters.eq;

public class AccountRepository implements EntityRepository<Account> {
    private final MongoCollection<Document> collection;
    private final EntityMapper<Account> accountMapper;

    public AccountRepository(MongoDatabase database, EntityMapper<Account> accountMapper) {
        this.collection = database.getCollection("accounts");
        this.accountMapper = accountMapper;
    }

    @Override
    public void create(Account account) {
        Document accountDocument = accountMapper.toDocument(account);
        collection.insertOne(accountDocument);
    }

    @Override
    public Account getById(String id) {
        Document document = collection.find(eq("_id", id)).first();

        if (document == null) {
            return null;
        }

        return accountMapper.fromDocument(document);
    }

    @Override
    public List<Account> getAll() {
        List<Account> accounts = new ArrayList<>();

        for (Document doc : collection.find()) {
            accounts.add(accountMapper.fromDocument(doc));
        }

        return accounts;
    }

    @Override
    public void update(Account account) {
        Document accountDoc = accountMapper.toDocument(account);
        collection.replaceOne(eq("_id", account.getId()), accountDoc);
    }

    @Override
    public void delete(Account account) {
        collection.deleteOne(eq("_id", account.getId()));
    }
}
