package org.example.Repositories;

import com.datastax.oss.driver.api.core.CqlIdentifier;
import com.datastax.oss.driver.api.core.CqlSession;
import com.datastax.oss.driver.api.core.cql.ResultSet;
import com.datastax.oss.driver.api.core.cql.SimpleStatement;
import com.datastax.oss.driver.api.core.type.DataTypes;
import com.datastax.oss.driver.api.querybuilder.SchemaBuilder;
import org.example.Dao.BoardDao;
import org.example.Entities.Account;
import org.example.Entities.Board;
import org.example.Mappers.RepositoryMapper;
import org.example.Mappers.RepositoryMapperBuilder;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class BoardRepository implements EntityRepository<Board> {

    private final BoardDao boardDao;
    private final CqlSession session;

    public BoardRepository(CqlSession session, CqlIdentifier keyspace) {
        setTables(session, keyspace);

        RepositoryMapper repositoryMapper = new RepositoryMapperBuilder(session).build();;
        this.boardDao = repositoryMapper.boardDao(keyspace);
        this.session = session;
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
        SimpleStatement stmt = SimpleStatement.builder("SELECT * FROM boards")
                .setPageSize(100)
                .build();
        ResultSet rs = session.execute(stmt);

        List<Board> boards = new ArrayList<>();

        rs.forEach(row -> {
            Board board = new Board(
                    row.getUuid("id"),
                    row.getString("name"),
                    row.getSet("post_ids", UUID.class),
                    row.getSet("member_ids", UUID.class)
            );
            boards.add(board);
        });

        return boards;
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
