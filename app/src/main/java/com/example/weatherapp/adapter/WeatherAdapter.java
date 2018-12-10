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

    private List<City> list;
    private OnItemClickListener onItemClickListener;

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

        public void bind(City city){
            this.city.setText(city.getName());
            if(city.getSys().getCountry().equals("")){
                country.setText("Russia");
                city.getSys().setCountry("Russia");
            } else {
                country.setText(city.getSys().getCountry());
            }
            temp.setText(""+city.getMain().getTemp());
        }
    }

    public interface OnItemClickListener{
        void onItemClick(City city);
    }
}

