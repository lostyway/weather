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

    public List<Location> getLocationsByUser(UserEntity user) {
        return locationRepository.getLocationsByUser(user);
    }

    public boolean isLocationByUserExist(UserEntity user, String city) {
        return locationRepository.existsByUserAndName(user, city);
    }

    public void saveLocation(Location location) {
        locationRepository.save(location);
    }

    public void deleteByIdAndUser(Long id, UserEntity user) {
        Location location = locationRepository.findByIdAndUser(id, user)
                .orElseThrow(() -> new OpenWeatherException("Локация не была найдена или не принадлежит пользователю"));

        locationRepository.delete(location);
    }
}
