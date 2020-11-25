package com.bignerdranch.android.listitup.room;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "shop_table")
public class Item {

    @PrimaryKey(autoGenerate = true)
    @NonNull
    @ColumnInfo(name = "id")
    private int mId;
    @ColumnInfo(name = "name")
    private String mName;
    @ColumnInfo(name = "quantity")
    private int mQuantity;
    @ColumnInfo(name = "bought")
    private int mBought;
    @ColumnInfo(name = "price")
    private float mPrice;


    public Item(String name, int quantity, int bought, float price) {
        this.mName = name;
        this.mQuantity = quantity;
        this.mBought = bought; //0 - false, 1 - true
        this.mPrice = price;
    }

//    public Item(String name, int quantity, int bought) {
//        this.mName = name;
//        this.mQuantity = quantity;
//        this.mBought = bought; //0 - false, 1 - true
//    }

    public int getId() {
        return mId;
    }

    public void setId(int mId) {
        this.mId = mId;
    }

    public String getName() {
        return mName;
    }

    public void setName(String mName) {
        this.mName = mName;
    }

    public int getQuantity() {
        return mQuantity;
    }

    public void setQuantity(int mQuantity) {
        this.mQuantity = mQuantity;
    }

    public int isBought() {
        return this.mBought;
    }

    public int getBought() {
        return mBought;
    }

    public void setBought(int bought) {
        this.mBought = bought;
    }

    public void changeBought() {
        if (this.mBought == 0) this.mBought = 1;
        else this.mBought = 0;
    }

    public float getPrice() {
        return mPrice;
    }

    public void setPrice(float mPrice) {
        this.mPrice = mPrice;
    }
}
