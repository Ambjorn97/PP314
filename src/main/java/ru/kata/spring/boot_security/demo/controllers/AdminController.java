package ru.kata.spring.boot_security.demo.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import ru.kata.spring.boot_security.demo.models.User;
import ru.kata.spring.boot_security.demo.services.RoleService;
import ru.kata.spring.boot_security.demo.services.UserService;


@Controller
@RequestMapping("/admin")
public class AdminController {
    private final UserService userService;
    private final RoleService roleService;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public AdminController(UserService userService, RoleService roleService, PasswordEncoder passwordEncoder) {
        this.userService = userService;
        this.roleService = roleService;
        this.passwordEncoder = passwordEncoder;
    }

    @GetMapping
    public String adminPage(Model model, @ModelAttribute("newUser") User user) {
        model.addAttribute("users", userService.findAll());
        model.addAttribute("availableRoles", roleService.findAll());
        return "layout";
    }

    @PostMapping("/edit")
    public String edit(@ModelAttribute("user") User user) {

        int id = user.getId();
        User exUser = userService.findById(id).get();
        exUser.setUsername(user.getUsername());
        exUser.setAge(user.getAge());
        exUser.setRoles(user.getRoles());
        userService.save(exUser);
        return "redirect:/admin";
    }

    @DeleteMapping("/delete")
    public String delete(@ModelAttribute("user") User user) {
        userService.deleteById(user.getId());
        return "redirect:/admin";
    }

    @PostMapping("/newUser")
    public String newUser(@ModelAttribute("newUser") User user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        userService.save(user);
        return "redirect:/admin";
    }

}
