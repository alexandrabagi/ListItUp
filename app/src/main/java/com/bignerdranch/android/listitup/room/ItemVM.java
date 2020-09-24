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
    private LiveData<List<Item>> mAllShopItems;
    private LiveData<List<Item>> mAllCartItems;


    public ItemVM (Application application) {
        super(application);
        mRepository = new ItemRepository(application);
        mAllShopItems = mRepository.getAllShopItems();
        mAllCartItems = mRepository.getAllCartItems();
    }

//    public LiveData<List<ShopItem>> getAllItems() {
//        return mAllShopItems;
//    }

//    public LiveData<List<CartItem>> getAllCartItems() {
//        return mAllCartItems;
//    }

    ///SHOPLIST///
    public void insertToShop(Item item) {
        mRepository.insertToShop(item);
    }

    public void putToCart(Item item) {
        Item newItem = item;
        newItem.changeBought();
        mRepository.putToCart(newItem);
    }

    public void setPrice(Item item, float price) {
        Item newItem = item;
        newItem.setPrice(price);
        mRepository.setPrice(newItem);
    }

    public void deleteFromShop(Item item) {
        mRepository.deleteShopItem(item);
    }

    public LiveData<List<Item>> getAllItemsByShops() {
        return mRepository.getAllItemsByShops();
    }

    public Item loadShopItem(int id) {
        return mRepository.loadItem(id).getValue();
    }

    public void deleteAllShop() {
            mRepository.deleteAllShop();
    }

    ///CARTLIST///
    public void insertToCart(Item item) {
        mRepository.insertToCart(item);
    }

    public void putToShop(Item item) {
        Item newItem = item;
        newItem.changeBought();
        mRepository.putToShop(newItem);
    }

    public void deleteFromCart(Item item) {
        mRepository.deleteCartItem(item);
    }

    public LiveData<List<Item>> getAllCartItems() {
        return mRepository.getAllCartItems();
    }

    public Item loadCartItem(int id) {
        return mRepository.loadCartItem(id).getValue();
    }

    public void deleteAllCart() {
        mRepository.deleteAllCart();
    }

    public float getItemPrice(int id) {
        return mRepository.getItemPrice(id);
    }
}
