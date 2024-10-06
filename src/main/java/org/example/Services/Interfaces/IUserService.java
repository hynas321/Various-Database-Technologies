package org.example.Services.Interfaces;

import org.example.Entities.Board;
import org.example.Entities.User;

import java.util.List;
import java.util.Set;

public interface IUserService {
    User register(String email, String password);
    User login(String email, String password);
    User getUserById(Long userId);
    List<User> getAllUsers();
    void updateUser(User user);
}
