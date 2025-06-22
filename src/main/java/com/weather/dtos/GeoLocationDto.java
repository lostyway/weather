package com.weather.dtos;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class GeoLocationDto {
    private String name;
    private String country;
    private String state;
    private double lat;
    private double lon;
}
