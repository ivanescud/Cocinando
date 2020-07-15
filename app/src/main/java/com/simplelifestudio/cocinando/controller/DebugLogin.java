package com.simplelifestudio.cocinando.controller;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.simplelifestudio.cocinando.R;
import com.squareup.picasso.Picasso;

import java.util.Objects;

public class DebugLogin extends AppCompatActivity {
private Button logoutBt;
private FirebaseUser mUser = FirebaseAuth.getInstance().getCurrentUser();
private FirebaseFirestore db = FirebaseFirestore.getInstance();
private TextView datosUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_debug_login);
        init();
        logout();
    }

    public void init(){
        TextView userName = findViewById(R.id.debugNameTV);
        ImageView userImg = findViewById(R.id.debugImageIV);
        logoutBt = findViewById(R.id.debugLogoutBT);
        datosUser = findViewById(R.id.debugDatosUserTV);
        Picasso.get().load(mUser.getPhotoUrl()).into(userImg);

        assert mUser != null;
        userName.setText(mUser.getDisplayName());
        db.collection("users").document(mUser.getUid()).get()
                .addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                        if(task.isSuccessful()){
                            datosUser.setText(Objects.requireNonNull(task.getResult()).toString());
                        }
                    }
                });
    }

    public void logout(){
        logoutBt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseAuth.getInstance().signOut();
                startActivity(new Intent(getApplicationContext(),Login.class));
                finish();
            }
        });
    }
}