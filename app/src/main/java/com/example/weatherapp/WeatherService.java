package com.example.weatherapp;

import com.example.weatherapp.entities.WeatherResponse;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface WeatherService {
    @GET("/data/2.5/find")
    Call<WeatherResponse> getPostOfUser(@Query("lat") double lat, @Query("lon") double lon,
                                               @Query("cnt") int cnt, @Query("appid") String appid);
}
