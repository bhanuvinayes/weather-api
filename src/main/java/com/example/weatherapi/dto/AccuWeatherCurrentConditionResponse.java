package com.example.weatherapi.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public record AccuWeatherCurrentConditionResponse(

        @JsonProperty("LocalObservationDateTime")
        String localObservationDateTime,

        @JsonProperty("WeatherText")
        String weatherText,

        @JsonProperty("Temperature")
        Temperature temperature
) {

    public record Temperature(

            @JsonProperty("Metric")
            UnitValue metric,

            @JsonProperty("Imperial")
            UnitValue imperial
    ) {
    }

    public record UnitValue(

            @JsonProperty("Value")
            Double value,

            @JsonProperty("Unit")
            String unit
    ) {
    }
}