package ru.kata.spring.boot_security.demo.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.kata.spring.boot_security.demo.models.Role;
import ru.kata.spring.boot_security.demo.models.User;
import ru.kata.spring.boot_security.demo.services.RoleService;
import ru.kata.spring.boot_security.demo.services.UserService;
import ru.kata.spring.boot_security.demo.util.UserValidator;

import javax.validation.Valid;
import java.security.Principal;
import java.util.List;
import java.util.Set;

@RestController
@RequestMapping("/api")
public class RESTController {

    private final UserService userService;
    private final RoleService roleService;
    private final UserValidator userValidator;

    public RESTController(UserService userService, RoleService roleService, UserValidator userValidator) {
        this.userService = userService;
        this.roleService = roleService;
        this.userValidator = userValidator;
    }

    @PostMapping("/auth/register")
    public ResponseEntity<HttpStatus> register(@Valid @RequestBody User user, BindingResult bindingResult) {
        userValidator.validate(user, bindingResult);
        if (bindingResult.hasErrors()) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        userService.register(user);
        return ResponseEntity.ok(HttpStatus.OK);
    }

    @GetMapping("/allUsers")
    public List<User> getAllUsers() {
        return userService.findAll();
    }

    @GetMapping("/user")
    public User user(Principal principal) {
        return userService.findByUsername(principal.getName());
    }

    @GetMapping("/admin/allRoles")
    public Set<Role> adminPage() {
        return roleService.findAll();
    }

    @GetMapping("/user/{id}")
    public User getUserById(@PathVariable int id) {
        return userService.findById(id);
    }

    @PutMapping("/user/{id}")
    public ResponseEntity<HttpStatus> update(@PathVariable int id, @Valid @RequestBody User user, BindingResult bindingResult) {
        userValidator.validate(user, bindingResult);
        if (bindingResult.hasErrors()) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        userService.update(user,id);
        return ResponseEntity.ok(HttpStatus.OK);
    }

    @DeleteMapping("/user/{id}")
    public ResponseEntity<HttpStatus> delete(@PathVariable int id) {
        userService.deleteById(id);
        return ResponseEntity.ok(HttpStatus.OK);
    }

    @PostMapping("/admin/newUser")
    public ResponseEntity<HttpStatus> newUser(@Valid @RequestBody User user, BindingResult bindingResult) {
        userValidator.validate(user, bindingResult);
        if (bindingResult.hasErrors()) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        userService.save(user);
        return ResponseEntity.ok(HttpStatus.OK);
    }

}
