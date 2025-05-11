package ru.kata.spring.boot_security.demo;


import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import ru.kata.spring.boot_security.demo.model.Role;
import ru.kata.spring.boot_security.demo.model.User;
import ru.kata.spring.boot_security.demo.repository.RoleRepository;
import ru.kata.spring.boot_security.demo.repository.UserRepository;

import javax.annotation.PostConstruct;
import java.util.HashSet;
import java.util.Set;

@Component
@RequiredArgsConstructor
public class Databaseinitializer {
    private final RoleRepository roleRepository;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @PostConstruct
    @Transactional
    public void initUsers() {
        Role adminRole = createRoleIfNotFound("ROLE_ADMIN");
        Role userRole = createRoleIfNotFound ("ROLE_USER");

        if(userRepository.findByUsername("Admin") == null) {
           User admin = new User();
           admin.setUsername("admin");
           admin.setAge((byte) 30);
           admin.setFirstName("Admin");
           admin.setLastName("Adminovich");
           admin.setPassword(passwordEncoder.encode("admin"));
           admin.setRoles((new HashSet<>(Set.of(adminRole, userRole))));
           admin.setEmail("admin@admin.ru");
           userRepository.save(admin);
        }
    }

    private  Role createRoleIfNotFound(String roleName) {
        Role role = roleRepository.findByName(roleName);
        if (role == null) {
            role = new Role();
            role.setName(roleName);
            return roleRepository.save(role);
        }
        return role;
    }
}
