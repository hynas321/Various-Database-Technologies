import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;

public abstract class BaseRepositoryTest {
    protected SessionFactory sessionFactory;
    protected Session session;

    @BeforeEach
    public void setUp() {
        sessionFactory = new Configuration()
                .configure("hibernate-test.cfg.xml")
                .buildSessionFactory();
        session = sessionFactory.openSession();
    }

    @AfterEach
    public void tearDown() {
        if (session != null) {
            session.close();
        }
        if (sessionFactory != null) {
            sessionFactory.close();
        }
    }
}