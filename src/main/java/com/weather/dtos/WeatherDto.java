package com.weather.dtos;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

@ToString
@EqualsAndHashCode(callSuper = false)
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class WeatherDto extends BasicLocationDto {
    @NotNull private String picturePath;
    @NotNull private String description;
    @NotNull private Integer feelsLike;
    @NotNull private Integer temperature;
    @NotNull private Integer humidity;
}
