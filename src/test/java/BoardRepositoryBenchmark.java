/*
import org.bson.types.ObjectId;
import org.example.Redis.RedisCache;
import org.example.Repositories.BoardRepository;
import org.example.Repositories.MongoDbConnection;
import org.openjdk.jmh.annotations.*;

@BenchmarkMode(Mode.AverageTime)
@State(Scope.Benchmark)
public class BoardRepositoryBenchmark {

    private BoardRepository boardRepository;
    private RedisCache redisCache;
    private ObjectId testBoardId;

    @Setup
    public void setUp() {
        MongoDbConnection mongoDbConnection = new MongoDbConnection("mongodb://mongodb1:27017,mongodb2:27018,mongodb3:27019/?replicaSet=replica_set_single", "testDatabase");
        redisCache = new RedisCache("localhost", 6379, "master123");
        boardRepository = new BoardRepository(mongoDbConnection.getDatabase(), redisCache);

        testBoardId = new ObjectId("123");
        boardRepository.getById(testBoardId);
    }

    @Benchmark
    public void testCacheExists() {
        boardRepository.getById(testBoardId);
    }

    @Benchmark
    public void testCacheDoesNotExist() {
        redisCache.deleteCache("board:" + testBoardId.toString());
        boardRepository.getById(testBoardId);
    }
}
*/
