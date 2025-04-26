package com.sijan.WeatherApp.model;

public class Weather {
    private String name;
    private Main main;
    private WeatherDetails[] weather;

    // Getters and Setters
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Main getMain() {
        return main;
    }

    public void setMain(Main main) {
        this.main = main;
    }

    public WeatherDetails[] getWeather() {
        return weather;
    }

    public void setWeather(WeatherDetails[] weather) {
        this.weather = weather;
    }
}

class Main {
    private double temp;

    public double getTemp() {
        return temp;
    }

    public void setTemp(double temp) {
        this.temp = temp;
    }
}

class WeatherDetails {
    private String description;
    private String icon;

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }
}
