package com.bignerdranch.android.listitup.room;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

@Entity(tableName = "temp_items_table")
public class TempItem {

    @PrimaryKey(autoGenerate = true)
    @NonNull
    @ColumnInfo(name = "tempItemId")
    private long mTempItemId;
    @ColumnInfo(name = "tempItemName")
    private String mTempItemName;
    @ColumnInfo(name = "tempItemQty")
    private int mTempItemQty;
    @ColumnInfo(name = "tempItemPrice")
    private double mTempItemPrice;

    public TempItem(String tempItemName, int tempItemQty, double tempItemPrice) {
        this.mTempItemName = tempItemName;
        this.mTempItemQty = tempItemQty;
        this.mTempItemPrice = tempItemPrice;
    }

    public long getTempItemId() {
        return mTempItemId;
    }

    public void setTempItemId(long id) {
        mTempItemId = id;
    }

    public String getTempItemName() {
        return mTempItemName;
    }

    public void setTempItemName(String name) {
        mTempItemName = name;
    }

    public int getTempItemQty() {
        return mTempItemQty;
    }

    public void setTempItemQty(int qty) {
        mTempItemQty = qty;
    }

    public double getTempItemPrice() {
        return mTempItemPrice;
    }

    public void setTempItemPrice(double price) {
        mTempItemPrice = price;
    }
}
