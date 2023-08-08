package com.example.foodzy;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class cart_details extends AppCompatActivity {
    public TextView head,t2,t3,t35,t33;
    public Button b1;
    public ArrayList<String> nameList,f_nameList,priceList,f_priceList,crossList,f_crossList,quantityList,f_quantityList,final_priceList;
    RecyclerView recyclerView;
    Double final_CARTprice = 0.0;
    Double final_CARTprice_final = 0.0;
    LinearLayoutManager layoutManager;
    List<itemModel> userlist;
    cartAdapter adapter;

    public ImageView refresh;

    DatabaseReference ref4,ref8,ref123;
    final int UPI_PAYMENT=0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart_details);
        head = findViewById(R.id.header);
        t2 = findViewById(R.id.textView2);
        t3 = findViewById(R.id.textView3);
        b1 = findViewById(R.id.payButton);
        t35 = findViewById(R.id.textView35);
        t33 =  findViewById(R.id.textView33);
        refresh = findViewById(R.id.imageView8);
//        f_nameList = loadList1();
//        f_priceList = loadList2();
//        f_crossList = loadList3();
//        f_quantityList = loadList4();
        initData();
        initRecyclerView();

        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    ref123 = FirebaseDatabase.getInstance().getReference("DINEIN_CART");
                    ref123.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            for (DataSnapshot ds : snapshot.getChildren()) {
                                String n = ds.getValue().toString();
                                double a = 100.00 * Integer.parseInt(n);
                                final_CARTprice_final += a;
                                System.out.println(final_CARTprice_final);
                            }
                            t3.setText("Rs. " + final_CARTprice_final);
                            t33.setText("Rs. " + (0.18*final_CARTprice_final + 40));
                            t35.setText("Rs. " + (0.18*final_CARTprice_final + 40 + final_CARTprice_final));
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {
                        }
                    });
                }
                catch (Exception e){
                    Toast.makeText(cart_details.this, "chutiya", Toast.LENGTH_SHORT).show();
                }
                payusingupi("Name","VRPFOODBOX@sc","", final_CARTprice_final.toString());
            }
        });

        refresh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    ref8 = FirebaseDatabase.getInstance().getReference("DINEIN_CART");
                    ref8.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            double refreshed_price=0.0;
                            for (DataSnapshot ds : snapshot.getChildren()) {
                                String n = ds.getValue().toString();
                                double a = 100.00 * Integer.parseInt(n);
                                refreshed_price += a;
                            }
