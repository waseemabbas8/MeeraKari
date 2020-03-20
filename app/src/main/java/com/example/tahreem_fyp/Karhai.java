package com.example.tahreem_fyp;


import androidx.annotation.NonNull;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.os.Bundle;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class Karhai extends AppCompatActivity {

    DatabaseReference databaseReference;
    private DatabaseReference historyRef = FirebaseDatabase.getInstance().getReference("History");
    FirebaseUser currentFirebaseUser = FirebaseAuth.getInstance().getCurrentUser();
    //model class
    ArrayList<com.example.tahreem_fyp.RecyclerView> list = new ArrayList<>();

    private androidx.recyclerview.widget.RecyclerView recyclersub;
    private recyclerviewsubcatadapter adapter;
    private LinearLayoutManager layoutManager;
    private Boolean countUpdated = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.karhai);

        recyclersub = findViewById(R.id.karahiRecyclerView);
//        layoutManager = new GridLayoutManager(this, 2);
        layoutManager = new LinearLayoutManager(this, androidx.recyclerview.widget.RecyclerView.VERTICAL, false);
        recyclersub.setLayoutManager(layoutManager);
        recyclersub.setHasFixedSize(true);

        final DatabaseReference karahiCount = historyRef.child(currentFirebaseUser.getUid()).child("karhai");
        karahiCount.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (countUpdated) return;
                Integer lastCount = dataSnapshot.getValue(Integer.class);
                int newCount = 0;
                if (lastCount != null){
                    newCount = lastCount;
                }
                newCount++;
                karahiCount.setValue(newCount);
                countUpdated = true;
                //do what you want with the email
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

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

                    String url = dataSnapshot1.child("imgUrl").getValue(String.class);
                    String txt = dataSnapshot1.child("itemName").getValue(String.class);
                    recyclerView.setName(txt);
                    recyclerView.setImage_drawable(url);
                    list1.add(recyclerView);
                    //Toast.makeText(MainActivity.this, url, Toast.LENGTH_SHORT).show();
                }
                adapter = new recyclerviewsubcatadapter(list1, Karhai.this);
                recyclersub.setAdapter(adapter);
                Toast.makeText(Karhai.this, "size : " + list.size(), Toast.LENGTH_SHORT).show();

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
        Toast.makeText(Karhai.this, "size : " + list.size(), Toast.LENGTH_SHORT).show();


    }

}
