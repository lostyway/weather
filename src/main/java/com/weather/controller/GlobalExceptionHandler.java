//package com.weather.controller;
//
//import org.springframework.ui.Model;
//import org.springframework.web.bind.annotation.ControllerAdvice;
//import org.springframework.web.bind.annotation.ExceptionHandler;
//
//@ControllerAdvice
//public class GlobalExceptionHandler {
//
//    @ExceptionHandler(exception = Exception.class)
//    public String handleException(Model model) {
//        model.addAttribute("errorTitle", "Произошло глобальное исключение");
//        model.addAttribute("errorMessage", "Какая-то ошибка");
//        return "error";
//    }
//}
