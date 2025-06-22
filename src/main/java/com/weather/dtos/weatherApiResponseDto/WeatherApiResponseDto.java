package com.weather.dtos.weatherApiResponseDto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@JsonIgnoreProperties(ignoreUnknown = true)
public class WeatherApiResponseDto {
    private List<Weather> weather;
    private Main main;
    private Sys sys;
    private String name;
    private Coord coord;
}
