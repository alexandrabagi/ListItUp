//package com.bignerdranch.android.listitup;
//
//import java.util.UUID;
//
///**
// * One single item on the individual shopping list
// */
//
//public class Item {
//
//    private UUID itemUUID;
//    private String mWhat;
//    private String mQuantity; // string to make it possible to type in entity
//    private String mShopName;
//    private boolean mBought;
//
//
//    public Item(String what, String shop, String quantity) {
//        mWhat = what;
//        mShopName = shop;
//        mQuantity = quantity;
//        itemUUID = UUID.randomUUID();
//
//    }
//
//    public Item(UUID ID, String what, String shop, String quantity) {
//        mWhat = what;
//        mShopName = shop;
//        mQuantity = quantity;
//        itemUUID = ID;
//    }
//
//    public String getPhotoFilename() {
//        return "IMG_" + getItemUUID().toString() + ".jpg";
//    }
//
//    @Override
//    public String toString() { return oneLine("","can be bought in: "); }
//    public UUID getItemUUID() { return itemUUID; }
//    public String getWhat() { return mWhat; }
//    public String getShop() { return mShopName; }
//    public String getQuantity() { return mQuantity; }
//    public void setWhat(String what) { mWhat = what; }
//    public void setShop(String shop) { mShopName = shop; }
//    public void setQuantity(String quantity) { mQuantity = quantity; }
//    public void setSolved(boolean bought) { mBought = bought;}
//
//    //what was oneLine() used for?
//    public String oneLine(String pre, String post) {
//        return pre+mWhat + " "+post + mShopName;
//    }
//}
