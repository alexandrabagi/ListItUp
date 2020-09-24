package com.bignerdranch.android.listitup.room;


import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

/**
 * (data access object) validates your SQL at compile-time and associates it with a method
 * Represents the most common database operations
 * Room uses the DAO to create a clean API for the code
 */
@Dao
public interface ItemDAO {

    ///SHOPLIST///
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void insertToShop(Item item);

    @Update
    void setBought(Item item);

    @Update
    void putToCart(Item item);

    @Update
    void setPrice(Item item);

    @Delete
    void deleteShopItem(Item item);

    @Query("DELETE FROM shop_table")
    void deleteAllShop();

    @Query("SELECT * from shop_table WHERE bought = 0 ORDER BY shopName ASC")
    LiveData<List<Item>> getAlphabetizedShops();

    @Query("SELECT * from shop_table WHERE bought = 0 ORDER BY name ASC")
    LiveData<List<Item>> getAllShopItems();

    @Query("SELECT * FROM shop_table WHERE bought = 0 AND id = :id")
    LiveData<Item> loadShopItem(int id);

//    @Query("SELECT * FROM shop_table WHERE id = :id")
//    ShopItem getItem(int id);

    ///CARTLIST///
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void insertToCart(Item item);

    @Update
    void putToShop(Item item);

    @Delete
    void deleteCartItem(Item item);

    @Query("DELETE FROM shop_table")
    void deleteAllCart();

    @Query("SELECT * from shop_table WHERE bought = 1 ORDER BY name ASC")
    LiveData<List<Item>> getAllCartItems();

    @Query("SELECT * FROM shop_table WHERE bought = 1 AND id = :id")
    LiveData<Item> loadCartItem(int id);

    @Query("SELECT price FROM shop_table WHERE id = :id")
    float getItemPrice(int id);
}
