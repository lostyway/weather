package com.weather.service;

import com.weather.api.OpenWeatherClient;
import com.weather.dtos.LocationDto;
import com.weather.model.Location;
import com.weather.model.UserEntity;
import com.weather.repository.WeatherRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class WeatherService {

    private final WeatherRepository weatherRepository;
    private final OpenWeatherClient openWeatherClient;


    public List<LocationDto> findWeatherByUser(UserEntity user) {
        List<Location> locations = weatherRepository.findByUser(user);
        openWeatherClient.getLocationDtosByLocations(locations);
        List<LocationDto> locationsDto = locations.stream()
    }
}
