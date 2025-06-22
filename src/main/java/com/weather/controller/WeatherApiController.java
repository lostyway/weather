package com.weather.controller;

import com.weather.api.OpenWeatherClient;
import com.weather.dtos.LocationDto;
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

    @GetMapping("/")
    public String showUserLocations(Model model, @AuthenticationPrincipal User user) {
        model.addAttribute("username", user.getUsername());

        UserEntity userFromDb = userService.findByLogin(user.getUsername());
        List<Location> locationsList = locationService.getLocationsByUser(userFromDb);
        var locations = locationsList.stream()
                .map(openWeatherClient::fetchWeatherDto)
                .toList();
        model.addAttribute("locations", locations);

        return "index";
    }

    @GetMapping("/search")
    public String search(Model model, @AuthenticationPrincipal User user, @RequestParam("name") String city) {
        model.addAttribute("username", user.getUsername());
        WeatherApiResponseDto apiDto = openWeatherClient.fetchByCityName(city.trim());
        LocationDto location = weatherApiMapper.toLocationDto(apiDto);
        model.addAttribute("location", location);
        return "search-results";
    }

    @PostMapping("/locations/add")
    public String addLocation(@AuthenticationPrincipal User user, @ModelAttribute LocationDto locationForm) {
        String city = locationForm.getCity();
        UserEntity userFromDb = userService.findByLogin(user.getUsername());

        boolean existingByUser = locationService.isLocationByUserExist(userFromDb, city);
        if (existingByUser) {
            throw new OpenWeatherException("Вы уже сохранили эту локацию!");
        }

        WeatherApiResponseDto weatherApiResponseDto = openWeatherClient.fetchByCityName(city.trim());
        Double latitude = locationForm.getLatitude();
        Double longitude = locationForm.getLongitude();
        Location location = weatherApiMapper.toLocation(weatherApiResponseDto, latitude, longitude, userFromDb);

        locationService.saveLocation(location);
        return "redirect:/";
    }

    @PostMapping("/locations/delete/{id}")
    public String deleteLocation(@PathVariable("id") Long id, @AuthenticationPrincipal User user) {
        UserEntity userFromDb = userService.findByLogin(user.getUsername());
        locationService.deleteByIdAndUser(id, userFromDb);
        return "redirect:/";
    }
}
