package com.example.contentproviders.foodadmin;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.contentproviders.ContactItem;
import com.example.contentproviders.R;
import com.example.contentproviders.databinding.CostumeContactItemBinding;
import com.example.contentproviders.onClick;

import java.util.ArrayList;

public class RecycleAddapter extends RecyclerView.Adapter<RecycleAddapter.ContactViewHolder> {
    ArrayList<ContactItem> arrayList;
    Context context;
    onClick click;

    public RecycleAddapter(Context context,ArrayList<ContactItem> arrayList,onClick onClick){
        this.context=context;
        this.arrayList=arrayList;
        this.click=onClick;
    }

    @NonNull
    @Override
    public ContactViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        CostumeContactItemBinding binding=CostumeContactItemBinding.inflate(LayoutInflater.from(parent.getContext()),parent,false);
        return new ContactViewHolder(binding);


    }

    @Override
    public void onBindViewHolder(@NonNull ContactViewHolder holder, int position) {
        holder.binding.name.setText(arrayList.get(position).getName());
        holder.binding.phoneNumber.setText(arrayList.get(position).getNumber());
        holder.binding.otherDetails.setText(arrayList.get(position).getOtherdetails());

        // image 7a toslne string ana 7awlta la bit map w 7atyta bel image
        Bitmap image=null;
        if(!arrayList.get(position).getProfilephoto().equals("") && arrayList.get(position).getProfilephoto()!=null){
            image= BitmapFactory.decodeFile(arrayList.get(position).getProfilephoto());
            if(image!=null){
                holder.binding.profile.setImageBitmap(image);
            }else{
                image=BitmapFactory.decodeResource(holder.binding.getRoot().getContext().getResources(), R.drawable.ic_launcher_background);
                holder.binding.profile.setImageBitmap(image);
            }
        }else{
            image=BitmapFactory.decodeResource(holder.binding.getRoot().getContext().getResources(),R.drawable.ic_launcher_background);
            holder.binding.profile.setImageBitmap(image);
        }
    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }

    class ContactViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        CostumeContactItemBinding binding;
        public ContactViewHolder(@NonNull CostumeContactItemBinding itemView) {
            super(itemView.getRoot());
            binding=itemView;
            itemView.getRoot().setOnClickListener(this);

        }

        @Override
        public void onClick(View v) {
            click.click(arrayList.get(getAdapterPosition()));
        }
    }
}
