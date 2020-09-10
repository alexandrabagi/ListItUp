package com.bignerdranch.android.listitup.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.viewpager2.widget.ViewPager2;

import com.bignerdranch.android.listitup.R;
import com.bignerdranch.android.listitup.ShoppingListAdapter;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;

/**
 * This Fragment hosts the pager for the tabs of "To Buy" and "In Cart" lists
 */

public class ListDemoFragment extends Fragment {

    // When requested, this adapter returns a ShoppingListFragment,
    // representing an object in the collection.
    private ShoppingListAdapter shoppingListAdapter;
    private ViewPager2 viewPager;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_listdemo, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        shoppingListAdapter = new ShoppingListAdapter(this);
        viewPager = view.findViewById(R.id.pager);
        viewPager.setAdapter(shoppingListAdapter);

        TabLayout tabLayout = view.findViewById(R.id.tab_layout);
        new TabLayoutMediator(tabLayout, viewPager,
                (tab, position) -> //tab.setText("OBJECT " + (position + 1))
                        tab.setText(position == 0 ? "TO BUY" : "IN CART")
        ).attach();

    }
}
