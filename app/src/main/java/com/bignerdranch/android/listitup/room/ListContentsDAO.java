//package com.bignerdranch.android.listitup.room;
//
//import androidx.lifecycle.LiveData;
//import androidx.room.Dao;
//import androidx.room.Delete;
//import androidx.room.Insert;
//import androidx.room.OnConflictStrategy;
//import androidx.room.Query;
//
//import java.util.List;
//
//@Dao
//public interface ListContentsDAO {
//
//    @Insert(onConflict = OnConflictStrategy.IGNORE)
//    void addNewItem(int listId, int itemId, int itemQuantity);
//
//    @Delete
//    void deleteListContent(int listId);
//
//    @Query("SELECT contentsItemId FROM list_contents_table WHERE contentsListId LIKE :listId")
//    LiveData<List<ListContents>> getItemsOnList(int listId);
//
//    @Query("SELECT contentsItemQuantity FROM list_contents_table WHERE contentsItemId LIKE :listId AND contentsItemId LIKE :itemId")
//    int getItemQuantity(int listId, int itemId);
//
//    @Query("SELECT * FROM list_contents_table")
//    LiveData<List<ListContents>> getAllListContents();
//
//    @Query("SELECT * FROM list_contents_table WHERE contentsListId LIKE :listId")
//    LiveData<List<ListContents>> getAllItemsOfList(int listId);
//}
