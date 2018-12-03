package com.example.weatherapp.entities;

import com.example.weatherapp.entities.City;

import java.util.List;

public class WeatherResponse {

    String message;
    int cod;
    int count;
    List<City> list;


    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public int getCod() {
        return cod;
    }

    public void setCod(int cod) {
        this.cod = cod;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public List<City> getList() {
        return list;
    }

    public void setList(List<City> list) {
        this.list = list;
    }

    @Override
    public String toString() {
        return "WeatherResponse{" +
                "message='" + message + '\'' +
                ", cod=" + cod +
                ", count=" + count +
                ", list=" + list +
                '}';
    }
}
