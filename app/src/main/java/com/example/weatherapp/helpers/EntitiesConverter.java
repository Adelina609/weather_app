package com.example.weatherapp.helpers;

import com.example.weatherapp.entities.City;
import com.example.weatherapp.entities.Main;
import com.example.weatherapp.entities.Sys;
import com.example.weatherapp.entities.Wind;

import androidx.room.TypeConverter;

public class EntitiesConverter {

    public static String REGEX = ",";
    @TypeConverter
    public String fromMain(Main main){
        return ""+main.getTemp()+REGEX+main.getPressure()+REGEX+main.getHumidity();
    }
    @TypeConverter
    public Main toMain(String main){
        String[] data = main.split(REGEX);
        Main thisMain = new Main();
        thisMain.setTemp(Double.parseDouble(data[0]));
        thisMain.setPressure(Integer.parseInt(data[1]));
        thisMain.setHumidity(Integer.parseInt(data[2]));
        return thisMain;
    }
    @TypeConverter
    public String fromSys(Sys sys){
        return sys.getCountry();
    }

    @TypeConverter
    public Sys toSys(String sys){
        Sys systemData = new Sys();
        systemData.setCountry(sys);
        return systemData;
    }

    @TypeConverter
    public Integer fromWind(Wind wind){
        return wind.getDeg();
    }

    @TypeConverter
    public Wind toWind(Integer wind){
        Wind thisWind = new Wind();
        thisWind.setDeg(wind);
        return thisWind;
    }


}
