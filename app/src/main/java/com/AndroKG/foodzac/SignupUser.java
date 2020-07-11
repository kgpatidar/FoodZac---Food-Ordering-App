package com.AndroKG.foodzac;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.concurrent.Executor;

public class SignupUser {

    LinearLayout signupLayout, loginLayout;

    private String fullname, username, password, location, gender, phone;
    Context context;
    View view;
    private FirebaseAuth mAuth;
    DatabaseReference reference;

    public SignupUser() {
    }

    public SignupUser(Context context, String fullname, String username, String password, String location, String phone, String gender) {
        this.context = context;
        this.fullname = fullname;
        this.username = username;
        this.password = password;
        this.location = location;
        this.gender = gender;
        this.phone = phone;
        view = LayoutInflater.from(context).inflate(R.layout.activity_login_signup, null, false);
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getFullname() {
        return fullname;
    }

    public void setFullname(String fullname) {
        this.fullname = fullname;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public void RegisterUser(final SignupUser signupUser) {

        loginLayout = view.findViewById(R.id.loginlayout);
        signupLayout = view.findViewById(R.id.signuplayout);
        mAuth = FirebaseAuth.getInstance();
        mAuth.createUserWithEmailAndPassword(username, password)
                .addOnCompleteListener((Activity) context, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            Variable.currentUserId = mAuth.getUid();
                            UploadData(signupUser);
                            new AlertDialog.Builder(context)
                                    .setTitle("Congratulation! You Are Registerd.")
                                    .setIcon(R.drawable.ic_check_circle_black_24dp)
                                    .setPositiveButton("Done", new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {
                                          //=================
                                        }
                                    })
                                    .show();
                        } else {
                            new AlertDialog.Builder(context)
                                    .setTitle("Oops! Not Registered.")
                                    .setMessage("You may already registered or Internet Problem")
                                    .setIcon(R.drawable.ic_info_black_24dp)
                                    .setPositiveButton("Done", new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {

                                        }
                                    })
                                    .show();
                        }

                    }

                });


    }

    public void UploadData(SignupUser signupUser) {
        reference = FirebaseDatabase.getInstance().getReference("Users");
        reference.child(Variable.currentUserId).setValue(signupUser);
    }

}
