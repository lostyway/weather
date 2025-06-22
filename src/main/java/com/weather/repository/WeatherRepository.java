package com.weather.repository;

import com.weather.model.Location;
import com.weather.model.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface WeatherRepository extends JpaRepository<Location, Long> {
    List<Location> findByUser(UserEntity user);
}
