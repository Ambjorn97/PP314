package ru.kata.spring.boot_security.demo.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import ru.kata.spring.boot_security.demo.models.User;
import ru.kata.spring.boot_security.demo.services.UserService;
import ru.kata.spring.boot_security.demo.util.UserValidator;

import java.security.Principal;

@Controller
public class UserController {

    private final UserService userService;

    @Autowired
    public UserController(UserService userService, UserValidator userValidator) {
        this.userService = userService;
    }

    @GetMapping("/allUsers")
    public String users(Model model) {
        model.addAttribute("users", userService.findAll());
        return "layout";


    }

    @GetMapping("/user")
    public String user(Model model) {
        return "layout";
    }

    @ModelAttribute("user")
    public User getAuthenticatedUser(Principal principal) {
        return principal != null ? userService.findByUsername(principal.getName()).orElse(null) : null;
    }
}
