package org.example.Services;

import org.example.Entities.User;
import org.example.Repositories.Interfaces.GenericRepository;
import org.example.Services.Interfaces.IUserService;

import java.util.List;

public class UserService implements IUserService {
    private final GenericRepository<User> userRepository;

    public UserService(GenericRepository<User> userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public User register(String email, String password) {
        if (userRepository.getAll().stream().anyMatch(user -> user.getEmail().equals(email))) {
            throw new IllegalArgumentException("User already exists with this email.");
        }

        User newUser = new User(email, password);
        userRepository.create(newUser);

        return newUser;
    }

    @Override
    public User login(String email, String password) {
        return userRepository.getAll().stream()
                .filter(u -> u.getEmail().equals(email) && u.getPassword().equals(password))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("Invalid email or password."));
    }

    @Override
    public User getUserById(Long userId) {
        return userRepository.getById(userId);
    }

    @Override
    public List<User> getAllUsers() {
        return userRepository.getAll();
    }

    @Override
    public void updateUser(User user) {
        userRepository.update(user);
    }
}