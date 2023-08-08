package com.example.foodzy;
import android.app.AlertDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

public class DeliveryMenuOptionsAdaptor extends RecyclerView.Adapter<DeliveryMenuOptionsAdaptor.ViewHolder> {
    private List<DeliveryMenuOptionsModal> UserList;
    private Context context;
    private Class<DeliveryMenuPage> mainActivity = DeliveryMenuPage.class;
    public ArrayList<String> cart_ItemName = new ArrayList();
    public ArrayList<String> cart_ItemPrice = new ArrayList();
    public ArrayList<String> cart_cross = new ArrayList();
    public ArrayList<String> cart_ItemQuantity = new ArrayList();

    public DeliveryMenuOptionsAdaptor(List<DeliveryMenuOptionsModal> userList, Context context) {
        UserList = userList;
        this.context = context;
    }

    @NonNull
    @Override
    public DeliveryMenuOptionsAdaptor.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.delivery_food_menu_option, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull DeliveryMenuOptionsAdaptor.ViewHolder holder, int position) {
        final DeliveryMenuOptionsModal temp2 = UserList.get(position);

        int resource = UserList.get(position).getImage();
        String text = UserList.get(position).getText();

        holder.setData(resource, text);

        holder.image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                AlertDialog.Builder builder = new AlertDialog.Builder(v.getRootView().getContext());
                View dialogView = LayoutInflater.from(v.getRootView().getContext()).inflate(R.layout.delivery_quantity_select_dialogue_box,null);
                ImageView dialogboxImage = dialogView.findViewById(R.id.idIVDialogueBox);
                Button increment, decrement, addToOrder;
                TextView textViewPrice, textViewQuantity, textViewFoodName;
                increment = (Button) dialogView.findViewById(R.id.idButtonIncrement);
                decrement = (Button) dialogView.findViewById(R.id.idButtonDecrement);
                addToOrder = (Button) dialogView.findViewById(R.id.idButtonAddtoOrder);
                textViewPrice = dialogView.findViewById(R.id.idTVPrice);
                textViewQuantity = dialogView.findViewById(R.id.idTVQuantity);
                textViewFoodName = dialogView.findViewById(R.id.idTVFoodName);

                textViewFoodName.setText(temp2.getText().toString());

                increment.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        int a = temp2.getQuantity();
                        a++;
                        double price = temp2.getPrice();
                        temp2.setQuantity(a);
                        textViewQuantity.setText(String.valueOf(a));
                        textViewPrice.setText(String.valueOf(a*price));
                    }
                });
                decrement.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        int a = temp2.getQuantity();
                        if(a>0){
                            a--;
                            double price = temp2.getPrice();
                            temp2.setQuantity(a);
                            textViewQuantity.setText(String.valueOf(a));
                            textViewPrice.setText(String.valueOf(a*price));
                        }
                    }
                });
                addToOrder.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (cart_ItemName.contains(textViewFoodName.getText().toString())){
                            int index = cart_ItemName.indexOf(textViewFoodName.getText().toString());
                            cart_ItemQuantity.set(index, cart_ItemQuantity.get(index) + Integer.parseInt(textViewQuantity.getText().toString()));
                        }
                        else
                        {
                            cart_ItemName.add(textViewFoodName.getText().toString());
                            cart_ItemQuantity.add(textViewQuantity.getText().toString());
                            cart_ItemPrice.add(textViewPrice.getText().toString());
                        }

                        Toast.makeText(context, "Added to the order", Toast.LENGTH_SHORT).show();
                        saveList();
                        System.out.println(cart_ItemName.size());
                    }
                });
                dialogboxImage.setImageResource(resource);
                builder.setView(dialogView);
                builder.setCancelable(true);
                builder.show();
            }
        });
    }
    public void saveList(){

        SharedPreferences sharedPreferences1 = context.getApplicationContext().getSharedPreferences("name1",Context.MODE_PRIVATE);
        SharedPreferences sharedPreferences2 = context.getApplicationContext().getSharedPreferences("price1",Context.MODE_PRIVATE);
        SharedPreferences sharedPreferences3 = context.getApplicationContext().getSharedPreferences("cross1",Context.MODE_PRIVATE);
        SharedPreferences sharedPreferences4 = context.getApplicationContext().getSharedPreferences("quantity1",Context.MODE_PRIVATE);
        SharedPreferences.Editor editor1 = sharedPreferences1.edit();
        SharedPreferences.Editor editor2 = sharedPreferences2.edit();
        SharedPreferences.Editor editor3 = sharedPreferences3.edit();
        SharedPreferences.Editor editor4 = sharedPreferences4.edit();
        Gson gson1 = new Gson();
        String json1 = gson1.toJson(cart_ItemName);
        editor1.putString("NAME",json1);
        editor1.apply();
        Gson gson2 = new Gson();
        String json2 = gson2.toJson(cart_ItemPrice);
        editor2.putString("PRICE",json2);
        editor2.apply();
        Gson gson3 = new Gson();
        String json3 = gson3.toJson(cart_cross);
        editor3.putString("CROSS",json3);
        editor3.apply();
        Gson gson4 = new Gson();
        String json4 = gson4.toJson(cart_ItemQuantity);
        editor4.putString("QUANTITY",json4);
        editor4.apply();
    }

    @Override
    public int getItemCount() {
        return UserList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private ImageView image;
        private TextView text;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            image = itemView.findViewById(R.id.idIVFood);
            text = itemView.findViewById(R.id.idTVFood);
        }

        public void setData(int resource, String text1) {
            image.setImageResource(resource);
            text.setText(text1);
        }
    }
}
