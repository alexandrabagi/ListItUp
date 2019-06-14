
package com.bignerdranch.android.listitup;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import com.bignerdranch.android.listitup.database.ItemCursorWrapper;
import android.database.sqlite.SQLiteDatabase;

import com.bignerdranch.android.listitup.database.ItemBaseHelper;
import com.bignerdranch.android.listitup.database.ItemDBSchema;
import com.bignerdranch.android.listitup.database.ItemDBSchema.ShoppingListTable;

import java.io.File;
import java.util.Iterator;
import java.util.ArrayList;
import java.util.List;
import java.util.Observable;
import java.util.UUID;

import static com.bignerdranch.android.listitup.database.ItemDBSchema.ShoppingListTable.Cols.ITEMNAME;

public class ListDB extends Observable {

    private static ListDB sListDB;
    private Context mContext;
    private SQLiteDatabase mDatabase;

    private ListDB(Context context) {
        mContext = context;
        mDatabase = new ItemBaseHelper(mContext)
            .getWritableDatabase();
    }

    public static ListDB get(Context context){
       if (sListDB == null) {
            sListDB = new ListDB(context);
       }
       return sListDB;
    }

    private int getItemIndex (UUID uuid){
        int index = 0;
        return index;
    }

    public void updateItem (Item item){
        String uuidString = item.getItemUUID().toString();
        ContentValues values = getContentValues(item);
        mDatabase.update(ShoppingListTable.NAME, values,
                ShoppingListTable.Cols.UUID + " = ?",
                new String[]{uuidString});
    }

    private ItemCursorWrapper queryItems (String whereClause, String[]whereArgs){
        Cursor cursor = mDatabase.query(
                ShoppingListTable.NAME,
                null, // columns - null selects all columns
                whereClause,
                whereArgs,
                null, // groupBy
                null, // having
                null  // orderBy
        );
        return new ItemCursorWrapper(cursor);
    }

    /*public void deleteThingNumber ( int thingNumber){
        if (sListDB.getListDB().size() >= thingNumber) {
            sListDB.getListDB().remove(thingNumber);
        }
    }*/

    //returns item for given UUID
    public Item getItemWithUUID (UUID ID){
        ItemCursorWrapper cursor = queryItems(
                ShoppingListTable.Cols.UUID + " = ?",
                new String[]{ID.toString()}
        );
        try {
            if (cursor.getCount() == 0) {
                return null;
            }
            cursor.moveToFirst();
            return cursor.getItem();
        } finally {
            cursor.close();
        }
    }

    private static ContentValues getContentValues (Item item){
        ContentValues values = new ContentValues();
        values.put(ShoppingListTable.Cols.UUID, item.getItemUUID().toString());
        values.put(ITEMNAME, item.getWhat());
        values.put(ShoppingListTable.Cols.SHOPNAME, item.getShop());
        values.put(ShoppingListTable.Cols.QUANTITY, item.getQuantity());
        return values;
    }

    public List<Item> getListDB () {

        List<Item> items = new ArrayList<>();
        ItemCursorWrapper cursor = queryItems(null, null);
        try {
            cursor.moveToFirst();
            while (!cursor.isAfterLast()) {
                items.add(cursor.getItem());
                cursor.moveToNext();
            }
        } finally {
            cursor.close();
        }
        return items;
    }

    public void addToDB (Item item){
        //thingsDB.add(thing);
        ContentValues values = getContentValues(item);
        mDatabase.insert(ShoppingListTable.NAME, null, values);
        this.setChanged();
        notifyObservers();
    }

    public void deleteItem (String what){
        mDatabase.delete(ShoppingListTable.NAME, ShoppingListTable.Cols.ITEMNAME + " = ?",
                new String[]{what});
        this.setChanged();
        notifyObservers();
    }

    /*
    returns File objects that point to the right locations (doesnt create files in filesystem)
     */
    public File getPhotoFile(Item item) {
        File filesDir = mContext.getFilesDir();
        return new File(filesDir, item.getPhotoFilename());
    }
}

