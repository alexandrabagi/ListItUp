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
    private int mItemId;
    @ColumnInfo(name = "itemName")
    private String mItemName;
    @ColumnInfo(name = "itemPrice")
    private float mItemPrice;

    public Item(String name, float price) {
        this.mItemName = name;
        this.mItemPrice = price;
    }
}
