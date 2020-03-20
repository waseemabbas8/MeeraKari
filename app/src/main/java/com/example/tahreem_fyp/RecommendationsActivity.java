package com.example.tahreem_fyp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.example.tahreem_fyp.utils.Helpers;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;


public class RecommendationsActivity extends AppCompatActivity {
    private final int KARHAI_INDEX = 1;
    private final int RIBBON_WORK_INDEX = 2;
    private final int QURESHIA_INDEX = 3;
    private final int MIRROR_WORK_INDEX = 4;

    private HashMap<Integer, String> categoriesMap = new HashMap<>(4);

    FirebaseUser currentFirebaseUser = FirebaseAuth.getInstance().getCurrentUser();
    private DatabaseReference historyRef = FirebaseDatabase.getInstance().getReference("History");

    private androidx.recyclerview.widget.RecyclerView rvRecommendations;

    private int countsArray[] = new int[5];

    private boolean karhaiCount, ribbonWorkCount, qureshiaCount, mirrorWorkCount = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recommendations);

        rvRecommendations = findViewById(R.id.rv_recommendations);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this, androidx.recyclerview.widget.RecyclerView.VERTICAL, false);
        rvRecommendations.setLayoutManager(layoutManager);
        rvRecommendations.setHasFixedSize(true);

        setCategoriesMap();

        getCommendedCategory();

    }

    private void setCategoriesMap() {
        categoriesMap.put(KARHAI_INDEX, "karhai");
        categoriesMap.put(RIBBON_WORK_INDEX, "RibbonWork");
        categoriesMap.put(QURESHIA_INDEX, "Qureshia");
        categoriesMap.put(MIRROR_WORK_INDEX, "MirrorWork");
    }

    private void getCommendedCategory() {
        DatabaseReference karahiCountRef = historyRef.child(currentFirebaseUser.getUid()).child("karhai");
        DatabaseReference ribbonWorkCountRef = historyRef.child(currentFirebaseUser.getUid()).child("RibbonWork");
        final DatabaseReference qureshiaCountRef = historyRef.child(currentFirebaseUser.getUid()).child("Qureshia");
        DatabaseReference mirrorWorkCountRef = historyRef.child(currentFirebaseUser.getUid()).child("MirrorWork");

        karahiCountRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                Integer value = dataSnapshot.getValue(Integer.class);
                countsArray[KARHAI_INDEX] = value != null ? value : 0;
                karhaiCount = true;
                checkDataToSet();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        ribbonWorkCountRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                Integer value = dataSnapshot.getValue(Integer.class);
                countsArray[RIBBON_WORK_INDEX] = value != null ? value : 0;
                ribbonWorkCount = true;
                checkDataToSet();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        qureshiaCountRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                Integer value = dataSnapshot.getValue(Integer.class);
                countsArray[QURESHIA_INDEX] = value != null ? value : 0;
                qureshiaCount = true;
                checkDataToSet();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        mirrorWorkCountRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                Integer value = dataSnapshot.getValue(Integer.class);
                countsArray[MIRROR_WORK_INDEX] = value != null ? value : 0;
                mirrorWorkCount = true;
                checkDataToSet();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }

    private void checkDataToSet() {
        if (karhaiCount && ribbonWorkCount && qureshiaCount && mirrorWorkCount) {
            int highestIndex = Helpers.getIndexOfHighestNumber(countsArray);
            String recommendedCategory = highestIndex != 0 ? categoriesMap.get(highestIndex) : "";
            Log.d("category", "category"+recommendedCategory);
            setDate(recommendedCategory);
        }
    }

    private void setDate(String categoryName) {
        if (categoryName.isEmpty()) return;

        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("custom").child(categoryName);
        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {

            ArrayList<RecyclerView> list1 = new ArrayList<>();

            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                int index = 0;
                for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()) {
                    com.example.tahreem_fyp.RecyclerView recyclerView = new com.example.tahreem_fyp.RecyclerView();

                    String url = dataSnapshot1.child("imgUrl").getValue(String.class);
                    String txt = dataSnapshot1.child("itemName").getValue(String.class);
                    recyclerView.setName(txt);
                    recyclerView.setImage_drawable(url);
                    list1.add(recyclerView);
                    index++;
                    if (index == 19) break;
                }
                recyclerviewsubcatadapter adapter = new recyclerviewsubcatadapter(list1, RecommendationsActivity.this);
                rvRecommendations.setAdapter(adapter);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }


}
