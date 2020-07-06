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
import com.facebook.login.LoginManager;
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
import com.simplelifestudio.cocinando.controller.DebugLogin;

import java.util.concurrent.Executor;


public class FacebookLogin extends Fragment {
private FirebaseAuth mAuth;
private CallbackManager callbackManager;
private LoginButton loginButton;

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
        mAuth = FirebaseAuth.getInstance();
        callbackManager = CallbackManager.Factory.create();
    }


    public void initOnCreateView(View v){
         loginButton = v.findViewById(R.id.facebookLoginButton);
        loginButton.setReadPermissions("email", "public_profile");
        loginButton.setFragment(this);
        setLoginButton();
    }

    public void loginFacebook(){
        Log.i("verf", "paso1");
      callbackManager = CallbackManager.Factory.create();
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
              Log.i("verf", "error facebook login:"+error);
          }
      });
        Log.i("verf", "paso1 finalizado");
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        callbackManager.onActivityResult(requestCode, resultCode, data);
    }



    private void handleFacebookAccessToken(AccessToken token) {
        Log.i("verf", "paso2");

        AuthCredential credential = FacebookAuthProvider.getCredential(token.getToken());
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener((Activity) getContext(), new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            Log.i("verf", " firebase sucess");
                            FirebaseUser user = mAuth.getCurrentUser();
                            //updateUI(user);
                            startActivity(new Intent(getContext(), DebugLogin.class));
                            getActivity().finish();
                        } else {

                            Log.i("verf", "fail firebase ");
                            Toast.makeText(getContext(), "Authentication failed.",
                                    Toast.LENGTH_SHORT).show();
                            //updateUI(null);
                        }

                        // ...
                    }
                });
    }

    public void setLoginButton(){
        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loginFacebook();
            }
        });
    }
}
