package com.example.tahreem_fyp;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;

public class adaptermatching extends RecyclerView.Adapter<adaptermatching.MyViewHolder> {

  public LayoutInflater inflater;
  public ArrayList<com.example.tahreem_fyp.matchingrecyclerview> imglist;
  public  Activity ctx;


  public adaptermatching(Activity ctx, ArrayList<matchingrecyclerview> imglist) {
    this.ctx=ctx;
    this.imglist = imglist;
    inflater = LayoutInflater.from(ctx);
  }



  @NonNull
  @Override
  public adaptermatching.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

    View view = inflater.inflate(R.layout.matchingviewlayout, parent, false);
    adaptermatching.MyViewHolder holder = new adaptermatching.MyViewHolder(view);

    return holder;
  }

  @Override
  public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
    Glide.with(ctx).load(imglist.get(position).getImage_drawable()).into(holder.iv);

//    Glide.with(ctx).asBitmap().load(imageModelArrayList.get(position).getImage_drawable()).into(holder.iv);
//    Picasso.get().load(imageModelArrayList.get(position).getImage_drawable());
    holder.time.setText(imglist.get(position).getName());
  }




  @Override
  public int getItemCount() {
    return imglist.size();
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