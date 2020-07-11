package com.AndroKG.foodzac;

import android.app.Activity;
import android.content.Context;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.FirebaseDatabase;

public class LoginUser {

    String email, password;
    Context context;
    FirebaseAuth mAuth;

    LoginUser(Context context,String email, String password) {
        this.password = password;
        this.email = email;
        this.context = context;
    }

    public void LoginMethodOfClass() {

    }


}