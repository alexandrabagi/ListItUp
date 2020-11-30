package com.bignerdranch.android.listitup.room;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;

@Entity(tableName = "list_contents_table",
        foreignKeys = {
        @ForeignKey(
                entity = Lists.class,
                parentColumns = "listId",
                childColumns = "contentsListId",
                onDelete = ForeignKey.CASCADE
                ),
        @ForeignKey(
                entity = Item.class,
                parentColumns =  "itemId",
                childColumns = "listsItemId",
                onDelete = ForeignKey.CASCADE
                )
        })

public class ListContents {

    @NonNull
    @ColumnInfo(name = "contentsListId")
    private int mContentsListId;
    @ColumnInfo(name = "contentsItemId")
    private int mContentsItemId;
    @ColumnInfo(name = "contentsItemQuantity")
    private int mContentsItemQuantity;

    public ListContents(int listId, int itemId, int quantity) {
        this.mContentsListId = listId;
        this.mContentsItemId = itemId;
        this.mContentsItemQuantity= quantity;
    }

    public int getContentsListId() {
        return mContentsListId;
    }

    public void setContentsListId(int listId) {
        mContentsListId = listId;
    }

    public int getItemId() {
        return mContentsItemId;
    }

    public void setItemId(int itemId) {
        mContentsItemId = itemId;
    }

    public int getContentsItemQuantity() {
        return mContentsItemQuantity;
    }

    public void setContentsItemQuantity(int itemQuantity) {
        mContentsItemQuantity = itemQuantity;
    }
}
