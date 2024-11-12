package ru.kata.spring.boot_security.demo.services;

import ru.kata.spring.boot_security.demo.models.User;

import java.util.List;

public interface UserService {
    User findByUsername(String username);

    User findById(int id);

    List<User> findAll();

    void save(User user);

    void deleteById(int id);

    void register(User user);

    void update(User user, int id);
}
