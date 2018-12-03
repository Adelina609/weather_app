package com.example.weatherapp.activities;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.weatherapp.R;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;

public class MainActivityThis extends AppCompatActivity{

    TextView tv;
    FusedLocationProviderClient fusedLocationProviderClient;
    Button button;
    //handles ask permissions/continue
    boolean completed = false;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.mainx);
        tv = findViewById(R.id.myyy);
        Toast.makeText(this, "UUU", Toast.LENGTH_SHORT).show();

    }

    @SuppressLint("MissingPermission")
    @Override
    protected void onResume() {
        super.onResume();
        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this);
        Toast.makeText(this, "after", Toast.LENGTH_LONG).show();

//        @SuppressLint("MissingPermission") Task<Location> task =
        fusedLocationProviderClient.getLastLocation()
                .addOnSuccessListener(this, new OnSuccessListener<Location>() {
                    @Override
                    public void onSuccess(Location location) {
                        Toast.makeText(MainActivityThis.this, "Loc null", Toast.LENGTH_SHORT).show();
                        if (location != null) {
                            Toast.makeText(MainActivityThis.this, "\"\"+location.getLatitude()", Toast.LENGTH_SHORT).show();
                            tv.setText(""+location.getLatitude());
                        } else {

                            tv.append("Else");
                        }
                    }
                });

        Toast.makeText(MainActivityThis.this, "Loc is yeeeeeeee", Toast.LENGTH_LONG).show();
//        if(task.isComplete()){
//            task.getResult().getAltitude();
//        }
//        Toast.makeText(MainActivityThis.this, "Loc is yeeeeeeee", Toast.LENGTH_LONG).show();
    }


//    @Override
//    public void onClick(View v) {
//        switch (v.getId()) {
//            case R.id.button:
//                if(!completed)
//                    askPermissions();
//                else{
//                    //start the main activity
//                }
//                break;
//        }
//    }


    public void askPermissions(){
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED ||
                ContextCompat.checkSelfPermission(this,Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED
                ){//Can add more as per requirement

            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.ACCESS_FINE_LOCATION,Manifest.permission.ACCESS_COARSE_LOCATION},
                    123);
        }else if(ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED &&
                ContextCompat.checkSelfPermission(this,Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED
                ){
            completed = true;
            button.setText("Permissions supplied. Press to continue");
        }

    }
}
