package com.example.weatherapp.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.weatherapp.R;
import com.example.weatherapp.adapter.WeatherAdapter;
import com.example.weatherapp.entities.City;
import com.example.weatherapp.entities.WeatherResponse;
import com.example.weatherapp.network.NetworkService;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    private TextView tv_city;
    private TextView tv_country;
    private TextView tv_temp;
    private RecyclerView recyclerView;
    private WeatherAdapter adapter;
    private List<City> cities;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        tv_city = findViewById(R.id.tv_city_item);
        tv_country = findViewById(R.id.tv_country_item);
        tv_temp = findViewById(R.id.tv_temperature_item);
        recyclerView = findViewById(R.id.rv_main);
        cities = new ArrayList<>();

        WeatherAdapter.OnItemClickListener onItemClickListener = new WeatherAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(City city) {
                Intent intent = new Intent(MainActivity.this, DetailsActivity.class);
                intent.putExtra("city", city);
                startActivity(intent);
            }
        };

        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new WeatherAdapter(new ArrayList<City>(0), onItemClickListener);
        recyclerView.setAdapter(adapter);

        NetworkService.getInstance()
                .getJSONApi()
                .getPostOfUser(55.8, 49.0, 20,"56fc6c6cb76c0864b4cd055080568268")
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
