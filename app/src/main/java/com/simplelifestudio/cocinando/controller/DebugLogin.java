package com.simplelifestudio.cocinando.controller;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.simplelifestudio.cocinando.R;

public class DebugLogin extends AppCompatActivity {
    private TextView bienvenida;
    private TextView userName;
    private TextView userEmail;
    private ImageView userImg;
    private FirebaseUser mUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_debug_login);
        init();
    }

    public void init(){
        bienvenida = findViewById(R.id.debugBienvenidaTV);
        userName = findViewById(R.id.debugNameTV);
        userEmail = findViewById(R.id.debugEmailTV);
        userImg = findViewById(R.id.debugImageIV);


        mUser = FirebaseAuth.getInstance().getCurrentUser();

        bienvenida.setText(mUser.getEmail());
        userImg.setImageURI(mUser.getPhotoUrl());
    }
}