package ru.kata.spring.boot_security.demo.repositories;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import ru.kata.spring.boot_security.demo.models.User;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {
    Optional<User> findById(int id);

    @EntityGraph(attributePaths = "roles")
    Optional<User> findByUsername(String username);

    void deleteById(int id);

    @Query("SELECT u from User u join fetch u.roles")
    List<User> findAll();

}
