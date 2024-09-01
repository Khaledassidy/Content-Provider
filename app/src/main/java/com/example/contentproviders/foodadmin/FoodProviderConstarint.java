package com.example.contentproviders.foodadmin;

import android.net.Uri;

public interface FoodProviderConstarint {
     String AUTHORITY="com.example.foodapp";

     String PATH_FOOD_LIST="FOOD_LIST";
     String PATH_FOOD_PLACE="FOOD_LIST_FORM_PLACE";
     String PATH_FOOD_COUNT="FOOD_COUNT";


    Uri CONTENT_URI_1=Uri.parse("content://"+AUTHORITY+"/"+PATH_FOOD_LIST);
    Uri CONTENT_URI_2=Uri.parse("content://"+AUTHORITY+"/"+PATH_FOOD_PLACE);
    Uri CONTENT_URI_3=Uri.parse("content://"+AUTHORITY+"/"+PATH_FOOD_COUNT);

     String FOODS_TB_CategoryId="CategoryId";
     String FOODS_TB_Description="Description";
     String FOODS_TB_Imagepath="Imagepath";
     String FOODS_TB_Price="Price";
     String FOODS_TB_Star="Star";
     String FOODS_TB_TimeValue="TimeValue";
     String FOODS_TB_Title="Title";
     String FOODS_TB_incart="incart";
     String FOODS_TB_numberInCart="numberInCart";
     String FOODS_TB_id="id";
     String FOODS_TB_LocationId="LocationId";
     String FOODS_TB_PriceId="PriceId";
     String FOODS_TB_TimeId="TimeId";
     String FOODS_TB_BESTFOOD="bestfood";
}
