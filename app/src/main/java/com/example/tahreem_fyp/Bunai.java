package com.example.tahreem_fyp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.os.Bundle;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;


public class Bunai extends AppCompatActivity {

  DatabaseReference databaseReference;
  //model class
  ArrayList<RecyclerView> list = new ArrayList<>();

  private androidx.recyclerview.widget.RecyclerView recyclersub;
  private recyclerviewsubcatadapter adapter;
  private LinearLayoutManager layoutManager;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.karhai);

    recyclersub = findViewById(R.id.karahiRecyclerView);
//        layoutManager = new GridLayoutManager(this, 2);
    layoutManager = new LinearLayoutManager(this, androidx.recyclerview.widget.RecyclerView.VERTICAL, false);
    recyclersub.setLayoutManager(layoutManager);
    recyclersub.setHasFixedSize(true);

    RecyclerMethod();
  }

  private void RecyclerMethod() {

//        ArrayList<com.example.tahreem_fyp.RecyclerView> list = new ArrayList<>();
    databaseReference = FirebaseDatabase.getInstance().getReference("custom").child("karhai");
    databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {

      ArrayList<com.example.tahreem_fyp.RecyclerView> list1 = new ArrayList<>();

      @Override
      public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

        for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()) {
          com.example.tahreem_fyp.RecyclerView recyclerView = new com.example.tahreem_fyp.RecyclerView();

          String url = dataSnapshot1.child("imgurl").getValue(String.class);
          String txt = dataSnapshot1.child("text").getValue(String.class);
          recyclerView.setName(txt);
          recyclerView.setImage_drawable(url);
          list1.add(recyclerView);
          //Toast.makeText(MainActivity.this, url, Toast.LENGTH_SHORT).show();
        }
        adapter = new recyclerviewsubcatadapter(list1, Bunai.this);
        recyclersub.setAdapter(adapter);
        Toast.makeText(Bunai.this, "size : " + list.size(), Toast.LENGTH_SHORT).show();

      }

      @Override
      public void onCancelled(@NonNull DatabaseError databaseError) {

      }
    });
    Toast.makeText(Bunai.this, "size : " + list.size(), Toast.LENGTH_SHORT).show();


  }
  @Override
  public void onBackPressed() {
    super.onBackPressed();
    finish();
  }
}
