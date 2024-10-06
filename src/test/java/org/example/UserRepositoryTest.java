package org.example;

import org.example.Entities.User;
import org.example.Repositories.UserRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

public class UserRepositoryTest extends BaseRepositoryTest {
    private UserRepository userRepository;

    @BeforeEach
    public void setUp() {
        super.setUp();
        userRepository = new UserRepository(session);
    }

    @Test
    public void testCreateAndGetById() {
        User user = new User("test@example.com", "password123");
        userRepository.create(user);

        User retrievedUser = userRepository.getById(user.getId());
        Assertions.assertNotNull(retrievedUser);
        Assertions.assertEquals("test@example.com", retrievedUser.getEmail());
    }

    @Test
    public void testGetAll() {
        User user1 = new User("user1@example.com", "password1");
        User user2 = new User("user2@example.com", "password2");
        userRepository.create(user1);
        userRepository.create(user2);

        List<User> users = userRepository.getAll();
        Assertions.assertEquals(2, users.size());
    }

    @Test
    public void testUpdate() {
        User user = new User("test@example.com", "initialPassword");
        userRepository.create(user);

        user.setPassword("updatedPassword");
        userRepository.update(user);

        User updatedUser = userRepository.getById(user.getId());
        Assertions.assertEquals("updatedPassword", updatedUser.getPassword());
    }

    @Test
    public void testDelete() {
        User user = new User("delete@example.com", "password123");
        userRepository.create(user);

        userRepository.delete(user);
        User deletedUser = userRepository.getById(user.getId());

        Assertions.assertNull(deletedUser);
    }
}
