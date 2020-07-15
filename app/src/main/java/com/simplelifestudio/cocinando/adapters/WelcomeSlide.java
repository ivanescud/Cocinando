package com.simplelifestudio.cocinando.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.viewpager.widget.PagerAdapter;

import com.simplelifestudio.cocinando.R;


public class WelcomeSlide extends PagerAdapter {
    Context context;
    LayoutInflater layoutInflater;
    public String[] slide_body;

    public WelcomeSlide(Context context) {
        this.context = context;
      this.slide_body = new String[]{
              context.getString(R.string.slide_0), context.getString(R.string.lorem_ipsum_s), context.getString(R.string.lorem_ipsum_s)
      };

    }



    @Override
    public int getCount() {
        return slide_body.length;
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return view == object;
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {
        layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = layoutInflater.inflate(R.layout.welcome_slide,container,false);
        TextView bodyText = view.findViewById(R.id.welcomeSlideTextTV);

         bodyText.setText(slide_body[position]);
         container.addView(view);
         return view;
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        container.removeView((FrameLayout)object);
    }
}
