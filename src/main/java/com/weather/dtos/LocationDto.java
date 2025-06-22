package com.weather.dtos;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class LocationDto {
    @NotNull private Integer temperature;
    @NotNull private Integer humidity;
    @NotNull private Integer feelsLike;
    @NotNull private String country;
    @NotNull private String city;
    @NotNull private String codeOfCountry;
    @NotNull private String iconCode;
    @NotNull private String description;
}
