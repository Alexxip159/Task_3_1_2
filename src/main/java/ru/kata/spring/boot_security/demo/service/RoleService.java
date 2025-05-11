package ru.kata.spring.boot_security.demo.service;

import ru.kata.spring.boot_security.demo.model.Role;

import java.util.List;
import java.util.Optional;
import java.util.Set;

public interface RoleService {
    List<Role> findAllRoles();
    Optional<Role> findRoleById(Long id);
    Role findRoleByName(String roleName);
    Set<Role> validateRole(String roles);
}
