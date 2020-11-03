package com.bignerdranch.android.listitup.activities;

import android.os.Bundle;
import android.util.Log;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import com.bignerdranch.android.listitup.R;
import com.bignerdranch.android.listitup.fragments.CartListFragment;
import com.bignerdranch.android.listitup.fragments.ShoppingListFragment;
import com.google.android.material.appbar.MaterialToolbar;
import com.google.android.material.bottomappbar.BottomAppBar;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.tabs.TabLayout;

/**
 * This activity hosts ListDemoFragment which operates the tabs of "To Buy" and "In Cart" lists
 */

public class ListActivity extends AppCompatActivity {

    MaterialToolbar appBar;
    BottomAppBar bottomAppBar;
    TabLayout tabLayout;
    private FloatingActionButton mAddNewFAB;
    private int tabPosition;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);

        appBar = findViewById(R.id.topAppBar);
        bottomAppBar = findViewById(R.id.bottomAppBar);
        tabLayout = findViewById(R.id.tab_layout);
        mAddNewFAB = findViewById(R.id.add_new_fab);
        if (tabPosition > 1) {
//            testText.setText("In Cart List");
            mAddNewFAB.setVisibility(View.INVISIBLE);
        }

        FragmentManager fm = getSupportFragmentManager();

        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                Log.d("tabs", "OnTabSelected was called");
                if(tabLayout.getSelectedTabPosition() == 0){
                    mAddNewFAB.setVisibility(View.VISIBLE);
                    tabPosition = tabLayout.getSelectedTabPosition() + 1;
                    Fragment fragment = new ShoppingListFragment();
                    Bundle args = new Bundle();
                    // Our object is just an integer
                    args.putInt(CartListFragment.ARG_OBJECT, tabLayout.getSelectedTabPosition() + 1);
                    fragment.setArguments(args);
                    fm.beginTransaction()
                            .replace(R.id.list_fragment_container, fragment)
                            .commit();
                } else {
                    tabPosition = tabLayout.getSelectedTabPosition() + 1;
                    mAddNewFAB.setVisibility(View.INVISIBLE);
                    Fragment fragment = new CartListFragment();
                    Bundle args = new Bundle();
                    // Our object is just an integer :-P
                    args.putInt(CartListFragment.ARG_OBJECT, tabLayout.getSelectedTabPosition() + 1);
                    fragment.setArguments(args);
                    fm.beginTransaction()
                            .replace(R.id.list_fragment_container, fragment)
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
                fm.beginTransaction()
                        .replace(R.id.list_fragment_container, fragment)
                        .commit();
            }
        });

        tabLayout.addTab(tabLayout.newTab().setText("TO BUY"));
        tabLayout.addTab(tabLayout.newTab().setText("IN CART"));

//        FragmentManager fm = getSupportFragmentManager();
//        Fragment fragment = fm.findFragmentById(R.id.list_fragment_container);
//
//        if (fragment == null) {
//            fragment = new ListDemoFragment();
//
//
//            fm.beginTransaction()
//                    .add(R.id.list_fragment_container, fragment)
//                    .commit();
//        }
    }
}