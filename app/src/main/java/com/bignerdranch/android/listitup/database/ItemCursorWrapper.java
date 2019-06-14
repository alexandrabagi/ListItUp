package com.bignerdranch.android.listitup.database;

import android.database.Cursor;
import android.database.CursorWrapper;

import com.bignerdranch.android.listitup.Item;

import java.util.UUID;


public class ItemCursorWrapper extends CursorWrapper {

    public ItemCursorWrapper(Cursor cursor) {
        super(cursor);
    }

    public Item getItem() {
        String uuidString = getString(getColumnIndex(ItemDBSchema.ShoppingListTable.Cols.UUID));
        String itemName = getString(getColumnIndex(ItemDBSchema.ShoppingListTable.Cols.ITEMNAME));
        String shopName = getString(getColumnIndex(ItemDBSchema.ShoppingListTable.Cols.SHOPNAME));
        String quantity = getString(getColumnIndex(ItemDBSchema.ShoppingListTable.Cols.QUANTITY));
        Item item = new Item(UUID.fromString(uuidString), itemName, shopName, quantity);
        return item;    }
}