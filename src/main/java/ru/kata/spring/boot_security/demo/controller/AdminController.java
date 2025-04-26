package ru.kata.spring.boot_security.demo.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import ru.kata.spring.boot_security.demo.service.UserService;
import ru.kata.spring.boot_security.demo.model.User;

import javax.validation.Valid;
import java.security.Principal;
import java.util.stream.Collectors;

@Controller
public class AdminController {

    private final UserService userService;

    public AdminController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping(value = {"/", "/index"})
    public String index() {
        return "index";
    }

    @GetMapping( "/user")
    public String userInfo(Principal principal, Model model) {
        model.addAttribute("user", userService.loadUserByUsername(principal.getName()));
        model.addAttribute("f", isAdmin(principal));
        return "user";
    }

    @GetMapping("/admin")
    public String listUsers(Model model) {
        model.addAttribute("users", userService.findAllUsers());
        return "admin";
    }

    @GetMapping("/admin/userid_{id}")
    public String showUser(@PathVariable("id") Long id, Principal principal, Model model) {
        model.addAttribute("user", userService.findUserById(id));
        model.addAttribute("f", isAdmin(principal));
        return "user";
    }

    @GetMapping("/admin/add")
    public String addUser(@ModelAttribute("user") User user) {
        return "add";
    }

    @PostMapping()
    public String create(@ModelAttribute("user") @Valid User user, BindingResult bindingResult) {
        if (bindingResult.hasErrors())
            return "add";

        userService.addUser(user);
        return "redirect:/admin";
    }

    @GetMapping("/admin/userid_{id}/edit")
    public String updateUser(@PathVariable("id") Long id, Model model) {
        model.addAttribute("user", userService.findUserById(id));
        return "update";
    }

    @PatchMapping("/admin/userid_{id}")
    public String update(@PathVariable("id") Long id, @ModelAttribute("user") @Valid User user, BindingResult bindingResult) {
        if (bindingResult.hasErrors())
            return "update";
        userService.updateUser(id, user);
        return "redirect:/admin";
    }

    @DeleteMapping("/admin/userid_{id}")
    public String remove(@PathVariable("id") Long id) {
        userService.removeUserById(id);
        return "redirect:/admin";
    }

    private boolean isAdmin(Principal principal) {
        return userService.loadUserByUsername(principal.getName()).getAuthorities()
                .stream().map(role -> role.getAuthority()).collect(Collectors.toList()).contains("ROLE_ADMIN");
    }

}
