package com.weather.controller;

import com.weather.api.OpenWeatherClient;
import com.weather.dtos.WeatherDto;
import com.weather.dtos.weatherApiResponseDto.WeatherApiMapper;
import com.weather.dtos.weatherApiResponseDto.WeatherApiResponseDto;
import com.weather.exceptions.OpenWeatherException;
import com.weather.model.Location;
import com.weather.model.UserEntity;
import com.weather.service.LocationService;
import com.weather.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequiredArgsConstructor
@RequestMapping("/")
public class WeatherApiController {

    private final OpenWeatherClient openWeatherClient;
    private final UserService userService;
    private final LocationService locationService;
    private final WeatherApiMapper weatherApiMapper;

    //todo Доделать реализацию API

    @GetMapping("/")
    public String showUserLocations(Model model, @AuthenticationPrincipal User user) {
        model.addAttribute("username", user.getUsername());

        UserEntity userFromDb = userService.findByLogin(user.getUsername());
        List<Location> locationsList = locationService.getLocationByUser(userFromDb);
        var locations = locationsList.stream()
                .map(openWeatherClient::fetchWeatherDto)
                .toList();
        model.addAttribute("locations", locations);

        return "index";
    }

    @GetMapping("/search")
    public String search(Model model, @AuthenticationPrincipal User user, @RequestParam("name") String city) {
        model.addAttribute("username", user.getUsername());
        try {
            WeatherApiResponseDto apiDto = openWeatherClient.fetchByCityName(city);
            WeatherDto location = weatherApiMapper.toWeatherDto(apiDto);
            model.addAttribute("location", location);
            System.out.println(location);
            return "search-results";
        } catch (OpenWeatherException e) {
            model.addAttribute("errorTitle", "Локация не найдена");
            model.addAttribute("errorMessage", e.getMessage());
            return "error";
        }
    }
}
