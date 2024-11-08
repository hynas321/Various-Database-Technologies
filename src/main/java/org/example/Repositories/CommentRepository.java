package org.example.Repositories;

import com.datastax.oss.driver.api.core.CqlIdentifier;
import com.datastax.oss.driver.api.core.CqlSession;
import com.datastax.oss.driver.api.core.cql.ResultSet;
import com.datastax.oss.driver.api.core.cql.SimpleStatement;
import com.datastax.oss.driver.api.core.type.DataTypes;
import com.datastax.oss.driver.api.querybuilder.SchemaBuilder;
import org.example.Dao.CommentDao;
import org.example.Entities.Board;
import org.example.Entities.Comment;
import org.example.Mappers.RepositoryMapper;
import org.example.Mappers.RepositoryMapperBuilder;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class CommentRepository implements EntityRepository<Comment> {

    private final CommentDao commentDao;
    private final CqlSession session;

    public CommentRepository(CqlSession session, CqlIdentifier keyspace) {
        setTables(session, keyspace);

        RepositoryMapper repositoryMapper = new RepositoryMapperBuilder(session).build();
        this.commentDao = repositoryMapper.commentDao(keyspace);
        this.session = session;
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
        SimpleStatement stmt = SimpleStatement.builder("SELECT * FROM comments")
                .setPageSize(100)
                .build();
        ResultSet rs = session.execute(stmt);

        List<Comment> comments = new ArrayList<>();

        rs.forEach(row -> {
            Comment comment = new Comment(
                    row.getUuid("id"),
                    row.getString("content"),
                    row.getUuid("post_id"),
                    row.getUuid("creator_id")
            );
            comments.add(comment);
        });

        return comments;
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
