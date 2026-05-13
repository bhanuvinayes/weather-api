package com.example.weatherapi.controller;

import com.example.weatherapi.client.AccuWeatherClient;
import com.example.weatherapi.dto.AccuWeatherCurrentConditionResponse;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/weather")
public class WeatherController {

    private final AccuWeatherClient accuWeatherClient;

    public WeatherController(AccuWeatherClient accuWeatherClient) {
        this.accuWeatherClient = accuWeatherClient;
    }

    @GetMapping("/current")
    public AccuWeatherCurrentConditionResponse getCurrentWeather(
            @RequestParam String city
    ) {
        return accuWeatherClient.getCurrentConditionByCity(city);
    }
}