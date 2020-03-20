package com.example.tahreem_fyp;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;


public class adapterCustom extends RecyclerView.Adapter<adapterCustom.MyViewHolder> {

    public LayoutInflater inflater;
    public ArrayList<recyclerviewcustom> imgescolumn;
    public  Activity ctx;

    public  adapterCustom(Activity ctx, ArrayList<recyclerviewcustom> imgescolumn)
    {
        this.ctx = ctx;
        this.imgescolumn = imgescolumn;
        inflater = LayoutInflater.from(ctx);

    }
    @NonNull
    @Override
    public adapterCustom.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = inflater.inflate(R.layout.layout_customvieww, parent, false);
        adapterCustom.MyViewHolder holder = new adapterCustom.MyViewHolder(view);

        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull adapterCustom.MyViewHolder holder, int position) {
        Glide.with(ctx).load(imgescolumn.get(position).getImage_drawable()).into(holder.iv);
        holder.time.setText(imgescolumn.get(position).getName());
    }


    @Override
    public int getItemCount() {
        return imgescolumn.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView time;
        ImageView iv;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            time = itemView.findViewById(R.id.txtCustom);
            iv = itemView.findViewById(R.id.imgTag);


        }
    }
}
