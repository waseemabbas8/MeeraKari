package com.example.tahreem_fyp;


import android.app.Activity;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.List;


public class recyclerviewsubcatadapter extends RecyclerView.Adapter<recyclerviewsubcatadapter.MyViewHolder> {

    public LayoutInflater inflater;
    public List<com.example.tahreem_fyp.RecyclerView> imageModelArrayList;
    public Activity ctx;

    public recyclerviewsubcatadapter(List<com.example.tahreem_fyp.RecyclerView> imageModelArrayList, Activity ctx) {
        this.imageModelArrayList = imageModelArrayList;
        this.ctx = ctx;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recyclersub, parent, false);
        recyclerviewsubcatadapter.MyViewHolder holder = new MyViewHolder(view);

        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {

        final com.example.tahreem_fyp.RecyclerView model = imageModelArrayList.get(position);
        Glide.with(ctx).load(model.getImage_drawable()).into(holder.iv);
        holder.time.setText(model.getName());

        holder.layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ctx, Product_view.class);
                intent.putExtra("imgURL", model.getImage_drawable());
                intent.putExtra("imgName", model.getName());
                ctx.startActivity(intent);
            }
        });

    }

    @Override
    public int getItemCount() {
        return imageModelArrayList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView time;
        ImageView iv;
        RelativeLayout layout;


        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            time = itemView.findViewById(R.id.txtAllAdapter);
            iv = itemView.findViewById(R.id.imgTagAllAdapter);
            layout = itemView.findViewById(R.id.recyclerItem);
        }
    }
}
