package com.example.weatherapp.activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.weatherapp.R;
import com.example.weatherapp.entities.City;
import com.example.weatherapp.entities.WeatherResponse;
import com.example.weatherapp.network.NetworkService;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class MainActivityMy extends AppCompatActivity {

    TextView textView;
    TextView tv_country;
    TextView tv_temperature;
    RecyclerView recyclerView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.mainx);
        textView = findViewById(R.id.myyy);
        Toast.makeText(MainActivityMy.this, "mmmmmmmmmmm", Toast.LENGTH_LONG).show();

        NetworkService.getInstance()
                .getJSONApi()
                .getPostOfUser(55.8, 49.0, 20,"56fc6c6cb76c0864b4cd055080568268")
                .enqueue(new Callback<WeatherResponse>() {
                    @Override
                    public void onResponse(Call<WeatherResponse> call, Response<WeatherResponse> response) {

                        WeatherResponse weatherResponse = response.body();
                        if(weatherResponse == null){
                            Toast.makeText(MainActivityMy.this, "NULL LIST", Toast.LENGTH_SHORT).show();
                        } else {
                            List<City> list = weatherResponse.getList();
                            for(City city : list){
                                textView.append(city.getName() + " CCCCCCCCC \n");
                                textView.append(city.getSys().getCountry() + " CCCCCCCCC \n");
                                textView.append(city.getMain().getTemp()+"\n");
                            }
                        Toast.makeText(MainActivityMy.this, "yyyyyyyy", Toast.LENGTH_LONG).show();
                        }
                    }


                    @Override
                    public void onFailure(Call<WeatherResponse> call, Throwable t) {
                        Toast.makeText(MainActivityMy.this, "faaaaaail", Toast.LENGTH_LONG).show();
                        System.out.println("AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA"+t.getMessage());
                    }
    });
    }
}

