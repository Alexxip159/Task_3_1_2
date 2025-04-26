package ru.kata.spring.boot_security.demo.service;

import org.springframework.security.core.userdetails.UserDetailsService;
import ru.kata.spring.boot_security.demo.model.User;

import java.util.List;

public interface UserService extends UserDetailsService {
//    User findUserByUsername(String username);
    List<User> findAllUsers();
    User findUserById(Long id);
    boolean addUser(User user);
    void removeUserById(Long id);
    void updateUser(Long id, User user);
}
