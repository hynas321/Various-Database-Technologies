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
}
