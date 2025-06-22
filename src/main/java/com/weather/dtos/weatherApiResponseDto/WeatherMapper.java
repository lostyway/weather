package com.weather.dtos.weatherApiResponseDto;

import com.weather.dtos.WeatherDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface WeatherMapper {
    @Mapping(source = "main.temp", target = "temperature")
    @Mapping(source = "weather[0].description", target = "description")
    @Mapping(source = "main.feels_like", target = "feelsLike")
    @Mapping(source = "main.humidity", target = "humidity")
    @Mapping(source = "name", target = "city")
    @Mapping(source = "sys.country", target = "countryCode")
    @Mapping(expression = "java(\"https://openweathermap.org/img/wn/\" + source.getWeather().get(0).getIcon() + \"@4x.png\")", target = "picturePath")
    WeatherDto toDto(WeatherApiResponseDto source);
}
