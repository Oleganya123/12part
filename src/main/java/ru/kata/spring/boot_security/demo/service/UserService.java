package ru.kata.spring.boot_security.demo.service;

import ru.kata.spring.boot_security.demo.model.Role;
import ru.kata.spring.boot_security.demo.model.User;
import java.util.List;
import java.util.Set;

public interface UserService {
    List<User> getAllUsers();
    User getUserById(Long id);
    User findByEmail(String email);
    User addUser(User user, Set<Role> roles);
    User updateUser(User user, String newPassword, Set<Role> roles);
    void deleteUser(Long id);
    List<Role> getAllRoles();
    Role findRoleByName(String name);
}
