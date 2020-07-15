package com.simplelifestudio.cocinando.fragment;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.simplelifestudio.cocinando.R;
import com.simplelifestudio.cocinando.controller.DebugLogin;

import java.util.Objects;

public class Login extends Fragment implements View.OnClickListener {

    private FirebaseAuth mAuth;
    private Button loginButton;
    private EditText email;
    private EditText pass;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_login, container, false);
        init(v);
        return v;
    }

    public void init(View v){
        mAuth = FirebaseAuth.getInstance();
        loginButton = v.findViewById(R.id.loginButton);
        email = v.findViewById(R.id.loginEmailET);
        pass = v.findViewById(R.id.loginPasswordET);
        loginButton.setOnClickListener(this);

        TextView term = v.findViewById(R.id.loginTermTV);
        term.setOnClickListener(this);

        TextView passReset = v.findViewById(R.id.loginPasswordResetTV);
        passReset.setOnClickListener(this);
    }

    public boolean verficationEmail(){
        boolean isEmailValid;
        if(email.getText().toString().isEmpty()){
            isEmailValid = false;
            Toast.makeText(getContext(),"Debes de llenar email primero.",Toast.LENGTH_SHORT).show();
        }
        else{
            String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";
            if (email.getText().toString().trim().matches(emailPattern)){
                isEmailValid = true;
            }
            else {
                Toast.makeText(getContext(),"email ivalido.",Toast.LENGTH_SHORT).show();
                isEmailValid = false;
            }
        }
        return isEmailValid;
    }

    public boolean verificationPassword(){
        boolean isPasswordValid;
        if(pass.getText().toString().isEmpty()){
            Toast.makeText(getContext(),"Debes de llenar contraseña primero.",Toast.LENGTH_SHORT).show();
            isPasswordValid = false;
        }

        else {
            isPasswordValid = true;
        }
        return isPasswordValid;
    }

    public void login(){
        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (verficationEmail()&&verificationPassword()) {
                    mAuth.signInWithEmailAndPassword(email.getText().toString(), pass.getText().toString())
                            .addOnCompleteListener((Activity) Objects.requireNonNull(getContext()), new OnCompleteListener<AuthResult>() {
                                @Override
                                public void onComplete(@NonNull Task<AuthResult> task) {
                                    if (task.isSuccessful()) {
                                        startActivity(new Intent(getContext(), DebugLogin.class));
                                        Objects.requireNonNull(getActivity()).finish();
                                    }
                                    else{
                                        Log.w("Firebase", "createUserWithEmail:failure", task.getException());
                                        Toast.makeText(getContext(), "error:"+ Objects.requireNonNull(task.getException()).getMessage(),
                                                Toast.LENGTH_SHORT).show();
                                    }
                                }
                            });
                }
            }});
    }

    public void termDialog(){
        TeminosCondiciones newFragment = new TeminosCondiciones();
        assert getFragmentManager() != null;
        newFragment.show(getFragmentManager(), "missiles");
    }

    public void passwordReset(){
        if(verficationEmail()) {
            mAuth.sendPasswordResetEmail(email.getText().toString()).addOnCompleteListener((Activity) Objects.requireNonNull(getContext()), new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {
                    if(task.isSuccessful()) {
                        Log.i("verf", "passwordReset succes");
                        Toast.makeText(getContext(), "Se te ha enviado un correo de recuperacion.",
                                Toast.LENGTH_SHORT).show();
                    } else {
                        Log.i("verf", "passwordReset Failed");
                        Toast.makeText(getContext(), "error:"+ Objects.requireNonNull(task.getException()).getMessage(),
                                Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }
    }

    @Override
    public void onClick(View v) {
        if(v.getId()==R.id.loginButton){
            login();
        }
        else if(v.getId()==R.id.loginTermTV){
            termDialog();
        }
        else if(v.getId()==R.id.loginPasswordResetTV){
            passwordReset();
        }
    }
}