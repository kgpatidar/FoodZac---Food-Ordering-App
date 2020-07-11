package com.AndroKG.foodzac;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.concurrent.Executor;

public class LoginSignup extends AppCompatActivity {

    LinearLayout loginLayout, signupLayout;

    //***** Signup Variables
    EditText fullname, username, password, location, phone;
    RadioButton male, female;
    Button signup;

    boolean isRegistered = false;

    FirebaseAuth mAuth;
    //***** Login Variables
    EditText usernameLogin, passwordLogin;
    Button login;

    DatabaseReference reference;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_signup);

        //******* Intialize interface Variable *****************
        intialierToInterface();
        usernameLogin.setText("k@gmail.com");
        passwordLogin.setText("123456");
        //******** For Changing Layout *************************
        loginandSignup();

        male.isChecked();
        //************ On Clicking Signup Button ********************************
        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                signingUpNewUser();
            }
        });


        //*************** On Clicking Login Button
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LoginMethod();
            }
        });


    }


    //************* Initializing variable to Inteface
    public void intialierToInterface() {
        //********** Signup Variable Interface
        fullname = findViewById(R.id.fullnameinsignup);
        username = findViewById(R.id.usernameinsignup);
        password = findViewById(R.id.passwordinsignup);
        location = findViewById(R.id.locationinsignup);
        male = findViewById(R.id.malecheckbox);
        female = findViewById(R.id.femalecheckbox);
        signup = findViewById(R.id.signupbutton);
        phone = findViewById(R.id.phoneinsignup);

        //*********** Login Variables
        usernameLogin = findViewById(R.id.usernameinlogin);
        passwordLogin = findViewById(R.id.passwordinlogin);
        login = findViewById(R.id.loginbutton);

        //******** Layout variable interface
        loginLayout = findViewById(R.id.loginlayout);
        signupLayout = findViewById(R.id.signuplayout);
        loginLayout.animate().rotationY(-90);
    }

    //************** Changin Layout to Login and Signup
    public void loginandSignup() {

        //already memvber click to signup
        findViewById(R.id.alreadymemberintent).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                signupLayout.animate().rotationY(90);
                loginLayout.animate().rotationY(0);
                loginLayout.setVisibility(View.VISIBLE);
                signupLayout.setVisibility(View.INVISIBLE);
            }
        });


        //not yet member click to Signup
        findViewById(R.id.notyetmemeberintent).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                signupLayout.animate().rotationY(0);
                loginLayout.animate().rotationY(-90);
                loginLayout.setVisibility(View.INVISIBLE);
                signupLayout.setVisibility(View.VISIBLE);
            }
        });

    }

    //*************** On Clicking Signup Button for new User****************
    public void signingUpNewUser() {

        final ProgressBar progressBar = new ProgressBar(LoginSignup.this);
        progressBar.setVisibility(View.VISIBLE);

        String fNamel = fullname.getText().toString();
        String uName = username.getText().toString();
        String pass = password.getText().toString();
        String loc = location.getText().toString();
        String phn = phone.getText().toString();
        String gndr = female.isChecked() ? "Female" : "Male";

        if (!fNamel.isEmpty() && !uName.isEmpty() && !pass.isEmpty() && !loc.isEmpty() && !phn.isEmpty() && !gndr.isEmpty()) {

            SignupUser signupUser = new SignupUser(
                    LoginSignup.this, fNamel, uName, pass, loc, phn, gndr
                    );
            signupUser.RegisterUser(signupUser);
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    // This method will be executed once the timer is over
                    progressBar.setVisibility(View.INVISIBLE);
                    signupLayout.animate().rotationY(90);
                    loginLayout.animate().rotationY(0);
                    loginLayout.setVisibility(View.VISIBLE);
                    signupLayout.setVisibility(View.INVISIBLE);
                    fullname.setText("");
                    username.setText("");
                    password.setText("");
                    location.setText("");
                    phone.setText("");
                    female.setChecked(false);
                    male.isChecked();
                }
            }, 1500);

        } else {
            Toast.makeText(getApplicationContext(), "Empty Fields", Toast.LENGTH_SHORT).show();
        }


    }

    //************** On Clicking Login Button fot User
    public void loginUpExistingUser() {
        if (true) {
            Intent intent = new Intent(LoginSignup.this, HomePage.class);
            startActivity(intent);
            finish();
        }
    }

    public void LoginMethod() {

        login.setText("Wait a Second");
        login.setEnabled(false);
        mAuth = FirebaseAuth.getInstance();
        String email = usernameLogin.getText().toString();
        String password = passwordLogin.getText().toString();

        if (!email.isEmpty() && !password.isEmpty()) {
            mAuth.signInWithEmailAndPassword(email, password)
                    .addOnCompleteListener(LoginSignup.this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                // Sign in success, update UI with the signed-in user's information
                                Variable.currentUserId = mAuth.getUid();
                                loginUpExistingUser();
                            }

                            // ...
                        }
                    })
                    .addOnFailureListener(LoginSignup.this, new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Toast.makeText(getApplicationContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
                            login.setText("Login");
                            login.setEnabled(true);
                        }
                    });
        } else {
            Toast.makeText(LoginSignup.this, "All Field Required", Toast.LENGTH_SHORT).show();
            login.setText("Login");
            login.setEnabled(true);
        }
    }


}
