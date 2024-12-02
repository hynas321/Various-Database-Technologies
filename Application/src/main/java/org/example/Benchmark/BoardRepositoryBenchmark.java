package org.example.Benchmark;

import org.bson.types.ObjectId;
import org.example.Entities.Board;
import org.example.Redis.RedisBoardDecorator;
import org.example.Redis.RedisCache;
import org.example.Repositories.BoardRepository;
import org.example.Repositories.EntityRepository;
import org.example.Repositories.MongoDbConnection;
import org.openjdk.jmh.annotations.*;

@BenchmarkMode(Mode.SampleTime)
@Warmup(iterations = 2)
@Measurement(iterations = 2)
@Fork(1)
@State(Scope.Benchmark)
public class BoardRepositoryBenchmark {

    private EntityRepository<Board> boardRepositoryWithCache;
    private RedisCache redisCache;
    private Board board;

    @Setup
    public void setUp() {
        MongoDbConnection mongoDbConnection = new MongoDbConnection("mongodb://mongodb1:27017,mongodb2:27018,mongodb3:27019/?replicaSet=replica_set_single", "testDatabase");
        redisCache = new RedisCache();
        boardRepositoryWithCache = new RedisBoardDecorator(new BoardRepository(mongoDbConnection.getDatabase()), redisCache);

        board = new Board();
        board.setId(new ObjectId());

        boardRepositoryWithCache.getById(board.getId());
    }

    @Benchmark
    public void testCacheExists() {
        boardRepositoryWithCache.getById(board.getId());
    }

    @Benchmark
    public void testCacheDoesNotExist() {
        redisCache.deleteCache("board:" + board.getId().toString());
        boardRepositoryWithCache.getById(board.getId());
    }
}
