package com.example.tahreem_fyp;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.squareup.picasso.Picasso;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;


import java.util.ArrayList;


public  class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.MyViewHolder> {

  public  LayoutInflater inflater;
  public  ArrayList<com.example.tahreem_fyp.RecyclerView> imageModelArrayList;
  public  Activity ctx;

  public RecyclerViewAdapter(Activity ctx, ArrayList<com.example.tahreem_fyp.RecyclerView> imageModelArrayList) {

    this.ctx = ctx;
    this.imageModelArrayList = imageModelArrayList;
    inflater = LayoutInflater.from(ctx);

  }


  @NonNull
  @Override
  public RecyclerViewAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

    View view = inflater.inflate(R.layout.recyclerviewlayout, parent, false);
    MyViewHolder holder = new MyViewHolder(view);

    return holder;
  }

  @Override
  public void onBindViewHolder(@NonNull RecyclerViewAdapter.MyViewHolder holder, int position) {

    Glide.with(ctx).load(imageModelArrayList.get(position).getImage_drawable()).into(holder.iv);

//    Glide.with(ctx).asBitmap().load(imageModelArrayList.get(position).getImage_drawable()).into(holder.iv);
//    Picasso.get().load(imageModelArrayList.get(position).getImage_drawable());
    holder.time.setText(imageModelArrayList.get(position).getName());
  }

  @Override
  public int getItemCount() {
    return imageModelArrayList.size();
  }

  public class MyViewHolder extends RecyclerView.ViewHolder {

    TextView time;
    ImageView iv;
    public MyViewHolder(@NonNull View itemView) {
      super(itemView);
      time = itemView.findViewById(R.id.txt);
      iv = itemView.findViewById(R.id.imgTag);



    }
  }
}
