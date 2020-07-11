package com.AndroKG.foodzac;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class HomePage extends AppCompatActivity {

    RelativeLayout topMainLayout, searchLayout;
    AutoCompleteTextView restaturentField;
    ListView restaurentListView;
    TextView topLocation;
    ProgressBar progressBar;

    ArrayList<String> idOfRestaurant, nameOfRest, locationOfRest, typeOfRest, vegOfRest, ratingOfRest, timeOfRest, saleOfRest;
    TextView nameOfRestaurant, descrbOfRestaurant, timerestaurant, ratingOfRestaurant;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_page);

        intializerUI();
        UserInformation();


        //********* On Clicking Search Layout ****************
        findViewById(R.id.searchiconinhomepage).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SearchLayoutFuntion();
            }
        });

        //********After Getting and Seaching data in search layout **************
        findViewById(R.id.searchautotexticoninhomepage).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onGettingSearchElement(restaturentField.getText().toString());
            }
        });

        //********* On Clicking Search Layout ****************
        findViewById(R.id.crossiconinhomepage).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                UserInformation();
                MainLayoutFuntion();
                restaturentField.setText("");
            }
        });

        //******** On Clicking Profile Floating Button ***************
        findViewById(R.id.profilefloatingbutton).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(HomePage.this, ProfileDetail.class));
            }
        });

        restaurentListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                OnClickingRestaurant(position);
            }
        });

    }

    public void LocalSet() {
        //set Shared Prefrence
    }


    public void intializerUI() {
        topMainLayout = findViewById(R.id.restuarentMaintopRelativelayout);
        searchLayout = findViewById(R.id.restuarentsearchRelativelayout);
        restaturentField = findViewById(R.id.editTexttoseacrhnewrestaurent);
        restaurentListView = findViewById(R.id.restaurentListView);
        topLocation = findViewById(R.id.locationofdelievettextviewinhomepage);
        progressBar = findViewById(R.id.loaderathomepage);
        progressBar.setProgress(100);
        progressBar.setVisibility(View.VISIBLE);
        progressBar.bringToFront();


        nameOfRest = new ArrayList<String>();
        locationOfRest = new ArrayList<String>();
        typeOfRest = new ArrayList<String>();
        vegOfRest = new ArrayList<String>();
        timeOfRest = new ArrayList<String>();
        ratingOfRest = new ArrayList<String>();
        saleOfRest = new ArrayList<String>();
        idOfRestaurant = new ArrayList<String>();
    }

    public void UserInformation() {
        DatabaseReference database = FirebaseDatabase.getInstance().getReference("Users").child(Variable.currentUserId);
        database.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                SignupUser user = dataSnapshot.getValue(SignupUser.class);
                topLocation.setText(user.getLocation());
                Variable.addressOfUser = user.getLocation();
                progressBar.setVisibility(View.INVISIBLE);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("Restaurants");
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                DataListClearance();
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    Restaurant restaurant = snapshot.getValue(Restaurant.class);
                    idOfRestaurant.add(snapshot.getKey());
                    nameOfRest.add(restaurant.getName());
                    locationOfRest.add(restaurant.getLocation());
                    timeOfRest.add("Timing : " + restaurant.getOpentime() + " - " + restaurant.getClosetime());
                    typeOfRest.add(restaurant.getType());
                    vegOfRest.add(restaurant.getVegNonVeg());
                    ratingOfRest.add(restaurant.getRating());
                    saleOfRest.add(restaurant.getSellOfProduct());
                }
                CustomeRestaurantList customeRestaurantList = new CustomeRestaurantList();
                restaurentListView.setAdapter(customeRestaurantList);


                ArrayAdapter<String> adapter1 = new ArrayAdapter<String>(HomePage.this, android.R.layout.simple_list_item_1, nameOfRest);
                restaturentField.setAdapter(adapter1);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    public void SearchLayoutFuntion() {
        searchLayout.setVisibility(View.VISIBLE);
        topMainLayout.setVisibility(View.INVISIBLE);
    }

    public void MainLayoutFuntion() {
        searchLayout.setVisibility(View.INVISIBLE);
        topMainLayout.setVisibility(View.VISIBLE);
    }

    public class CustomeRestaurantList extends BaseAdapter {
        @Override
        public int getCount() {
            return nameOfRest.size();
        }

        @Override
        public Object getItem(int position) {
            return null;
        }

        @Override
        public long getItemId(int position) {
            return 0;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            convertView = getLayoutInflater().inflate(R.layout.restaurentcustomelistview, null);

            nameOfRestaurant = convertView.findViewById(R.id.hotelnameincustome);
            descrbOfRestaurant = convertView.findViewById(R.id.descriptionofhotel);
            timerestaurant = convertView.findViewById(R.id.timingofhotel);
            ratingOfRestaurant = convertView.findViewById(R.id.ratingofhotelincustome);

            nameOfRestaurant.setText(nameOfRest.get(position));
            descrbOfRestaurant.setText(vegOfRest.get(position) + " & " + typeOfRest.get(position));
            timerestaurant.setText(timeOfRest.get(position));
            ratingOfRestaurant.setText(ratingOfRest.get(position) + "(" + saleOfRest.get(position) + ")");


            return convertView;
        }
    }

    public void OnClickingRestaurant(int position) {
        Variable.restaurantName = nameOfRest.get(position);
        Variable.restaurantDesc = typeOfRest.get(position) + " & " + vegOfRest.get(position);
        Variable.restaurantTime = timeOfRest.get(position);
        Variable.restaurantId = idOfRestaurant.get(position);
        startActivity(new Intent(HomePage.this, RestuarantPage.class));
    }

    public void DataListClearance() {
        idOfRestaurant.clear();
        nameOfRest.clear();
        locationOfRest.clear();
        typeOfRest.clear();
        vegOfRest.clear();
        timeOfRest.clear();
        ratingOfRest.clear();
        saleOfRest.clear();
    }

    public void onGettingSearchElement(String nameOfSearchRest) {

        int id = nameOfRest.indexOf(nameOfSearchRest);

        if (id != -1) {
            String idOfSearch = idOfRestaurant.get(id);
            String nameOfSearch = nameOfRest.get(id);
            String locationOfSearch = locationOfRest.get(id);
            String typeOfSearch = typeOfRest.get(id);
            String vegOfSearch = vegOfRest.get(id);
            String timeOfSearch = timeOfRest.get(id);
            String ratingOfSearch = ratingOfRest.get(id);
            String saleOfSearch = saleOfRest.get(id);


            DataListClearance();

            idOfRestaurant.add(idOfSearch);
            nameOfRest.add(nameOfSearch);
            locationOfRest.add(locationOfSearch);
            timeOfRest.add(timeOfSearch);
            typeOfRest.add(typeOfSearch);
            vegOfRest.add(vegOfSearch);
            ratingOfRest.add(ratingOfSearch);
            saleOfRest.add(saleOfSearch);

            CustomeRestaurantList customeRestaurantList = new CustomeRestaurantList();
            restaurentListView.setAdapter(customeRestaurantList);
        } else {
            Toast.makeText(HomePage.this, "No Restaurant Found", Toast.LENGTH_SHORT).show();
        }

    }
}
