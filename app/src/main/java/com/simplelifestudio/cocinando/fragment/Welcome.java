package com.simplelifestudio.cocinando.fragment;

import android.os.Bundle;
import android.text.Html;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import com.simplelifestudio.cocinando.R;
import com.simplelifestudio.cocinando.adapters.WelcomeSlide;

import java.util.Objects;
import java.util.Timer;
import java.util.TimerTask;

public class Welcome extends Fragment {
private LinearLayout linearLayout;
private ViewPager viewpager;
private TextView[] dots;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Timer timer = new Timer();
        timer.scheduleAtFixedRate(new SliderTimer(), 4000 , 6000);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_welcome,container,false);
        init(v);
        addDotsIndicator(0);
        return v;
    }

    public void init(View v){
        linearLayout = v.findViewById(R.id.welcomeDotsLY);
        WelcomeSlide welcomeSlideAdapeter = new WelcomeSlide(Objects.requireNonNull(getContext()));
        viewpager = v.findViewById(R.id.welcomeViewPagerVP);
        viewpager.setAdapter(welcomeSlideAdapeter);
        viewpager.addOnPageChangeListener(viewlistener);
        Button iniciar = v.findViewById(R.id.welcomeIniciarBT);
        iniciar.setOnClickListener((View.OnClickListener) getContext());
        Button registrar = v.findViewById(R.id.welcomeRegistrarBT);
        registrar.setOnClickListener((View.OnClickListener) getContext());
    }
    public void addDotsIndicator(int position){
        dots = new TextView[3];
        linearLayout.removeAllViews();

        for(int i = 0; i< dots.length;i++){
            dots[i] = new TextView(getContext());
            dots[i].setText((Html.fromHtml("&#8226")));
            dots[i].setTextSize(35);
            dots[i].setTextColor(getResources().getColor(R.color.shadow));
            linearLayout.addView(dots[i]);
        }
        if(dots.length >0){
            dots[position].setTextColor(getResources().getColor(R.color.white));
        }
    }

    ViewPager.OnPageChangeListener viewlistener = new ViewPager.OnPageChangeListener() {
        @Override
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

        }

        @Override
        public void onPageSelected(int position) {
            addDotsIndicator(position);
        }

        @Override
        public void onPageScrollStateChanged(int state) {

        }
    };

    private class SliderTimer extends TimerTask{


        @Override
        public void run() {
            try {
                Objects.requireNonNull(getActivity()).runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Log.i("Timer slideWelcome","iniciado");
                        if (viewpager.getCurrentItem() < dots.length - 1) {
                            viewpager.setCurrentItem(viewpager.getCurrentItem() + 1);
                        } else {
                            viewpager.setCurrentItem(0);
                        }
                    }
                });
            }
            catch (Exception ignored){

            }

        }
    }

}