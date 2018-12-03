package com.example.weatherapp.helper;

public class Helper {

    public static String simpleConvertDeg(Integer deg){

        if(deg <= 90){
            return "B";
        } else if(deg > 90 && deg <= 180){
            return "Ю";
        } else if (deg > 180 && deg <= 270){
            return "З";
        } else {
            return "С";
        }
    }

    public static String degToDirOfWind(Integer deg){
        if(deg > 315 ||  deg < 45){
            return "С";
        } else if(deg >=45 && deg < 90 ){
            return "C/B";
        } else if(deg >=90 && deg < 135){
            return "B";
        } else if(deg >=135 && deg < 180){
            return "Ю/B";
        } else if(deg >=180 && deg < 225){
            return "Ю/З";
        } else if(deg >= 225 && deg < 270){
            return "З";
        } else{
            return "C/З";
        }
    }

}
