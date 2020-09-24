package com.bignerdranch.android.listitup.room;

import android.app.Application;

import androidx.lifecycle.LiveData;

import java.util.List;

/**
 * Abstracts access to multiple data sources
 * Provides a clean API for data access to the rest of the application
 * Manages queries and allows the use of multiple backends
 * (can contain logic for deciding whether to fetch data from network or use cached results)
 */

public class ItemRepository {

    private ItemDAO mItemDao;
    private LiveData<List<Item>> mAllShopItems;
    private LiveData<List<Item>> mAllCartItems;

    ItemRepository(Application application) {
        ItemRoomDB db = ItemRoomDB.getDatabase(application);
        mItemDao = db.itemDao();
        mAllShopItems = mItemDao.getAllShopItems();
        mAllCartItems = mItemDao.getAllCartItems();
    }

    ///SHOPLIST///
    // Room executes all queries on a separate thread
    // Observed LiveData will notify the observer when the data has changed

    // Must be called on a non-UI thread
    // Room ensures that no long running operations happen on the main thread, blocking the UI
    void insertToShop(Item item) {
        ItemRoomDB.databaseWriteExecutor.execute(() -> {
            mItemDao.insertToShop(item);
        });
    }

    void putToCart(Item item) {
        ItemRoomDB.databaseWriteExecutor.execute(() -> {
            mItemDao.putToCart(item);
        });
    }

    void setPrice(Item item) {
        ItemRoomDB.databaseWriteExecutor.execute(() -> {
            mItemDao.setPrice(item);
        });
    }

    void deleteShopItem(Item item) {
        ItemRoomDB.databaseWriteExecutor.execute(() -> {
            mItemDao.deleteShopItem(item);
        });
    }

    void deleteAllShop() {
        ItemRoomDB.databaseWriteExecutor.execute(() -> {
            mItemDao.deleteAllShop();
        });
    }

    LiveData<List<Item>> getAllShopItems() {
        return mAllShopItems;
    }

    LiveData<List<Item>> getAllItemsByShops() {
        return mItemDao.getAlphabetizedShops();
    }

    LiveData<Item> loadItem(int id) {
        return mItemDao.loadShopItem(id);
    }

    ///CARTLIST///
    void insertToCart(Item item) {
        ItemRoomDB.databaseWriteExecutor.execute(() -> {
            mItemDao.insertToCart(item);
        });
    }

    void deleteCartItem(Item item) {
        ItemRoomDB.databaseWriteExecutor.execute(() -> {
            mItemDao.deleteCartItem(item);
            mItemDao.setBought(item);
        });
    }

    void deleteAllCart() {
        ItemRoomDB.databaseWriteExecutor.execute(() -> {
            mItemDao.deleteAllCart();
        });
    }

    LiveData<List<Item>> getAllCartItems() {
        return mAllCartItems;
    }

    LiveData<Item> loadCartItem(int id) {
        return mItemDao.loadCartItem(id);
    }
}
