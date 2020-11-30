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
    private float mListSumPrice;

    public Lists(String name) {
        this.mListName = name;
    }
}
