package com.AndroKG.foodzac;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.coordinatorlayout.widget.CoordinatorLayout;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class RestuarantPage extends AppCompatActivity {

    TextView nameOfRestaurant, descOfRestaurant, timeofRestaurant;
    ListView itemList;

    CoordinatorLayout coordinatorLayout;

    TextView nameOfItem, priceOfItem;
    Button addItem;

    ArrayList<String> nameOfItemList, priceList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_restuarant_page);

        intializer();

        RestuarantData();


    }

    public void intializer() {
        nameOfRestaurant = findViewById(R.id.nameofrestaurantinrestaurant);
        descOfRestaurant = findViewById(R.id.descofrestaurantinrestaurant);
        timeofRestaurant = findViewById(R.id.timeofrestaurantinrestaurant);
        itemList = findViewById(R.id.menuofrestaurantlistview);

        nameOfItemList = new ArrayList<String>();
        priceList = new ArrayList<String>();

        coordinatorLayout = findViewById(R.id.coordinatorlayoutforadded);

        nameOfRestaurant.setText(Variable.restaurantName);
        descOfRestaurant.setText(Variable.restaurantDesc);
        timeofRestaurant.setText(Variable.restaurantTime);
    }

    public void RestuarantData() {
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("RestaurantMenu").child(Variable.restaurantId);
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    Price price = snapshot.getValue(Price.class);
                    nameOfItemList.add(price.getNameOfItem());
                    priceList.add(price.getPrice());
                }

                ItemCustomeAdapter itemCustomeAdapter = new ItemCustomeAdapter();
                itemList.setAdapter(itemCustomeAdapter);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }

    public class ItemCustomeAdapter extends BaseAdapter {

        @Override
        public int getCount() {
            return priceList.size();
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
        public View getView(final int position, View convertView, ViewGroup parent) {
            convertView = getLayoutInflater().inflate(R.layout.menuitemcustomelayout, null);

            nameOfItem = convertView.findViewById(R.id.nameofiteminmenu);
            priceOfItem = convertView.findViewById(R.id.costofitemtextview);
            addItem = convertView.findViewById(R.id.itemaddingbutton);

            nameOfItem.setText(nameOfItemList.get(position));
            priceOfItem.setText(priceList.get(position));

            addItem.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    coordinatorLayout.setVisibility(View.VISIBLE);

                    if (!Variable.itemToOrder.contains(nameOfItemList.get(position))) {
                        Variable.orderItem += nameOfItemList.get(position) + ", ";
                        Variable.itemToData += nameOfItemList.get(position) + ":";
                        Variable.priceToData += String.valueOf(priceList.get(position)) + " ";
                        Variable.itemToOrder.add(nameOfItemList.get(position));
                        Variable.numberofOrderItem.add(1);
                        Variable.totalpriceofOrderItem.add(Integer.parseInt(priceList.get(position)));
                        Variable.priceofOrderItem.add(Integer.parseInt(priceList.get(position)));
                    }

                    Snackbar snackbar = Snackbar
                            .make(coordinatorLayout, Variable.orderItem, Snackbar.LENGTH_LONG)
                            .setAction("View", new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {
                                    startActivity(new Intent(RestuarantPage.this, FInalOrderPage.class));
                                }
                            });

                    snackbar.show();

                }
            });

            return convertView;
        }
    }
}
