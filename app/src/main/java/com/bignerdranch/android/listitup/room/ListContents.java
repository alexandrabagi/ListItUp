package com.bignerdranch.android.listitup.room;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;

// Cross-reference table

@Entity(tableName = "list_contents_table",
        primaryKeys = {"listId", "itemId"}
        )

//        foreignKeys = {
//        @ForeignKey(
//                entity = ListInfo.class,
//                parentColumns = "listId",
//                childColumns = "contentsListId",
//                onDelete = ForeignKey.CASCADE
//                ),
//        @ForeignKey(
//                entity = Item.class,
//                parentColumns =  "itemId",
//                childColumns = "listsItemId",
//                onDelete = ForeignKey.CASCADE
//                )
//        })

public class ListContents {

    @NonNull
    @ColumnInfo(name = "listId")
    private long mContListId;
    @ColumnInfo(name = "itemId")
    private long mContItemId;
    @ColumnInfo(name = "itemQty")
    private int mContItemQty;

    public ListContents(int listId, int itemId, int quantity) {
        this.mContListId = listId;
        this.mContItemId = itemId;
        this.mContItemQty = quantity;
    }

    public long getContentsListId() {
        return mContListId;
    }

    public void setContentsListId(int listId) {
        mContListId = listId;
    }

    public long getItemId() {
        return mContItemId;
    }

    public void setItemId(long itemId) {
        mContItemId = itemId;
    }

    public int getContentsItemQuantity() {
        return mContItemQty;
    }

    public void setContentsItemQuantity(int itemQuantity) {
        mContItemQty = itemQuantity;
    }
}
