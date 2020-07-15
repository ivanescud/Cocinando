package com.simplelifestudio.cocinando.fragment;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;
import com.simplelifestudio.cocinando.R;
import com.simplelifestudio.cocinando.model.User;

import java.util.Date;
import java.util.Objects;

public class GoogleLogin extends Fragment implements View.OnClickListener {
    private static final int RC_SIGN_IN = 9001;
    private Fragment parentFragment;
    private SignInButton signInButton;
    private  Button signInbutton1;
    private  Button signInbutton2;
    private int signInbuttonStyleId;
    private GoogleSignInClient mGoogleSignInClient;
    private FirebaseAuth mAuth;

    @Override
    public void onCreate (Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id)).requestEmail().build();
        mGoogleSignInClient = GoogleSignIn.getClient(Objects.requireNonNull(getContext()), gso);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_google_login, container, false);
        init(v);
        return v;
    }

    public void init(View v){
        parentFragment = this.getParentFragment();
        signInButton = v.findViewById(R.id.googleLoginButton0);
        signInbutton1 = v.findViewById(R.id.googleLoginButton1);
        signInbutton2 = v.findViewById(R.id.googleLoginButton2);
        signInButton.setOnClickListener(this);
        signInbutton1.setOnClickListener(this);
        signInbutton2.setOnClickListener(this);
        styleLoginButton();
        mAuth = FirebaseAuth.getInstance();
    }

    public void styleLoginButton(){
        try {
            if(parentFragment.getClass().getSimpleName().equals("Login")){
                signInbutton2.setVisibility(View.VISIBLE);
                signInbuttonStyleId = R.id.googleLoginButton2;
            }
            else if(parentFragment.getClass().getSimpleName().equals("Registry")){
                signInbutton1.setVisibility(View.VISIBLE);
                signInbuttonStyleId = R.id.googleLoginButton1;
            }
            else{
                signInButton.setVisibility(View.VISIBLE);
                signInbuttonStyleId = R.id.googleLoginButton0;
            }
        }catch (Exception err){
            signInButton.setVisibility(View.VISIBLE);
            signInbuttonStyleId = R.id.googleLoginButton0;
        }

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == RC_SIGN_IN) {
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            handleSignInResult(task);
        }
    }

    private void handleSignInResult(Task<GoogleSignInAccount> completedTask) {
        try {
            Log.i("verf", "obtuvo token");
            GoogleSignInAccount account = completedTask.getResult(ApiException.class);
            assert account != null;
            firebaseAuthWithGoogle(account.getIdToken());
        } catch (ApiException e) {
            Log.i("verf", "signInResult:failed code=" + e.getStatusCode());
        }
    }

    private void firebaseAuthWithGoogle(String idToken) {
        AuthCredential credential = GoogleAuthProvider.getCredential(idToken, null);
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
        if(v.getId() == signInbuttonStyleId){
            Intent signInIntent = mGoogleSignInClient.getSignInIntent();
            startActivityForResult(signInIntent,RC_SIGN_IN);
        }
    }
}