package com.bignerdranch.android.listitup;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.bignerdranch.android.listitup.fragments.CartListFragment;
import com.bignerdranch.android.listitup.fragments.ShoppingListFragment;

/**
 * This adapter operates the tabs of "To Buy" and "In Cart" lists
 */

// source: https://developer.android.com/guide/navigation/navigation-swipe-view-2

public class ShoppingListAdapter extends FragmentStateAdapter {

    public ShoppingListAdapter(Fragment fragment) {
        super(fragment);
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        // Return a NEW fragment instance in createFragment(int)
        Fragment fragment;
        if (position == 0) {
            fragment = new ShoppingListFragment();
            Bundle args = new Bundle();
            // Our object is just an integer :-P
            args.putInt(ShoppingListFragment.ARG_OBJECT, position + 1);
            fragment.setArguments(args);
        } else {
            fragment = new CartListFragment();
            Bundle args = new Bundle();
            // Our object is just an integer :-P
            args.putInt(CartListFragment.ARG_OBJECT, position + 1);
            fragment.setArguments(args);
        }

        return fragment;
    }

    @Override
    public int getItemCount() {
        return 2;
    }
}