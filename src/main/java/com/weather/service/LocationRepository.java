package com.weather.service;

import com.weather.model.Location;
import com.weather.model.UserEntity;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Optional;

public interface LocationRepository extends CrudRepository<Location, Long> {
    List<Location> getLocationsByUser(UserEntity user);

    boolean existsByUserAndName(UserEntity user, String name);

    Optional<Location> findByIdAndUser(long id, UserEntity user);
}
