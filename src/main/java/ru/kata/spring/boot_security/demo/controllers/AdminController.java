package ru.kata.spring.boot_security.demo.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import ru.kata.spring.boot_security.demo.models.Role;
import ru.kata.spring.boot_security.demo.models.User;
import ru.kata.spring.boot_security.demo.services.RoleService;
import ru.kata.spring.boot_security.demo.services.UserService;

import javax.validation.Valid;

@Controller
@RequestMapping("/admin")
public class AdminController {
    private final UserService userService;
    private final RoleService roleService;

    @Autowired
    public AdminController(UserService userService, RoleService roleService) {
        this.userService = userService;
        this.roleService = roleService;
    }

    @GetMapping
    public String adminPage() {
        return "admin";
    }

    @GetMapping("/add")
    public String addAdminPage(@ModelAttribute("user") User user, Model model) {
        model.addAttribute("users", userService.findAll());
        return "addAdmin";
    }

    @PostMapping("/add")
    public String addUserToAdmin(@ModelAttribute("user") User user) {
        int id = user.getId();
        User exUser = userService.findById(id).get();
        Role role = roleService.findByName("ROLE_ADMIN");
        exUser.getRoles().add(role);
        userService.save(exUser);
        return "redirect:/admin";
    }

    @GetMapping("/delete-user")
    public String deleteUserPage(@ModelAttribute("user") User user, Model model) {
        model.addAttribute("users", userService.findAll());
        return "deleteUser";
    }

    @PostMapping("/delete-user")
    public String deleteUser(@ModelAttribute("user") User user) {
        int id = user.getId();
        userService.deleteById(id);
        return "redirect:/admin";
    }

    @GetMapping("/edit")
    public String editPage(Model model) {
        model.addAttribute("users", userService.findAll());
        return "editUser";
    }

    @GetMapping("/edit/form")
    public String editForm(@RequestParam(name = "id", required = false) int id, Model model) {
        User user = userService.findById(id).get();
        model.addAttribute("user", user);
        return "editUserForm";
    }

    @PatchMapping("/edit/form")
    public String editUser(@Valid @ModelAttribute("user") User user, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "editUserForm";
        }
        int id = user.getId();
        User exUser = userService.findById(id).get();
        exUser.setUsername(user.getUsername());
        exUser.setAge(user.getAge());
        userService.save(exUser);
        return "redirect:/admin";
    }
}
