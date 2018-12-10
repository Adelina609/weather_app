package com.example.weatherapp;

import com.example.weatherapp.entities.City;
import com.example.weatherapp.helpers.EntitiesConverter;

import java.util.List;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;
import io.reactivex.Flowable;

@Database(entities = {City.class /*, AnotherEntityType.class, AThirdEntityType.class */}, version = 1)
@TypeConverters(EntitiesConverter.class)
public abstract class AppDataBase extends RoomDatabase{

    private static final String DATABASE_NAME = "my_app_name.db";
    private static AppDatabase sInstance;

    public abstract CityDao getCityDao();

    public static AppDatabase getInstance() {
        if (sInstance == null) {
            sInstance = Room.databaseBuilder(App.getContext(),
                    AppDatabase.class, DATABASE_NAME)
                    .fallbackToDestructiveMigration()
                    .build();
        }
        return sInstance;
    }
}
