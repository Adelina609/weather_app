package com.example.weatherapp;

import com.example.weatherapp.entities.City;

import java.util.List;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import io.reactivex.Flowable;
import io.reactivex.Observable;
import io.reactivex.Single;

@Dao
public interface CityDao {

    @Insert
    void insertAll(List<City> cities);

    @Delete
    void delete(City city);

    @Query("SELECT * FROM city")
    Single<List<City>> getCities();
}
