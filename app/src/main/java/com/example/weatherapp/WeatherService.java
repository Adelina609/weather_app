package com.example.weatherapp;

import com.example.weatherapp.entities.WeatherResponse;

import io.reactivex.Single;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface WeatherService {
    @GET("/data/2.5/find")
    Single<WeatherResponse> getWeatherData(@Query("lat") double lat, @Query("lon") double lon,
                                           @Query("cnt") int cnt, @Query("appid") String appid, @Query("units") String units);

}
