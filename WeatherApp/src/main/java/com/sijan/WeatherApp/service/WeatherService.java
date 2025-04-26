package com.sijan.WeatherApp.service;

import com.sijan.WeatherApp.model.Weather;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

//@Service
//public class WeatherService {
//
//    @Value("${openweather.api.key}")
//    private String apiKey;
//
//    @Value("${openweather.api.url}")
//    private String apiUrl;
//    public Weather getWeather(String city) {
//        RestTemplate restTemplate = new RestTemplate();
//        String url = apiUrl + "?q=" + city + "&appid=" + apiKey + "&units=metric";
//        return restTemplate.getForObject(url, Weather.class);
//    }
//}


@Service
public class WeatherService {

    @Value("${openweather.api.key}")
    private String apiKey;

    @Value("${openweather.api.url}")
    private String apiUrl;

    public Weather getWeather(String city) {
        if (city == null || city.trim().isEmpty()) {
            throw new IllegalArgumentException("City name must not be empty");
        }

        RestTemplate restTemplate = new RestTemplate();
        String url = String.format("%s?q=%s&appid=%s&units=metric", apiUrl, city, apiKey);

        try {
            return restTemplate.getForObject(url, Weather.class);
        } catch (HttpClientErrorException e) {
            throw new RuntimeException("Error fetching weather data: " + e.getResponseBodyAsString(), e);
        }
    }

}
