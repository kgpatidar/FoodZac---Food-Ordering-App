package com.AndroKG.foodzac;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class PastOrderClass extends AppCompatActivity {

    ListView pastOrderlist;

    ArrayList<String> datelist, timelist, totalCostlist, status, itemslist, pricelist, restaurantlist;

    TextView itemList;

    TextView numberoforder, statusoforder, restaurantoforder, dateoforder, timeoforder, totolamountoforder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_past_order_class);

        pastOrderlist = findViewById(R.id.pastorderlistview);
        intialize();

        insetDataintoCustome();


    }

    public void intialize() {
        datelist = new ArrayList<String>();
        timelist = new ArrayList<String>();
        totalCostlist = new ArrayList<String>();
        status = new ArrayList<String>();
        itemslist = new ArrayList<String>();
        pricelist = new ArrayList<String>();
        restaurantlist = new ArrayList<String>();
    }

    public void insetDataintoCustome() {
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("PastOrder").child(Variable.currentUserId);
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    OrderDetail detail = snapshot.getValue(OrderDetail.class);
                    datelist.add(detail.getOrderDate());
                    timelist.add(detail.getOrderTime());
                    restaurantlist.add(detail.getRestaurantName());
                    totalCostlist.add(detail.getTotalAmount());
                    status.add(detail.getStatus());
                    itemslist.add(detail.getOrderItems());
                    pricelist.add(detail.getOrderPrice());
                }
                CustomeListAdapter customeListAdapter = new CustomeListAdapter();
                pastOrderlist.setAdapter(customeListAdapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }


    public class CustomeListAdapter extends BaseAdapter {

        @Override
        public int getCount() {
            return pricelist.size();
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
            convertView = getLayoutInflater().inflate(R.layout.pastordercustomelayout, null);

            totolamountoforder = convertView.findViewById(R.id.nettotalamoutotbepaidinpastorder);
            numberoforder = convertView.findViewById(R.id.numberoforderinpastorder);
            dateoforder = convertView.findViewById(R.id.dateoforderinpastorder);
            timeoforder = convertView.findViewById(R.id.timeoforderinpastorder);
            statusoforder = convertView.findViewById(R.id.statusoforderinpastorder);
            restaurantoforder = convertView.findViewById(R.id.nameofrestaurantinpastorder);

            totolamountoforder.setText("Rs." + totalCostlist.get(position));
            numberoforder.setText(String.valueOf(position+1));
            dateoforder.setText(datelist.get(position));
            timeoforder.setText(timelist.get(position));
            statusoforder.setText(status.get(position));
            restaurantoforder.setText(restaurantlist.get(position));

            String[] itemsArray = itemslist.get(position).split(":");
            String[] priceArray = pricelist.get(position).split(" ");
            itemList = convertView.findViewById(R.id.orderiteminpastorder);

            for (int i=0;i<itemsArray.length;i++) {
                itemList.append(itemsArray[i] + "\t\tRs." + priceArray[i] + "\n");
            }

            return convertView;
        }
    }

}
