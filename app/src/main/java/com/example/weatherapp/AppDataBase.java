package com.example.weatherapp;

import com.example.weatherapp.entities.City;

import java.util.List;

import androidx.room.Database;
import androidx.room.RoomDatabase;
import io.reactivex.Flowable;

@Database(entities = {City.class /*, AnotherEntityType.class, AThirdEntityType.class */}, version = 1)
public abstract class AppDataBase extends RoomDatabase{
    public abstract CityDao getCityDao();
}
