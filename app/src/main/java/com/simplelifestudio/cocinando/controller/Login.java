package com.simplelifestudio.cocinando.controller;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;
import android.view.View;

import com.simplelifestudio.cocinando.R;
import com.simplelifestudio.cocinando.fragment.Registry;
import com.simplelifestudio.cocinando.fragment.Welcome;

public class Login extends AppCompatActivity implements View.OnClickListener{
    private FragmentTransaction fragmentTransaction;
    private Fragment welcome,login,register;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        init();
    }

    public void init(){
        welcome = new Welcome();
         login = new com.simplelifestudio.cocinando.fragment.Login();
         register = new Registry();
        getSupportFragmentManager().beginTransaction().add(R.id.loginFL,welcome).commit();
    }

    private void transitionManager(View v){
        fragmentTransaction = getSupportFragmentManager().beginTransaction();
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
        fragmentTransaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();

    }



    @Override
    public void onClick(View v) {
          transitionManager(v);
    }
}