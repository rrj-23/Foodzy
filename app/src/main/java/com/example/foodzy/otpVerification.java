package com.example.foodzy;
//import android.support.v7.app.AppCompatActivity;
import static com.google.android.gms.common.internal.safeparcel.SafeParcelable.NULL;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;

import java.util.concurrent.TimeUnit;

public class otpVerification extends AppCompatActivity {
    private TextView textView;
    private EditText one,two, three, four, five, six;
    private Button verify;
    private AppCompatButton resendOtp1;
    private String backendotp;FirebaseAuth auth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_otp_verification);

        auth = FirebaseAuth.getInstance();
        verify = findViewById(R.id.verifyOtp);textView = findViewById(R.id.subtextPhone);
        one = findViewById(R.id.numberOne);two = findViewById(R.id.numberTwo);three = findViewById(R.id.numberThree);
        four = findViewById(R.id.numberFour);five = findViewById(R.id.numberFive);six = findViewById(R.id.numberSix);
        textView.setText(String.format("+91-%s",
                getIntent().getStringExtra("mobile")));
        resendOtp1 = findViewById(R.id.resendOtp);
        backendotp = getIntent().getStringExtra("backendotp");
        ProgressBar progressbar2 = findViewById(R.id.progressBarverifyOtp);
        verify.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(one.getText().toString().isEmpty() ||two.getText().toString().isEmpty() ||three.getText().toString().isEmpty() ||four.getText().toString().isEmpty() ||five.getText().toString().isEmpty() ||six.getText().toString().isEmpty()){
                    Toast.makeText(otpVerification.this, "please Enter the otp", Toast.LENGTH_SHORT).show();
                }else{
                    String enterCodeOtp = one.getText().toString() + two.getText().toString() + three.getText().toString() +
                            four.getText().toString() + five.getText().toString() + six.getText().toString();
                    if(backendotp != null){
                        progressbar2.setVisibility(View.VISIBLE);
                        verify.setVisibility(View.INVISIBLE);
                        PhoneAuthCredential phoneAuthCredential = PhoneAuthProvider.getCredential(
                                backendotp, enterCodeOtp
                        );
                        FirebaseAuth.getInstance().signInWithCredential(phoneAuthCredential)
                                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                                    @Override
                                    public void onComplete(@NonNull Task<AuthResult> task) {
                                        progressbar2.setVisibility(View.GONE);
                                        verify.setVisibility(View.VISIBLE);
                                        if(task.isSuccessful()){
                                            String mail100 = getIntent().getStringExtra("mail");
                                            String pass100 = getIntent().getStringExtra("password");
                                            regis(mail100, pass100);
                                            Toast.makeText(otpVerification.this, "Successfully Registered", Toast.LENGTH_SHORT).show();
                                            Intent intent = new Intent(otpVerification.this, logInPage.class);
                                            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK|Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                            startActivity(intent);
                                        }else{
                                            Toast.makeText(otpVerification.this, "Enter Correct Otp", Toast.LENGTH_SHORT).show();
                                        }
                                    }
                                });
                    }
                }
            }
        });

        numberotpmove();

        resendOtp1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PhoneAuthProvider.getInstance().verifyPhoneNumber(
                        "+91" + textView.getText().toString(),
                        60,
                        TimeUnit.SECONDS,
                        otpVerification.this,
                        new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
                            @Override
                            public void onVerificationCompleted(@NonNull PhoneAuthCredential phoneAuthCredential) {

                            }

                            @Override
                            public void onVerificationFailed(@NonNull FirebaseException e) {

                                Toast.makeText(otpVerification.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                            }

                            @Override
                            public void onCodeSent(@NonNull String newbackendotp, @NonNull PhoneAuthProvider.ForceResendingToken forceResendingToken) {
                                backendotp = newbackendotp;
                                Toast.makeText(otpVerification.this, "Otp sent Successfully", Toast.LENGTH_SHORT).show();
                            }
                        }
                );
            }
        });
    }

    private void numberotpmove() {
        one.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if(!s.toString().trim().isEmpty()){
                    two.requestFocus();
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        two.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if(!s.toString().trim().isEmpty()){
                    three.requestFocus();
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        three.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if(!s.toString().trim().isEmpty()){
                    four.requestFocus();
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        four.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if(!s.toString().trim().isEmpty()){
                    five.requestFocus();
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        five.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if(!s.toString().trim().isEmpty()){
                    six.requestFocus();
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }
    private void regis(String mail2, String pass2){
        auth.createUserWithEmailAndPassword(mail2, pass2).addOnCompleteListener(otpVerification.this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()){
                    Toast.makeText(otpVerification.this, "Successfully Registered", Toast.LENGTH_SHORT).show();
                }else{
                    Toast.makeText(otpVerification.this, "failed", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}