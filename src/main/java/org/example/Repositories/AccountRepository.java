package org.example.Repositories;

import com.google.gson.reflect.TypeToken;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import org.bson.types.ObjectId;
import org.example.Entities.Account;
import org.example.Entities.Admin;
import org.example.Entities.User;
import org.example.Redis.RedisCache;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import static com.mongodb.client.model.Filters.eq;

public class AccountRepository implements EntityRepository<Account> {
    private final MongoCollection<Account> collection;
    private final RedisCache redisCache;
    private static final int CACHE_EXPIRATION = 1800;
    private static final String USER_CACHE_KEY = "accounts:users";
    private static final String ADMIN_CACHE_KEY = "accounts:admins";

    public AccountRepository(MongoDatabase database, RedisCache redisCache) {
        this.collection = database.getCollection("accounts", Account.class);
        this.redisCache = redisCache;
    }

    @Override
    public void create(Account account) {
        collection.insertOne(account);

        String cacheKey = "account:" + account.getId().toString();
        redisCache.cacheData(cacheKey, account, CACHE_EXPIRATION);

        if (account instanceof User) {
            List<User> users = redisCache.getCachedData(USER_CACHE_KEY, new TypeToken<List<User>>() {}.getType());
            if (users == null) {
                users = new ArrayList<>();
            }
            users.add((User) account);
            redisCache.cacheData(USER_CACHE_KEY, users, CACHE_EXPIRATION);
        } else if (account instanceof Admin) {
            List<Admin> admins = redisCache.getCachedData(ADMIN_CACHE_KEY, new TypeToken<List<Admin>>() {}.getType());
            if (admins == null) {
                admins = new ArrayList<>();
            }
            admins.add((Admin) account);
            redisCache.cacheData(ADMIN_CACHE_KEY, admins, CACHE_EXPIRATION);
        }
    }

    @Override
    public Account getById(ObjectId id) {
        String cacheKey = "account:" + id.toString();
        Account account = redisCache.getCachedData(cacheKey, Account.class);

        if (account == null) {
            account = collection.find(eq("_id", id)).first();
            if (account != null) {
                redisCache.cacheData(cacheKey, account, CACHE_EXPIRATION);
            }
        }
        return account;
    }

    @Override
    public List<Account> getAll() {
        List<Account> accounts = new ArrayList<>();

        Type userListType = new TypeToken<List<User>>() {}.getType();
        Type adminListType = new TypeToken<List<Admin>>() {}.getType();

        List<User> users = redisCache.getCachedData(USER_CACHE_KEY, userListType);
        List<Admin> admins = redisCache.getCachedData(ADMIN_CACHE_KEY, adminListType);

        if (users == null) {
            users = new ArrayList<>();
            List<User> finalUsers = users;
            collection.find(eq("type", User.class.getSimpleName())).forEach((user) -> finalUsers.add((User) user));
            if (!users.isEmpty()) {
                redisCache.cacheData(USER_CACHE_KEY, users, CACHE_EXPIRATION);
            }
        }

        if (admins == null) {
            admins = new ArrayList<>();
            List<Admin> finalAdmins = admins;
            collection.find(eq("type", Admin.class.getSimpleName())).forEach((admin) -> finalAdmins.add((Admin) admin));
            if (!admins.isEmpty()) {
                redisCache.cacheData(ADMIN_CACHE_KEY, admins, CACHE_EXPIRATION);
            }
        }

        accounts.addAll(users);
        accounts.addAll(admins);
        return accounts;
    }

    @Override
    public void update(Account account) {
        collection.replaceOne(eq("_id", account.getId()), account);
        redisCache.invalidateCache("account:" + account.getId().toString());
    }

    @Override
    public void delete(Account account) {
        collection.deleteOne(eq("_id", account.getId()));
        redisCache.invalidateCache("account:" + account.getId().toString());

        if (account instanceof User) {
            redisCache.invalidateCache(USER_CACHE_KEY);
        } else if (account instanceof Admin) {
            redisCache.invalidateCache(ADMIN_CACHE_KEY);
        }
    }
}
