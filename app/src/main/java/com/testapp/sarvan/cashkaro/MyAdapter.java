package com.testapp.sarvan.cashkaro;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.support.v4.app.NotificationCompat;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.testapp.sarvan.cashkaro.model.Coupons;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by sarva on 23-08-2017.
 */

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.ViewHolder> {
    private List<Coupons> couponList = new ArrayList<Coupons>();

    public MyAdapter(List<Coupons> couponList1) {
        couponList.addAll(couponList1);
    }

    @Override
    public MyAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.deal_cardview, parent, false);
        ViewHolder vh = new ViewHolder(view);
        return vh;
    }

    @Override
    public void onBindViewHolder(MyAdapter.ViewHolder holder, int position) {
        /*
            This will be obtained from the cash karo web services dynamically
            and displayed accordingly
            */
        holder.textViewDealName.setText(couponList.get(position).getName());
        holder.textViewDealValueValue.setText(couponList.get(position).getValue());
        holder.textViewCashback.setText(couponList.get(position).getCashback());
        holder.textViewValidity.setText(couponList.get(position).getValidity());
        holder.imageView.setImageResource(couponList.get(position).getDrawable());
    }

    public void showNotification(Context ctx, CharSequence text) {
        Intent intent = new Intent();
        PendingIntent contentIntent = PendingIntent.getActivity(ctx, 0, intent,
                PendingIntent.FLAG_UPDATE_CURRENT);

        NotificationCompat.Builder b = new NotificationCompat.Builder(ctx);

        b.setAutoCancel(true)
                .setDefaults(Notification.DEFAULT_ALL)
                .setWhen(System.currentTimeMillis())
                .setSmallIcon(R.mipmap.ic_launcher)
                .setTicker(ctx.getString(R.string.app_name))
                .setContentTitle("Coupon Applied")
                .setContentText("Congratulations you have clicked on " + text + "!")
                .setContentIntent(contentIntent)
                .setContentInfo("Info");

        NotificationManager notificationManager =
                (NotificationManager) ctx.getSystemService(Context.NOTIFICATION_SERVICE);
        notificationManager.notify(1, b.build());
    }


    @Override
    public int getItemCount() {
        return couponList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private Button checkOut;
        private CardView cardView;
        private ImageView imageView;
        private TextView textViewDealName, textViewDealValueValue, textViewCashback, textViewValidity;


        public ViewHolder(View v) {
            super(v);
            cardView = v.findViewById(R.id.card_view);
            imageView = v.findViewById(R.id.dealImageDetail);
            textViewDealName = v.findViewById(R.id.dealNameDetail);
            textViewDealValueValue = v.findViewById(R.id.dealDetailValueValue);
            textViewCashback = v.findViewById(R.id.dealDetailCBValue);
            textViewValidity = v.findViewById(R.id.dealDetailValidityValue);
            cardView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    System.out.println("Clicked");
                }
            });
            checkOut = v.findViewById(R.id.btnCheckout);
            checkOut.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    System.out.println("Clicked: " + ((Button) view).getText());
                    showNotification(checkOut.getContext(), textViewDealName.getText());
                }
            });
        }
    }
}