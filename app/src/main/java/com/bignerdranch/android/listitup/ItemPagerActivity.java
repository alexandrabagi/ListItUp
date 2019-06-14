package com.bignerdranch.android.listitup;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;

import java.util.List;
import java.util.UUID;

public class ItemPagerActivity extends AppCompatActivity {

    private ViewPager mViewPager;
    private List<Item> mItems;
    private static ListDB mListDB;
    private static final String EXTRA_ITEM_ID = "com.bignerdranch.android.criminalintent.crime_id";

    public static Intent newIntent(Context packageContext, UUID itemUUID) {
        Intent intent = new Intent(packageContext, ItemPagerActivity.class);
        intent.putExtra(EXTRA_ITEM_ID, itemUUID);
        return intent;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_item_pager);
        UUID itemId = (UUID) getIntent().getSerializableExtra(EXTRA_ITEM_ID);

        mViewPager = (ViewPager) findViewById(R.id.item_view_pager);
        mItems = ListDB.get(this).getListDB();
        FragmentManager fragmentManager = getSupportFragmentManager();
        mViewPager.setAdapter(new FragmentStatePagerAdapter(fragmentManager) {
            @Override
            public Fragment getItem(int position) {
                Item item = mItems.get(position);
                return ItemFragment.newInstance(item.getItemUUID());
            }
            @Override
            public int getCount() {return mItems.size(); }

        });

        /*
        sets the correct item to look at individually, when clicking on an item in our list
         */
        for (int i = 0; i < mItems.size(); i++) {
            if (mItems.get(i).getItemUUID().equals(itemId)) {
                mViewPager.setCurrentItem(i);
                break; }
        }
    }
}
