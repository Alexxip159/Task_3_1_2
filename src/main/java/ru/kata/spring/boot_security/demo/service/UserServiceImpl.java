package ru.kata.spring.boot_security.demo.service;

import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.kata.spring.boot_security.demo.model.Role;
import ru.kata.spring.boot_security.demo.model.User;
import ru.kata.spring.boot_security.demo.repository.RoleRepository;
import ru.kata.spring.boot_security.demo.repository.UserRepository;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    private User userDb = new User();



    @Override
    @Transactional(readOnly = true)
    public List<User> findAllUsers() {
        return userRepository.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public User findUserById(Long id) {
        Optional<User> optionalUser = userRepository.findById(id);
        return optionalUser.map(this::loadUser).orElseGet(User::new);
    }

    @Override
    @Transactional
    public boolean CreateUser(User localUser) {
        if (userRepository.findByUsername(localUser.getUsername()) == null) {
            userDb = upLoadUser(localUser);
            if (localUser.getRoles() == null) {
                Set<Role> roles = new HashSet<>();
                roles.add(roleRepository.findByName("ROLE_USER"));
                userDb.setRoles(roles);
            }
            userRepository.save(userDb);
            return true;
        }
        return false;
    }

    @Override
    @Transactional
    public void removeUserById(Long id) {
        userRepository.deleteById(id);
    }

    @Override
    @Transactional
    public void updateUser(Long id, User localUser) {
        userRepository.save(upLoadUser(localUser));
        userDb = new User();
    }

    private User loadUser(User user) {
        User localUser = new User();
        if (user != null) {
            localUser.setId(user.getId());
            localUser.setUsername(user.getUsername());
            localUser.setFirstName(user.getFirstName());
            localUser.setLastName(user.getLastName());
            localUser.setAge(user.getAge());
            localUser.setEmail(user.getEmail());
            localUser.setRoles(user.getRoles());
            localUser.setRolesStr(user.getRoles().stream()
                    .map(Role::getName)
                    .map(roleName -> roleName.substring(5))
                    .collect(Collectors.joining(", ")));
        }
        return localUser;
    }

    private User upLoadUser(User localUser) {
        userDb.setId(localUser.getId());
        userDb.setUsername(localUser.getUsername());
        userDb.setFirstName(localUser.getFirstName());
        userDb.setLastName(localUser.getLastName());
        userDb.setAge(localUser.getAge());
        userDb.setEmail(localUser.getEmail());
        userDb.setRoles(localUser.getRoles());
        userDb.setPassword(bCryptPasswordEncoder.encode(localUser.getPassword()));
        return userDb;
    }

}