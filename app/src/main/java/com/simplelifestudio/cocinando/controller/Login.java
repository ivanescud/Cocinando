package com.simplelifestudio.cocinando.controller;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.VideoView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.simplelifestudio.cocinando.R;
import com.simplelifestudio.cocinando.fragment.Registry;
import com.simplelifestudio.cocinando.fragment.Welcome;

public class Login extends AppCompatActivity implements View.OnClickListener{
    private Fragment login;
    private Fragment register;
    private VideoView bgVideo;
    private MediaPlayer mMediaPlayer;
    private FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        isLogin();
        init();
    }

    public void init(){
        bgVideo = findViewById(R.id.loginContainerBackGroundRL);
        Uri uri = Uri.parse("android.resource://"+getPackageName()+"/"+R.raw.food);
        bgVideo.setVideoURI(uri);
        bgVideo.start();
        bgVideo.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
            @Override
            public void onPrepared(MediaPlayer mp) {
                mMediaPlayer = mp;
                mMediaPlayer.setLooping(true);
            }
        });
        Fragment welcome = new Welcome();
         login = new com.simplelifestudio.cocinando.fragment.Login();
         register = new Registry();
        getSupportFragmentManager().beginTransaction().add(R.id.loginFL, welcome).commit();
    }

    private void transitionManager(View v){
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        switch (v.getId()){
            case R.id.welcomeIniciarBT:
                fragmentTransaction.replace(R.id.loginFL,login);
                fragmentTransaction.addToBackStack(null);
                break;
            case R.id.welcomeRegistrarBT:
                fragmentTransaction.replace(R.id.loginFL,register);
                fragmentTransaction.addToBackStack(null);
                break;
        }
        fragmentTransaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN).setCustomAnimations(R.animator.fade_in,R.animator.fade_out);
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();

    }
    public void isLogin(){
        if (user != null) {
            startActivity(new Intent(getApplicationContext(), DebugLogin.class));
            finish();}
    }

    @Override
    protected void onPause() {
        super.onPause();
        bgVideo.pause();
    }

    @Override
    protected void onPostResume() {
        super.onPostResume();
        bgVideo.start();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        bgVideo.start();
    }

    @Override
    public void onClick(View v) {
          transitionManager(v);
    }
}