package com.example.foodzy;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class StaticMenuOptionsAdaptor extends RecyclerView.Adapter<StaticMenuOptionsAdaptor.ViewHolder> {
    private List<StaticMenuOptionsModal> UserList;
    private Context context;
    private Class<StaticMenu> mainActivity = StaticMenu.class;

    private final List<String> fav = new ArrayList<>();
    DatabaseReference ref1;

    public StaticMenuOptionsAdaptor(List<StaticMenuOptionsModal> userList, Context context) {
        UserList = userList;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.static_food_menu_option, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        final StaticMenuOptionsModal temp2 = UserList.get(position);


        int resource = UserList.get(position).getImage();
        String text = UserList.get(position).getText();
        String price = String.valueOf(UserList.get(position).getPrice());
        ArrayList <String> a1 = new ArrayList<>();
        String desc = String.valueOf(UserList.get(position).getDesc().toString());
        holder.setData(resource, text, price, desc);
        holder.star.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                 ref1 = FirebaseDatabase.getInstance().getReference().child("FAVOURITES");
                ref1.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        if (snapshot.hasChild(text.toUpperCase())){
                            //remove that child
                            System.out.println("contains");
                            ref1.child(text.toUpperCase()).removeValue();
                        }
                        else{
                            System.out.println("not contained");
                            ref1.child(text.toUpperCase()).setValue(price);
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });


//                System.out.println(fav);

            }
        });
    }

    @Override
    public int getItemCount() {
        return UserList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private ImageView image;
        private TextView text, price;
        private ImageView star;
        private TextView desc;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            image = itemView.findViewById(R.id.favImage);
            text = itemView.findViewById(R.id.fav_name);
            price = itemView.findViewById(R.id.fav_price);
            star = itemView.findViewById(R.id.star);
            desc = itemView.findViewById(R.id.foodDescription);

        }

        public void setData(int resource, String text1, String price1, String desc1) {
            image.setImageResource(resource);
            text.setText(text1);
            price.setText(price1);
            desc.setText(desc1);
        }

    }
}
