//package com.bignerdranch.android.listitup.room;
//
//import androidx.annotation.NonNull;
//import androidx.room.ColumnInfo;
//import androidx.room.Entity;
//import androidx.room.PrimaryKey;
//
//import java.util.UUID;
//
//@Entity(tableName = "shop_table")
//public class ShopItem {
//
//    @PrimaryKey(autoGenerate = true)
//    @NonNull
//    @ColumnInfo(name = "id")
//    private int mId;
//    @NonNull
//    @ColumnInfo(name = "name")
//    private String mName;
//    @NonNull
//    @ColumnInfo(name = "shopName")
//    private String mShopName;
//    @NonNull
//    @ColumnInfo(name = "quantity")
//    private int mQuantity;
//    @ColumnInfo(name = "bought")
//    private boolean mBought;
//
//    public ShopItem(String name, String shopName, int quantity) {
//        this.mName = name;
//        this.mShopName = shopName;
//        this.mQuantity = quantity;
//        this.mBought = false;
//    }
//
//    public int getId() {
//        return mId;
//    }
//
//    public String getName() {
//        return mName;
//    }
//
//    public String getShopName() {
//        return mShopName;
//    }
//
//    public int getQuantity() {
//        return mQuantity;
//    }
//
//    public boolean isBought() {
//        return mBought;
//    }
//
//    public void setId(int mId) {
//        this.mId = mId;
//    }
//
//    public void setName(@NonNull String mName) {
//        this.mName = mName;
//    }
//
//    public void setShopName(@NonNull String mShopName) {
//        this.mShopName = mShopName;
//    }
//
//    public void setQuantity(int mQuantity) {
//        this.mQuantity = mQuantity;
//    }
//
//    public void setBought(boolean mBought) {
//        this.mBought = mBought;
//    }
//
////    public void setId(int mId) {
////        this.mId = mId;
////    }
//}
