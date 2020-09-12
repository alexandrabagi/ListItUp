package com.bignerdranch.android.listitup.room;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "cart_table")
public class CartIem {

    @PrimaryKey
    @NonNull
    @ColumnInfo(name = "id")
    private int mId;
    @ColumnInfo(name = "name")
    private String mName;
    @ColumnInfo(name = "shopName")
    private String mShopName;
    @ColumnInfo(name = "quantity")
    private int mQuantity;
    @ColumnInfo(name = "bought")
    private boolean mBought;

    public CartIem(int id, String name, String shopName, int quantity) {
        this.mId = id;
        this.mName = name;
        this.mShopName = shopName;
        this.mQuantity = quantity;
        this.mBought = true;
    }

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

    public String getShopName() {
        return mShopName;
    }

    public void setShopName(String mShopName) {
        this.mShopName = mShopName;
    }

    public int getQuantity() {
        return mQuantity;
    }

    public void setQuantity(int mQuantity) {
        this.mQuantity = mQuantity;
    }

    public boolean isBought() {
        return mBought;
    }

    public void setBought(boolean mBought) {
        this.mBought = mBought;
    }
}
