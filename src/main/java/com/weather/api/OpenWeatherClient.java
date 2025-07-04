package com.weather.api;

import com.fasterxml.jackson.databind.json.JsonMapper;
import com.weather.dtos.WeatherDto;
import com.weather.dtos.weatherApiResponseDto.WeatherApiMapper;
import com.weather.dtos.weatherApiResponseDto.WeatherApiResponseDto;
import com.weather.exceptions.OpenWeatherException;
import com.weather.model.Location;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.net.URI;
import java.net.URISyntaxException;
import java.net.URLEncoder;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.charset.StandardCharsets;

@Component
@RequiredArgsConstructor
public class OpenWeatherClient {

    @Value("${weather.api.key}")
    private String apiKey;

    private final JsonMapper jsonMapper = new JsonMapper();
    private final HttpClient client = HttpClient.newHttpClient();
    private final WeatherApiMapper weatherMapper;


    public WeatherDto fetchWeatherDto(Location location) {
        try {
            WeatherApiResponseDto apiResponseDto = requestToOpenWeatherWithLocation(location);
            WeatherDto dto = weatherMapper.toWeatherDto(apiResponseDto);
            dto.setId(location.getId());
            return dto;
        } catch (Exception e) {
            throw new OpenWeatherException("Не получилось получить погоду в по этой локации");
        }
    }

    private WeatherApiResponseDto requestToOpenWeatherWithLocation(Location location) throws URISyntaxException, java.io.IOException, InterruptedException {
        HttpRequest request = HttpRequest.newBuilder()
                .uri(uriByLocation(location))
                .GET()
                .build();

        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

        return jsonMapper.readValue(response.body(), WeatherApiResponseDto.class);
    }

    public WeatherApiResponseDto fetchByCityName(String city) {
        try {
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(uriByCityName(city))
                    .GET()
                    .build();

            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
            return jsonMapper.readValue(response.body(), WeatherApiResponseDto.class);

        } catch (Exception e) {
            throw new OpenWeatherException("Не получилось найти прогноз на город %s".formatted(city));
        }
    }

    private URI uriByCityName(String city) throws URISyntaxException {
        String encodedCity = URLEncoder.encode(city, StandardCharsets.UTF_8);

        return new URI(
                String.format("https://api.openweathermap.org/data/2.5/weather?q=%s&appid=%s&units=metric",
                        encodedCity, apiKey)
        );
    }

    private URI uriByLocation(Location location) throws URISyntaxException {
       return new URI(
                String.format("https://api.openweathermap.org/data/2.5/weather?lat=%f&lon=%f&units=metric&appid=%s",
                        location.getLatitude(), location.getLongitude(), apiKey));
    }
}
