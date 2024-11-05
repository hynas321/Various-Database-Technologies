package org.example.Repositories;

import com.datastax.oss.driver.api.core.CqlIdentifier;
import com.datastax.oss.driver.api.core.CqlSession;
import com.datastax.oss.driver.api.core.type.DataTypes;
import com.datastax.oss.driver.api.querybuilder.SchemaBuilder;
import org.example.Dao.BoardDao;
import org.example.Entities.Board;
import org.example.Mappers.RepositoryMapper;
import org.example.Mappers.RepositoryMapperBuilder;

import java.util.List;
import java.util.UUID;

public class BoardRepository implements EntityRepository<Board> {

    private final BoardDao boardDao;

    public BoardRepository(CqlSession session, CqlIdentifier keyspace) {
        setTables(session, keyspace);

        RepositoryMapper repositoryMapper = new RepositoryMapperBuilder(session).build();;
        this.boardDao = repositoryMapper.boardDao(keyspace);
    }

    @Override
    public void create(Board board) {
        boardDao.create(board);
    }

    @Override
    public Board getById(UUID id) {
        return boardDao.getById(id);
    }

    @Override
    public List<Board> getAll() {
        return boardDao.getAll();
    }

    @Override
    public void update(Board board) {
        boardDao.update(board);
    }

    @Override
    public void delete(Board board) {
        boardDao.delete(board);
    }

    private static void setTables(CqlSession session, CqlIdentifier keyspace) {
        session.execute(SchemaBuilder.createTable(keyspace, CqlIdentifier.fromCql("boards"))
                .ifNotExists()
                .withPartitionKey(CqlIdentifier.fromCql("id"), DataTypes.UUID)
                .withColumn(CqlIdentifier.fromCql("name"), DataTypes.TEXT)
                .withColumn(CqlIdentifier.fromCql("post_ids"), DataTypes.setOf(DataTypes.UUID))
                .withColumn(CqlIdentifier.fromCql("member_ids"), DataTypes.setOf(DataTypes.UUID))
                .build());
    }
}
