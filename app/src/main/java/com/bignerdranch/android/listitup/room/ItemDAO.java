package com.bignerdranch.android.listitup.room;


import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import java.util.List;

/**
 * (data access object) validates your SQL at compile-time and associates it with a method
 * Represents the most common database operations
 * Room uses the DAO to create a clean API for the code
 */
@Dao
public interface ItemDAO {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void insert(ShopItem item);

    @Delete
    void delete(ShopItem item);

    @Query("DELETE FROM shop_table")
    void deleteAll();

    @Query("SELECT * from shop_table ORDER BY shopName ASC")
    LiveData<List<ShopItem>> getAlphabetizedShops();

    @Query("SELECT * from shop_table ORDER BY name ASC")
    LiveData<List<ShopItem>> getAllItems();

    @Query("SELECT * FROM shop_table WHERE id = :id")
    LiveData<ShopItem> loadItem(int id);

    @Query("SELECT * FROM shop_table WHERE id = :id")
    ShopItem getItem(int id);
}
