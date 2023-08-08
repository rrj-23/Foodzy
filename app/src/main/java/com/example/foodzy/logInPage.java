package com.example.foodzy;

import androidx.appcompat.widget.AppCompatButton;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class logInPage extends AppCompatActivity {
    private FirebaseAuth auth;
    private EditText mail10, pass10;
    private AppCompatButton signIn, forgetPass, signUp;
    public static String PREFS_NAME = "MyPrefsFile";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_log_in_page);

        mail10 = findViewById(R.id.username);
        pass10 = findViewById(R.id.loginPass);

        auth = FirebaseAuth.getInstance();
        signIn = findViewById(R.id.signInButton);
        signUp = findViewById(R.id.signUpButton);
        forgetPass = findViewById(R.id.forgotPass);
        signIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String mail = mail10.getText().toString();
                String pass = pass10.getText().toString();

                if(mail.isEmpty() || pass.isEmpty()){
                    Toast.makeText(logInPage.this, "Enter Credentials", Toast.LENGTH_SHORT).show();
                }else{
                    login(mail, pass);
                }
            }
        });

        signUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(logInPage.this, uploadDetails.class);
                startActivity(intent);
            }
        });
    }

    private void login(String mail100, String pass100){
        auth.signInWithEmailAndPassword(mail100, pass100).addOnCompleteListener(logInPage.this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()){

                    SharedPreferences sharedPreferences = getSharedPreferences(logInPage.PREFS_NAME, 0);
                    SharedPreferences.Editor editor = sharedPreferences.edit();

                    editor.putBoolean("hasLoggedIn", true);
                    editor.commit();

                    Intent intent = new Intent(logInPage.this, homePage.class);
                    startActivity(intent);
                    Toast.makeText(logInPage.this, "Login Successful", Toast.LENGTH_SHORT).show();
                    finish();
                }else{
                    Toast.makeText(logInPage.this, "Failed", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(this, logInPage.class);
        startActivity(intent);
    }
}