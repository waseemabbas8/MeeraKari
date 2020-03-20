package com.example.tahreem_fyp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;

public class CustomActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_custom);
        CardView item1 = findViewById(R.id.itemone);
        CardView item2 = findViewById(R.id.itemtwo);
        CardView item3 = findViewById(R.id.itemthree);
        CardView item4 = findViewById(R.id.itemfour);
        CardView item5 = findViewById(R.id.itemfive);
        CardView item6 = findViewById(R.id.itemsix);
        ImageView s_back2 = findViewById(R.id.s_back2);

        item1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent MainIntent = new Intent(CustomActivity.this,Karhai.class);
                startActivity(MainIntent);
            }
        });

        item2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent MainIntent = new Intent(CustomActivity.this,Bunai.class);
                startActivity(MainIntent);
            }
        });
        item3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent MainIntent = new Intent(CustomActivity.this,Qureshia.class);
                startActivity(MainIntent);
                finish();
            }
        });


        item4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent MainIntent = new Intent(CustomActivity.this,Diying.class);
                startActivity(MainIntent);
                finish();
            }
        });


        item5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent MainIntent = new Intent(CustomActivity.this,Ribbonwork.class);
                startActivity(MainIntent);
                finish();
            }
        });

        item6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent MainIntent = new Intent(CustomActivity.this,Mirrorwork.class);
                startActivity(MainIntent);
                finish();
            }
        });



        s_back2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(CustomActivity.this,MainActivity.class);
            }
        });

    }

}
