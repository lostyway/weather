package com.weather.api;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.json.JsonMapper;
import com.weather.dtos.GeoLocationDto;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.List;

@Component
@RequiredArgsConstructor
public class OpenWeatherClient {

    @Value("${weather.api.key}")
    private String apiKey;

    private final JsonMapper jsonMapper = new JsonMapper();
    private final HttpClient client = HttpClient.newHttpClient();

    public GeoLocationDto getLocation(String city) {
        try {
            URI uri = URI.create(String.format("https://api.openweathermap.org/geo/1.0/direct?q=%s&limit=1&appid=%s&units=metric", city, apiKey));
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(uri)
                    .GET()
                    .build();

            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
            List<GeoLocationDto> locations = jsonMapper.readValue(response.body(), new TypeReference<>() {});

            if (locations.isEmpty()) {
                throw new RuntimeException("Локация не найдена");
            }
            return locations.getFirst();
        } catch (Exception e) {
            throw new RuntimeException("Ошибка при получении локации: " + e.getMessage(), e);
        }
    }
}
