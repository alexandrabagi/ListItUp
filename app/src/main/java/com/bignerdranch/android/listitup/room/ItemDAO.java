package com.bignerdranch.android.listitup.room;


import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

@Dao
public interface ItemDAO {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void addNewItem(Item item);

    @Delete
    void deleteItem(Item item);

    @Query("UPDATE items_table SET itemName = :name, itemPrice= :price WHERE itemId LIKE :id ")
    void updateItem(int id, String name, double price);

    @Query("SELECT itemName FROM items_table WHERE itemId LIKE :id")
    String getItemName(int id);

    @Query("SELECT itemPrice FROM items_table WHERE itemId LIKE :id")
    double getItemPrice(int id);
}
