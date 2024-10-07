package org.example.Services;

import org.example.Entities.User;
import org.example.Repositories.Interfaces.EntityRepository;
import org.example.Services.Interfaces.IUserService;

import java.util.List;
import java.util.Optional;

public class UserService implements IUserService {
    private final EntityRepository<User> userRepository;

    public UserService(EntityRepository<User> userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public boolean createUser(String email, String password) {
        if (email == null || email.isEmpty() || password == null || password.isEmpty()) {
            return false;
        }

        if (userRepository.getAll().stream().anyMatch(u -> u.getEmail().equals(email))) {
            return false;
        }

        User user = new User(email, password);
        userRepository.create(user);
        return true;
    }

    @Override
    public boolean deleteUser(Long userId) {
        if (userId == null) {
            return false;
        }

        User user = userRepository.getById(userId);

        if (user == null) {
            return false;
        }

        userRepository.delete(user);
        return true;
    }

    @Override
    public boolean updateUser(User user) {
        if (user == null || user.getId() == null) {
            return false;
        }

        User existingUser = userRepository.getById(user.getId());

        if (existingUser == null) {
            return false;
        }

        userRepository.update(user);
        return true;
    }

    @Override
    public Optional<User> getUserById(Long userId) {
        if (userId == null) {
            return Optional.empty();
        }

        return Optional.ofNullable(userRepository.getById(userId));
    }

    @Override
    public List<User> getAllUsers() {
        return userRepository.getAll();
    }
}
