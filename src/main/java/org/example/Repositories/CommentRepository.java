package org.example.Repositories;

import com.datastax.oss.driver.api.core.CqlIdentifier;
import com.datastax.oss.driver.api.core.CqlSession;
import com.datastax.oss.driver.api.core.type.DataTypes;
import com.datastax.oss.driver.api.querybuilder.SchemaBuilder;
import org.example.Dao.CommentDao;
import org.example.Entities.Comment;
import org.example.Mappers.RepositoryMapper;
import org.example.Mappers.RepositoryMapperBuilder;

import java.util.List;
import java.util.UUID;

public class CommentRepository implements EntityRepository<Comment> {

    private final CommentDao commentDao;

    public CommentRepository(CqlSession session, CqlIdentifier keyspace) {
        setTables(session, keyspace);

        RepositoryMapper repositoryMapper = new RepositoryMapperBuilder(session).build();
        this.commentDao = repositoryMapper.commentDao(keyspace);
    }

    @Override
    public void create(Comment comment) {
        commentDao.create(comment);
    }

    @Override
    public Comment getById(UUID id) {
        return commentDao.getById(id);
    }

    @Override
    public List<Comment> getAll() {
        return commentDao.getAll();
    }

    @Override
    public void update(Comment comment) {
        commentDao.update(comment);
    }

    @Override
    public void delete(Comment comment) {
        commentDao.delete(comment);
    }

    private static void setTables(CqlSession session, CqlIdentifier keyspace) {
        session.execute(SchemaBuilder.createTable(keyspace, CqlIdentifier.fromCql("comments"))
                .ifNotExists()
                .withPartitionKey(CqlIdentifier.fromCql("id"), DataTypes.UUID)
                .withColumn(CqlIdentifier.fromCql("content"), DataTypes.TEXT)
                .withColumn(CqlIdentifier.fromCql("post_id"), DataTypes.UUID)
                .withColumn(CqlIdentifier.fromCql("creator_id"), DataTypes.UUID)
                .build());
    }
}
