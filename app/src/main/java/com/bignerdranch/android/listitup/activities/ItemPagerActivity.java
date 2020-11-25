package com.bignerdranch.android.listitup.activities;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.viewpager.widget.ViewPager;
import androidx.viewpager2.adapter.FragmentStateAdapter;

//import com.bignerdranch.android.listitup.fragments.ItemDetailFragment;
import com.bignerdranch.android.listitup.fragments.ItemFragment;
import com.bignerdranch.android.listitup.R;
import com.bignerdranch.android.listitup.room.Item;
import com.bignerdranch.android.listitup.room.ItemVM;
import com.google.android.material.appbar.MaterialToolbar;
import com.google.android.material.bottomappbar.BottomAppBar;

import java.util.ArrayList;
import java.util.List;

/*public class ItemPagerActivity extends AppCompatActivity {

    private ViewPager mViewPager;
    private List<Item> mItems;
    private ItemVM mItemVM;
    public static final String EXTRA_ITEM_ID = "com.bignerdranch.android.criminalintent.crime_id";

    private static final String TAG = "pager";

    MaterialToolbar appBar;
    BottomAppBar bottomAppBar;

    public static Intent newIntent(Context packageContext, int itemID) {
        Intent intent = new Intent(packageContext, ItemPagerActivity.class);
        intent.putExtra(EXTRA_ITEM_ID, itemID);
        return intent;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_item_pager);

        int itemId = (int) getIntent().getIntExtra(EXTRA_ITEM_ID, 0);

        mItems = new ArrayList<>();

        mViewPager = (ViewPager) findViewById(R.id.item_view_pager);
        FragmentManager fragmentManager = getSupportFragmentManager();

        mItemVM = ViewModelProviders.of(this).get(ItemVM.class);
        mItemVM.getAllItemsByShops().observe(this, new Observer<List<Item>>() {
            @Override
            public void onChanged(List<Item> items) {
                if (items != null) {
                    mItems = items;
                    // sets the correct item to look at individually, when clicking on an item in our list

                    mViewPager.setAdapter(new FragmentStatePagerAdapter(fragmentManager) {
                        @Override
                        public Fragment getItem(int position) {
                            Item item = mItems.get(position);
                            return ItemDetailFragment.newInstance(item.getId(), item.getName(), item.getShopName(), item.getQuantity());
                        }
                        @Override
                        public int getCount() {
                            return mItems.size();
                        }
                    });
                    for (int i = 0; i < mItems.size(); i++) {
                        if (mItems.get(i).getId() == itemId) {
                            mViewPager.setCurrentItem(i);
                            break;
                        } else continue;
                    }

                } else {
                    Log.i(TAG, "mItems was null");
                }
            }
        });

        appBar = findViewById(R.id.topAppBar);
        bottomAppBar = findViewById(R.id.bottomAppBar);
    }

//    private void getAllItems() {
//        Log.i(TAG, "getAllItems was called");
//        Log.i(TAG, "mItemVM check: " + (mItemVM == null));
//        mItemVM.getAllItems().observe(this, new Observer<List<ShopItem>>() {
//            @Override
//            public void onChanged(List<ShopItem> items) {
//                if (items != null) {
//                    mItems = items;
//                    Log.i(TAG, "mItems check 2: " + mItems.size());
//                } else {
//                    Log.i(TAG, "mItems was null");
//                }
//            }
//        });
//    }

}
*/