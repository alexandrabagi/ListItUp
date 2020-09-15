//package com.bignerdranch.android.listitup.database;
//
//import android.content.Context;
//import android.database.sqlite.SQLiteDatabase;
//import android.database.sqlite.SQLiteOpenHelper;
//
//import com.bignerdranch.android.listitup.database.ItemDBSchema.ShoppingListTable;
//
///**
// * • Check to see whether the database already exists.
// * • If it does not, create it and create the tables and initial data it needs.
// * • If it does, open it up and see what version of your CrimeDbSchema it has.
// * (You may want to add or remove things in future versions of CriminalIntent.)
// * • If it is an old version, upgrade it to a newer version.
// */
//
//public class ItemBaseHelper extends SQLiteOpenHelper {
//    private static final int VERSION = 1;
//    private static final String DATABASE_NAME = "itemBase.db";
//
//    public ItemBaseHelper(Context context) {
//        super(context, DATABASE_NAME, null, VERSION);
//    }
//
//    @Override
//    public void onCreate(SQLiteDatabase db) {
//        db.execSQL("create table " + ShoppingListTable.NAME +
//                "(" + " _id integer primary key autoincrement, "
//                + ShoppingListTable.Cols.UUID
//                + ", " + ShoppingListTable.Cols.ITEMNAME
//                + ", " + ShoppingListTable.Cols.SHOPNAME
//                + ", " + ShoppingListTable.Cols.QUANTITY
//                + ", " + ShoppingListTable.Cols.BOUGHT +
//                ")"
//        );
//    }
//    @Override
//    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
//    }
//}
