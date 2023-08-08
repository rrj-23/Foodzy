package com.example.foodzy;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class select_location extends AppCompatActivity {
    Button b1,b2;
    EditText t1,t2,t3,t4;
    TextView tv4;
    SharedPreferences sp;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_location);
        b1 = findViewById(R.id.mapMarkerBtn);
        b2 = findViewById(R.id.proceedBtn);
        t1 = findViewById(R.id.editText1);
        t2 = findViewById(R.id.editText2);
        t3 = findViewById(R.id.editText3);
        t4= findViewById(R.id.editText4);
        tv4 = findViewById(R.id.tv4);
        sp = getSharedPreferences("Delivery address", Context.MODE_PRIVATE);
        setTitle("Select delivery location");

        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(select_location.this,map_marker.class);
                startActivity(intent);
            }
        });
    }
    public void proceed(View view){
        if(t1.getText().toString().trim().length()==0) {
            t1.setError("Enter address field");
        }
        else if(t2.getText().toString().trim().length()==0){
            t2.setError("Enter address field");
        }
        else if(t4.getText().toString().trim().length()==0){
            t4.setError("Enter address field");
        }
        else if(t3.getText().toString().trim().length()==0){
            t3.setError("Enter address field");
        }
        else{
            String delivery_address = t1.getText().toString()+", "+t2.getText().toString()+", "+t4.getText().toString()+", "+t3.getText().toString();
            SharedPreferences.Editor editor = sp.edit();
            editor.putBoolean("delivery location taken",true);
            editor.putString("final delivery address",delivery_address);
            editor.commit();
            Intent intent = new Intent(select_location.this,Deliverycart_details.class);
            startActivity(intent);
        }
    }
}
