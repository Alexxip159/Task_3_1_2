package ru.kata.spring.boot_security.demo.service;

import ru.kata.spring.boot_security.demo.model.User;
import java.util.List;

public interface UserService {
    //    User findUserByUsername(String username);
    List<User> findAllUsers();
    User findUserById(Long id);
    boolean CreateUser(User user);
    void removeUserById(Long id);
    void updateUser(Long id, User user);
}