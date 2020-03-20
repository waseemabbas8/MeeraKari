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

public class recommendationrecyclerviewadapter1 extends RecyclerView.Adapter<recommendationrecyclerviewadapter1.MyViewHolder> {

    public LayoutInflater inflater;
    public ArrayList<recommendationrecyclerview> imageModelArrayList;
    public Activity ctx;

    public recommendationrecyclerviewadapter1(ArrayList<recommendationrecyclerview> imageModelArrayList, Activity ctx) {
        this.imageModelArrayList = imageModelArrayList;
        this.ctx = ctx;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        //abe yeh kia kiya hua hai
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.recyclersub, viewGroup, false);
        recommendationrecyclerviewadapter1.MyViewHolder holder = new recommendationrecyclerviewadapter1.MyViewHolder(view);

        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder myViewHolder, int i) {

        Glide.with(ctx).load(imageModelArrayList.get(i).getImage_drawable()).into(myViewHolder.iv);
        //vahi
        //adapter set nhi horha
        //phr kia kru ab
        myViewHolder.time.setText(imageModelArrayList.get(i).getName());

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

            time = itemView.findViewById(R.id.txtAllAdapter);
            iv = itemView.findViewById(R.id.imgTagAllAdapter);
            //chal gyaaa
            // ab vendor ka kaam
        }
    }
}
