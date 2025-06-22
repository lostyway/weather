package com.weather.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class LocationFormDto {
    private String city;
    private String countryCode;
    private Double temperature;
    private Double feelsLike;
    private String description;
    private Integer humidity;
    private String picturePath;
}
