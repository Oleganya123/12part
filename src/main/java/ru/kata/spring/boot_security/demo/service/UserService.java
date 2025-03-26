package ru.kata.spring.boot_security.demo.service;

import ru.kata.spring.boot_security.demo.model.User;
import java.util.List;

public interface UserService {
    List<User> getAllUsers();
    User getUserById(Long id);
    User findByEmail(String name);
    User addUser(User user);
    User updateUser(User user, String newPassword);
    void deleteUser(Long id);
}
