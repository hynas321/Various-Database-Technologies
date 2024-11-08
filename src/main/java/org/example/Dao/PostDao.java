package org.example.Dao;

import com.datastax.oss.driver.api.mapper.annotations.*;
import org.example.Entities.Post;
import java.util.List;
import java.util.UUID;

@Dao
public interface PostDao {

    @StatementAttributes(consistencyLevel = "QUORUM")
    @Insert
    void create(Post post);

    @StatementAttributes(consistencyLevel = "QUORUM")
    @Select
    Post getById(UUID id);

    @StatementAttributes(consistencyLevel = "QUORUM")
    @Update
    void update(Post post);

    @StatementAttributes(consistencyLevel = "ONE")
    @Delete
    void delete(Post post);
}
