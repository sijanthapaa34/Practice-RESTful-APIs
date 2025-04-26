package com.sijan.WeatherApp.controller;

import com.sijan.WeatherApp.model.Weather;
import com.sijan.WeatherApp.service.WeatherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class WeatherController {
    @Autowired
    private WeatherService weatherService;

    @GetMapping("/weather")
    public Weather getWeather(@RequestParam String city) {
        return weatherService.getWeather(city);
    }
}
