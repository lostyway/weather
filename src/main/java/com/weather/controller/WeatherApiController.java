package com.weather.controller;

import com.weather.api.OpenWeatherClient;
import com.weather.dtos.LocationDto;
import com.weather.model.UserEntity;
import com.weather.service.UserService;
import com.weather.service.WeatherService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequiredArgsConstructor
@RequestMapping("/")
public class WeatherApiController {

    private final OpenWeatherClient openWeatherClient;
    private final WeatherService weatherService;
    private final UserService userService;

    //todo Доделать реализацию API

    @GetMapping("/")
    public String index(Model model, @AuthenticationPrincipal User user) {
        model.addAttribute("username", user.getUsername());

        UserEntity userFromDb = userService.findByLogin(user.getUsername());
        List<LocationDto> locations = weatherService.findWeatherByUser(userFromDb);
        model.addAttribute("locations", locations);

        return "index";
    }

    @GetMapping("/search")
    public String search(Model model, @AuthenticationPrincipal User user) {
        model.addAttribute("username", user.getUsername());
        //List<Location> locations = weatherService.findDefaultLocations();
        return "search-results";
    }

    @PostMapping("/search/{city}")
    public String search(@PathVariable String city, Model model, @AuthenticationPrincipal User user) {
        model.addAttribute("username", user.getUsername());
        return "search-results";
    }
}
