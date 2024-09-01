package com.example.contentproviders.foodadmin;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.contentproviders.R;

import java.util.ArrayList;

public class adpter extends RecyclerView.Adapter<adpter.costumeviewholder> {

    private Context context;
    private ArrayList<Food> foodList;
    onClick2 onClick2;

    public adpter(Context context, ArrayList<Food> foodList,onClick2 onClick2) {
        this.context = context;
        this.foodList = foodList;
        this.onClick2=onClick2;
    }
    @NonNull
    @Override
    public costumeviewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(context).inflate(R.layout.costume_textview, parent, false);
        return new costumeviewholder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull costumeviewholder holder, int position) {
        Food food=foodList.get(position);
        holder.textView.setText("id:"+food.getId()+"title:"+food.getTitle()+"categoryid:"+food.getCategoryId()+"description:"+food.getDescription()+"bestfood:"+food.isBestFood()+"price"+food.getPrice()+"star:"+food.getStar()+"timevalue:"+food.getTimeValue());
    }

    @Override
    public int getItemCount() {
        return foodList.size();
    }

    class costumeviewholder extends RecyclerView.ViewHolder implements View.OnClickListener{
        TextView textView;
        public costumeviewholder(@NonNull View itemView) {
            super(itemView);
            textView=itemView.findViewById(R.id.costume_text);
            itemView.setOnClickListener(this);


        }

        @Override
        public void onClick(View v) {

            onClick2.click(getAdapterPosition());
        }
    }
}
