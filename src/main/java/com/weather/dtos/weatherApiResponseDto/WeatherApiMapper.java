package com.weather.dtos.weatherApiResponseDto;

import com.weather.dtos.LocationDto;
import com.weather.dtos.WeatherDto;
import com.weather.model.Location;
import com.weather.model.UserEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface WeatherApiMapper {

    @Mapping(source = "main.temp", target = "temperature")
    @Mapping(expression = "java(dto.getWeather() != null && !dto.getWeather().isEmpty() ? dto.getWeather().get(0).getDescription() : null)", target = "description")
    @Mapping(source = "main.feels_like", target = "feelsLike")
    @Mapping(source = "main.humidity", target = "humidity")
    @Mapping(source = "name", target = "city")
    @Mapping(source = "sys.country", target = "countryCode")
    @Mapping(expression = "java(dto.getWeather() != null && !dto.getWeather().isEmpty() ? \"https://openweathermap.org/img/wn/\" + dto.getWeather().get(0).getIcon() + \"@4x.png\" : null)", target = "picturePath")
    WeatherDto toWeatherDto(WeatherApiResponseDto dto);

    @Mapping(source = "dto.name", target = "city")
    @Mapping(source = "dto.sys.country", target = "countryCode")
    @Mapping(source = "dto.coord.lat", target = "latitude")
    @Mapping(source = "dto.coord.lon", target = "longitude")
    @Mapping(expression = "java(dto.getWeather() != null && !dto.getWeather().isEmpty() ? \"https://openweathermap.org/img/wn/\" + dto.getWeather().get(0).getIcon() + \"@4x.png\" : null)", target = "picturePath")
    LocationDto toLocationDto(WeatherApiResponseDto dto);

    @Mapping(source = "latitude", target = "latitude")
    @Mapping(source = "longitude", target = "longitude")
    @Mapping(source = "user", target = "user")
    @Mapping(target = "id", ignore = true)
    Location toLocation(WeatherApiResponseDto dto, Double latitude, Double longitude, UserEntity user);
}
