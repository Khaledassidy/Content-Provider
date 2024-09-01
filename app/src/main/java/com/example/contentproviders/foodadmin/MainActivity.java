package com.example.contentproviders.foodadmin;

import android.Manifest;
import android.content.ContentResolver;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.widget.ArrayAdapter;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.contentproviders.R;
import com.example.contentproviders.databinding.ActivityMain2Binding;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    ActivityMain2Binding binding;
    ArrayList<Food> arrayList;
    ArrayList<Food> arrayList1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding=ActivityMain2Binding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        arrayList=new ArrayList<>();
        arrayList1=new ArrayList<>();
        getcount();

        binding.getallfood.setOnClickListener(v->{
           setAllFood();
           populaterecyelview(arrayList);
        });

        binding.getfoodcategory.setOnClickListener(v->{
           getforcategoryid();
           populaterecyelview(arrayList1);

        });

        binding.next.setOnClickListener(v->{
            Intent intent=new Intent(getApplicationContext(), MainActivity2.class);
            startActivity(intent);
        });






    }

    public void getcount(){
        Cursor cursor = getContentResolver().query(FoodProviderConstarint.CONTENT_URI_3, null, null, null,null,null);
        if (cursor != null && cursor.moveToFirst()) {
            binding.countResult.setText("Total Food Count: " + cursor.getInt(0));
        }
        cursor.close();


    }

    public ArrayList<Food> setAllFood(){
        Cursor cursor = getContentResolver().query(FoodProviderConstarint.CONTENT_URI_1, null, null, null, null,null);
        if (cursor != null && cursor.moveToFirst()) {
            do {
                int category = cursor.getInt(cursor.getColumnIndexOrThrow(FoodProviderConstarint.FOODS_TB_CategoryId));
                String description = cursor.getString(cursor.getColumnIndexOrThrow(FoodProviderConstarint.FOODS_TB_Description));
                double price = cursor.getDouble(cursor.getColumnIndexOrThrow(FoodProviderConstarint.FOODS_TB_Price));
                double star = cursor.getDouble(cursor.getColumnIndexOrThrow(FoodProviderConstarint.FOODS_TB_Star));
                int timevalue = cursor.getInt(cursor.getColumnIndexOrThrow(FoodProviderConstarint.FOODS_TB_TimeValue));
                String title = cursor.getString(cursor.getColumnIndexOrThrow(FoodProviderConstarint.FOODS_TB_Title));
                boolean incart = cursor.getInt(cursor.getColumnIndexOrThrow(FoodProviderConstarint.FOODS_TB_incart)) == 1;
                int numb_cart = cursor.getInt(cursor.getColumnIndexOrThrow(FoodProviderConstarint.FOODS_TB_numberInCart));
                int id=cursor.getInt(cursor.getColumnIndexOrThrow(FoodProviderConstarint.FOODS_TB_id));
                Food food = new Food(category,id, description, price, star, timevalue, title, incart, numb_cart);
                arrayList.add(food);
            } while (cursor.moveToNext());
            cursor.close(); // Close cursor after use
        }
        return arrayList;
    }


    public void populaterecyelview(ArrayList<Food> arrayList){
        adpter adapter = new adpter(this, arrayList, new onClick2() {
            @Override
            public void click(int position) {
                Food food = arrayList.get(position);
                Intent intent = new Intent(getApplicationContext(), MainActivity2.class);
                intent.putExtra("food", food);
                intent.putExtra("flag", 1);
                startActivity(intent);
            }
        });
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        binding.foodAdminRecyele.setLayoutManager(layoutManager);
        binding.foodAdminRecyele.setAdapter(adapter);
    }


    public ArrayList<Food> getforcategoryid(){
        String x = binding.editTextCategory.getText().toString();
        Cursor cursor = getContentResolver().query(FoodProviderConstarint.CONTENT_URI_2, null, FoodProviderConstarint.FOODS_TB_CategoryId+"=?", new String[]{x}, null);
        if (cursor != null && cursor.moveToFirst()) {
            do {
                int category = cursor.getInt(cursor.getColumnIndexOrThrow(FoodProviderConstarint.FOODS_TB_CategoryId));
                String description = cursor.getString(cursor.getColumnIndexOrThrow(FoodProviderConstarint.FOODS_TB_Description));
                double price = cursor.getDouble(cursor.getColumnIndexOrThrow(FoodProviderConstarint.FOODS_TB_Price));
                double star = cursor.getDouble(cursor.getColumnIndexOrThrow(FoodProviderConstarint.FOODS_TB_Star));
                int timevalue = cursor.getInt(cursor.getColumnIndexOrThrow(FoodProviderConstarint.FOODS_TB_TimeValue));
                String title = cursor.getString(cursor.getColumnIndexOrThrow(FoodProviderConstarint.FOODS_TB_Title));
                boolean incart = cursor.getInt(cursor.getColumnIndexOrThrow(FoodProviderConstarint.FOODS_TB_incart)) == 1;
                int numb_cart = cursor.getInt(cursor.getColumnIndexOrThrow(FoodProviderConstarint.FOODS_TB_numberInCart));
                int id=cursor.getInt(cursor.getColumnIndexOrThrow(FoodProviderConstarint.FOODS_TB_id));
                Food food = new Food(category,id, description, price, star, timevalue, title, incart, numb_cart);
                arrayList1.add(food);
            } while (cursor.moveToNext());
            cursor.close(); // Close cursor after use
        }
        return arrayList1;
    }

}