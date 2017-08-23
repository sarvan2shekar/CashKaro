package com.testapp.sarvan.cashkaro.model;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by sarva on 23-08-2017.
 * Model Class for Coupon's details
 */

public class Coupons implements Parcelable {

    public static final Creator<Coupons> CREATOR = new Creator<Coupons>() {
        @Override
        public Coupons createFromParcel(Parcel in) {
            return new Coupons(in);
        }

        @Override
        public Coupons[] newArray(int size) {
            return new Coupons[size];
        }
    };
    private int drawable = 0;
    private String name = null;
    private String value = null;
    private String cashback = null;
    private String validity = null;

    public Coupons(int drawableId, String name, String value, String cashback,
                   String validity) {
        this.drawable = drawableId;
        this.name = name;
        this.value = value;
        this.cashback = cashback;
        this.validity = validity;
    }

    public Coupons() {
    }

    protected Coupons(Parcel in) {
        drawable = in.readInt();
        name = in.readString();
        value = in.readString();
        cashback = in.readString();
        validity = in.readString();
    }

    public int getDrawable() {
        return drawable;
    }

    public void setDrawable(int drawable) {
        this.drawable = drawable;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }


    public String getCashback() {
        return cashback;
    }

    public void setCashback(String cashback) {
        this.cashback = cashback;
    }

    public String getValidity() {
        return validity;
    }

    public void setValidity(String validity) {
        this.validity = validity;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(drawable);
        parcel.writeString(name);
        parcel.writeString(value);
        parcel.writeString(cashback);
        parcel.writeString(validity);
    }
}
