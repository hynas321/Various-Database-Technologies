import com.mongodb.client.MongoDatabase;
import org.example.Repositories.MongoDbConnection;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;

public abstract class BaseRepositoryTest {
    protected MongoDbConnection mongoDbConnection;
    protected MongoDatabase database;

    @BeforeEach
    public void setUp() {
        mongoDbConnection = new MongoDbConnection("mongodb://localhost:27017,localhost:27018,localhost:27019/?replicaSet=rs0", "testDatabase");
        database = mongoDbConnection.getDatabase();
    }

    @AfterEach
    public void tearDown() {
        if (mongoDbConnection != null) {
            mongoDbConnection.close();
        }
    }
}
