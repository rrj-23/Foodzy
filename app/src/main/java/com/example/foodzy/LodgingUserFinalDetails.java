package com.example.foodzy;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.Locale;

public class LodgingUserFinalDetails extends AppCompatActivity {
    TextView t1,t2,t3,t4,t5,t6;
    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_final_details);
        t1 = findViewById(R.id.textView19);
        t2 = findViewById(R.id.textView20);
        t3 = findViewById(R.id.textView21);
        t4 = findViewById(R.id.textView22);
        t5 = findViewById(R.id.textView23);
        t6 = findViewById(R.id.textView24);

        Intent t = getIntent();
        String s1 = t.getStringExtra("name").toUpperCase(Locale.ROOT);
        String s2 = t.getStringExtra("room");
        String s3 = t.getStringExtra("phn");
        String s4 = t.getStringExtra("date");
        String s5 = t.getStringExtra("amt");
        String s6 = t.getStringExtra("txid");

        t1.setText(s1);
        t2.setText(s2);
        t3.setText(s3);
        t4.setText(s4);
        t5.setText(s5);
        t6.setText(s6);
    }
}