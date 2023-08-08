package com.example.foodzy;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import com.google.firebase.database.FirebaseDatabase;

public class ResetActivity extends AppCompatActivity {
    EditText id,pw;
    ImageButton b;
    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reset);
        id = findViewById(R.id.editTextTextPersonName2);
        pw = findViewById(R.id.editTextTextPassword);
        b = findViewById(R.id.imageButton);

        b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(id.getText().toString().equals("root") && pw.getText().toString().equals("root")){
                    FirebaseDatabase.getInstance().getReference("CAPACITY").child("1").setValue("0");
                    FirebaseDatabase.getInstance().getReference("CAPACITY").child("2").setValue("0");
                    FirebaseDatabase.getInstance().getReference("CAPACITY").child("3").setValue("0");
                    FirebaseDatabase.getInstance().getReference("CAPACITY").child("4").setValue("0");
                    FirebaseDatabase.getInstance().getReference("CAPACITY").child("5").setValue("0");
                    FirebaseDatabase.getInstance().getReference("CAPACITY").child("8").setValue("0");
                    FirebaseDatabase.getInstance().getReference("CAPACITY").child("CHOOSE SQUAD SIZE").setValue("FETCH AVAILABILITY");

                    Toast.makeText(ResetActivity.this, "RESETED", Toast.LENGTH_SHORT).show();
                }
                else{
                    Toast.makeText(ResetActivity.this, "INVALID ID OR PASSWORD", Toast.LENGTH_SHORT).show();
                }
            }
        });


    }
}