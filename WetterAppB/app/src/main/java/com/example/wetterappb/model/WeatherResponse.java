package com.example.wetterappb.model;

public class WeatherResponse {
    private WeatherRequest request;
    private WeatherCurrent current;
    private WeatherLocation location;

    public WeatherRequest getRequest() {
        return request;
    }

    public WeatherCurrent getCurrent() {
        return current;
    }

    public WeatherLocation getLocation() {
        return location;
    }

}
