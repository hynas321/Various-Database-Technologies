package org.example.Dao;

import com.datastax.oss.driver.api.mapper.annotations.*;
import org.example.Entities.Comment;
import java.util.List;
import java.util.UUID;

@Dao
public interface CommentDao {

    @StatementAttributes(consistencyLevel = "QUORUM")
    @Insert
    void create(Comment comment);

    @StatementAttributes(consistencyLevel = "QUORUM")
    @Select
    Comment getById(UUID id);

    @StatementAttributes(consistencyLevel = "QUORUM")
    @Update
    void update(Comment comment);

    @StatementAttributes(consistencyLevel = "ONE")
    @Delete
    void delete(Comment comment);
}
