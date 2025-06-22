package com.weather.controller;

import com.weather.dtos.UserDto;
import com.weather.service.UserService;
import jakarta.validation.ConstraintViolationException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequiredArgsConstructor
@RequestMapping("/user")
public class UserController {
    private final UserService userService;

    @GetMapping("/sign-in")
    public String getSignInPage() {
        return "sign-in";
    }

    @GetMapping("/sign-up-page")
    public String getSignUpPage() {
        return "sign-up";
    }

    @PostMapping("/sign-up")
    public String register(@RequestParam String username,
                           @RequestParam String password,
                           @RequestParam(name = "repeat-password") String repeatPassword,
                           Model model) {
        model.addAttribute("username", username);

        try {
            UserDto userDto = new UserDto(username, password, repeatPassword);
            userService.register(userDto);
            return "redirect:/sign-in";
        } catch (ConstraintViolationException e) {
            model.addAttribute("registrationError", "Длина логина или пароля меньше 3");
            return "sign-up";
        } catch (IllegalArgumentException | UsernameNotFoundException e) {
            model.addAttribute("registrationError", e.getMessage());
            return "sign-up";
        }
    }
}
