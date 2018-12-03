package com.example.weatherapp.activities;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.location.Location;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import com.example.weatherapp.R;
import com.example.weatherapp.adapter.WeatherAdapter;
import com.example.weatherapp.entities.City;
import com.example.weatherapp.entities.WeatherResponse;
import com.example.weatherapp.helpers.ConverterDegToDir;
import com.example.weatherapp.network.NetworkService;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.tasks.OnSuccessListener;

import java.util.ArrayList;
import java.util.List;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private WeatherAdapter adapter;
    private FusedLocationProviderClient mfusedLocationProviderClient;

    public final String CITY = "city";
    public final String TEMP = "temp";
    public final String HUMIDITY = "humidity";
    public final String PRESSURE = "pressure";
    public final String WIND = "wind";
    public final String COUNTRY = "country";

    private double lat;
    private double lon;

    @SuppressLint("MissingPermission")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        recyclerView = findViewById(R.id.rv_main);

        WeatherAdapter.OnItemClickListener onItemClickListener = new WeatherAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(City city) {
                Intent intent = new Intent(MainActivity.this, DetailsActivity.class);
                intent.putExtra(TEMP, ""+Math.round(city.getMain().getTemp()-273));
                intent.putExtra(HUMIDITY, "" + city.getMain().getHumidity());
                intent.putExtra(PRESSURE, "" + city.getMain().getPressure());
                intent.putExtra(WIND, ConverterDegToDir.simpleConvertDeg(city.getWind().getDeg()));
                intent.putExtra(CITY, city.getName());
                if(city.getSys().getCountry().equals("")){
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

        mfusedLocationProviderClient.getLastLocation()
                .addOnSuccessListener(this, new OnSuccessListener<Location>() {
                    @Override
                    public void onSuccess(Location location) {
                        if (location != null) {
                            lat = location.getLatitude();
                            lon = location.getLongitude();
                        } else {
                            //Lat and Long of the Kazan
                            lat = 55.8;
                            lon = 49.0;
                        }
                    }
                });

        NetworkService.getInstance()
                .getJSONApi()
                .getPostOfUser(lat, lon, 20,"56fc6c6cb76c0864b4cd055080568268")
                .enqueue(new Callback<WeatherResponse>() {
                    @Override
                    public void onResponse(Call<WeatherResponse> call, Response<WeatherResponse> response) {
                        WeatherResponse weatherResponse = response.body();
                        if (weatherResponse != null) {
                            List<City> list = weatherResponse.getList();
                            adapter.updateData(list);
                        }
                    }
                    @Override
                    public void onFailure(Call<WeatherResponse> call, Throwable t) {
                        System.out.println(t.getMessage());
                    }
                });
    }
}
