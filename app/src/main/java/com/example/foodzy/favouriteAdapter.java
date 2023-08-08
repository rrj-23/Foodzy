package com.example.foodzy;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class favouriteAdapter extends RecyclerView.Adapter<favouriteAdapter.ViewHolder> {
    private Context context;
    private ArrayList<favouriteModelClass> userList;

    public favouriteAdapter(Context context, ArrayList<favouriteModelClass> userList){
        this.context = context;
        this.userList=userList;}



    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.fav_itemdesign,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        int resource = userList.get(position).getFoodimage();
        String name = userList.get(position).getName();
        String price = userList.get(position).getPrice();
        holder.setData(resource,name,price);
    }

    @Override
    public int getItemCount() {
        return userList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private ImageView imageView;
        private TextView textView;
        private TextView textView2;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            imageView = itemView.findViewById(R.id.favImage);
            textView = itemView.findViewById(R.id.fav_name);
            textView2 = itemView.findViewById(R.id.fav_price);
        }

        public void setData(int resource,String name,String price){
            imageView.setImageResource(resource);
            textView.setText(name);
            textView2.setText(price);
        }
    }
}
