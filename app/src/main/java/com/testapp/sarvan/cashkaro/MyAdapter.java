package com.testapp.sarvan.cashkaro;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.media.AudioAttributes;
import android.media.RingtoneManager;
import android.os.Build;
import android.support.v7.app.NotificationCompat;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.testapp.sarvan.cashkaro.model.Coupons;
import com.testapp.sarvan.cashkaro.view.DealStorePageActivity;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by sarva on 23-08-2017.
 */

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.ViewHolder> {
    private static final int NOTIFICATION_ID = 1;
    private static final String NOTIFICATION_CHANNEL_ID = "my_notification_channel";

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
        holder.url = couponList.get(position).getUrl();
    }

    private int getNotificationIcon() {
        boolean useWhiteIcon = (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP);
        return useWhiteIcon ? R.drawable.ic_silhouette : R.mipmap.ic_launcher_round;
    }

    public void showNotification(Context ctx, CharSequence text) {
        Intent intent = new Intent();
        PendingIntent contentIntent = PendingIntent.getActivity(ctx, 0, intent,
                PendingIntent.FLAG_UPDATE_CURRENT);

        NotificationManager notificationManager =
                (NotificationManager) ctx.getSystemService(Context.NOTIFICATION_SERVICE);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel notificationChannel = new NotificationChannel(NOTIFICATION_CHANNEL_ID,
                    "My Notifications", NotificationManager.IMPORTANCE_LOW);
            AudioAttributes audioAttributes = new AudioAttributes.Builder()
                    .setUsage(AudioAttributes.USAGE_ASSISTANCE_SONIFICATION)
                    .setContentType(AudioAttributes.CONTENT_TYPE_SONIFICATION)
                    .build();

            // Configure the notification channel.
            notificationChannel.setDescription("Cash Karo");
            notificationChannel.enableLights(true);
            notificationChannel.setLightColor(Color.RED);
            notificationChannel.enableVibration(true);
            notificationChannel.setSound(RingtoneManager.getDefaultUri(
                    RingtoneManager.TYPE_NOTIFICATION), audioAttributes);
            notificationChannel.setVibrationPattern(new long[]{0, 1000, 500, 1000});
            notificationManager.createNotificationChannel(notificationChannel);
        }

        NotificationCompat.Builder b = new NotificationCompat.Builder(ctx);

        b.setAutoCancel(true)
                .setWhen(System.currentTimeMillis())
                .setSmallIcon(getNotificationIcon())
                .setSound(RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION))
                .setVibrate(new long[]{0, 100, 100, 100, 100, 100})
                .setTicker(ctx.getString(R.string.app_name))
                .setColor(Color.BLUE)
                .setContentTitle("Coupon Applied")
                .setContentText("Congratulations you have clicked on " + text + "!")
                .setContentIntent(contentIntent)
                .setChannel(NOTIFICATION_CHANNEL_ID)
                .setContentInfo("Info");

        notificationManager.notify(NOTIFICATION_ID, b.build());
    }


    @Override
    public int getItemCount() {
        return couponList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private Button checkOut;
        private CardView cardView;
        private ImageView imageView;
        private String url;
        private TextView textViewDealName, textViewDealValueValue,
                textViewCashback, textViewValidity;


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
                }
            });
            checkOut = v.findViewById(R.id.btnCheckout);
            checkOut.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    showNotification(checkOut.getContext(), textViewDealName.getText());
                    DealStorePageActivity.openLink(url);
                }
            });
        }
    }
}