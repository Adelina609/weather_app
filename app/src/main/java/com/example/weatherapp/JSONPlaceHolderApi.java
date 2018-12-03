package com.example.weatherapp;

import com.example.weatherapp.entities.WeatherResponse;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface JSONPlaceHolderApi {
    @GET("/data/2.5/find")
    public Call<WeatherResponse> getPostOfUser(@Query("lat") double lat, @Query("lon") double lon,
                                               @Query("cnt") int cnt, @Query("appid") String appid);
}