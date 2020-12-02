package com.bignerdranch.android.listitup.room;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "list_info_table")
public class ListInfo {

    @PrimaryKey(autoGenerate = true)
    @NonNull
    @ColumnInfo(name = "listId")
    private long mListId;
    @ColumnInfo(name = "listName")
    private String mListName;
    @ColumnInfo(name = "sumPrice")
    private double mListSumPrice;

    public ListInfo(String listName) {
        this.mListName = listName;
    }

    public void setListId(long listId) {
        mListId = listId;
    }

    public long getListId() {
        return mListId;
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
