package com.weather.dtos.weatherApiResponseDto;

import com.weather.dtos.LocationDto;
import com.weather.model.Location;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface LocationMapper {

    LocationDto toDto(Location location);
}
