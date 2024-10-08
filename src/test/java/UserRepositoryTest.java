import jakarta.persistence.OptimisticLockException;
import org.example.Entities.User;
import org.example.Repositories.Interfaces.EntityRepository;
import org.example.Repositories.UserRepository;
import org.hibernate.Session;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class UserRepositoryTest extends BaseRepositoryTest {

    private EntityRepository<User> userRepository;

    @BeforeEach
    @Override
    public void setUp() {
        super.setUp();
        userRepository = new UserRepository(session);
    }

    @Test
    void create_ShouldSaveUser() {
        User user = new User("test1@example.com", "password123");
        userRepository.create(user);

        User retrievedUser = userRepository.getById(user.getId());
        assertNotNull(retrievedUser);
        assertEquals("test1@example.com", retrievedUser.getEmail());
    }

    @Test
    void getById_ShouldReturnUser() {
        User user = new User("test2@example.com", "password123");
        userRepository.create(user);

        User retrievedUser = userRepository.getById(user.getId());
        assertNotNull(retrievedUser);
        assertEquals("test2@example.com", retrievedUser.getEmail());
    }

    @Test
    void getAll_ShouldReturnListOfUsers() {
        User user1 = new User("user_1@example.com", "password123");
        User user2 = new User("user_2@example.com", "password456");

        userRepository.create(user1);
        userRepository.create(user2);

        List<User> users = userRepository.getAll();
        assertFalse(users.isEmpty());
    }

    @Test
    void update_ShouldUpdateUser() {
        User user = new User("update@example.com", "password123");
        userRepository.create(user);

        user.setEmail("updated@example.com");
        userRepository.update(user);

        User updatedUser = userRepository.getById(user.getId());
        assertEquals("updated@example.com", updatedUser.getEmail());
    }

    @Test
    void delete_ShouldDeleteUser() {
        User user = new User("delete@example.com", "password123");
        userRepository.create(user);

        userRepository.delete(user);

        User deletedUser = userRepository.getById(user.getId());
        assertNull(deletedUser);
    }

    @Test
    void versionField_ShouldIncrementOnEachUpdate_ForUser() {
        Session createSession = sessionFactory.openSession();
        createSession.beginTransaction();
        User user = new User("version_test_user_2@example.com", "password123");
        userRepository.create(user);
        createSession.getTransaction().commit();
        createSession.close();

        Session session1 = sessionFactory.openSession();
        session1.beginTransaction();
        User retrievedUser = session1.get(User.class, user.getId());
        int initialVersion = retrievedUser.getVersion();
        session1.getTransaction().commit();
        session1.close();

        Session session2 = sessionFactory.openSession();
        session2.beginTransaction();
        retrievedUser.setEmail("updated_user@example.com");
        session2.merge(retrievedUser);
        session2.getTransaction().commit();

        session2.beginTransaction();
        User updatedUser = session2.get(User.class, retrievedUser.getId());
        int firstUpdatedVersion = updatedUser.getVersion();
        session2.getTransaction().commit();
        session2.close();

        assertTrue(firstUpdatedVersion > initialVersion, "Version should increment after the first update");

        Session session3 = sessionFactory.openSession();
        session3.beginTransaction();
        updatedUser.setEmail("updated_user_second@example.com");
        session3.merge(updatedUser);
        session3.getTransaction().commit();

        session3.beginTransaction();
        User secondUpdatedUser = session3.get(User.class, updatedUser.getId());
        int secondUpdatedVersion = secondUpdatedUser.getVersion();
        session3.getTransaction().commit();
        session3.close();

        assertTrue(secondUpdatedVersion > firstUpdatedVersion, "Version should increment after the second update");
    }

}
