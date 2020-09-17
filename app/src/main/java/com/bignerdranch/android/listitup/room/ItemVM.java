package com.bignerdranch.android.listitup.room;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Transformations;

import java.util.List;

/**
 * Acts as a communication center between the Repository and the UI
 * Can share data between fragments
 * Part of the lifecycle library
 */

public class ItemVM extends AndroidViewModel {

    private ItemRepository mRepository;
    private LiveData<List<ShopItem>> mAllItems;
//    private LiveData<Integer> itemIdLiveData;
//    private LiveData<ShopItem> mLiveItem = Transformations.switchMap(itemIdLiveData, itemID -> {
//        return mRepository.loadItem(itemID);
//    });

    public ItemVM (Application application) {
        super(application);
        mRepository = new ItemRepository(application);
        mAllItems = mRepository.getAllItems();
    }

    public LiveData<List<ShopItem>> getAllItems() {
        return mAllItems;
    }


    public LiveData<List<ShopItem>> getAllItemsByShops() {
        return mRepository.getAllItemsByShops();
    }

    public void insert(ShopItem item) {
        mRepository.insert(item);
    }

    public void delete(ShopItem item) {
        mRepository.delete(item);
    }

    public ShopItem loadItem(int id) {
        return mRepository.loadItem(id).getValue();
    }

    public ShopItem getItem(int id) {
        return mRepository.getItem(id);
    }

    public void deleteAll() {
            mRepository.deleteAll();
        }

}
