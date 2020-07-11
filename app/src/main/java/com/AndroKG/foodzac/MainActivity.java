package com.AndroKG.foodzac;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

//        for (int i = 0; i < 5; i++) {
//            Restaurant restaurant = new Restaurant("Jimo ji Restaurants", "Indore", "Indian-Chinese", "Pure Veg", "3.7", "9:00 Am", "11:00 PM", "100");
//            DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("Restaurants");
//            String key = databaseReference.push().getKey();
//            databaseReference.child(key).setValue(restaurant);
//        }

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                // This method will be executed once the timer is over
                Intent i = new Intent(MainActivity.this, LoginSignup.class);
                startActivity(i);
                finish();
            }
        }, 1500);


    }

//    public void menuInit() {
//        String[] keyofrest = {"-Lz2xF0xJwiSIkQW-NOe", "-Lz2xF1UgeA-7TlVdA1n", "-Lz2xF1a1bFpB1RWinU9", "-Lz2xF1gre95_oCAPNs8", "-Lz2xF1nFVmQDszfsU61"};
//        DatabaseReference  reference = FirebaseDatabase.getInstance().getReference("RestaurantMenu");
//
//
//        for(int i=0;i<5;i++) {
//            Price price = new Price("menu_price", "item_name");
//            for(int j=0;j<5;j++) {
//                String key = reference.push().getKey();
//                reference.child(keyofrest[i]).child(key).setValue(price);
//            }
//        }
//
//    }
}
