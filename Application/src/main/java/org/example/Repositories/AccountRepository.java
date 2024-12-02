package org.example.Repositories;

import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import org.bson.types.ObjectId;
import org.example.Entities.Account;
import org.example.Entities.Admin;
import org.example.Entities.User;

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

        List<User> users = collection.find(eq("type", "User"), User.class).into(new ArrayList<>());
        List<Admin> admins = collection.find(eq("type", "Admin"), Admin.class).into(new ArrayList<>());

        accounts.addAll(users);
        accounts.addAll(admins);

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
