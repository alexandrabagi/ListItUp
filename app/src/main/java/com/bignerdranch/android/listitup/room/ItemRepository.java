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
    private LiveData<List<ListInfo>> mAllListInfos;
    private LiveData<List<ListWithItems>> mAllListsWithItems;

    ItemRepository(Application application) {
        ItemRoomDB db = ItemRoomDB.getDatabase(application);
        mItemDao = db.itemDao();
        mAllListInfos = mItemDao.getAllListInfos();
        mAllListsWithItems = mItemDao.getAllListsWithItems();
    }


    // ITEMS TABLE

    // Insert to items table - Item(id, name, price)
    // Should be called when a new list is saved
    void addNewItem(Item item) {
        ItemRoomDB.databaseWriteExecutor.execute(() -> {
            mItemDao.addNewItem(item);
        });
    }

    // Update name or price of the item
    void updateItem(long id, String name, double price) {
        ItemRoomDB.databaseWriteExecutor.execute(() -> {
            mItemDao.updateItem(id, name, price);
        });
    }

    void deleteItem(Item item) {
        ItemRoomDB.databaseWriteExecutor.execute(() -> {
            mItemDao.deleteItem(item);
        });
    }

    // LIST INFO TABLE

    // Insert new list
    void addNewList(ListInfo newList) {
        ItemRoomDB.databaseWriteExecutor.execute(() -> {
            mItemDao.addNewList(newList);
        });
    }

    void updateListInfo(long id, String name, double sumPrice) {
        ItemRoomDB.databaseWriteExecutor.execute(() -> {
            mItemDao.updateListInfo(id, name, sumPrice);
        });
    }

    void deleteListInfo(ListInfo listToDelete) {
        ItemRoomDB.databaseWriteExecutor.execute(() -> {
            mItemDao.deleteListInfo(listToDelete);
        });
    }

    LiveData<String> getListName(long id) {
        return mItemDao.getListName(id);
    }

    LiveData<List<ListInfo>> getAllListInfos() {
        return mItemDao.getAllListInfos();
    }

    // LIST CONTENTS TABLE

//    // add new list content - should be executed when new list is added
//    void addNewListContent(ListContents content) {
//        ItemRoomDB.databaseWriteExecutor.execute(() -> {
//            mItemDao.addNewListContent(content);
//        });
//    }
//
//    void updateListContent(int listId, int itemId, int quantity) {
//        ItemRoomDB.databaseWriteExecutor.execute(() -> {
//            mItemDao.updateListContent(listId, itemId, quantity);
//        });
//    }
//
//    // delete all content of one list
//    void deleteWholeListContent(int listId) {
//        ItemRoomDB.databaseWriteExecutor.execute(() -> {
//            mItemDao.deleteWholeListContent(listId);
//        });
//    }
//
//    void deleteOneListContent(int listId, int itemId) {
//        ItemRoomDB.databaseWriteExecutor.execute(() -> {
//            mItemDao.deleteOneListContent(listId, itemId);
//        });
//    }
//
//    // get all items on one list
//    LiveData<List<ListContents>> getAllItemsOfList(int listId) {
//        return mItemDao.getAllItemsOfList(listId);
//    }

    // get all lists with items
    LiveData<List<ListWithItems>> getAllListWithItems() {
        return mAllListsWithItems;
    }

    // get list with items
    LiveData<ListWithItems> getListWithItems(long listId) {
        return mItemDao.getListWithItems(listId);
    }



    ///SHOPLIST///
    // Room executes all queries on a separate thread
    // Observed LiveData will notify the observer when the data has changed

    // Must be called on a non-UI thread
    // Room ensures that no long running operations happen on the main thread, blocking the UI
//    void insertToShop(ItemOld item) {
//        ItemRoomDB.databaseWriteExecutor.execute(() -> {
//            mItemDao.insertToShop(item);
//        });
//    }

//    void updateShopItem(long id, String itemName, int itemQuantity, float itemPrice) {
//        ItemRoomDB.databaseWriteExecutor.execute(() -> {
//            mItemDao.updateShopItem(id, itemName, itemQuantity, itemPrice);
//        });
//    }

//    void putToCart(ItemOld item) {
//        ItemRoomDB.databaseWriteExecutor.execute(() -> {
//            mItemDao.putToCart(item);
//        });
//    }
//
//    void setPrice(ItemOld item) {
//        ItemRoomDB.databaseWriteExecutor.execute(() -> {
//            mItemDao.setPrice(item);
//        });
//    }
//
//    void deleteShopItem(ItemOld item) {
//        ItemRoomDB.databaseWriteExecutor.execute(() -> {
//            mItemDao.deleteShopItem(item);
//        });
//    }
//
//    void deleteAllShop() {
//        ItemRoomDB.databaseWriteExecutor.execute(() -> {
//            mItemDao.deleteAllShop();
//        });
//    }
//
//    LiveData<List<ItemOld>> getAllShopItems() {
//        return mAllShopItems;
//    }
//
////    ItemRepositoryOld
//
//    LiveData<ItemOld> loadItem(int id) {
//        return mItemDao.loadShopItem(id);
//    }
//
//    ///CARTLIST///
//    void insertToCart(ItemOld item) {
//        ItemRoomDB.databaseWriteExecutor.execute(() -> {
//            mItemDao.insertToCart(item);
//        });
//    }
//
//    void putToShop(ItemOld item) {
//        ItemRoomDB.databaseWriteExecutor.execute(() -> {
//            mItemDao.putToShop(item);
//        });
//    }
//
//    void deleteCartItem(ItemOld item) {
//        ItemRoomDB.databaseWriteExecutor.execute(() -> {
//            mItemDao.deleteCartItem(item);
//            mItemDao.setBought(item);
//        });
//    }
//
//    void deleteAllCart() {
//        ItemRoomDB.databaseWriteExecutor.execute(() -> {
//            mItemDao.deleteAllCart();
//        });
//    }
//
//    LiveData<List<ItemOld>> getAllCartItems() {
//        return mAllCartItems;
//    }
//
//    LiveData<ItemOld> loadCartItem(int id) {
//        return mItemDao.loadCartItem(id);
//    }
//
//    float getItemPrice(int id) {
//        return mItemDao.getItemPrice(id);
//    }
}
