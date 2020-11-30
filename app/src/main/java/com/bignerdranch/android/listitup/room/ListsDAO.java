package com.bignerdranch.android.listitup.room;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import java.util.List;

@Dao
public interface ListsDAO {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void addNewList(int listId, String listName, double sumPrice);

    @Delete
    void deleteList(int listId);

    @Query("UPDATE lists_table SET listName = :name, sumPrice= :price WHERE listId LIKE :id ")
    void updateList(int id, String name, double price);

    @Query("UPDATE lists_table SET listName = :name WHERE listId LIKE :id")
    void updateListName(int id, String name);

    @Query("UPDATE lists_table SET sumPrice = :price WHERE listId LIKE :id")
    void updateListSumPrice(int id, double price);

    @Query("SELECT listName FROM lists_table WHERE listId LIKE :id")
    String getListName(int id);

    @Query("SELECT sumPrice FROM lists_table WHERE listId LIKE :id")
    double getSumPrice(int id);

    @Query("SELECT listId FROM lists_table")
    LiveData<List<Integer>> getAllListIds();

    @Query("SELECT listName FROM lists_table")
    LiveData<List<String>> getAllListNames();
}
