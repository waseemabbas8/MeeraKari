package com.example.tahreem_fyp;

import android.content.Context;
import android.content.Intent;

import androidx.annotation.NonNull;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

public class onehomeAdaptor extends PagerAdapter {


    private Context ctx;
    private LayoutInflater layoutInflater;

    //images le raha hai ye slider ki
    private int[] Images = {R.drawable.draw1, R.drawable.draw2,R.drawable.teh};



    public onehomeAdaptor(Context ctx) {
        this.ctx = ctx;

        layoutInflater = LayoutInflater.from(ctx);

    }


    @Override
    public int getCount() {
        return Images.length;
    }

    // yahan par slider ka kaam ho raha hai view pager se
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, final int position) {
        layoutInflater = (LayoutInflater)ctx.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = layoutInflater.inflate(R.layout.content_main, null);
        ImageView imageView = view.findViewById(R.id.image);
        imageView.setImageResource(Images[position]);
        ViewPager viewPager = (ViewPager)container;
        viewPager.addView(view);

        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (position == 0){
                    Intent intent = new Intent(ctx,StockActivity.class);
                    ctx.startActivity(intent);

                }
                else if(position == 1)
                {
                    Intent intent = new Intent(ctx,MatchingActivity.class);
                    ctx.startActivity(intent);
                    // Toast.makeText(ctx, "Falana Activity 2", Toast.LENGTH_SHORT).show();

                }

                else if(position == 2)
                {
                    Intent intent = new Intent(ctx,CustomActivity.class);
                    ctx.startActivity(intent);

                }
            }
        });

        return  view;
    }



//    @Override
//        public Object instantiateItem(ViewGroup view, int position) {
//            View imageLayout = layoutInflater.inflate(R.layout.content_main, view, false);
//
//            assert imageLayout != null;
//            final ImageView imageView = (ImageView) imageLayout
//                    .findViewById(R.id.imageView);
//
//
//            imageView.setImageResource(Images.get(position));
//
//            view.addView(imageLayout, 0);
//
//            return imageLayout;
//        }


//Pager ka kaam ho raha hai ye


//        ViewPager vp = (ViewPager) container;
//        vp.addView(item_view, 0);
//
//        return item_view;
//
//

//    }







//        }
//    }
//}
    //     @Override
//                public void onClick(View v){
//
//
//        }


    //  }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        ViewPager viewPager = (ViewPager)container;
        View view = (View)object;
        viewPager.removeView(view);
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }


//    @Override
//    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
//       //
//        // container.removeView((LinearLayout) object);
//        ViewPager vp = (ViewPager) container;
//        View Item_view = (View) object;
//        vp.removeView(Item_view);
//
//
//    }






}
