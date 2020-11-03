package com.bignerdranch.android.listitup.fragments;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.viewpager2.widget.ViewPager2;

import com.bignerdranch.android.listitup.R;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;

/**
 * This Fragment hosts the pager for the tabs of "To Buy" and "In Cart" lists
 */

/* public class ListDemoFragment extends Fragment {

    // When requested, this adapter returns a ShoppingListFragment,
    // representing an object in the collection.
//    private ShoppingListAdapter shoppingListAdapter;
    private ViewPager2 viewPager;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_listdemo, container, false);

        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {

        TabLayout tabLayout = view.findViewById(R.id.tab_layout);

        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                Log.d("tabs", "OnTabSelected was called");
                if(tabLayout.getSelectedTabPosition() == 0){
                    Fragment fragment = new ShoppingListFragment();
                    Bundle args = new Bundle();
                    // Our object is just an integer
                    args.putInt(CartListFragment.ARG_OBJECT, tabLayout.getSelectedTabPosition() + 1);
                    fragment.setArguments(args);
                    getChildFragmentManager()
                            .beginTransaction()
                            .replace(R.id.fragment_container, fragment)
                            .commit();
                } else {
                    Fragment fragment = new CartListFragment();
                    Bundle args = new Bundle();
                    // Our object is just an integer :-P
                    args.putInt(CartListFragment.ARG_OBJECT, tabLayout.getSelectedTabPosition() + 1);
                    fragment.setArguments(args);
                    getChildFragmentManager()
                            .beginTransaction()
                            .replace(R.id.fragment_container, fragment)
                            .commit();
                }
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {
                Log.d("tabs", "OnTabReselected was called");
                Fragment fragment = new ShoppingListFragment();
                Bundle args = new Bundle();
                // Our object is just an integer
                args.putInt(CartListFragment.ARG_OBJECT, tabLayout.getSelectedTabPosition() + 1);
                fragment.setArguments(args);
                getChildFragmentManager()
                        .beginTransaction()
                        .replace(R.id.fragment_container, fragment)
                        .commit();
            }
        });

        tabLayout.addTab(tabLayout.newTab().setText("TO BUY"));
        tabLayout.addTab(tabLayout.newTab().setText("IN CART"));
    }
} */
