package com.AndroKG.foodzac;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class FInalOrderPage extends AppCompatActivity {

    TextView addresOfOrder, totalAmout, delieryAmt, netTotalAmount;
    ListView orderItemList;

    LinearLayout confirmationLayout;
    ProgressBar pBar;
    TextView timerForConfirmation;

    TextView nameOfItemForCust, priceOfItemforCust, numberOfItemforCust;
    Button increase, decrease, confirmOrder;

    int totAmt = 0, timeToConfirm = 4;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_final_order_page);

        IntializeUI();
        totAmount();

        confirmOrder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                confirmingOrder();
            }
        });


        ItemToOrderCustomeAdapter item = new ItemToOrderCustomeAdapter();
        orderItemList.setAdapter(item);

    }

    public void totAmount() {
        totAmt = 0;
        for(int amt : Variable.totalpriceofOrderItem) {
            totAmt += amt;
        }
        totalAmout.setText("Rs." + String.valueOf(totAmt));
        delieryAmt.setText("Rs. " + String.valueOf(40));
        netTotalAmount.setText("Rs. " + String.valueOf(totAmt + 40));
    }

    public void IntializeUI() {
        addresOfOrder = findViewById(R.id.addressoforderinguser);
        addresOfOrder.setText(Variable.addressOfUser);
        delieryAmt = findViewById(R.id.delivertotalamoutotbepaid);
        netTotalAmount = findViewById(R.id.nettotalamoutotbepaid);
        totalAmout = findViewById(R.id.totalamoutotbepaid);
        orderItemList = findViewById(R.id.orderitemlistview);
        confirmationLayout = findViewById(R.id.orderconfirmationlayout);
        pBar = findViewById(R.id.progressbarconfirminorder);
        timerForConfirmation = findViewById(R.id.timeinconfirmation);
        confirmOrder = findViewById(R.id.orderconfirmationbutton);


        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            NotificationManager mNotificationManager =
                    (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
            int importance = NotificationManager.IMPORTANCE_HIGH;
            NotificationChannel mChannel = new NotificationChannel(Constants.CHANNEL_ID, Constants.CHANNEL_NAME, importance);
            mChannel.setDescription(Constants.CHANNEL_DESCRIPTION);
            mChannel.enableLights(true);
            mChannel.setLightColor(Color.RED);
            mChannel.enableVibration(true);
            mChannel.setVibrationPattern(new long[]{100, 200, 300, 400, 500, 400, 300, 200, 400});
            mNotificationManager.createNotificationChannel(mChannel);
        }
    }

    public void confirmingOrder() {
        confirmationLayout.setVisibility(View.VISIBLE);
        confirmationLayout.animate().scaleX(1.10f);
        pBar.bringToFront();
        Handler();

    }

    public class ItemToOrderCustomeAdapter extends BaseAdapter {
        @Override
        public int getCount() {
            return Variable.numberofOrderItem.size();
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
            convertView = getLayoutInflater().inflate(R.layout.orderitemcustomelistview, null);

            nameOfItemForCust = convertView.findViewById(R.id.nameofitemtobeorder);
            priceOfItemforCust = convertView.findViewById(R.id.priceofitemtobeorder);
            numberOfItemforCust = convertView.findViewById(R.id.numberofperitemstoorder);
            increase = convertView.findViewById(R.id.incresenumberofitems);
            decrease = convertView.findViewById(R.id.decresenumberofitems);

            nameOfItemForCust.setText(Variable.itemToOrder.get(position));
            priceOfItemforCust.setText("Rs." + String.valueOf(Variable.priceofOrderItem.get(position)));
            numberOfItemforCust.setText(String.valueOf(Variable.numberofOrderItem.get(position)));

            increase.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Variable.numberofOrderItem.set(position, Variable.numberofOrderItem.get(position) + 1);
                    Variable.totalpriceofOrderItem.set(position, Variable.priceofOrderItem.get(position)*Variable.numberofOrderItem.get(position));
                    totAmount();
                    notifyDataSetChanged();
                }
            });

            decrease.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(Variable.numberofOrderItem.get(position)>1) {
                        Variable.numberofOrderItem.set(position, Variable.numberofOrderItem.get(position) - 1);
                        Variable.totalpriceofOrderItem.set(position, Variable.priceofOrderItem.get(position)*Variable.numberofOrderItem.get(position));
                        totAmount();
                        notifyDataSetChanged();
                    }
                }
            });

            return convertView;

        }
    }

    public void Handler() {

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                if(timeToConfirm==0) {
                    confirmationLayout.setVisibility(View.INVISIBLE);
                    new AlertDialog.Builder(FInalOrderPage.this)
                            .setTitle("Your Order is Confirmed!")
                            .setMessage("Your order will be at your Door in Next 30min.")
                            .setIcon(R.drawable.ic_shopping_cart_black_24dp)
                            .setPositiveButton("Done", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    MyNotificationManager.getInstance(FInalOrderPage.this).displayNotification("FoodZac - Food Order", "Your Order With " + Variable.restaurantName +
                                            " is Placed. We will Soon to you.");
                                    Variable.totalpriceofOrderItem.clear();
                                    Variable.numberofOrderItem.clear();
                                    Variable.priceofOrderItem.clear();
                                    Variable.itemToOrder.clear();
                                    Variable.orderItem = "";
                                    startActivity(new Intent(FInalOrderPage.this, HomePage.class));
                                    finish();
                                }
                            })
                            .show();
                } else {
                    Handler();
                }

                if(timeToConfirm==4) {
                    dataIntoFirebase();
                }
                timerForConfirmation.setText(String.valueOf(timeToConfirm--));
            }
        }, 1000);
    }


    public void dataIntoFirebase() {
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("PastOrder").child(Variable.currentUserId);
        SimpleDateFormat sd = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault());
        String currentDate = sd.format(new Date());
        SimpleDateFormat sdf = new SimpleDateFormat("hh:mm aa", Locale.getDefault());
        String currentTime = sdf.format(new Date());

        OrderDetail detail = new OrderDetail(currentDate, currentTime, String.valueOf(totAmt+40), Variable.itemToData, Variable.priceToData, "40", "0", "delivered", Variable.restaurantName);

        String key = reference.push().getKey();
        reference.child(key).setValue(detail);

    }

    public class Constants {
        public static final String CHANNEL_ID = "my_channel_01";
        public static final String CHANNEL_NAME = "Coding Notification";
        public static final String CHANNEL_DESCRIPTION = "www.ardent.net";
    }

}
