package com.example.weatherapp.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.weatherapp.R;
import com.example.weatherapp.entities.City;

import java.util.List;


public class WeatherAdapter extends RecyclerView.Adapter<WeatherAdapter.CityViewHolder>{

    List<City> list;
    OnItemClickListener onItemClickListener;

    public WeatherAdapter(List<City> list, OnItemClickListener onItemClickListener) {
        this.list = list;
        this.onItemClickListener = onItemClickListener;
    }

    public List<City> getList() {
        return list;
    }

    @Override
    public CityViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_view_main, parent, false);
        return new CityViewHolder(view);
    }

    @Override
    public void onBindViewHolder(CityViewHolder holder, int position) {
        holder.bind(list.get(position));
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public void updateData(List<City> list) {
        this.list = list;
        notifyDataSetChanged();
    }

    class CityViewHolder extends RecyclerView.ViewHolder{
        TextView city;
        TextView country;
        TextView temp;
        public CityViewHolder(View view){
            super(view);
            city = view.findViewById(R.id.tv_city_item);
            country = view.findViewById(R.id.tv_country_item);
            temp = view.findViewById(R.id.tv_temperature_item);
            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    City city = list.get(getLayoutPosition());
                    onItemClickListener.onItemClick(city);
                }
            });
        }

        public void bind(City object ){
            city.setText(object.getName());
            if(object.getSys().getCountry().equals("")){
                country.setText("Russia");
                object.getSys().setCountry("Russia");
            } else {
                country.setText(object.getSys().getCountry());
            }
            temp.setText(""+Math.round(object.getMain().getTemp()-273));
        }
    }

    public interface OnItemClickListener{
        void onItemClick(City city);
    }
}

