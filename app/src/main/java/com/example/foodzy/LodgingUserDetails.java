package com.example.foodzy;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Locale;

public class LodgingUserDetails extends AppCompatActivity {
    TextView t7, t6, t10, t8, t12, t14;
    Button b;
    final int UPI_PAYMENT=0;
    String txid="";

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_details);
        Intent t = getIntent();

        t7 = findViewById(R.id.textView7);
        t6 = findViewById(R.id.textView6);
        t10 = findViewById(R.id.textView10);
        t8 = findViewById(R.id.textView8);
        t12 = findViewById(R.id.textView12);
        t14 = findViewById(R.id.textView14);
        b = findViewById(R.id.button);

        String s1 = t.getStringExtra("name").toUpperCase(Locale.ROOT);
        String s2 = t.getStringExtra("sqsize");
        String s3 = t.getStringExtra("phn");
        String s4 = t.getStringExtra("date");
        String s5 = t.getStringExtra("time");
        String s6 = t.getStringExtra("address").toUpperCase(Locale.ROOT);

        t7.setText(s1);
        t6.setText(s2);
        t10.setText(s3);
        t8.setText(s4);
        t12.setText(s5);

        if(s2.equals("NORMAL (1 BEDDED)")){
            t14.setText("RS. 500");
        }
        else if(s2.equals("DELUXE (1 BEDDED)")){
            t14.setText("RS. 700");
        }else if(s2.equals("SUPER DELUXE (1 BEDDED)")){
            t14.setText("RS. 1000");
        }else if(s2.equals("NORMAL (2 BEDDED)")){
            t14.setText("RS. 800");
        }else if(s2.equals("DELUXE (2 BEDDED)")){
            t14.setText("RS. 1000");
        }else if(s2.equals("SUPER DELUXE (2 BEDDED)")){
            t14.setText("RS. 1200");
        }else if(s2.equals("HALL (5 BEDDED)")){
            t14.setText("RS. 1500");
        }else if(s2.equals("HALL (7 BEDDED)")){
            t14.setText("RS. 2000");
        }

        b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                // PAYMENT PORTAL
