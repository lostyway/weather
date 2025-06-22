package com.weather.dtos;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@ToString
@EqualsAndHashCode(callSuper = false)
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@JsonIgnoreProperties(ignoreUnknown = true)
public class WeatherDto extends BasicLocationDto {
    private Long id;
    @NotNull private String description;
    @NotNull private Integer feelsLike;
    @NotNull private Integer temperature;
    @NotNull private Integer humidity;
}
