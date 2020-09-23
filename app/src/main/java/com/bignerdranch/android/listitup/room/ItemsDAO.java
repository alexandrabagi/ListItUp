//package com.bignerdranch.android.listitup.room;
//
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
///**
// * (data access object) validates your SQL at compile-time and associates it with a method
// * Represents the most common database operations
// * Room uses the DAO to create a clean API for the code
// */
//@Dao
//public interface ItemsDAO {
//
//    ///SHOPLIST///
//    @Insert(onConflict = OnConflictStrategy.IGNORE)
//    void insertToShop(ShopItem item);
//
//    @Delete
//    void deleteShopItem(ShopItem item);
//
//    @Query("DELETE FROM shop_table")
//    void deleteAllShop();
//
//    @Query("SELECT * from shop_table ORDER BY shopName ASC")
//    LiveData<List<ShopItem>> getAlphabetizedShops();
//
//    @Query("SELECT * from shop_table ORDER BY name ASC")
//    LiveData<List<ShopItem>> getAllShopItems();
//
//    @Query("SELECT * FROM shop_table WHERE id = :id")
//    LiveData<ShopItem> loadShopItem(int id);
//
////    @Query("SELECT * FROM shop_table WHERE id = :id")
////    ShopItem getItem(int id);
//
//    ///CARTLIST///
//    @Insert(onConflict = OnConflictStrategy.IGNORE)
//    void insertToCart(CartItem item);
//
//    @Delete
//    void deleteCartItem(CartItem item);
//
//    @Query("DELETE FROM cart_table")
//    void deleteAllCart();
//
//    @Query("SELECT * from cart_table ORDER BY name ASC")
//    LiveData<List<CartItem>> getAllCartItems();
//
//    @Query("SELECT * FROM cart_table WHERE id = :id")
//    LiveData<CartItem> loadCartItem(int id);
//}
