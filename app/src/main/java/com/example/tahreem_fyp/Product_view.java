package com.example.tahreem_fyp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

public class Product_view extends AppCompatActivity {


    ImageView imgview;
    Button btn_order;
    TextView tv,tvname;
    String modelImg = "", modelName="";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_view);

        imgview = (ImageView)findViewById(R.id.img_big);
        tv = (TextView)findViewById(R.id.item_price);
        tvname = (TextView)findViewById(R.id.chk_txt_big);
        btn_order = (Button)findViewById(R.id.btn_order);

        modelImg = getIntent().getStringExtra("imgURL");
        modelName = getIntent().getStringExtra("imgName");

        Glide.with(this).load(modelImg).into(imgview);


       btn_order.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View view) {

           }
       });
    }
}
