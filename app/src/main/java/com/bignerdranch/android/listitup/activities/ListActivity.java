package com.bignerdranch.android.listitup.activities;

import android.app.AlertDialog;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;


import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.ViewModelProvider;

import com.bignerdranch.android.listitup.R;
//import com.bignerdranch.android.listitup.fragments.CartListFragment;
import com.bignerdranch.android.listitup.fragments.ActiveListFragment;
import com.bignerdranch.android.listitup.fragments.CartFragment;
import com.bignerdranch.android.listitup.fragments.HomeFragment;
import com.bignerdranch.android.listitup.fragments.ListChooserFragment;
import com.bignerdranch.android.listitup.fragments.ProfileFragment;
//import com.bignerdranch.android.listitup.fragments.ShoppingListFragment;
//import com.bignerdranch.android.listitup.room.ItemOld;
import com.bignerdranch.android.listitup.room.ItemVM;
//import com.bignerdranch.android.listitup.room.ItemVMOld;
import com.google.android.material.appbar.MaterialToolbar;
import com.google.android.material.bottomnavigation.BottomNavigationView;

/**
 * This activity hosts ListDemoFragment which operates the tabs of "To Buy" and "In Cart" lists
 */

public class ListActivity extends AppCompatActivity {

    MaterialToolbar myToolbar;
    ActionBar ab;
    BottomNavigationView bottomNavView;
    private ItemVM mItemVM;

    Fragment fragmentHome;
    Fragment fragmentListChooser;
    Fragment fragmentActiveList;
    Fragment fragmentCart;
    Fragment fragmentProfile;
    private Fragment active;
    private Fragment activeList;
    private boolean listSecondaryScreen = false;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);

//        mItemVM = ViewModelProviders.of(this).get(ItemVM.class);
        mItemVM = new ViewModelProvider(this).get(ItemVM.class);
        System.out.println("ViewModel in Activity: " + mItemVM.toString());


        myToolbar = findViewById(R.id.top_tool_bar);
        setSupportActionBar(myToolbar);
        ab = getSupportActionBar();


        fragmentHome = new HomeFragment();
        fragmentListChooser = new ListChooserFragment();
        fragmentActiveList = new ActiveListFragment();
        fragmentCart = new CartFragment();
        fragmentProfile = new ProfileFragment();

        Bundle args = new Bundle();

        bottomNavView = findViewById(R.id.bottom_navigation);

        showFragments();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.top_app_bar, menu);
        return true;
    }

    private void showFragments() {
        active = fragmentHome;
        activeList = fragmentListChooser;

        FragmentManager fm = getSupportFragmentManager();

        fm.beginTransaction().add(R.id.list_fragment_container, fragmentHome, "1").show(fragmentHome).commit();
        fm.beginTransaction().add(R.id.list_fragment_container, fragmentListChooser, "2").hide(fragmentListChooser).commit();
        fm.beginTransaction().add(R.id.list_fragment_container, fragmentCart,"3").hide(fragmentCart).commit();
        fm.beginTransaction().add(R.id.list_fragment_container, fragmentProfile, "4").hide(fragmentProfile).commit();

        ab.setTitle("Home");

        bottomNavView.setSelectedItemId(R.id.home_button);
        bottomNavView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                switch (item.getItemId()) {
                    case R.id.home_button:

                        myToolbar.setTitle("Home");
                        hideAppBarBack();

                        fm.beginTransaction()
                                .hide(active)
                                .show(fragmentHome)
                                .commit();
                        active = fragmentHome;

                        return true;

                    case R.id.list_button:
                        // Handle list fragment

                        myToolbar.setTitle("My Lists");

                        if (!listSecondaryScreen) {
                            activeList = fragmentListChooser;
                            hideAppBarBack();
                        } else {
                            showAppBarBack();
                        }
                        fm.beginTransaction()
                                .hide(active)
                                .show(activeList)
                                .commit();
                        active = activeList;

                        return true;

                    case R.id.cart_button:
                        // Handle cart fragment

                        myToolbar.setTitle("My Cart");
                        hideAppBarBack();

                        fm.beginTransaction()
                                .hide(active)
                                .show(fragmentCart)
                                .commit();
                        active = fragmentCart;

                        return true;

                    case R.id.profile_button:

                        myToolbar.setTitle("Profile");
                        hideAppBarBack();

                        fm.beginTransaction()
                                .hide(active)
                                .show(fragmentProfile)
                                .commit();
                        active = fragmentProfile;

                        return true;
                }
                return false;
            }
        });
    }

    public void setActiveList(long listId) {
        listSecondaryScreen = true;
        FragmentManager fm = getSupportFragmentManager();
//        fragmentActiveList = new ActiveListFragment();
//        activeList = fragmentActiveList;
        activeList = new ActiveListFragment();
        Bundle args = new Bundle();
        args.putLong("listId", listId);
        activeList.setArguments(args);
//        fm.beginTransaction()
//                .replace(R.id.list_fragment_container, fragment)
//                .addToBackStack("ActiveListFragment")
//                .commit();
        fm.beginTransaction()
                .hide(active)
                .add(R.id.list_fragment_container, activeList, "2_2")
                .show(activeList)
//                .addToBackStack("ActiveListFragment")
                .commit();
        active = activeList;

        showAppBarBack();
    }

    private void hideAppBarBack() {
        ab.setDisplayHomeAsUpEnabled(false);
        ab.setDisplayShowHomeEnabled(false);
    }

    private void showAppBarBack() {
        ab.setDisplayHomeAsUpEnabled(true);
        ab.setDisplayShowHomeEnabled(true);
        ab.setHomeAsUpIndicator(R.drawable.ic_back);
    }

    // Back button behaviour - TODO: refactor ///////
    @Override
    public void onBackPressed() {
        FragmentManager fm = getSupportFragmentManager();
        if (listSecondaryScreen) { // go back to list chooser
            fm.beginTransaction()
                    .hide(active)
                    .show(fragmentListChooser)
                    .commit();
            activeList = fragmentListChooser;
            active = activeList;
            listSecondaryScreen = false;
            hideAppBarBack();
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onSupportNavigateUp() {
        if (listSecondaryScreen) {
            onBackPressed();
            return true;
        } else {
            return false;
        }
    }

    ////////////////////////////////////////////////////////////////
}