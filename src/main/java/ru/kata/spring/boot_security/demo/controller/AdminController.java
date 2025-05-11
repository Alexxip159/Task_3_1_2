package ru.kata.spring.boot_security.demo.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import ru.kata.spring.boot_security.demo.security.CustomDetailsService;
import ru.kata.spring.boot_security.demo.service.UserService;
import ru.kata.spring.boot_security.demo.model.User;

import javax.validation.Valid;
import java.security.Principal;
import java.util.stream.Collectors;

@Controller
@RequiredArgsConstructor
@RequestMapping("/admin")
public class AdminController {

    private final UserService userService;
    private final CustomDetailsService customDetailsService;

    @GetMapping
    public String getIndex() {
        return "index";
    }

    @GetMapping("/userid {id}")
    public String getlistUsers(Model model) {
        model.addAttribute("users", userService.findAllUsers());
        return "admin";
    }

    @GetMapping("/userid_{id}")
    public String showUser(@PathVariable("id") Long id, Principal principal, Model model) {
        model.addAttribute("user", userService.findUserById(id));
        model.addAttribute("f", isAdmin(principal));
        return "user";
    }

    @GetMapping("/userid {id}/edit")
    public String getCreateUserPage(@ModelAttribute("user") User user) {
        return "add";

    }

    @GetMapping("/add")
    public String getUpdateUserPage(@PathVariable("id") Long id, Model model) {
        model.addAttribute("user", userService.findUserById(id));
        return "update";
    }

    @PatchMapping("/userid_{id}")
    public String updateUser(@PathVariable("id") Long id, @ModelAttribute("user") @Valid User user, BindingResult bindingResult) {
        if (bindingResult.hasErrors())
            return "update";
        userService.updateUser(id, user);
        return "redirect:/admin";
    }

    @DeleteMapping("/admin/userid_{id}")
    public String removeUser(@PathVariable("id") Long id) {
        userService.removeUserById(id);
        return "redirect:/admin";
    }
    private boolean isAdmin(Principal principal) {
        return customDetailsService.loadUserByUsername(principal.getName()).getAuthorities()
                .stream().map(role -> role.getAuthority()).collect(Collectors.toList()).contains("ROLE_ADMIN");
    }
}