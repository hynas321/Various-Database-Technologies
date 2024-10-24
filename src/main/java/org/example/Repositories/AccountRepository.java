package org.example.Repositories;

import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import org.bson.types.ObjectId;
import org.example.Entities.Account;

import java.util.ArrayList;
import java.util.List;

import static com.mongodb.client.model.Filters.eq;

public class AccountRepository implements EntityRepository<Account> {
    private final MongoCollection<Account> collection;

    public AccountRepository(MongoDatabase database) {
        this.collection = database.getCollection("accounts", Account.class);
    }

    @Override
    public void create(Account account) {
        collection.insertOne(account);
    }

    @Override
    public Account getById(ObjectId id) {
        return collection.find(eq("_id", id)).first();
    }

    @Override
    public List<Account> getAll() {
        List<Account> accounts = new ArrayList<>();
        collection.find().into(accounts);
        return accounts;
    }

    @Override
    public void update(Account account) {
        collection.replaceOne(eq("_id", account.getId()), account);
    }

    @Override
    public void delete(Account account) {
        collection.deleteOne(eq("_id", account.getId()));
    }
}
