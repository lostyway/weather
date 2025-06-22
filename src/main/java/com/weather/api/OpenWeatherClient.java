package com.weather.api;

import com.fasterxml.jackson.databind.json.JsonMapper;
import com.weather.WeatherApplication;
import com.weather.dtos.WeatherDto;
import com.weather.dtos.weatherApiResponseDto.WeatherApiResponseDto;
import com.weather.dtos.weatherApiResponseDto.WeatherMapper;
import com.weather.model.Location;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

@Component
@RequiredArgsConstructor
public class OpenWeatherClient {

    @Value("${weather.api.key}")
    private String apiKey;

    private final JsonMapper jsonMapper = new JsonMapper();
    private final HttpClient client = HttpClient.newHttpClient();
    private final WeatherMapper weatherMapper;


    public WeatherDto getLocationDtoByLocations(Location location) {
        try {
            URI uri = new URI(
                    String.format("https://api.openweathermap.org/data/2.5/weather?lat=%f&lon=%f&units=metric&appid=%s",
                            location.getLatitude(), location.getLongitude(), apiKey));

            HttpRequest request = HttpRequest.newBuilder()
                    .uri(uri)
                    .GET()
                    .build();

            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

            WeatherApiResponseDto apiResponseDto = jsonMapper.readValue(response.body(), WeatherApplication.class);

            return weatherMapper.toDto(apiResponseDto);
        } catch (Exception e) {
            throw new RuntimeException("Не получилось получить погоду в по этой локации");
        }
    }
}
