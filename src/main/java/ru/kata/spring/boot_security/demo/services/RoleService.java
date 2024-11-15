package ru.kata.spring.boot_security.demo.services;

import ru.kata.spring.boot_security.demo.models.Role;

import java.util.List;
import java.util.Optional;
import java.util.Set;

public interface RoleService {
    Role findByName(String name);

    Set<Role> findAll();
}
