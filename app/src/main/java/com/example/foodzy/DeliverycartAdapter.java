package com.example.foodzy;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class DeliverycartAdapter extends RecyclerView.Adapter<DeliverycartAdapter.ViewHolder> {

    private List<DeliveryitemModel> userlist;
    public DeliverycartAdapter(List<DeliveryitemModel>userlist){this.userlist = userlist;}

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.deliveryitem_design,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        String p_name = userlist.get(position).getName();
        String p_price = userlist.get(position).getPrice();
        String p_cross = userlist.get(position).getCross();
        String p_quantity = userlist.get(position).getQuantity();
        String p_fprice = userlist.get(position).getFprice();

        holder.setData(p_name,p_price,p_cross,p_quantity,p_fprice);
    }

    @Override
    public int getItemCount() {
        return userlist.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        private TextView t1;
        private TextView t2;
        private TextView t3;
        private TextView t4;
        private TextView t5;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            t1 = itemView.findViewById(R.id.item_name);
            t2 = itemView.findViewById(R.id.item_price);
            t3 = itemView.findViewById(R.id.cross);
            t4 = itemView.findViewById(R.id.item_quantity);
            t5 = itemView.findViewById(R.id.final_price);
        }
        public void setData(String p_name, String p_price, String p_cross, String p_quantity, String p_fprice) {
            t1.setText(p_name);
            t2.setText(p_price);
            t3.setText(p_cross);
            t4.setText(p_quantity);
            t5.setText(p_fprice);
        }
    }
}
