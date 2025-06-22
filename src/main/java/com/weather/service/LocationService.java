package com.weather.service;

import com.weather.exceptions.OpenWeatherException;
import com.weather.model.Location;
import com.weather.model.UserEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class LocationService {

    private final LocationRepository locationRepository;

    public List<Location> getLocationByUser(UserEntity user) {
        return locationRepository.getLocationsByUser(user);
    }

    public Location getLocationByCity(String city) {
        return locationRepository.getLocationByName(city)
                .orElseThrow(() -> new OpenWeatherException("Город не был найден: " + city));
    }
}
