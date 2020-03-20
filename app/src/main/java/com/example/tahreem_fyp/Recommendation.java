package com.example.tahreem_fyp;

import androidx.annotation.NonNull;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import android.os.Bundle;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class Recommendation extends AppCompatActivity {


    DatabaseReference databaseReference;
    //model class
    ArrayList<recommendationrecyclerview> list = new ArrayList<>();

    private androidx.recyclerview.widget.RecyclerView recyclersub;
    private recommendationrecyclerviewadapter1 adapter;
    private LinearLayoutManager layoutManager;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recommendation);

        recyclersub= findViewById(R.id.recommendationRecyclerView);
//        layoutManager = new GridLayoutManager(this, 2);
        layoutManager = new LinearLayoutManager(this, androidx.recyclerview.widget.RecyclerView.VERTICAL,false);
        recyclersub.setLayoutManager(layoutManager);
        recyclersub.setHasFixedSize(true);

        RecyclerMethod();
    }

    private void RecyclerMethod(){

//        ArrayList<com.example.tahreem_fyp.RecyclerView> list = new ArrayList<>();
        databaseReference = FirebaseDatabase.getInstance().getReference("stock");
        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {

            ArrayList<recommendationrecyclerview> list1 = new ArrayList<>();
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                for (DataSnapshot dataSnapshot1: dataSnapshot.getChildren())
                {
                   recommendationrecyclerview recyclerView = new recommendationrecyclerview();

                    String url = dataSnapshot1.child("imgurl").getValue(String.class);
                    String txt = dataSnapshot1.child("text").getValue(String.class);
                    recyclerView.setName(txt);
                    recyclerView.setImage_drawable(url);
                    list1.add(recyclerView);
                    //Toast.makeText(MainActivity.this, url, Toast.LENGTH_SHORT).show();
                }
                adapter = new recommendationrecyclerviewadapter1(list1,Recommendation.this);
                recyclersub.setAdapter(adapter);
                Toast.makeText(Recommendation.this, "size : " + list.size(), Toast.LENGTH_SHORT).show();

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
        Toast.makeText(Recommendation.this, "size : " + list.size(), Toast.LENGTH_SHORT).show();

        //Run kr dikha error kahn hai aut login wala bhi chk kr
        //ok
        //recommendation p open app again de raa h

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }
}
