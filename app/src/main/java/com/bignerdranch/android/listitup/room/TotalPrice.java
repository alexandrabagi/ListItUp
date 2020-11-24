package com.bignerdranch.android.listitup.room;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;

public class TotalPrice {

    private float mTotalPrice;

    private static TotalPrice INSTANCE = null;

    // other instance variables can be here

    private TotalPrice() {};

    public static TotalPrice getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new TotalPrice();
        }
        return(INSTANCE);
    }

    public float getTotalPrice() {
        return mTotalPrice;
    }

    public void addToTotalPrice(float price) {
        mTotalPrice += price;
    }

    public void subtractFromTotalPrice(float price) {
        mTotalPrice -= price;
    }

    public void setTotalPrice(float price) {
        mTotalPrice = price;
    }
}
