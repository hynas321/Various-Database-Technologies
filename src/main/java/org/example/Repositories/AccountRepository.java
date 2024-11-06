package org.example.Repositories;

import com.datastax.oss.driver.api.core.CqlIdentifier;
import com.datastax.oss.driver.api.core.CqlSession;
import com.datastax.oss.driver.api.core.type.DataTypes;
import com.datastax.oss.driver.api.querybuilder.SchemaBuilder;
import org.example.Dao.AccountDao;
import org.example.Entities.Account;
import org.example.Mappers.RepositoryMapper;
import org.example.Mappers.RepositoryMapperBuilder;

import java.util.List;
import java.util.UUID;

public class AccountRepository implements EntityRepository<Account> {

    private final AccountDao accountDao;

    public AccountRepository(CqlSession session, CqlIdentifier keyspace) {
        setTables(session, keyspace);

        RepositoryMapper builder = new RepositoryMapperBuilder(session).build();;
        this.accountDao = builder.accountDao(keyspace);
    }

    @Override
    public void create(Account account) {
        accountDao.create(account);
    }

    @Override
    public Account getById(UUID id) {
        return accountDao.getById(id);
    }

    @Override
    public List<Account> getAll() {
        return accountDao.getAll();
    }

    @Override
    public void update(Account account) {
        accountDao.update(account);
    }

    @Override
    public void delete(Account account) {
        accountDao.delete(account);
    }

    private static void setTables(CqlSession session, CqlIdentifier keyspace) {
        session.execute(SchemaBuilder.createTable(keyspace, CqlIdentifier.fromCql("accounts"))
                .ifNotExists()
                .withPartitionKey(CqlIdentifier.fromCql("id"), DataTypes.UUID)
                .withColumn(CqlIdentifier.fromCql("email"), DataTypes.TEXT)
                .withColumn(CqlIdentifier.fromCql("password"), DataTypes.TEXT)
                .withColumn(CqlIdentifier.fromCql("type"), DataTypes.TEXT)
                .withColumn(CqlIdentifier.fromCql("post_ids"), DataTypes.setOf(DataTypes.UUID))
                .withColumn(CqlIdentifier.fromCql("board_ids"), DataTypes.setOf(DataTypes.UUID))
                .build());
    }
}
