package com.example.wetterappb.model;

public class WeatherCurrent {
    private String observation_time;
    private String temperature;
    private int weather_code;
    private String feelslike;
    private String wind_speed;
    private String[] weather_icons;
    private String[] weather_descriptions;

    public String getWind_speed() {
        return wind_speed;
    }

    public String getObservation_time() {
        return observation_time;
    }

    public String getTemperature() {
        return temperature;
    }

    public int getWeather_code() {
        return weather_code;
    }

    public String getFeelslike() {
        return feelslike;
    }

    public String[] getWeather_icons() {
        return weather_icons;
    }

    public String[] getWeather_descriptions() {
        return weather_descriptions;
    }
}
