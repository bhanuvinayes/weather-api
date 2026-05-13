package com.example.weatherapi.client;

import com.example.weatherapi.config.AccuWeatherProperties;
import com.example.weatherapi.dto.AccuWeatherCurrentConditionResponse;
import com.example.weatherapi.dto.AccuWeatherLocationResponse;
import com.example.weatherapi.exception.AccuWeatherException;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpStatusCode;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;
import org.springframework.web.client.RestClientException;

import java.util.List;

@Service
public class AccuWeatherClient {

    private final RestClient restClient;
    private final AccuWeatherProperties properties;

    public AccuWeatherClient(
            RestClient accuweatherRestClient,
            AccuWeatherProperties properties
    ) {
        this.restClient = accuweatherRestClient;
        this.properties = properties;
    }

    public String getLocationKey(String city) {
        try {
            List<AccuWeatherLocationResponse> locations = restClient.get()
                    .uri(uriBuilder -> uriBuilder
                            .path("/locations/v1/cities/search")
                            .queryParam("apikey", properties.apiKey())
                            .queryParam("q", city)
                            .build())
                    .retrieve()
                    .onStatus(HttpStatusCode::isError, (request, response) -> {
                        throw new AccuWeatherException(
                                "AccuWeather location API failed: " + response.getStatusCode()
                        );
                    })
                    .body(new ParameterizedTypeReference<>() {
                    });

            if (locations == null || locations.isEmpty()) {
                throw new AccuWeatherException("No AccuWeather location found for city: " + city);
            }

            return locations.get(0).key();

        } catch (RestClientException ex) {
            throw new AccuWeatherException("Failed to call AccuWeather location API", ex);
        }
    }

    public AccuWeatherCurrentConditionResponse getCurrentCondition(String locationKey) {
        try {
            List<AccuWeatherCurrentConditionResponse> response = restClient.get()
                    .uri(uriBuilder -> uriBuilder
                            .path("/currentconditions/v1/{locationKey}")
                            .queryParam("apikey", properties.apiKey())
                            .queryParam("details", false)
                            .build(locationKey))
                    .retrieve()
                    .onStatus(HttpStatusCode::isError, (request, apiResponse) -> {
                        throw new AccuWeatherException(
                                "AccuWeather current conditions API failed: "
                                        + apiResponse.getStatusCode()
                        );
                    })
                    .body(new ParameterizedTypeReference<>() {
                    });

            if (response == null || response.isEmpty()) {
                throw new AccuWeatherException(
                        "No current condition found for location key: " + locationKey
                );
            }

            return response.get(0);

        } catch (RestClientException ex) {
            throw new AccuWeatherException("Failed to call AccuWeather current conditions API", ex);
        }
    }

    public AccuWeatherCurrentConditionResponse getCurrentConditionByCity(String city) {
        String locationKey = getLocationKey(city);
        return getCurrentCondition(locationKey);
    }
}