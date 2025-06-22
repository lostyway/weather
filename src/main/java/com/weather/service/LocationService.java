package com.weather.service;

import com.weather.exceptions.OpenWeatherException;
import com.weather.model.Location;
import com.weather.model.UserEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class LocationService {

    private final LocationRepository locationRepository;

    @Transactional(readOnly = true)
    public List<Location> getLocationsByUser(UserEntity user) {
        return locationRepository.getLocationsByUser(user);
    }

    @Transactional(readOnly = true)
    public boolean isLocationByUserExist(UserEntity user, String city) {
        return locationRepository.existsByUserAndName(user, city);
    }

    @Transactional
    public void saveLocation(Location location) {
        locationRepository.save(location);
    }

    @Transactional
    public void deleteByIdAndUser(Long id, UserEntity user) {
        Location location = locationRepository.findByIdAndUser(id, user)
                .orElseThrow(() -> new OpenWeatherException("Локация не была найдена или не принадлежит пользователю"));

        locationRepository.delete(location);
    }
}
