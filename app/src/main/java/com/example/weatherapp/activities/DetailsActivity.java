package com.example.weatherapp.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import com.example.weatherapp.R;
import com.example.weatherapp.entities.City;

public class DetailsActivity extends AppCompatActivity {

    public final String CITY = "city";
    public final String TEMP = "temp";
    public final String HUMIDITY = "humidity";
    public final String PRESSURE = "pressure";
    public final String WIND = "wind";
    public final String COUNTRY = "country";

    private TextView tv_temp;
    private TextView tv_humidity;
    private TextView tv_pressure;
    private TextView tv_wind;
    private TextView tv_city;
    private TextView tv_country;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.details_activity);
        tv_temp = findViewById(R.id.tv_temperature_value);
        tv_humidity = findViewById(R.id.tv_humidity_value);
        tv_pressure = findViewById(R.id.tv_pressure_value);
        tv_wind = findViewById(R.id.tv_wind_value);
        tv_city = findViewById(R.id.tv_city_name);
        tv_country = findViewById(R.id.tv_country_name);
        Intent intent = getIntent();
        tv_temp.setText(intent.getStringExtra(TEMP));
        tv_humidity.setText(intent.getStringExtra(HUMIDITY));
        tv_pressure.setText(intent.getStringExtra(PRESSURE));
        tv_wind.setText(intent.getStringExtra(WIND));
        tv_city.setText(intent.getStringExtra(CITY));
        tv_country.setText(intent.getStringExtra(COUNTRY));

    }
}
