package com.simplelifestudio.cocinando.fragment;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.simplelifestudio.cocinando.R;
import com.simplelifestudio.cocinando.controller.DebugLogin;
import com.simplelifestudio.cocinando.model.User;
import java.util.Objects;


public class TeminosCondiciones extends DialogFragment {
    private User newUser;
    private Context mContext;
    private Activity mActivity;

    public TeminosCondiciones() {
    }

    public TeminosCondiciones(User newUser, Context mContext, Activity mActivity) {
        this.newUser = newUser;
        this.mContext = mContext;
        this.mActivity = mActivity;
    }


    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = Objects.requireNonNull(getActivity()).getLayoutInflater();
        View v = inflater.inflate(R.layout.fragment_teminos_condiciones, null);
        CheckBox accept = v.findViewById(R.id.terminosAcceptarCheck);
        builder.setView(v).setTitle("Terminos y condiciones").setIcon(R.drawable.ic_launcher_foreground);
        if(newUser != null) {
            builder.setPositiveButton("continuar", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    newUser.setAcceptaTerminos(true);
                    FirebaseFirestore.getInstance().collection("users").document(newUser.getUserId())
                            .addSnapshotListener(new EventListener<DocumentSnapshot>() {
                                @Override
                                public void onEvent(@javax.annotation.Nullable DocumentSnapshot documentSnapshot, @javax.annotation.Nullable FirebaseFirestoreException e) {
                                    assert documentSnapshot != null;
                                    if(documentSnapshot.exists()){
                                        mContext.startActivity(new Intent(mContext, DebugLogin.class));
                                        mActivity.finish();
                                    }
                                    else {
                                        FirebaseFirestore.getInstance().collection("users")
                                                .document(newUser.getUserId()).set(newUser).addOnSuccessListener(new OnSuccessListener<Void>() {
                                            @Override
                                            public void onSuccess(Void aVoid) {
                                                mContext.startActivity(new Intent(mContext, DebugLogin.class));
                                                mActivity.finish();

                                            }
                                        }).addOnFailureListener(new OnFailureListener() {
                                            @Override
                                            public void onFailure(@NonNull Exception e) {
                                                cancelRegistration();
                                            }
                                        });
                                    }
                                }
                            });
                }
            }).setNegativeButton("cancelar", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            cancelRegistration();
                        }
                    });
            final AlertDialog dialog = builder.create();
            accept.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    if (isChecked) {
                        dialog.getButton(AlertDialog.BUTTON_POSITIVE).setEnabled(true);
                    } else {
                        dialog.getButton(AlertDialog.BUTTON_POSITIVE).setEnabled(false);
                    }
                }
            });
            dialog.show();
            dialog.getButton(AlertDialog.BUTTON_POSITIVE).setEnabled(false);
            return dialog;
        }
        else{
            accept.setVisibility(View.GONE);
            builder.setPositiveButton("ok",null);
            final AlertDialog dialog = builder.create();
            dialog.show();
            return dialog;
        }

    }

    public void cancelRegistration(){
        FirebaseAuth user = FirebaseAuth.getInstance();
        Objects.requireNonNull(user.getCurrentUser()).delete();
        user.signOut();
    }
}
