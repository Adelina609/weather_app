package com.example.weatherapp.activities;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.location.Location;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.Toast;

import com.example.weatherapp.AppDataBase;
import com.example.weatherapp.R;
import com.example.weatherapp.adapter.WeatherAdapter;
import com.example.weatherapp.entities.City;
import com.example.weatherapp.entities.WeatherResponse;
import com.example.weatherapp.helpers.ConverterDegToDir;
import com.example.weatherapp.network.NetworkService;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.karumi.dexter.Dexter;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionDeniedResponse;
import com.karumi.dexter.listener.PermissionGrantedResponse;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.single.PermissionListener;

import java.util.ArrayList;
import java.util.List;

import androidx.room.Room;
import io.reactivex.Observable;
import io.reactivex.Scheduler;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private WeatherAdapter adapter;
    private FusedLocationProviderClient mfusedLocationProviderClient;
    private AppDataBase dataBase;

    public final String CITY = "city";
    public final String TEMP = "temp";
    public final String HUMIDITY = "humidity";
    public final String PRESSURE = "pressure";
    public final String WIND = "wind";
    public final String COUNTRY = "country";
    public final int CITY_COUNT = 20;
    public final String APP_ID = "56fc6c6cb76c0864b4cd055080568268";
    public final String UNITS = "metric";

    private double lat;
    private double lon;

    @SuppressLint({"MissingPermission", "CheckResult"})
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        recyclerView = findViewById(R.id.rv_main);

        WeatherAdapter.OnItemClickListener onItemClickListener = new WeatherAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(City city) {
                Intent intent = new Intent(MainActivity.this, DetailsActivity.class);
                intent.putExtra(TEMP, "" + Math.round(city.getMain().getTemp() - 273));
                intent.putExtra(HUMIDITY, "" + city.getMain().getHumidity());
                intent.putExtra(PRESSURE, "" + city.getMain().getPressure());
                intent.putExtra(WIND, ConverterDegToDir.simpleConvertDeg(city.getWind().getDeg()));
                intent.putExtra(CITY, city.getName());
                if (city.getSys().getCountry().equals("")) {
                    city.getSys().setCountry("Russia");
                    intent.putExtra(COUNTRY, "Russia");
                } else {
                    intent.putExtra(COUNTRY, city.getSys().getCountry());
                }
                startActivity(intent);
            }
        };

        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new WeatherAdapter(new ArrayList<City>(0), onItemClickListener);
        recyclerView.setAdapter(adapter);

        Dexter.withActivity(this)
                .withPermission(Manifest.permission.ACCESS_FINE_LOCATION)
                .withListener(new PermissionListener() {
                    @Override
                    public void onPermissionGranted(PermissionGrantedResponse response) {
                        mfusedLocationProviderClient.getLastLocation()
                                .addOnSuccessListener(MainActivity.this, new OnSuccessListener<Location>() {
                                    @Override
                                    public void onSuccess(Location location) {
                                        lat = location.getLatitude();
                                        lon = location.getLongitude();
                                    }
                                });
                    }

                    @Override
                    public void onPermissionDenied(PermissionDeniedResponse response) {
                        mfusedLocationProviderClient.getLastLocation()
                                .addOnFailureListener(MainActivity.this, new OnFailureListener() {
                                    @Override
                                    public void onFailure(@NonNull Exception e) {
                                        //Lat and Long of the Kazan
                                        lat = 55.8;
                                        lon = 49.0;
                                    }
                                });
                    }

                    @Override
                    public void onPermissionRationaleShouldBeShown(PermissionRequest permission, PermissionToken token) {
                        token.continuePermissionRequest();
                    }
                }).check();

        dataBase = Room.databaseBuilder(getApplicationContext(),
                AppDataBase.class, "cities").build();

        NetworkService.getInstance()
                .getWeatherService()
                .getWeatherData(lat, lon, CITY_COUNT, APP_ID, UNITS)
                .map(WeatherResponse::getList)
                .map(list -> {
                    dataBase.getCityDao().insertAll(list);
                    return list;
                })
                .onErrorResumeNext(error ->
                        dataBase.getCityDao().getCities()
                )
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(list -> adapter.updateData(list), throwable -> {

//                    dataBase.getCityDao().getCities()
//                            .subscribe(list -> {
//                                        adapter.updateData(list);
//                                    }
//                            );
                    Toast.makeText(MainActivity.this, "Can't get connection", Toast.LENGTH_SHORT).show();
                });

    }

}
