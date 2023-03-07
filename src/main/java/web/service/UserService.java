package web.service;

import web.model.User;

import java.util.List;

public interface UserService {
    List<User> listUsers();
    User showUser(Long id);
    void addUser(User user);
    void removeUser(Long id);
    void updateUser(Long id, User user);

}
