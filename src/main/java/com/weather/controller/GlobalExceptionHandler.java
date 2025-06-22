package com.weather.controller;

import com.weather.exceptions.OpenWeatherException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(exception = OpenWeatherException.class)
    public String handleOpenWeatherException(Model model, OpenWeatherException e) {
        model.addAttribute("errorTitle", "Произошла ошибка при поиске данных по погоде и городам");
        model.addAttribute("errorMessage", e.getMessage());
        return "error";
    }

    @ExceptionHandler(exception = UsernameNotFoundException.class)
    public String handleOpenWeatherException(Model model, UsernameNotFoundException e) {
        model.addAttribute("errorTitle", "Произошла ошибка при поиске пользователя");
        model.addAttribute("errorMessage", e.getMessage());
        return "error";
    }

    @ExceptionHandler(exception = Exception.class)
    public String handleException(Model model) {
        model.addAttribute("errorTitle", "Произошло глобальное исключение");
        model.addAttribute("errorMessage", "Какая-то ошибка");
        return "error";
    }
}
