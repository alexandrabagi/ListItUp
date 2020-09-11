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

        LiveData<List<ShopItem>> getAllItems() {
            return mAllItems;
        }

        public void insert(ShopItem item) {
            mRepository.insert(item);
        }

        public void deleteAll() {
            mRepository.deleteAll();
        }

}
