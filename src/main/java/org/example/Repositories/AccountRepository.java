package org.example.Repositories;

import com.datastax.oss.driver.api.core.CqlIdentifier;
import com.datastax.oss.driver.api.core.CqlSession;
import com.datastax.oss.driver.api.core.cql.ResultSet;
import com.datastax.oss.driver.api.core.cql.SimpleStatement;
import com.datastax.oss.driver.api.core.type.DataTypes;
import com.datastax.oss.driver.api.querybuilder.SchemaBuilder;
import org.example.Dao.AccountDao;
import org.example.Entities.Account;
import org.example.Mappers.RepositoryMapper;
import org.example.Mappers.RepositoryMapperBuilder;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class AccountRepository implements EntityRepository<Account> {

    private final AccountDao accountDao;
    private final CqlSession session;

    public AccountRepository(CqlSession session, CqlIdentifier keyspace) {
        setTables(session, keyspace);

        RepositoryMapper builder = new RepositoryMapperBuilder(session).build();;
        this.accountDao = builder.accountDao(keyspace);
        this.session = session;
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
        SimpleStatement stmt = SimpleStatement.builder("SELECT * FROM accounts")
                .setPageSize(100)
                .build();
        ResultSet rs = session.execute(stmt);

        List<Account> accounts = new ArrayList<>();

        rs.forEach(row -> {
            Account account = new Account(
                    row.getUuid("id"),
                    row.getString("email"),
                    row.getString("password"),
                    row.getString("type"),
                    row.getSet("post_ids", UUID.class),
                    row.getSet("board_ids", UUID.class)
            );
            accounts.add(account);
        });

        return accounts;
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
