package org.example.Dao;


import com.datastax.oss.driver.api.mapper.annotations.*;
import org.example.Entities.Account;
import java.util.List;
import java.util.UUID;

@Dao
public interface AccountDao {

    @StatementAttributes(consistencyLevel = "QUORUM")
    @Insert
    void create(Account account);

    @StatementAttributes(consistencyLevel = "QUORUM")
    @Select
    Account getById(UUID id);

    @StatementAttributes(consistencyLevel = "QUORUM")
    @Update
    void update(Account account);

    @StatementAttributes(consistencyLevel = "ONE")
    @Delete
    void delete(Account account);
}
