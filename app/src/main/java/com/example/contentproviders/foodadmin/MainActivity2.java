package com.example.contentproviders.foodadmin;

import android.content.ContentValues;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.contentproviders.R;
import com.example.contentproviders.databinding.ActivityMain3Binding;

public class MainActivity2 extends AppCompatActivity {
    ActivityMain3Binding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding=ActivityMain3Binding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        getfood();

        binding.add.setOnClickListener(v->{
            addfood();
        });

        binding.delete.setOnClickListener(v->{
            deletefood();
        });

        binding.update.setOnClickListener(v->{
            updatefood();
        });





    }

    public void addfood(){
        String categoryid=binding.category.getText().toString();
        String description=binding.description.getText().toString();
        String price=binding.price.getText().toString();
        String star=binding.star.getText().toString();
        String time=binding.timevalue.getText().toString();
        String title=binding.title.getText().toString();
        ContentValues values=new ContentValues();
        values.put(FoodProviderConstarint.FOODS_TB_CategoryId,Integer.valueOf(categoryid));
        values.put(FoodProviderConstarint.FOODS_TB_Description,description);
        values.put(FoodProviderConstarint.FOODS_TB_Price,Double.valueOf(price));
        values.put(FoodProviderConstarint.FOODS_TB_Star,Double.valueOf(star));
        values.put(FoodProviderConstarint.FOODS_TB_TimeValue,Integer.valueOf(time));
        values.put(FoodProviderConstarint.FOODS_TB_Title,title);
        values.put(FoodProviderConstarint.FOODS_TB_incart,false);
        values.put(FoodProviderConstarint.FOODS_TB_numberInCart,0);
        values.put(FoodProviderConstarint.FOODS_TB_LocationId,0);
        values.put(FoodProviderConstarint.FOODS_TB_PriceId,0);
        values.put(FoodProviderConstarint.FOODS_TB_TimeId,0);
        values.put(FoodProviderConstarint.FOODS_TB_BESTFOOD,0);
        Uri uri=getContentResolver().insert(FoodProviderConstarint.CONTENT_URI_1,values);
        Log.d("jacson",uri.getPath()+"");
    }

    public void getfood(){
        Food food=(Food)getIntent().getSerializableExtra("food");
        int x=(Integer)getIntent().getIntExtra("flag",0);
        if(x==1){
            binding.id.setText(String.valueOf(food.getId()));
            binding.category.setText(String.valueOf(food.getCategoryId()));
            binding.price.setText(String.valueOf(food.getPrice()));
            binding.star.setText(String.valueOf(food.getStar()));
            binding.timevalue.setText(String.valueOf(food.getTimeValue()));
            binding.title.setText(food.getTitle());
            binding.update.setVisibility(View.VISIBLE);
            binding.add.setVisibility(View.GONE);
            binding.delete.setVisibility(View.VISIBLE);

        }else{
            binding.category.setEnabled(true);
            binding.price.setEnabled(true);
            binding.star.setEnabled(true);
            binding.timevalue.setEnabled(true);
            binding.title.setEnabled(true);
            binding.category.setText("");
            binding.price.setText("");
            binding.star.setText("");
            binding.timevalue.setText("");
            binding.title.setText("");
            binding.update.setVisibility(View.GONE);
            binding.add.setVisibility(View.VISIBLE);
            binding.delete.setVisibility(View.GONE);
        }
    }

    public void deletefood(){
        String x=binding.id.getText().toString();
        getContentResolver().delete(FoodProviderConstarint.CONTENT_URI_1,FoodProviderConstarint.FOODS_TB_id+"=?",new String[]{x});
        finish();
    }

    public void updatefood(){


        String id=binding.id.getText().toString();
        String categoryid=binding.category.getText().toString();
        String description=binding.description.getText().toString();
        String price=binding.price.getText().toString();
        String star=binding.star.getText().toString();
        String time=binding.timevalue.getText().toString();
        String title=binding.title.getText().toString();
        ContentValues contentValues1=new ContentValues();
        contentValues1.put(FoodProviderConstarint.FOODS_TB_CategoryId,Integer.valueOf(categoryid));
        contentValues1.put(FoodProviderConstarint.FOODS_TB_Description,description);
        contentValues1.put(FoodProviderConstarint.FOODS_TB_Price,Double.valueOf(price));
        contentValues1.put(FoodProviderConstarint.FOODS_TB_Star,Double.valueOf(star));
        contentValues1.put(FoodProviderConstarint.FOODS_TB_TimeValue,Double.valueOf(time));
        contentValues1.put(FoodProviderConstarint.FOODS_TB_Title,title);
        getContentResolver().update(FoodProviderConstarint.CONTENT_URI_1,contentValues1,FoodProviderConstarint.FOODS_TB_id+"=?",new String[]{id});
        finish();
    }
}