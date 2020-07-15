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
import android.widget.Toast;

import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FacebookAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.simplelifestudio.cocinando.R;
import com.simplelifestudio.cocinando.model.User;

import java.util.Date;
import java.util.Objects;


public class FacebookLogin extends Fragment implements View.OnClickListener {
private FirebaseAuth mAuth;
private CallbackManager callbackManager;
private LoginButton loginButton;
private Button loginButton1;
private Button loginButton2;
private int styleLoginId;
private Fragment parentFragment;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_facebook_login, container, false);
        initOnCreateView(v);
        return v;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }


    public void initOnCreateView(View v){
         parentFragment = this.getParentFragment();
         loginButton = v.findViewById(R.id.facebookLoginButton0);
         loginButton1 = v.findViewById(R.id.facebookLoginButton1);
         loginButton2 = v.findViewById(R.id.facebookLoginButton2);
         loginButton1.setOnClickListener(this);
         loginButton2.setOnClickListener(this);
         loginButton.setReadPermissions("email", "public_profile");
         loginButton.setFragment(this);
         styleLoginButton();

        mAuth = FirebaseAuth.getInstance();
        callbackManager = CallbackManager.Factory.create();

        //facebook manager
        loginButton.registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {
                Log.i("verf", "start firebase handler");
                handleFacebookAccessToken(loginResult.getAccessToken());
            }

            @Override
            public void onCancel() {
                Log.i("verf", "stop facebook login");
            }

            @Override
            public void onError(FacebookException error) {
                Log.i("verf", "error facebook login:"+error.getMessage());
            }
        });
    }

    public void styleLoginButton(){
        try {
            if(parentFragment.getClass().getSimpleName().equals("Login")){
                loginButton2.setVisibility(View.VISIBLE);
                styleLoginId = R.id.facebookLoginButton2;
            }
            else if(parentFragment.getClass().getSimpleName().equals("Registry")){
                loginButton1.setVisibility(View.VISIBLE);
                styleLoginId = R.id.facebookLoginButton1;
            }
            else {
                loginButton.setVisibility(View.VISIBLE);
            }
        }catch (Exception err){
            loginButton.setVisibility(View.VISIBLE);
        }
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        callbackManager.onActivityResult(requestCode, resultCode, data);
    }



    private void handleFacebookAccessToken(AccessToken token) {
        AuthCredential credential = FacebookAuthProvider.getCredential(token.getToken());
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener((Activity) Objects.requireNonNull(getContext()), new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            FirebaseUser user = mAuth.getCurrentUser();
                            User newUser = new User();
                            assert user != null;
                            newUser.setName(user.getDisplayName());
                            newUser.setEmail(user.getEmail());
                            newUser.setUserId(user.getUid());
                            newUser.setUserImg(Objects.requireNonNull(user.getPhotoUrl()).toString());
                            newUser.setRango("cocinero novato");
                            newUser.setPremiun(false);
                            newUser.setFechaCreacion(new Date());
                            TeminosCondiciones teminosCondiciones = new TeminosCondiciones(newUser,getContext(),getActivity());
                            assert getFragmentManager() != null;
                            teminosCondiciones.show(getFragmentManager(), "Terminos");
                        } else {
                            Log.w("Firebase", "createUserWithEmail:failure", task.getException());
                            Toast.makeText(getContext(), "error:"+ Objects.requireNonNull(task.getException()).getMessage(),
                                    Toast.LENGTH_SHORT).show();
                        }

                    }
                });
    }

    @Override
    public void onClick(View v) {
        if(v.getId()==styleLoginId){
            loginButton.performClick();
        }
    }
}

