package com.simplelifestudio.cocinando.fragment;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.simplelifestudio.cocinando.R;
import com.simplelifestudio.cocinando.model.User;

import java.util.Date;
import java.util.Objects;


public class Registry extends Fragment implements View.OnClickListener{
    private FirebaseAuth mAuth;
    private EditText name;
    private EditText email;
    private EditText country;
    private EditText pass;
    private EditText repass;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mAuth = FirebaseAuth.getInstance();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_registry, container, false);
        init(view);
        return view;
    }

    public void init(View v){
        name = v.findViewById(R.id.registryNameET);
        email = v.findViewById(R.id.registryEmailET);
        country = v.findViewById(R.id.registryCountryET);
        pass = v.findViewById(R.id.registryPasswordET);
        repass = v.findViewById(R.id.registryRePasswordET);

        Button registry = v.findViewById(R.id.registryButton);
        registry.setOnClickListener(this);

    }

    public Boolean verficationEmailPassWord(){
         if(email.getText().toString().isEmpty()){
             Toast.makeText(getContext(),"Debes de llenar email primero.",Toast.LENGTH_SHORT).show();
             return false;
         }
         else{
             String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";
             if (!email.getText().toString().trim().matches(emailPattern)){
                 Toast.makeText(getContext(),"email ivalido.",Toast.LENGTH_SHORT).show();
                 return false;
             }
         }

         if(pass.getText().toString().isEmpty()){
             Toast.makeText(getContext(),"Debes de llenar contraseña primero.",Toast.LENGTH_SHORT).show();
             return false;
         }
         else if(pass.getText().toString().length() < 7){
             Toast.makeText(getContext(),"La contraseña debe ser mayor a 6 caracteres.",Toast.LENGTH_SHORT).show();
             return false;
         }
         else if(!pass.getText().toString().equals(repass.getText().toString())){
             Toast.makeText(getContext(),"Las contraseñas no conciden",Toast.LENGTH_SHORT).show();
             return false;
         }

         if(name.getText().toString().isEmpty()){
             Toast.makeText(getContext(),"Debes de llenar nombre primero.",Toast.LENGTH_SHORT).show();
             return false;
         }

        if(country.getText().toString().isEmpty()){
            Toast.makeText(getContext(),"Debes de llenar pais primero.",Toast.LENGTH_SHORT).show();
            return false;
        }
         return true;
    }

    public void createAccount(){
        if(verficationEmailPassWord()){
            mAuth.createUserWithEmailAndPassword(email.getText().toString(),pass.getText().toString())
                    .addOnCompleteListener((Activity) Objects.requireNonNull(getContext()), new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if(task.isSuccessful()) {
                                FirebaseUser user = mAuth.getCurrentUser();
                                User newUser = new User();
                                newUser.setName(name.getText().toString());
                                newUser.setEmail(email.getText().toString());
                                assert user != null;
                                newUser.setUserId(user.getUid());
                                newUser.setPais(country.getText().toString());
                                newUser.setRango("cocinero novato");
                                newUser.setPremiun(false);
                                newUser.setFechaCreacion(new Date());
                                TeminosCondiciones teminosCondiciones = new TeminosCondiciones(newUser,getContext(),getActivity());
                                assert getFragmentManager() != null;
                                teminosCondiciones.show(getFragmentManager(), "Terminos");
                            }else{
                                Log.w("Firebase", "createUserWithEmail:failure", task.getException());
                                Toast.makeText(getContext(), "error:"+ Objects.requireNonNull(task.getException()).getMessage(),
                                        Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
        }
    }

    public void buttonsListener(int btID){
        if (btID == R.id.registryButton) {
            createAccount();
        }
    }

    @Override
    public void onClick(View v) {
       buttonsListener(v.getId());
    }

}