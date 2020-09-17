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
    private LiveData<List<ShopItem>> mAllShopItems;
    private LiveData<List<CartItem>> mAllCartItems;


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
    public void insertToShop(ShopItem item) {
        mRepository.insertToShop(item);
    }

    public void deleteFromShop(ShopItem item) {
        mRepository.deleteShopItem(item);
    }

    public LiveData<List<ShopItem>> getAllItemsByShops() {
        return mRepository.getAllItemsByShops();
    }

    public ShopItem loadShopItem(int id) {
        return mRepository.loadItem(id).getValue();
    }

    public void deleteAllShop() {
            mRepository.deleteAllShop();
    }

    ///CARTLIST///
    public void insertToCart(CartItem item) {
        mRepository.insertToCart(item);
    }

    public void deleteFromCart(CartItem item) {
        mRepository.deleteCartItem(item);
    }

    public LiveData<List<CartItem>> getAllCartItems() {
        return mRepository.getAllCartItems();
    }

    public CartItem loadCartItem(int id) {
        return mRepository.loadCartItem(id).getValue();
    }

    public void deleteAllCart() {
        mRepository.deleteAllCart();
    }
}
