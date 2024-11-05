package org.example.Repositories;

import com.datastax.oss.driver.api.core.CqlIdentifier;
import com.datastax.oss.driver.api.core.CqlSession;
import com.datastax.oss.driver.api.core.type.DataTypes;
import com.datastax.oss.driver.api.querybuilder.SchemaBuilder;
import org.example.Dao.PostDao;
import org.example.Entities.Post;
import org.example.Mappers.RepositoryMapper;
import org.example.Mappers.RepositoryMapperBuilder;

import java.util.List;
import java.util.UUID;

public class PostRepository implements EntityRepository<Post> {

    private final PostDao postDao;

    public PostRepository(CqlSession session, CqlIdentifier keyspace) {
        prepareTables(session, keyspace);

        RepositoryMapper repositoryMapper = new RepositoryMapperBuilder(session).build();;
        this.postDao = repositoryMapper.postDao(keyspace);
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
        return postDao.getAll();
    }

    @Override
    public void update(Post post) {
        postDao.update(post);
    }

    @Override
    public void delete(Post post) {
        postDao.delete(post);
    }
}
