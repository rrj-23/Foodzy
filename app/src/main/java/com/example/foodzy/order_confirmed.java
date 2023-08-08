package com.example.foodzy;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.model.LatLng;

import java.io.IOException;
import java.util.List;

public class order_confirmed extends AppCompatActivity {
    private MapView mapView;
    private GoogleMap googleMap;
    Double latitude,longitude;
    Double distance;
    TextView tv;
    Double time;
    ProgressBar mProgressBar;
    CountDownTimer mCountDownTimer;
    int i=0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_confirmed);
        tv = findViewById(R.id.tvTimeDist);
        mProgressBar=(ProgressBar)findViewById(R.id.progressBar3);
        mProgressBar.setProgress(i);
        time = 1500000.0;
        mCountDownTimer=new CountDownTimer(150000,1000) {

            @Override
            public void onTick(long millisUntilFinished) {
                Log.v("Log_tag", "Tick of Progress"+ i+ millisUntilFinished);
                i++;
                mProgressBar.setProgress((int)i*100/(150000/1000));

            }

            @Override
            public void onFinish() {
                //Do what you want
                i++;
                mProgressBar.setProgress(100);
            }
        };
        mCountDownTimer.start();

        SharedPreferences sp = getApplicationContext().getSharedPreferences("Delivery address", Context.MODE_PRIVATE);
        Boolean Delivery_location_taken = sp.getBoolean("delivery location taken",false);
        Boolean Delivery_location_taken_frm_map = sp.getBoolean("delivery location taken from map",false);

        if(Delivery_location_taken){
            String final_delivery_address = sp.getString("final delivery address","");
            LatLng point = getLocationFromAddress(order_confirmed.this, final_delivery_address);
            latitude = point.latitude;
            longitude = point.longitude;
        }
        else{
            String[] locationArray = sp.getString("final delivery point", "").split(",");
            String Lat = locationArray[0];
            String Longi = locationArray[1];//you may need to use trim() method
            latitude = Double.parseDouble(Lat);
            longitude = Double.parseDouble(Longi);
        }
        LatLng delivery_point = new LatLng(latitude, longitude);
        LatLng restaurant_marker = new LatLng(latitude + 0.03, longitude + 0.03);
//        distance = SphericalUtil.computeDistanceBetween(restaurant_marker, delivery_point);
//        Double speed = 25.0;
//
//        time = distance/speed + 14.0;
        tv.setText("25 mins");

    }
    public LatLng getLocationFromAddress(Context context, String inputtedAddress) {

        Geocoder coder = new Geocoder(context);
        List<Address> address;
        LatLng resLatLng = null;

        try {
            // May throw an IOException
            address = coder.getFromLocationName(inputtedAddress, 5);
            if (address == null) {
                return null;
            }

            if (address.size() == 0) {
                return null;
            }

            Address location = address.get(0);
            location.getLatitude();
            location.getLongitude();

            resLatLng = new LatLng(location.getLatitude(), location.getLongitude());

        } catch (IOException ex) {

            ex.printStackTrace();
            Toast.makeText(context, ex.getMessage(), Toast.LENGTH_LONG).show();
        }

        return resLatLng;
    }

    public void track_order(View view){
        Intent intent = new Intent(order_confirmed.this,track_order.class);
        startActivity(intent);
    }
}
