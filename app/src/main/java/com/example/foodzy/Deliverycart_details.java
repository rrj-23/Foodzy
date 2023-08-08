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
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class Deliverycart_details extends AppCompatActivity {

    public TextView head,t2,t3,address_tv,changeAddress;
    String final_delivery_address,final_delivery_address_from_map;
    public Button b1,b2;
    public ArrayList<String> nameList,f_nameList,priceList,f_priceList,crossList,f_crossList,quantityList,f_quantityList,final_priceList;
    RecyclerView recyclerView;
    Double final_CARTprice = 0.0;
    LinearLayoutManager layoutManager;
    List<DeliveryitemModel> userlist;
    DeliverycartAdapter adapter;
    SharedPreferences.Editor editor ;
    boolean Delivery_location_taken_frm_map,Delivery_location_taken;
    LinearLayout layout;
    final int UPI_PAYMENT=0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_deliverycart_details);
        head = findViewById(R.id.header);
        t2 = findViewById(R.id.textView2);
        t3 = findViewById(R.id.textView3);
        address_tv = findViewById(R.id.textView1);
        changeAddress = findViewById(R.id.changeAddress);
        b1 = findViewById(R.id.payButton);
        b2 = findViewById(R.id.deliveryAddressBtn);
        f_nameList = loadList1();
        f_priceList = loadList2();
        f_crossList = loadList3();
        f_quantityList = loadList4();
        layout=(LinearLayout)this.findViewById(R.id.layout1);
        SharedPreferences sp = getApplicationContext().getSharedPreferences("Delivery address",Context.MODE_PRIVATE);
        editor = sp.edit();
        initData();
        initRecyclerView();

        final_delivery_address = sp.getString("final delivery address","");
        final_delivery_address_from_map = sp.getString("final delivery address from map","");
        Delivery_location_taken = sp.getBoolean("delivery location taken",false);
        Delivery_location_taken_frm_map = sp.getBoolean("delivery location taken from map",false);

        if (Delivery_location_taken || Delivery_location_taken_frm_map){
            b2.setVisibility(Button.GONE);
            layout.setVisibility(LinearLayout.VISIBLE);
        }
        else {
            layout.setVisibility(LinearLayout.GONE);
        }

        if(Delivery_location_taken){
            address_tv.setText(final_delivery_address);
        }
        else{
            address_tv.setText(final_delivery_address_from_map);
        }



        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (Delivery_location_taken || Delivery_location_taken_frm_map){
                    payusingupi("Name","VRPFOODBOX@sc","", final_CARTprice.toString());
                }
                else {
                    Toast.makeText(Deliverycart_details.this, "Select delivery location", Toast.LENGTH_SHORT).show();
                }
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
        }
        else
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
                }
                else
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
        if (isConnectionAvailable(Deliverycart_details.this))
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
                Intent intent = new Intent(Deliverycart_details.this,order_confirmed.class);
                startActivity(intent);
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
        for (int j=0;j<f_nameList.size();j++) {
            Double f1 = Double.parseDouble(f_priceList.get(j)) * Double.parseDouble(f_quantityList.get(j));
            String f2 = f1.toString();
            final_CARTprice += Double.parseDouble(f2);
            userlist.add(new DeliveryitemModel(f_nameList.get(j), f_priceList.get(j), "X", f_quantityList.get(j), f2));
        }
        t3.setText(final_CARTprice.toString());
    }

    private void initRecyclerView() {
        recyclerView =findViewById(R.id.recyclerview);
        layoutManager = new LinearLayoutManager(this);
        layoutManager.setOrientation(RecyclerView.VERTICAL);
        recyclerView.setLayoutManager(layoutManager);
        adapter = new DeliverycartAdapter(userlist);
        recyclerView.setAdapter(adapter);
        adapter.notifyDataSetChanged();
    }

    private ArrayList<String> loadList1() {
        SharedPreferences sharedPreferences = getApplicationContext().getSharedPreferences("name1",MODE_PRIVATE);
        Gson gson1 = new Gson();
        String json1 = sharedPreferences.getString("NAME","");
        Type type1 = new TypeToken<ArrayList<String>>(){

        }.getType();

        nameList = gson1.fromJson(json1,type1);
        return nameList;
    }
    private ArrayList<String> loadList2() {
        SharedPreferences sharedPreferences = getApplicationContext().getSharedPreferences("price1",MODE_PRIVATE);
        Gson gson2 = new Gson();
        String json2 = sharedPreferences.getString("PRICE","");
        Type type2 = new TypeToken<ArrayList<String>>(){

        }.getType();

        priceList = gson2.fromJson(json2,type2);
        return priceList;
    }
    private ArrayList<String> loadList3() {
        SharedPreferences sharedPreferences = getApplicationContext().getSharedPreferences("cross1",MODE_PRIVATE);
        Gson gson3 = new Gson();
        String json3 = sharedPreferences.getString("CROSS","");
        Type type3 = new TypeToken<ArrayList<String>>(){

        }.getType();

        crossList = gson3.fromJson(json3,type3);
        return crossList;
    }
    private ArrayList<String> loadList4() {
        SharedPreferences sharedPreferences = getApplicationContext().getSharedPreferences("quantity1",MODE_PRIVATE);
        Gson gson4 = new Gson();
        String json4 = sharedPreferences.getString("QUANTITY","");
        Type type4 = new TypeToken<ArrayList<String>>(){

        }.getType();

        quantityList = gson4.fromJson(json4,type4);
        return quantityList;
    }
    public void select_delivery_location(View view){
        Intent intent = new Intent(Deliverycart_details.this,select_location.class);
        startActivity(intent);
    }
    public void change_address(View view){
        editor.remove("delivery location taken").commit();
        editor.remove("delivery location taken from map").commit();
        Intent intent = new Intent(Deliverycart_details.this,select_location.class);
        startActivity(intent);
    }
}