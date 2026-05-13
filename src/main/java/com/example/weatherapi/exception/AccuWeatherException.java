package com.example.weatherapi.exception;

public class AccuWeatherException extends RuntimeException {

    public AccuWeatherException(String message) {
        super(message);
    }

    public AccuWeatherException(String message, Throwable cause) {
        super(message, cause);
    }
}