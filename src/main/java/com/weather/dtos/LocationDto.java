package com.weather.dtos;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@ToString
@EqualsAndHashCode(callSuper = false)
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class LocationDto extends BasicLocationDto {
    @NotNull
    private String state;

    @Min(-90)
    @Max(90)
    @NotNull
    private Double latitude;

    @Min(-180)
    @Max(180)
    @NotNull
    private Double longitude;
}
