package com.example.weatherapi.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public record AccuWeatherLocationResponse(
        @JsonProperty("Key")
        String key,

        @JsonProperty("LocalizedName")
        String localizedName
) {
}