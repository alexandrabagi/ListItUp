package com.bignerdranch.android.listitup.room;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "items_table")
public class Item {

    @PrimaryKey(autoGenerate = true)
    @NonNull
    @ColumnInfo(name = "itemId")
    private long mItemId;
    @ColumnInfo(name = "itemName")
    private String mItemName;
    @ColumnInfo(name = "itemPrice")
    private double mItemPrice;

    public Item(String itemName, double itemPrice) {
        this.mItemName = itemName;
        this.mItemPrice = itemPrice;
    }

    public void setItemId(long itemId) {
        mItemId = itemId;
    }

    public long getItemId() {
        return mItemId;
    }

    public String getItemName() {
        return mItemName;
    }

    public void setItemName(String name) {
        mItemName = name;
    }

    public double getItemPrice() {
        return mItemPrice;
    }

    public void setItemPrice(double price) {
        mItemPrice = price;
    }
}
