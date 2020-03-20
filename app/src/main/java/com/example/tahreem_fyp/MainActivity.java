package com.example.tahreem_fyp;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;

import com.bumptech.glide.Glide;
import com.facebook.login.LoginManager;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import androidx.annotation.NonNull;
import androidx.core.view.GravityCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Adapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Objects;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {


    private static int currentPage = 0;
    private static int NUM_PAGES = 0;


    private ProgressDialog LoadingBar;

    ViewPager mpager;
    SessionManager manager;
    String email = "";
    Button stockviewall, matchingviewall, customviewall;


    DatabaseReference databaseReference;

    TextView textViewnamenav, textviewemailnav;
    String user_id;
    // dtbase  ka refereence hai yahan par jahan firebase ka aur us k storage aur prefernces ka usage aarha hai
    //DatabaseReference databaseReference;
    FirebaseAuth firebaseAuth;
    String U_Fname = "", U_LName = "", U_Email = "", U_pic = "";
    ImageView imageViewProf;


    Button logoutBtn;

    //STOCK RECYCLERVIEW
    private RecyclerView recyclerView;
    private ArrayList<com.example.tahreem_fyp.RecyclerView> imageModelArrayList;
    private RecyclerViewAdapter adapter;

    ArrayList<com.example.tahreem_fyp.RecyclerView> list = new ArrayList<>();


    ////MATCHING RECYCLERVIEW
    private RecyclerView matchingrecycler;
    private ArrayList<com.example.tahreem_fyp.matchingrecyclerview> imglist;
    private adaptermatching adaptermatchingg;


    //CUSTOM RECYCLERVIEW
    private RecyclerView customrecycler;
    private ArrayList<recyclerviewcustom> imgcolumn;
    private adapterCustom adapterCustom;


    //Navigation Drawer ::
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        firebaseAuth = FirebaseAuth.getInstance();

        manager = new SessionManager(this);
        manager.checkLogin();


        textViewnamenav = findViewById(R.id.profilename);
        textviewemailnav = findViewById(R.id.profileemail);
        imageViewProf = findViewById(R.id.imageViewprof);

        databaseReference = FirebaseDatabase.getInstance().getReference();

//        getPersonalInfo();

        String str = firebaseAuth.getUid();
        Toast.makeText(this, "this " + str, Toast.LENGTH_SHORT).show();


        //  textViewnamenav.setText("First Name" + "Last Name");
        // lastnameProf.setText("Last Name");
        //    textviewemailnav.setText("Email ");


        HashMap<String, String> user = manager.getUserDetail();
        email = user.get(SessionManager.EMAIL);

        databaseReference = FirebaseDatabase.getInstance().getReference();


        stockviewall = (Button) findViewById(R.id.btn_stock_viewall);
        stockviewall.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent MainIntent = new Intent(MainActivity.this, StockActivity.class);
                startActivity(MainIntent);
                finish();

            }
        });

        matchingviewall = (Button) findViewById(R.id.viewallmatching);
        matchingviewall.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent MainIntent = new Intent(MainActivity.this, MatchingActivity.class);
                startActivity(MainIntent);
                finish();

            }
        });

        customviewall = (Button) findViewById(R.id.viewallcustom);
        customviewall.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent MainIntent = new Intent(MainActivity.this, CustomActivity.class);
                startActivity(MainIntent);
                finish();

            }
        });


        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();
        //  getPersonalInfo();


        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);


        //STOCK RECYCLERVIEW
        recyclerView = (RecyclerView) findViewById(R.id.viewRecycler);
        recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.HORIZONTAL, false));

        RecyclerMethod();


        //MATCHING RECYCLERVIEW
        matchingrecycler = (RecyclerView) findViewById(R.id.Recyclermatching);
        matchingrecycler.setLayoutManager(new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.HORIZONTAL, false));

        MatchingRecyclerMethod();


        //CUSTOM RECYCLERVIEW
        customrecycler = (RecyclerView) findViewById((R.id.recyclercustom));
        customrecycler.setLayoutManager(new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.HORIZONTAL, false));
        CustomRecyclerMethod();


    }


    //STOCK RECYCLERVIEW

    private void RecyclerMethod() {

//        ArrayList<com.example.tahreem_fyp.RecyclerView> list = new ArrayList<>();
        databaseReference = FirebaseDatabase.getInstance().getReference("stock");
        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {

            ArrayList<com.example.tahreem_fyp.RecyclerView> list1 = new ArrayList<>();

            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()) {
                    com.example.tahreem_fyp.RecyclerView recyclerViewc = new com.example.tahreem_fyp.RecyclerView();
                    //  RecyclerView recyclerViewc = new RecyclerView();

                    String url = dataSnapshot1.child("imgurl").getValue(String.class);
                    String txt = dataSnapshot1.child("text").getValue(String.class);
                    recyclerViewc.setName(txt);
                    recyclerViewc.setImage_drawable(url);
                    list1.add(recyclerViewc);
                    //Toast.makeText(MainActivity.this, url, Toast.LENGTH_SHORT).show();
                }
                adapter = new RecyclerViewAdapter(MainActivity.this, list1);
                recyclerView.setAdapter(adapter);
                Toast.makeText(MainActivity.this, "size : " + list.size(), Toast.LENGTH_SHORT).show();

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
        Toast.makeText(MainActivity.this, "size : " + list.size(), Toast.LENGTH_SHORT).show();


    }


    //MATCHING RECYCLERVIEW

    private void MatchingRecyclerMethod() {

//        ArrayList<com.example.tahreem_fyp.RecyclerView> list = new ArrayList<>();
        databaseReference = FirebaseDatabase.getInstance().getReference("custom");
        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {

            ArrayList<recyclerviewcustom> list3 = new ArrayList<>();

            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()) {
                    recyclerviewcustom recyclerViewc = new recyclerviewcustom();

                    String url = dataSnapshot1.child("imgurl").getValue(String.class);
                    String txt = dataSnapshot1.child("text").getValue(String.class);
                    recyclerViewc.setName(txt);
                    recyclerViewc.setImage_drawable(url);
                    list3.add(recyclerViewc);
                }
                adapterCustom = new adapterCustom(MainActivity.this, list3);
                customrecycler.setAdapter(adapterCustom);
                Toast.makeText(MainActivity.this, "size : " + list3.size(), Toast.LENGTH_SHORT).show();

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
        Toast.makeText(MainActivity.this, "size : " + list.size(), Toast.LENGTH_SHORT).show();


    }


    //CUSTOM RECYCLERVIEW

    private void CustomRecyclerMethod() {


        databaseReference = FirebaseDatabase.getInstance().getReference("matching");
        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {

            ArrayList<matchingrecyclerview> list2 = new ArrayList<>();

            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()) {
                    matchingrecyclerview recyclerViewm = new matchingrecyclerview();

                    String url = dataSnapshot1.child("imgurl").getValue(String.class);
                    String txt = dataSnapshot1.child("text").getValue(String.class);
                    recyclerViewm.setName(txt);
                    recyclerViewm.setImage_drawable(url);
                    list2.add(recyclerViewm);
                    Toast.makeText(MainActivity.this, url, Toast.LENGTH_SHORT).show();
                }
                adaptermatchingg = new adaptermatching(MainActivity.this, list2);
                matchingrecycler.setAdapter(adaptermatchingg);
                Toast.makeText(MainActivity.this, "size : " + list2.size(), Toast.LENGTH_SHORT).show();

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
        Toast.makeText(MainActivity.this, "size : " + list.size(), Toast.LENGTH_SHORT).show();


    }


    //navigation drawer work
