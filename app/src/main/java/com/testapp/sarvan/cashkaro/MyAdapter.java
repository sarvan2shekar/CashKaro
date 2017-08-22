package com.testapp.sarvan.cashkaro;

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
        holder.textViewCashback.setText(couponList.get(position).getCashback());
        holder.textViewValidity.setText(couponList.get(position).getValidity());
        holder.imageView.setImageResource(couponList.get(position).getDrawable());
    }

    @Override
    public int getItemCount() {
        return couponList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private Button checkOut;
        private ImageView imageView;
        private TextView textViewDealName, textViewCashback, textViewValidity;


        public ViewHolder(View v) {
            super(v);
            imageView = v.findViewById(R.id.dealImageDetail);
            textViewDealName = v.findViewById(R.id.dealNameDetail);
            textViewCashback = v.findViewById(R.id.dealDetailCBValue);
            textViewValidity = v.findViewById(R.id.dealDetailValidityValue);
            checkOut = v.findViewById(R.id.btnCheckout);
            checkOut.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    System.out.println("Clicked: " + ((Button) view).getText());
                }
            });
        }
    }
}