package com.bignerdranch.android.listitup.room;

import android.app.Application;
import android.os.AsyncTask;

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
    private LiveData<List<ShopItem>> mAllItems;

    ItemRepository(Application application) {
        ItemRoomDB db = ItemRoomDB.getDatabase(application);
        mItemDao = db.itemDao();
        mAllItems = mItemDao.getAllItems();
    }

    // Room executes all queries ona separate thread
    // Observed LiveData will notify the observer when the data has changed
    LiveData<List<ShopItem>> getAllItems() {
        return mAllItems;
    }

    LiveData<List<ShopItem>> getAllItemsByShops() {
        return mItemDao.getAlphabetizedShops();
    }

    // Must be called on a non-UI thread
    // Room ensures that no long running operations happen on the main thread, blocking the UI
    void insert(ShopItem item) {
        ItemRoomDB.databaseWriteExecutor.execute(() -> {
            mItemDao.insert(item);
        });
    }

    void delete(ShopItem item) {
        ItemRoomDB.databaseWriteExecutor.execute(() -> {
            mItemDao.delete(item);
        });
    }

    LiveData<ShopItem> loadItem(int id) {
        return mItemDao.loadItem(id);
    }

    ShopItem getItem(int id) {
        return mItemDao.getItem(id);
    }

    void deleteAll() {
        mItemDao.deleteAll();
    }


}
