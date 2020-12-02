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
    @ColumnInfo(name = "itemId", index = true)
    private long mContItemId;
    @ColumnInfo(name = "itemQty")
    private int mContItemQty;

    public ListContents(long contListId, long contItemId, int contItemQty) {
        this.mContListId = contListId;
        this.mContItemId = contItemId;
        this.mContItemQty = contItemQty;
    }

    public long getContListId() {
        return mContListId;
    }

    public void setContListId(int listId) {
        mContListId = listId;
    }

    public long getContItemId() {
        return mContItemId;
    }

    public void setContItemId(long contItemId) {
        mContItemId = contItemId;
    }

    public int getContItemQty() {
        return mContItemQty;
    }

    public void setContItemQty(int contItemQty) {
        mContItemQty = contItemQty;
    }
}