//                Intent i = new Intent(LodgingUserDetails.this,LodgingUserFinalDetails.class);
//                i.putExtra("name",t7.getText().toString());
//                i.putExtra("room",t6.getText().toString());
//                i.putExtra("phn",t10.getText().toString());
//                i.putExtra("date",t8.getText().toString());
//                i.putExtra("amt",t14.getText().toString());
//                i.putExtra("txid",txid);
//                startActivity(i);
//                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
//                    if (checkSelfPermission(Manifest.permission.SEND_SMS) == PackageManager.PERMISSION_GRANTED)
//                        sendSMS();
//                    else {
//                        requestPermissions(new String[]{Manifest.permission.SEND_SMS}, 1);
//                        sendSMS();
//                    }
//                }
                payusingupi(t7.getText().toString(), "VRPFOODBOX@sc", "", t14.getText().toString());

                Toast.makeText(LodgingUserDetails.this, "PAYMENT IN PROCESS...", Toast.LENGTH_SHORT).show();

                // AFTER SUCCESSFUL PAYMENT




            }
        });
    }
    void payusingupi(String name, String upiId, String note, String amount) {
        Log.e("main","name: " + name + "--id--" + upiId + "--" + note + "--" + amount);
        Uri uri = Uri.parse("upi://pay").buildUpon()
                .appendQueryParameter("pa", upiId)
                .appendQueryParameter("pn", name)
                .appendQueryParameter("tr","25584584")
                .appendQueryParameter("tn", note)
                .appendQueryParameter("am", amount)
                .appendQueryParameter("cu","INR")
                .build();
        Intent upiPayIntent = new Intent(Intent.ACTION_VIEW);
        upiPayIntent.setData(uri);

        Intent chooser = Intent.createChooser(upiPayIntent, "Pay With");

        if (null != chooser.resolveActivity(getPackageManager())) {
            startActivityForResult(chooser, UPI_PAYMENT);
        } else {
            Toast.makeText(this, "No UPI App Installed", Toast.LENGTH_SHORT).show();
        }

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Log.e("main","response "+resultCode);  // -1 result code = tr happened but not sure successfully
        switch (requestCode) {
            case UPI_PAYMENT:
                if ((RESULT_OK == resultCode) || (resultCode == 11)) {
                    if (data != null) {
                        String text = data.getStringExtra("response");
                        Log.e("UPI", "onActivityResult: " + text);
                        ArrayList<String> dataList = new ArrayList<>();
                        dataList.add(text);
                        upiPaymentDataOperation(dataList);
                    } else {
                        Log.e("UPI", "onActivityResult: " + "Return Data Is null");
                        ArrayList<String> dataList = new ArrayList<>();
                        dataList.add("nothing");
                        upiPaymentDataOperation(dataList);
                    }
                } else {
                    Log.e("UPI", "onActivityResult: " + "Return Data is null");
                    ArrayList<String> dataList = new ArrayList<>();
                    dataList.add("nothing");
                    upiPaymentDataOperation(dataList);
                }
                break;
        }
    }
    private void upiPaymentDataOperation(ArrayList<String> data)
    {
        if (isConnectionAvailable(LodgingUserDetails.this))
        {
            String str = data.get(0);
            Log.e("UPIPAY","upiPaymentDataOperation: " + str);
            String paymentCancel = "";
            if (str==null)
            {
                str = "discard";
            }


            String status = "";
            String approvalRefNo = "";
            String response[] = str.split("&");
            //response = [txnid, responsecode, status,ref]
            for (int i=0;i<response.length;i++)
            {
                String equalStr[] = response[i].split("=");
                //equalstr = [responsecode,code]

                if (equalStr.length>=2)
                {
                    if (equalStr[0].toLowerCase().equals("Status".toLowerCase()))
                    {
                        status = equalStr[1].toLowerCase(); // Success or Failure
                    }
                    else if (equalStr[0].toLowerCase().equals("ApprovalRefNo".toLowerCase()) || equalStr[0].toLowerCase().equals("txnRef".toLowerCase()))
                    {
                        approvalRefNo = equalStr[1];
                    }
                    else if (equalStr[0].toLowerCase().equals("txnId".toLowerCase())){
                        txid = equalStr[1];
                    }
                }
                else
                {
                    paymentCancel = "Payment cancelled by user";
                }
            }
            if (status.equals("success"))
            {
                Toast.makeText(this, "Transaction Successful", Toast.LENGTH_SHORT).show();
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    if (checkSelfPermission(Manifest.permission.SEND_SMS) == PackageManager.PERMISSION_GRANTED)
                        sendSMS();
                    else {
                        requestPermissions(new String[]{Manifest.permission.SEND_SMS}, 1);
                        sendSMS();
                    }
                }

                Intent t = getIntent();
                String s1 = t.getStringExtra("name").toUpperCase(Locale.ROOT);
                String s2 = t.getStringExtra("sqsize");
                String s3 = t.getStringExtra("phn");
                String s4 = t.getStringExtra("date");
                String s5 = t.getStringExtra("time");
                String s6 = t.getStringExtra("address").toUpperCase(Locale.ROOT);
                final int[] x = {0};

                DatabaseReference ref6 = FirebaseDatabase.getInstance().getReference().child("LODGING CAPACITY").child(s2);
                ValueEventListener listen_2 = ref6.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        String s = snapshot.getValue(String.class);
                        x[0] = Integer.parseInt(s);
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {
                    }
                });


                FirebaseDatabase.getInstance().getReference(s1).child("ADDRESS").setValue(s6);
                FirebaseDatabase.getInstance().getReference(s1).child("PHONE NUMBER").setValue(s3);
                FirebaseDatabase.getInstance().getReference(s1).child("ROOM TYPE").setValue(s2);
                FirebaseDatabase.getInstance().getReference(s1).child("DATE AND TIME").setValue(s4 + "  " + s5);
                FirebaseDatabase.getInstance().getReference(s1).child("TXN ID").setValue(txid);
                FirebaseDatabase.getInstance().getReference("LODGING CAPACITY").child(s2).setValue(Integer.toString(x[0] + 1));



                Intent i = new Intent(LodgingUserDetails.this, LodgingUserFinalDetails.class);
                i.putExtra("name",s1);
                i.putExtra("room",s2);
                i.putExtra("phn",s3);
                i.putExtra("date",s4);
                i.putExtra("amt",t14.getText().toString());
                i.putExtra("txid",txid);
                startActivity(i);


                Log.e("UPI","responseStr: " + approvalRefNo);
            }
            else if ("Payment cancelled by user".equals(paymentCancel))
            {
                Toast.makeText(this, "Payment cancelled by user", Toast.LENGTH_SHORT).show();
            }
            else
            {
                Toast.makeText(this, "Transaction failed. Please try again", Toast.LENGTH_SHORT).show();
            }
        }
        else
        {
            Toast.makeText(this, "Internet connection is not available. Please check and try again", Toast.LENGTH_SHORT).show();
        }
    }

    public static boolean isConnectionAvailable(Context context)
    {
        ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        if (connectivityManager!=null)
        {
            NetworkInfo netInfo = connectivityManager.getActiveNetworkInfo();
            if (netInfo!=null && netInfo.isConnected() && netInfo.isConnectedOrConnecting() && netInfo.isAvailable())
            {
                return true;
            }
        }
        return false;
    }



    private void sendSMS(){
        Intent t = getIntent();
        String s1 = t.getStringExtra("name").toUpperCase(Locale.ROOT);
        String s2 = t.getStringExtra("sqsize");
        String s3 = t.getStringExtra("phn");
        String s4 = t.getStringExtra("date");
        String s5 = t.getStringExtra("time");
        String s6 = t.getStringExtra("address").toUpperCase(Locale.ROOT);

        String SMS = "YOUR ROOM IS BOOKED \n NAME: "+s1+"\n ADDRESS: "+s6+"\n SQUAD SIZE: "+s2+"\n DATE & TIME: "+s4+"  "+s5+"\n TXN NO. : " + txid;
        try {
            SmsManager smsManager = SmsManager.getDefault();
            smsManager.sendTextMessage(s3,null,SMS,null,null);
            Toast.makeText(this, "Message Sent", Toast.LENGTH_SHORT).show();
        }
        catch (Exception e){
            e.printStackTrace();
            Toast.makeText(this, "Message Not Sent", Toast.LENGTH_SHORT).show();
        }
    }
}