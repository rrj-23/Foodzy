package com.example.foodzy;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class DeliveryCategoryOptionAdaptor extends RecyclerView.Adapter<DeliveryCategoryOptionAdaptor.ViewHolder>{
    private List<DeliveryCategoryOptionModal> UserList2;
    private Context context;
    private CategoryClickInterface categoryClickInterface;

    public DeliveryCategoryOptionAdaptor(List<DeliveryCategoryOptionModal> userList2, Context context, CategoryClickInterface categoryClickInterface) {
        this.UserList2 = userList2;
        this.context = context;
        this.categoryClickInterface = categoryClickInterface;
    }

    @NonNull
    @Override
    public DeliveryCategoryOptionAdaptor.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.delivery_food_type_options, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull DeliveryCategoryOptionAdaptor.ViewHolder holder, int position) {
        final DeliveryCategoryOptionModal temp = UserList2.get(position);


        int resource2 = UserList2.get(position).getImage();
        String text2 = UserList2.get(position).getText();
        holder.setData(resource2, text2);

        holder.image10.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                categoryClickInterface.onCategoryClick(position);
            }
        });

    }

    @Override
    public int getItemCount() {
        return UserList2.size();
    }

    public interface CategoryClickInterface{
        void onCategoryClick(int position);
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private ImageView image10;
        private TextView text10;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            image10 = itemView.findViewById(R.id.idIVCategory);
            text10 = itemView.findViewById(R.id.idTVCategory);
        }
        public void setData(int resource100, String text100){
            image10.setImageResource(resource100);
            text10.setText(text100);
        }
    }
}
