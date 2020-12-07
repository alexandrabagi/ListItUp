package com.bignerdranch.android.listitup.room;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import java.util.List;

/**
 * Acts as a communication center between the Repository and the UI
 * Can share data between fragments
 * Part of the lifecycle library
 */

public class ItemVM extends AndroidViewModel {

    private ItemRepository mRepository;
    private LiveData<List<ListInfo>> mAllListInfos;
    private LiveData<List<ItemOld>> mAllShopItems;
    private LiveData<List<ItemOld>> mAllCartItems;

    public ItemVM(Application application) {
        super(application);
        mRepository = new ItemRepository(application);
        mAllListInfos = mRepository.getAllListInfos();
    }

    // ITEMS TABLE

    public void addNewItem(Item item) {
        mRepository.addNewItem(item);
    }

    public void updateItem(long id, String name, double price) {
        mRepository.updateItem(id, name, price);
    }

    public void deleteItem(Item item) {
        mRepository.deleteItem(item);
    }

    // LIST INFOS TABLE

    public void addNewList(ListInfo newList) {
        mRepository.addNewList(newList);
    }

    public void updateListInfo(long id, String name, double sumPrice) {
        mRepository.updateListInfo(id, name, sumPrice);
    }

    public LiveData<String> getListName(long id) {
        return mRepository.getListName(id);
    }

    public void deleteListInfo(ListInfo listToDelete) {
        mRepository.deleteListInfo(listToDelete);
    }

    public LiveData<List<ListInfo>> getAllListInfos() {
        return mRepository.getAllListInfos();
    }

    // LIST CONTENTS TABLE

//    public void addNewListContent(ListContents content) {
//        mRepository.addNewListContent(content);
//    }
//
//    public void updateListContent(int listId, int itemId, int quantity) {
//        mRepository.updateListContent(listId, itemId, quantity);
//    }
//
//    public void deleteWholeListContent(int listId) {
//        mRepository.deleteWholeListContent(listId);
//    }
//
//    public void deleteOneListContent(int listId, int itemId) {
//        mRepository.deleteOneListContent(listId, itemId);
//    }
//
//    public LiveData<List<ListContents>> getAllItemsOfList(int listId) {
//        return mRepository.getAllItemsOfList(listId);
//    }

    public LiveData<List<ListWithItems>> getAllListsWithItems() {
        return mRepository.getAllListWithItems();
    }

    public LiveData<ListWithItems> getListWithItems(long listId) {
        return mRepository.getListWithItems(listId);
    }


//    ///SHOPLIST///
//    public void insertToShop(ItemOld item) {
//        mRepository.insertToShop(item);
//    }
//
//    public void updateShopItem(int id, String itemName, int itemQuantity, float itemPrice) {
//        mRepository.updateShopItem(id, itemName, itemQuantity, itemPrice);
//    }
//
//    public void putToCart(ItemOld item) {
//        ItemOld newItem = item;
//        newItem.changeBought();
//        mRepository.putToCart(newItem);
//    }
//
//    public void setPrice(ItemOld item, float price) {
//        ItemOld newItem = item;
//        newItem.setPrice(price);
//        mRepository.setPrice(newItem);
//    }
//
//    public void deleteFromShop(ItemOld item) {
//        mRepository.deleteShopItem(item);
//    }
//
//    public LiveData<List<ItemOld>> getAllShopItems() {
//        return mRepository.getAllShopItems();
//    }
//
////    public LiveData<List<Item>> getAllItemsByShops() {
////        return mRepository.getAllItemsByShops();
////    }
//
//    public ItemOld loadShopItem(int id) {
//        return mRepository.loadItem(id).getValue();
//    }
//
//    public void deleteAllShop() {
//            mRepository.deleteAllShop();
//    }
//
//    ///CARTLIST///
//    public void insertToCart(ItemOld item) {
//        mRepository.insertToCart(item);
//    }
//
//    public void putToShop(ItemOld item) {
//        ItemOld newItem = item;
//        newItem.changeBought();
//        mRepository.putToShop(newItem);
//    }
//
//    public void deleteFromCart(ItemOld item) {
//        mRepository.deleteCartItem(item);
//    }
//
//    public LiveData<List<ItemOld>> getAllCartItems() {
//        return mRepository.getAllCartItems();
//    }
//
//    public ItemOld loadCartItem(int id) {
//        return mRepository.loadCartItem(id).getValue();
//    }
//
//    public void deleteAllCart() {
//        mRepository.deleteAllCart();
//    }
//
//    public float getItemPrice(int id) {
//        return mRepository.getItemPrice(id);
//    }
}