/*
    void getPersonalInfo() {

        firebaseAuth = FirebaseAuth.getInstance();
        databaseReference = FirebaseDatabase.getInstance().getReference("Users");

        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                user_id  = Objects.requireNonNull(firebaseAuth.getCurrentUser()).getUid();
                U_Email = firebaseAuth.getCurrentUser().getEmail();

                for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()) {
//                    if(dataSnapshot1.child(user_id).child("firstn").getValue(String.class) != null) {
                        U_Fname = dataSnapshot1.child(user_id).child("firstn").getValue(String.class);
//                    }
//                    if(dataSnapshot1.child(user_id).child("lastn").getValue(String.class) != null) {
                        U_LName = dataSnapshot1.child(user_id).child("lastn").getValue(String.class);
//                    }
//                    U_Email = dataSnapshot1.child(user_id).child("email_id").getValue(String.class);

                    if (dataSnapshot1.child(user_id).child("url").exists())
                    {
                        // child hai jis me URL ki value get karaa re hain
                        U_pic = dataSnapshot1.child(user_id).child("url").getValue(String.class);

                    }

                }



                // data set karayaa
                // jo heading par likhna hai .. us k liey bho ae ga ye


                textViewnamenav.setText(U_Fname + " " +U_LName);
                // edt_lastname.setText(U_LName);
                textviewemailnav.setText(U_Email);
                //  userNameDisplay.setText(U_Fname +" "+ U_LName);
                //  userEmailDisplay.setText(U_Email);
                if (!(U_pic.isEmpty())) {

                    // glide ki lib use hoi hai yahan
                    Glide.with(MainActivity.this).load(U_pic).into(imageViewProf);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

                Toast.makeText(MainActivity.this, "Error!!", Toast.LENGTH_SHORT).show();

            }
        });

        // database ka refernce de diya hai yahan par
//        databaseReference = FirebaseDatabase.getInstance().getReference("Users");
//        // listener add karaa diyaa yahan par
//        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
//            @Override
//
//            // data ko change kr k vps db me bhi to store krana hoga naa
//            // to vo data snapshot me ae gaa jis me User ka table hai
//            // aur us k childs hain 3  jis me name last name aur email address hain
//            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
//
//            }
//
//            @Override
//            public void onCancelled(@NonNull DatabaseError databaseError) {
//
//            }
//        });
    }
*/

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
//            return true;
            Intent intent = new Intent(this, Edit_Profile.class);
            startActivity(intent);
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();
        // Fragment fragment = null;

        if (id == R.id.nav_home) {
            DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
            drawer.closeDrawer(GravityCompat.START);
            return true;

        } else if (id == R.id.nav_recomm) {
            Intent MainIntent = new Intent(MainActivity.this, RecommendationsActivity.class);
            startActivity(MainIntent);

        } else if (id == R.id.nav_aboutus) {
            getSupportFragmentManager().beginTransaction().replace(R.id.content, new threeaboutus()).commit();

        } else if (id == R.id.nav_editprofile) {
            Intent MainIntent = new Intent(MainActivity.this, Edit_Profile.class);
            startActivity(MainIntent);

        } else if (id == R.id.nav_updatepass) {
            Intent MainIntent = new Intent(MainActivity.this, Update_Password.class);
            startActivity(MainIntent);
        } else if (id == R.id.nav_logout) {

            LoadingBar = new ProgressDialog(this);
            LoadingBar.setTitle("LOGGING OUT");
            LoadingBar.setMessage("PLEASE WAIT");
            //ye is liey kiyaa h use taake kahin touch krne pr loadng baar bnd na hoo

            LoadingBar.setCanceledOnTouchOutside(false);
            LoadingBar.show();
            LoginManager.getInstance().logOut();

            Toast.makeText(MainActivity.this, "Logged Out!", Toast.LENGTH_SHORT).show();
            SessionManager manager = new SessionManager(MainActivity.this);
            manager.logout();


        }


        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;


    }

}