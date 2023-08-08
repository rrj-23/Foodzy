package com.example.foodzy;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.FirebaseDatabase;

public class LodgingResetActivity extends AppCompatActivity {
    EditText id,pw;
    ImageButton b;
    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lodging_reset);
        id = findViewById(R.id.editTextTextPersonName2);
        pw = findViewById(R.id.editTextTextPassword);
        b = findViewById(R.id.imageButton);

        b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (id.getText().toString().equals("root") && pw.getText().toString().equals("root")) {
                    FirebaseDatabase.getInstance().getReference("LODGING CAPACITY").child("NORMAL (1 BEDDED)").setValue("0");
                    FirebaseDatabase.getInstance().getReference("LODGING CAPACITY").child("DELUXE (1 BEDDED)").setValue("0");
                    FirebaseDatabase.getInstance().getReference("LODGING CAPACITY").child("SUPER DELUXE (1 BEDDED)").setValue("0");
                    FirebaseDatabase.getInstance().getReference("LODGING CAPACITY").child("NORMAL (2 BEDDED)").setValue("0");
                    FirebaseDatabase.getInstance().getReference("LODGING CAPACITY").child("DELUXE (2 BEDDED)").setValue("0");
                    FirebaseDatabase.getInstance().getReference("LODGING CAPACITY").child("SUPER DELUXE (2 BEDDED)").setValue("0");
                    FirebaseDatabase.getInstance().getReference("LODGING CAPACITY").child("HALL (5 BEDDED)").setValue("0");
                    FirebaseDatabase.getInstance().getReference("LODGING CAPACITY").child("HALL (7 BEDDED)").setValue("0");
                    FirebaseDatabase.getInstance().getReference("LODGING CAPACITY").child("CHOOSE ROOM").setValue("FETCH AVAILABILITY");

                    Toast.makeText(LodgingResetActivity.this, "RESETED", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(LodgingResetActivity.this, "INVALID ID OR PASSWORD", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}