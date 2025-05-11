package ru.kata.spring.boot_security.demo.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import ru.kata.spring.boot_security.demo.model.User;
import ru.kata.spring.boot_security.demo.security.CustomDetailsService;
import ru.kata.spring.boot_security.demo.service.UserService;

import javax.validation.Valid;
import java.security.Principal;
import java.util.stream.Collectors;

@Controller
@RequiredArgsConstructor
@RequestMapping("/user")
public class UserController {

    private final UserService userService;
    private final CustomDetailsService customDetailsService;

    @GetMapping( "")
    public String getUserInfo(Principal principal, Model model) {
        model.addAttribute("user", customDetailsService.loadUserByUsername(principal.getName()));
        model.addAttribute("f", isAdmin(principal));
        return "user";
    }

    @PostMapping()
    public String createUser(@ModelAttribute("user") @Valid User user, BindingResult bindingResult) {
        if (bindingResult.hasErrors())
            return "add";

        userService.CreateUser(user);
        return "redirect:/admin";
    }

    private boolean isAdmin(Principal principal) {
        return customDetailsService.loadUserByUsername(principal.getName()).getAuthorities()
                .stream().map(role -> role.getAuthority()).collect(Collectors.toList()).contains("ROLE_ADMIN");
    }
}
