import com.mongodb.client.MongoDatabase;
import jakarta.json.bind.JsonbBuilder;
import org.example.Redis.RedisCache;
import org.example.Repositories.MongoDbConnection;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;

public abstract class BaseRepositoryTest {
    protected MongoDbConnection mongoDbConnection;
    protected MongoDatabase database;
    protected RedisCache redisCache;

    @BeforeEach
    public void setUp() {
        mongoDbConnection = new MongoDbConnection("mongodb://mongodb1:27017,mongodb2:27018,mongodb3:27019/?replicaSet=replica_set_single", "testDatabase");
        database = mongoDbConnection.getDatabase();
        redisCache = new RedisCache("localhost", 6379, "master123");
    }

    @AfterEach
    public void tearDown() {
        if (mongoDbConnection != null) {
            mongoDbConnection.close();
        }
        if (redisCache != null) {
            redisCache.close();
        }
    }
}