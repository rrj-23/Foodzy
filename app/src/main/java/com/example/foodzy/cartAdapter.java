package com.example.foodzy;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.io.FileReader;
import java.util.List;
import java.util.Objects;

public class cartAdapter extends RecyclerView.Adapter<cartAdapter.ViewHolder> {

    private List<itemModel> userlist;

    DatabaseReference ref5,ref6;
    public TextView t100,t101;
    cartAdapter adapter;
    DatabaseReference ref8;
    public cartAdapter(List<itemModel>userlist){this.userlist = userlist;}

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_design,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        String p_name = userlist.get(position).getName();
        String p_price = userlist.get(position).getPrice();
//        String p_cross = userlist.get(position).getCross();
        String p_quantity = userlist.get(position).getQuantity();
        String p_fprice = userlist.get(position).getFprice();

        holder.setData(p_name,p_price,p_quantity,p_fprice);

        holder.b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String a = holder.t4.getText().toString();
                int b = Integer.parseInt(a) + 1;
                holder.t4.setText(Integer.toString(b));
                holder.t5.setText("Rs. " + Integer.toString(b*100) );
                ref6 = FirebaseDatabase.getInstance().getReference().child("DINEIN_CART");
                ref5 = FirebaseDatabase.getInstance().getReference().child("DINEIN_CART");
                ref5.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        for (DataSnapshot ds : snapshot.getChildren()) {
                            if (Objects.equals(ds.getKey(), holder.t1.getText().toString())){
                                ref5.child(holder.t1.getText().toString()).setValue(b);
                            }
                        }
//                        adapter.notifyDataSetChanged();
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
            }
        });


        holder.b2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String a = holder.t4.getText().toString();
                if (Integer.parseInt(a)>0){
                    int b = Integer.parseInt(a) - 1;
                    holder.t5.setText("Rs. " + Integer.toString(b*100) );
                    holder.t4.setText(Integer.toString(b));
                    ref5 = FirebaseDatabase.getInstance().getReference().child("DINEIN_CART");
                    ref5.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            for (DataSnapshot ds : snapshot.getChildren()) {
                                if (Objects.equals(ds.getKey(), holder.t1.getText().toString())){
                                    ref5.child(holder.t1.getText().toString()).setValue(b);
                                }
                            }
//                            adapter.notifyDataSetChanged();
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {

                        }
                    });
                }

                if (holder.t4.getText().toString().equals("0")){

                }

            }
        });



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

        private Button b1;

        private Button b2;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            t1 = itemView.findViewById(R.id.item_name);
            t2 = itemView.findViewById(R.id.item_price);
//            t3 = itemView.findViewById(R.id.cross);
            t4 = itemView.findViewById(R.id.item_quantity);
            t5 = itemView.findViewById(R.id.final_price);
            b1 = itemView.findViewById(R.id.idButtonIncrement);
            b2 = itemView.findViewById(R.id.idButtonDecrement);
        }
        public void setData(String p_name, String p_price, String p_quantity, String p_fprice) {
            t1.setText(p_name);
            t2.setText(p_price);
//            t3.setText(p_cross);
            t4.setText(p_quantity);
            t5.setText(p_fprice);
        }
    }
}