//                            adapter.notifyDataSetChanged();
                            t3.setText("Rs. " + refreshed_price);
                            t33.setText("Rs. " + (0.18*refreshed_price + 40));
                            t35.setText("Rs. " + (0.18*refreshed_price + 40 + refreshed_price));
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {
                        }
                    });
                }
                catch (Exception ignored){}
            }
        });

    }
    void payusingupi(String name, String upiId, String note, String amount)
    {
        Log.e("main","name" + name + "--up--" + upiId + "--" + note + "--" + amount);
        Uri uri = Uri.parse("upi://pay").buildUpon()
                .appendQueryParameter("pa",upiId)
                .appendQueryParameter("pn",name)
                .appendQueryParameter("tn", note)
                .appendQueryParameter("am", amount)
//                .appendEncodedPath("cu","INR")
                .build();
        Intent upiPayIntent = new Intent(Intent.ACTION_VIEW);
        upiPayIntent.setData(uri);

        Intent chooser = Intent.createChooser(upiPayIntent,"Pay With");

        if (null!= chooser.resolveActivity(getPackageManager()))
        {
            startActivityForResult(chooser,UPI_PAYMENT);
        }else
        {
            Toast.makeText(this, "No UPI App Installed", Toast.LENGTH_SHORT).show();
        }
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Log.e("main","response " + resultCode);

        switch (requestCode)
        {
            case UPI_PAYMENT:
                if ((RESULT_OK == resultCode) || (resultCode == 11))
                {
                    if (data!=null)
                    {
                        String text = data.getStringExtra("response");
                        Log.e("UPI","onActivityResult: " + text);
                        ArrayList<String> dataList = new ArrayList<>();
                        dataList.add(text);
                        upiPaymentDataOperation(dataList);
                    }
                    else
                    {
                        Log.e("UPI","onActivityResult: " + "Return Data Is null");
                        ArrayList<String> dataList= new ArrayList<>();
                        dataList.add("nothing");
                        upiPaymentDataOperation(dataList);
                    }
                }else
                {
                    Log.e("UPI","onActivityResult: " + "Return Data is null");
                    ArrayList<String> dataList = new ArrayList<>();
                    dataList.add("nothing");
                    upiPaymentDataOperation(dataList);
                }
                break;
        }
    }
    private void upiPaymentDataOperation(ArrayList<String> data)
    {
        if (isConnectionAvailable(cart_details.this))
        {
            String str = data.get(0);
            Log.d("UPIPAY","upiPaymentDataOperation: " + str);
            String paymentCancel = "";
            if (str==null)
            {
                str = "discard";
            }
            String status = "";
            String approvalRefNo = "";
            String response[] = str.split("&");
            for (int i=0;i<response.length;i++)
            {
                String equalStr[] = response[i].split("=");
                if (equalStr.length>=2)
                {
                    if (equalStr[0].toLowerCase().equals("Status".toLowerCase()))
                    {
                        status = equalStr[1].toLowerCase();
                    }
                    else if (equalStr[0].toLowerCase().equals("ApprovalRefNo".toLowerCase()) || equalStr[0].toLowerCase().equals("txnRef".toLowerCase()))
                    {
                        approvalRefNo = equalStr[1];
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
                Log.d("UPI","responseStr: " + approvalRefNo);
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

    private void initData() {
        userlist = new ArrayList<>();
        try {
            ref4 = FirebaseDatabase.getInstance().getReference("DINEIN_CART");
            ref4.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    for (DataSnapshot ds : snapshot.getChildren()) {
                        String m = ds.getKey();
                        String n = ds.getValue().toString();
                        double a = 100.00 * Integer.parseInt(n);
                        final_CARTprice += a;
                        System.out.println(m);
                        System.out.println(n);
                        itemModel x = new itemModel(m, "Rs. 100.0",  n,"Rs. " +  Double.toString(a));
                        userlist.add(x);
                    }
                    adapter.notifyDataSetChanged();
                    t3.setText("Rs. " + final_CARTprice);
                    t33.setText("Rs. " + (0.18*final_CARTprice + 40));
                    t35.setText("Rs. " + (0.18*final_CARTprice + 40 + final_CARTprice));
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {
                }
            });
        }
        catch (Exception ignored){}
    }

    private void initRecyclerView() {
        recyclerView =findViewById(R.id.recyclerview);
        layoutManager = new LinearLayoutManager(this);
        layoutManager.setOrientation(RecyclerView.VERTICAL);
        recyclerView.setLayoutManager(layoutManager);
        adapter = new cartAdapter(userlist);
        recyclerView.setAdapter(adapter);
        adapter.notifyDataSetChanged();
    }

//    private ArrayList<String> loadList1() {
//        SharedPreferences sharedPreferences = getApplicationContext().getSharedPreferences("name1",MODE_PRIVATE);
//        Gson gson1 = new Gson();
//        String json1 = sharedPreferences.getString("NAME","");
//        Type type1 = new TypeToken<ArrayList<String>>(){
//
//        }.getType();
//
//        nameList = gson1.fromJson(json1,type1);
//        return nameList;
//    }
//    private ArrayList<String> loadList2() {
//        SharedPreferences sharedPreferences = getApplicationContext().getSharedPreferences("price1",MODE_PRIVATE);
//        Gson gson2 = new Gson();
//        String json2 = sharedPreferences.getString("PRICE","");
//        Type type2 = new TypeToken<ArrayList<String>>(){
//
//        }.getType();
//
//        priceList = gson2.fromJson(json2,type2);
//        return priceList;
//    }
//    private ArrayList<String> loadList3() {
//        SharedPreferences sharedPreferences = getApplicationContext().getSharedPreferences("cross1",MODE_PRIVATE);
//        Gson gson3 = new Gson();
//        String json3 = sharedPreferences.getString("CROSS","");
//        Type type3 = new TypeToken<ArrayList<String>>(){
//
//        }.getType();
//
//        crossList = gson3.fromJson(json3,type3);
//        return crossList;
//    }
//    private ArrayList<String> loadList4() {
//        SharedPreferences sharedPreferences = getApplicationContext().getSharedPreferences("quantity1",MODE_PRIVATE);
//        Gson gson4 = new Gson();
//        String json4 = sharedPreferences.getString("QUANTITY","");
//        Type type4 = new TypeToken<ArrayList<String>>(){
//
//        }.getType();
//
//        quantityList = gson4.fromJson(json4,type4);
//        return quantityList;
//    }


}