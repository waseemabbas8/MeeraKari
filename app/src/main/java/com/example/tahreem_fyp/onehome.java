package com.example.tahreem_fyp;

import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

public class onehome extends Fragment {
    ImageView img;

    ViewPager mpager;
    onehomeAdaptor adaptor;

    public onehome() {


    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_onehome, container, false);

//         return group;
        //  View view =inflater.inflate(R.layout.fragment_onehome,container,false);
        // img = view.findViewById(R.id.Images);

        //  picasso.get().load(getArguments().getString("source")).Into(img);
        //   img.setOnClickListener(new View.OnClickListener() {
        //    @Override
        //   public void onClick(View view) {
        //   Intent intent = new Intent(getActivity(),StockActivity.class);
        // Intent.putExtra();
        //yahan par kaam ho raha hai jo image url phikvaa raha hai
        // picasso ki dependency ka use nh hai


        //   }
        //  });
        // return view;


    }
}


//    public void onClick(ImageView img){
//        Intent intent = new Intent(this.getContext(),MainActivity.class);
//        startActivity(intent);
//
//        intent.putExtra(
//                if (R.id.imageView == 0){
//                       Intent intent = new Intent(this.getContext(),StockActivity.class);
//                        startActivity(intent);
//
//        }
//        );if (R.id.imageView == 0) {
//
//
//        } else if (R.id.imageView == 1) {
//            Intent intent = new Intent(this.getContext(),MatchingActivity.class);
//            startActivity(intent);
//
//        } else if (R.id.imageView == 2) {
//            Intent intent = new Intent(this.getContext(),CustomActivity.class);
//            startActivity(intent);
//
