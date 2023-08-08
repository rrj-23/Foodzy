package com.example.foodzy;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Calendar;

public class TableBooking extends AppCompatActivity {
    EditText date,time,name,address,phn;
    Spinner sqsize;
    AppCompatButton b;
    Button sb;
    TextView t,t2,t3,t17;
    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_table_booking);
        name = findViewById(R.id.editTextTextPersonName);
        address = findViewById(R.id.editTextTextPostalAddress);
        sqsize = findViewById(R.id.spinner2);
        phn = findViewById(R.id.editTextPhone);
        date = findViewById(R.id.editTextDate);
        time = findViewById(R.id.editTextTime);
        b = findViewById(R.id.button2);
        t = findViewById(R.id.textView);
        t2 = findViewById(R.id.textView2);
        t3 = findViewById(R.id.textView3);
        t17 = findViewById(R.id.textView17);
        sb = findViewById(R.id.switch1);

        t.setText("* NOTE:  DOUBLE TAP ON DATE AND TIME.");
        t17.setText("INCASE OF SQUAD SIZE CHANGE , RECHECK THE AVAILABILITY");
        t3.setText("FETCH AVAILABILITY");

        sb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DatabaseReference ref6 = FirebaseDatabase.getInstance().getReference().child("CAPACITY").child(sqsize.getSelectedItem().toString());
                ValueEventListener listen_2 = ref6.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        String s = snapshot.getValue(String.class);
//                        t3.setText(Integer.toString(5-Integer.parseInt(s)));
                        if(s.equals("FETCH AVAILABILITY")){
                            t3.setText(s);
                        }
                        else{
                            int x = 5-Integer.parseInt(s);
                            if(x>0){
                                t3.setText(Integer.toString(x));
                            }
                            else{
                                t3.setText("NO SEAT");
                            }
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {
                    }
                });
            }
        });

        date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DATE();
            }
        });

        time.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                TIME();
            }
        });

        t2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(TableBooking.this,ResetActivity.class);
                startActivity(i);
            }
        });

        b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(name.getText().toString().equals("") || address.getText().toString().equals("") || sqsize.getSelectedItem().toString().equals("") || phn.getText().toString().equals("") || date.getText().toString().equals("") || time.getText().toString().equals("")){
                    Toast.makeText(TableBooking.this, "FILL ALL THE FIELDS", Toast.LENGTH_SHORT).show();
                }
                else {
                    if (t3.getText().toString() == "FETCH AVAILABILITY") {
                        Toast.makeText(TableBooking.this, "MAKE CHECKBOX TRUE", Toast.LENGTH_SHORT).show();
                    } else {
                        int z = Integer.parseInt(t3.getText().toString());
                        if (z == 0 || z < 0) {
                            Toast.makeText(TableBooking.this, "MENTION SQUAD SIZE TABLE IS NOT AVAILABLE", Toast.LENGTH_SHORT).show();
                        } else {
                            Intent t = new Intent(TableBooking.this, TableBookingDetails.class);
                            t.putExtra("name", name.getText().toString());
                            t.putExtra("sqsize", sqsize.getSelectedItem().toString());
                            t.putExtra("phn", phn.getText().toString());
                            t.putExtra("date", date.getText().toString());
                            t.putExtra("time", time.getText().toString());
                            t.putExtra("address", address.getText().toString());
                            startActivity(t);
                            Toast.makeText(TableBooking.this, "DONE!", Toast.LENGTH_SHORT).show();
                        }
                    }
                }
            }
        });
    }
    public void DATE(){
        Calendar c = Calendar.getInstance();
        int year = c.get(Calendar.YEAR);
        int month = c.get(Calendar.MONTH);
        int day = c.get(Calendar.DAY_OF_MONTH);
        DatePickerDialog dp = new DatePickerDialog(TableBooking.this, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int i, int i1, int i2) {
                date.setText(i2+"/"+(i1+1)+"/"+i);
            }
        },year,month,day);
        dp.show();
    }
    public void TIME() {
        Calendar c = Calendar.getInstance();
        int hour = c.get(Calendar.HOUR_OF_DAY);
        int min = c.get(Calendar.MINUTE);
        TimePickerDialog timePickerDialog = new TimePickerDialog(TableBooking.this, new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker timePicker, int fethour, int fetminute) {
                time.setText(fethour+":"+fetminute);
            }
        }, hour, min, true);
        timePickerDialog.show();
    }
}
