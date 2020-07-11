package com.AndroKG.foodzac;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class ProfileDetail extends AppCompatActivity {

    ListView allProfileDetail;
    ArrayList<String> all;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile_detail);

        allProfileDetail = findViewById(R.id.listviewofprofiledetail);
        all =  new ArrayList<String>();

        ProfileContentButttonList();

        allProfileDetail.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                onClickingListItem(position);
            }
        });

    }

    public void ProfileContentButttonList() {
        all.add("Promotion");
        all.add("Past Orders");
        all.add("Favourite");
        all.add("Help");
        all.add("Contact Us");
        ArrayAdapter<String> past = new ArrayAdapter<String>(getApplicationContext(), android.R.layout.simple_list_item_1, all);
        allProfileDetail.setAdapter(past);
    }

    public void onClickingListItem(int position) {
        switch (position) {
            case 0:
                break;
            case 1:
                startActivity(new Intent(ProfileDetail.this, PastOrderClass.class));
                break;
            case 2:
                break;
            case 3:
                break;
            case 4:
                break;
        }
    }
}
