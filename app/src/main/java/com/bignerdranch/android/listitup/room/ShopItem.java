package com.bignerdranch.android.listitup.room;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "shop_table")
public class ShopItem {

    @PrimaryKey
    @NonNull
    @ColumnInfo(name = "id")
    private int mId;
    @NonNull
    @ColumnInfo(name = "name")
    private String mName;
    @NonNull
    @ColumnInfo(name = "shopName")
    private String mShopName;
    @NonNull
    @ColumnInfo(name = "quantity")
    private int mQuantity;
    @ColumnInfo(name = "bought")
    private boolean mBought;

    public ShopItem(int id, String name, String shopName, int quantity) {
        this.mId = id;
        this.mName = name;
        this.mShopName = shopName;
        this.mQuantity = quantity;
        this.mBought = false;
    }

    public int getmId() {
        return mId;
    }

    public String getmName() {
        return mName;
    }

    public String getmShopName() {
        return mShopName;
    }

    public int getmQuantity() {
        return mQuantity;
    }

    public boolean ismBought() {
        return mBought;
    }

    public void setmId(int mId) {
        this.mId = mId;
    }
}
