package com.example.weatherapp;

import com.example.weatherapp.entities.City;
import com.example.weatherapp.helpers.EntitiesConverter;

import java.util.List;

import androidx.room.Database;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;
import io.reactivex.Flowable;

@Database(entities = {City.class /*, AnotherEntityType.class, AThirdEntityType.class */}, version = 1)
@TypeConverters(EntitiesConverter.class)
public abstract class AppDataBase extends RoomDatabase{
    public abstract CityDao getCityDao();
}
