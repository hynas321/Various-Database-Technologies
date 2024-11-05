package org.example.Mappers;

import com.datastax.oss.driver.api.mapper.annotations.Mapper;
import com.datastax.oss.driver.api.mapper.annotations.DaoFactory;
import com.datastax.oss.driver.api.mapper.annotations.DaoKeyspace;
import com.datastax.oss.driver.api.core.CqlIdentifier;
import org.example.Dao.AccountDao;
import org.example.Dao.BoardDao;
import org.example.Dao.CommentDao;
import org.example.Dao.PostDao;

@Mapper
public interface RepositoryMapper {

    @DaoFactory
    AccountDao accountDao(@DaoKeyspace CqlIdentifier keyspace);

    @DaoFactory
    BoardDao boardDao(@DaoKeyspace CqlIdentifier keyspace);

    @DaoFactory
    CommentDao commentDao(@DaoKeyspace CqlIdentifier keyspace);

    @DaoFactory
    PostDao postDao(@DaoKeyspace CqlIdentifier keyspace);
}
