package org.example.Dao;

import com.datastax.oss.driver.api.mapper.annotations.*;
import org.example.Entities.Board;
import java.util.List;
import java.util.UUID;

@Dao
public interface BoardDao {

    @StatementAttributes(consistencyLevel = "QUORUM")
    @Insert
    void create(Board board);

    @StatementAttributes(consistencyLevel = "QUORUM")
    @Select
    Board getById(UUID id);

    @StatementAttributes(consistencyLevel = "ONE")
    @Query("SELECT * FROM boards")
    List<Board> getAll();

    @StatementAttributes(consistencyLevel = "QUORUM")
    @Update
    void update(Board board);

    @StatementAttributes(consistencyLevel = "ONE")
    @Delete
    void delete(Board board);
}
