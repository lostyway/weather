package com.weather.dtos.weatherApiResponseDto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@JsonIgnoreProperties(ignoreUnknown = true)
public class Main {
    private Double temp;
    private Double feels_like;
    private Integer humidity;
}
