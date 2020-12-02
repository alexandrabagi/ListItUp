package com.bignerdranch.android.listitup.room;

// Data class

import androidx.room.Embedded;
import androidx.room.Junction;
import androidx.room.Relation;

import java.util.List;

public class ListWithItems {

    @Embedded public ListInfo listInfo;
    @Relation(
            parentColumn = "listId",
            entityColumn = "itemId",
            associateBy = @Junction(ListContents.class)
    )

    public List<Item> items;

    public ListInfo getListInfo() {
        return listInfo;
    }

    public List<Item> getListItems() {
        return items;
    }
}
