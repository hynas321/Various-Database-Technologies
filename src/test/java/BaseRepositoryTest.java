import com.datastax.oss.driver.api.core.CqlSession;
import org.example.Cassandra.CassandraConnection;
import org.junit.jupiter.api.*;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public abstract class BaseRepositoryTest {
    protected CassandraConnection cassandraConnection;
    protected CqlSession session;

    @BeforeAll
    public void setUp() {
        cassandraConnection = new CassandraConnection("dc1", "site");
        session = cassandraConnection.getSession();
    }

    @AfterAll
    public void tearDown() {
        if (cassandraConnection != null) {
            cassandraConnection.close();
        }
    }
}
