package org.example.Repositories;

import com.datastax.oss.driver.api.core.CqlIdentifier;
import com.datastax.oss.driver.api.core.CqlSession;
import com.datastax.oss.driver.api.core.cql.ResultSet;
import com.datastax.oss.driver.api.core.cql.SimpleStatement;
import com.datastax.oss.driver.api.core.type.DataTypes;
import com.datastax.oss.driver.api.querybuilder.SchemaBuilder;
import org.example.Dao.PostDao;
import org.example.Entities.Comment;
import org.example.Entities.Post;
import org.example.Mappers.RepositoryMapper;
import org.example.Mappers.RepositoryMapperBuilder;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class PostRepository implements EntityRepository<Post> {

    private final PostDao postDao;
    private final CqlSession session;

    public PostRepository(CqlSession session, CqlIdentifier keyspace) {
        prepareTables(session, keyspace);

        RepositoryMapper repositoryMapper = new RepositoryMapperBuilder(session).build();;
        this.postDao = repositoryMapper.postDao(keyspace);
        this.session = session;
    }

    @Override
    public void create(Post post) {
        postDao.create(post);
    }

    @Override
    public Post getById(UUID id) {
        return postDao.getById(id);
    }

    @Override
    public List<Post> getAll() {
        SimpleStatement stmt = SimpleStatement.builder("SELECT * FROM posts")
                .setPageSize(100)
                .build();
        ResultSet rs = session.execute(stmt);

        List<Post> posts = new ArrayList<>();

        rs.forEach(row -> {
            Post post = new Post(
                    row.getUuid("id"),
                    row.getString("content"),
                    row.getUuid("creator_id"),
                    row.getUuid("board_id"),
                    row.getSet("comment_ids", UUID.class)
            );
            posts.add(post);
        });

        return posts;
    }

    @Override
    public void update(Post post) {
        postDao.update(post);
    }

    @Override
    public void delete(Post post) {
        postDao.delete(post);
    }

    private static void prepareTables(CqlSession session, CqlIdentifier keyspace) {
        session.execute(SchemaBuilder.createTable(keyspace, CqlIdentifier.fromCql("posts"))
                .ifNotExists()
                .withPartitionKey(CqlIdentifier.fromCql("id"), DataTypes.UUID)
                .withColumn(CqlIdentifier.fromCql("content"), DataTypes.TEXT)
                .withColumn(CqlIdentifier.fromCql("creator_id"), DataTypes.UUID)
                .withColumn(CqlIdentifier.fromCql("board_id"), DataTypes.UUID)
                .withColumn(CqlIdentifier.fromCql("comment_ids"), DataTypes.setOf(DataTypes.UUID))
                .build());
    }
}
