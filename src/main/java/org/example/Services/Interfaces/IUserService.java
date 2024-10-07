package org.example.Services.Interfaces;

import org.example.Entities.User;

import java.util.List;
import java.util.Optional;

public interface IUserService {
    boolean createUser(String email, String password);
    boolean deleteUser(Long userId);
    boolean updateUser(User user);
    Optional<User> getUserById(Long userId);
    List<User> getAllUsers();
}