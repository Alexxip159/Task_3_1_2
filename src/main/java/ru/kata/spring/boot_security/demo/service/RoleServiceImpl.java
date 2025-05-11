package ru.kata.spring.boot_security.demo.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.kata.spring.boot_security.demo.model.Role;
import ru.kata.spring.boot_security.demo.model.User;
import ru.kata.spring.boot_security.demo.repository.RoleRepository;

import javax.validation.Valid;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class RoleServiceImpl implements RoleService {

    private final RoleRepository roleRepository;

    @Override
    @Transactional(readOnly = true)
    public List<Role> findAllRoles() {
        return roleRepository.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Role> findRoleById(Long id) {
        return roleRepository.findById(id);
    }

    @Override
    @Transactional(readOnly = true)
    public Role findRoleByName(String roleName) {
        return roleRepository.findByName(roleName);
    }

    @Override
    @Transactional(readOnly = true)
    public Set<Role> validateRole(String roles) {
        try {
            return Arrays.stream(roles.split(","))
                    .map(String::trim)
                    .map(String::toUpperCase)
                    .map(role -> "ROLE_" + role)
                    .map(roleName -> {
                        Role role = roleRepository.findByName(roleName);
                        if (role == null) {
                            throw new RuntimeException("Role not found: " + roleName);
                        }
                        return role;
                    })
                    .collect(Collectors.toSet());

        } catch (RuntimeException e) {
            return Set.of();
        }
    }
}
