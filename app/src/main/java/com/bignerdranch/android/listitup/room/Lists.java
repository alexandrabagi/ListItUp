package com.bignerdranch.android.listitup.room;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "lists_table")
public class Lists {

    @PrimaryKey(autoGenerate = true)
    @NonNull
    @ColumnInfo(name = "listId")
    private int mListId;
    @ColumnInfo(name = "listName")
    private String mListName;
    @ColumnInfo(name = "sumPrice")
    private double mListSumPrice;

    public Lists(String name) {
        this.mListName = name;
    }

    public String getListName() {
        return mListName;
    }

    public void setListName(String name) {
        mListName = name;
    }

    public double getListSumPrice() {
        return mListSumPrice;
    }

    public void setListSumPrice(double price) {
        mListSumPrice = price;
    }
}
