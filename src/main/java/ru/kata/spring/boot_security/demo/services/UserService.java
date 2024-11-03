package ru.kata.spring.boot_security.demo.services;

import ru.kata.spring.boot_security.demo.models.User;

import java.util.List;
import java.util.Optional;

public interface UserService {
    Optional<User> findByUsername(String username);

    Optional<User> findById(int id);

    List<User> findAll();

    void save(User user);

    void deleteById(int id);

    void register(User user);

}
