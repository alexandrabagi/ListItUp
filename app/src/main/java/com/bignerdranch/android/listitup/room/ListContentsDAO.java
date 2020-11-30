package com.bignerdranch.android.listitup.room;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import java.util.List;

@Dao
public interface ListContentsDAO {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void addNewItem(int listId, int itemId, int itemQuantity);

//    @Delete
//    void deleteItem(int listId, int itemId);

    @Query("SELECT contentsItemId FROM list_contents_table WHERE contentsListId LIKE :id")
    List<Integer> getItemsOnList(int id);

    @Query("SELECT contentsItemQuantity FROM list_contents_table WHERE contentsItemId LIKE :listId AND contentsItemId LIKE :itemId")
    int getItemQuantity(int listId, int itemId);
}
