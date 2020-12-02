//package com.bignerdranch.android.listitup.room;
//
//import android.app.Application;
//
//import androidx.lifecycle.AndroidViewModel;
//import androidx.lifecycle.LiveData;
//
//import java.util.List;
//
///**
// * Acts as a communication center between the Repository and the UI
// * Can share data between fragments
// * Part of the lifecycle library
// */
//
//public class ItemVMOld extends AndroidViewModel {
//
//    private ItemRepositoryOld mRepository;
//    private LiveData<List<ItemOld>> mAllShopItems;
//    private LiveData<List<ItemOld>> mAllCartItems;
//
//
//    public ItemVMOld(Application application) {
//        super(application);
//        mRepository = new ItemRepositoryOld(application);
//        mAllShopItems = mRepository.getAllShopItems();
//        mAllCartItems = mRepository.getAllCartItems();
//    }
//
//
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
//}
