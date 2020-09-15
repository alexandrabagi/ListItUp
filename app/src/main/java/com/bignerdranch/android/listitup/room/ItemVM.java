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
        private LiveData<List<ShopItem>> mAllItems;

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

        public void deleteAll() {
            mRepository.deleteAll();
        }

}
