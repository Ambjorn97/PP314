package ru.kata.spring.boot_security.demo.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import ru.kata.spring.boot_security.demo.models.User;
import ru.kata.spring.boot_security.demo.services.RoleService;
import ru.kata.spring.boot_security.demo.services.UserService;
import ru.kata.spring.boot_security.demo.util.UserValidator;

import javax.validation.Valid;

@Controller
@RequestMapping("/admin")
public class AdminController {
    private final UserService userService;
    private final RoleService roleService;
    private final UserValidator userValidator;

    @Autowired
    public AdminController(UserService userService, RoleService roleService, UserValidator userValidator) {
        this.userService = userService;
        this.roleService = roleService;
        this.userValidator = userValidator;
    }

    @GetMapping
    public String adminPage(Model model, @ModelAttribute("newUser") User user) {
        model.addAttribute("users", userService.findAll());
        model.addAttribute("availableRoles", roleService.findAll());
        return "layout";
    }

    @PostMapping("/edit")
    public String edit( @Valid  @ModelAttribute("user") User user, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "layout";
        }
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
    public String newUser(@ModelAttribute("newUser") @Valid User user, BindingResult bindingResult) {
        userValidator.validate(user, bindingResult);
        if (bindingResult.hasErrors()) {
            return "layout";
        }
        userService.save(user);
        return "redirect:/admin";

    }


//    @GetMapping("/add")
//    public String addAdminPage(@ModelAttribute("user") User user, Model model) {
//        model.addAttribute("users", userService.findAll());
//        return "addAdmin";
//    }
//
//    @PostMapping("/add")
//    public String addUserToAdmin(@ModelAttribute("user") User user) {
//        int id = user.getId();
//        User exUser = userService.findById(id).get();
//        Role role = roleService.findByName("ROLE_ADMIN");
//        exUser.getRoles().add(role);
//        userService.save(exUser);
//        return "redirect:/admin";
//    }
//
//    @GetMapping("/delete-user")
//    public String deleteUserPage(@ModelAttribute("user") User user, Model model) {
//        model.addAttribute("users", userService.findAll());
//        return "deleteUser";
//    }
//
//    @PostMapping("/delete-user")
//    public String deleteUser(@ModelAttribute("user") User user) {
//        int id = user.getId();
//        userService.deleteById(id);
//        return "redirect:/admin";
//    }
//
//    @GetMapping("/edit")
//    public String editPage(Model model) {
//        model.addAttribute("users", userService.findAll());
//        return "editUser";
//    }
//
//    @GetMapping("/edit/form")
//    public String editForm(@RequestParam(name = "id", required = false) int id, Model model) {
//        User user = userService.findById(id).get();
//        model.addAttribute("user", user);
//        return "editUserForm";
//    }
//
//    @PatchMapping("/edit/form")
//    public String editUser(@Valid @ModelAttribute("user") User user, BindingResult bindingResult) {
//        if (bindingResult.hasErrors()) {
//            return "editUserForm";
//        }
//        int id = user.getId();
//        User exUser = userService.findById(id).get();
//        exUser.setUsername(user.getUsername());
//        exUser.setAge(user.getAge());
//        userService.save(exUser);
//        return "redirect:/admin";
//    }
}
