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
public interface ItemDAOold {

    ///SHOPLIST///
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void insertToShop(ItemOld item);

    @Update
    void setBought(ItemOld item);

    @Update
    void putToCart(ItemOld item);

    @Update
    void setPrice(ItemOld item);

    @Delete
    void deleteShopItem(ItemOld item);

    @Query("UPDATE ItemOld SET name = :name ,quantity= :quantity,price= :price WHERE id LIKE :id ")
    void updateShopItem(int id, String name, int quantity, float price);


    @Query("DELETE FROM ItemOld")
    void deleteAllShop();

//    @Query("SELECT * from shop_table WHERE bought = 0 ORDER BY shopName ASC")
//    LiveData<List<Item>> getAlphabetizedShops();

    @Query("SELECT * from ItemOld WHERE bought = 0 ORDER BY name ASC")
    LiveData<List<ItemOld>> getAllShopItems();

    @Query("SELECT * FROM ItemOld WHERE bought = 0 AND id = :id")
    LiveData<ItemOld> loadShopItem(int id);


    ///CARTLIST///
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void insertToCart(ItemOld item);

    @Update
    void putToShop(ItemOld item);

    @Delete
    void deleteCartItem(ItemOld item);

    @Query("DELETE FROM ItemOld")
    void deleteAllCart();

    @Query("SELECT * from ItemOld WHERE bought = 1 ORDER BY name ASC")
    LiveData<List<ItemOld>> getAllCartItems();

    @Query("SELECT * FROM ItemOld WHERE bought = 1 AND id = :id")
    LiveData<ItemOld> loadCartItem(int id);

    @Query("SELECT price FROM ItemOld WHERE id = :id")
    float getItemPrice(int id);
}
