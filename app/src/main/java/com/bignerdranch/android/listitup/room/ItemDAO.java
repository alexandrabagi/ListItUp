package com.bignerdranch.android.listitup.room;


import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Transaction;

import java.util.List;

@Dao
public interface ItemDAO {

    // Item table

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void addNewItem(Item item);

    @Delete
    void deleteItem(Item item);

    @Query("UPDATE items_table SET itemName = :name, itemPrice= :price WHERE itemId LIKE :id ")
    void updateItem(long id, String name, double price);

    @Query("SELECT itemName FROM items_table WHERE itemId LIKE :id")
    String getItemName(long id);

    @Query("SELECT itemPrice FROM items_table WHERE itemId LIKE :id")
    double getItemPrice(long id);

    @Query("SELECT * FROM items_table")
    LiveData<List<Item>> getAllItems();

    // List INFO table

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void addNewList(ListInfo newList);

    @Delete
    void deleteListInfo(ListInfo listToDelete);

    @Query("UPDATE list_info_table SET listName = :name, sumPrice= :price WHERE listId LIKE :id ")
    void updateListInfo(long id, String name, double price);

    @Query("UPDATE list_info_table SET listName = :name WHERE listId LIKE :id")
    void updateListName(long id, String name);

    @Query("UPDATE list_info_table SET sumPrice = :price WHERE listId LIKE :id")
    void updateListSumPrice(long id, double price);

    @Query("SELECT listName FROM list_info_table WHERE listId LIKE :id")
    LiveData<String> getListName(long id);

    @Query("SELECT sumPrice FROM list_info_table WHERE listId LIKE :id")
    double getSumPrice(long id);

    @Query("SELECT listId FROM list_info_table")
    LiveData<List<Integer>> getAllListIds();

    @Query("SELECT listName FROM list_info_table")
    LiveData<List<String>> getAllListNames();

    @Query("SELECT * FROM list_info_table")
    LiveData<List<ListInfo>> getAllListInfos();

    // TempList table

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void addNewTempItem(TempItem newTempItem);

    @Delete
    void deleteTempItem(TempItem tempItem);

    @Query("UPDATE temp_items_table SET tempItemName = :name, tempItemQty = :quantity, tempItemPrice = :price")
    void updateTempItem(String name, int quantity, double price);

    @Query("SELECT * FROM temp_items_table WHERE tempItemId LIKE :id")
    LiveData<TempItem> getTempItem(long id);

    @Query("SELECT * FROM temp_items_table")
    LiveData<List<TempItem>> getAllTempItems();


    // List Contents table

//    @Insert(onConflict = OnConflictStrategy.IGNORE)
//    void addNewListContent(ListContents content);
//
//    @Delete
//    void deleteWholeListContent(int listId);
//
//    @Delete
//    void deleteOneListContent(int listId, int itemId);
//
//    @Query("UPDATE list_contents_table SET listId = :listId, itemId = :itemId, itemQty = :quantity")
//    void updateListContent(int listId, int itemId, int quantity);
//
//    @Query("SELECT contentsItemId FROM list_contents_table WHERE contentsListId LIKE :listId")
//    LiveData<List<ListContents>> getItemsOnList(int listId);
//
//    @Query("SELECT contentsItemQuantity FROM list_contents_table WHERE contentsItemId LIKE :listId AND contentsItemId LIKE :itemId")
//    int getItemQuantity(int listId, int itemId);
//
//    @Query("SELECT * FROM list_contents_table")
//    LiveData<List<ListContents>> getAllListContents();
//
//    @Query("SELECT * FROM list_contents_table WHERE contentsListId LIKE :listId")
//    LiveData<List<ListContents>> getAllItemsOfList(int listId);

    @Transaction
    @Query("SELECT * FROM list_info_table")
    LiveData<List<ListWithItems>> getAllListsWithItems();

    @Transaction
    @Query("SELECT * FROM list_info_table WHERE listId LIKE :listId")
    public LiveData<ListWithItems> getListWithItems(long listId);
}
