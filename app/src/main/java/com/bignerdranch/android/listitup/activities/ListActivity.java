package com.bignerdranch.android.listitup.activities;

import android.app.AlertDialog;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;


import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.ViewModelProviders;

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
import com.google.android.material.bottomappbar.BottomAppBar;
import com.google.android.material.bottomnavigation.BottomNavigationView;

/**
 * This activity hosts ListDemoFragment which operates the tabs of "To Buy" and "In Cart" lists
 */

public class ListActivity extends AppCompatActivity {

    MaterialToolbar appBar;
    BottomAppBar bottomAppBar;
    BottomNavigationView bottomNavView;
    private ItemVM mItemVM;

    private Fragment active;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);

        mItemVM = ViewModelProviders.of(this).get(ItemVM.class);

        appBar = findViewById(R.id.top_tool_bar);
        setSupportActionBar(appBar);

        // TODO have the toolbar too

//        mAddNewFAB = findViewById(R.id.add_new_fab);


        FragmentManager fm = getSupportFragmentManager();

        Bundle args = new Bundle();

//        appBar.setTitle("Home");
//        Fragment fragment = new HomeFragment();
//        fm.beginTransaction()
//                .replace(R.id.list_fragment_container, fragment)
//                .commit();

        bottomNavView = findViewById(R.id.bottom_navigation);

        showFragments();
    }

    private void showFragments() {
        Fragment fragmentHome = new HomeFragment();
        Fragment fragmentListChooser = new ListChooserFragment();
        Fragment fragmentCart = new CartFragment();
        Fragment fragmentProfile = new ProfileFragment();
        active = fragmentHome;

        FragmentManager fm = getSupportFragmentManager();

        fm.beginTransaction().add(R.id.list_fragment_container, fragmentHome, "1").show(fragmentHome).commit();
        fm.beginTransaction().add(R.id.list_fragment_container, fragmentListChooser, "2").hide(fragmentListChooser).commit();
        fm.beginTransaction().add(R.id.list_fragment_container, fragmentCart,"3").hide(fragmentCart).commit();
        fm.beginTransaction().add(R.id.list_fragment_container, fragmentProfile, "4").hide(fragmentProfile).commit();

        getSupportActionBar().setDisplayHomeAsUpEnabled(false);
        getSupportActionBar().setDisplayShowHomeEnabled(false);

        bottomNavView.setSelectedItemId(R.id.home_button);
        appBar.setTitle("Home");
        bottomNavView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                switch (item.getItemId()) {
                    case R.id.home_button:

                        appBar.setTitle("Home");

//                        Fragment fragment = new HomeFragment();
                        //                    fm.beginTransaction()
                        //                            .replace(R.id.list_fragment_container, fragment)
                        //                            .commit();
                        fm.beginTransaction()
                                .hide(active)
                                .show(fragmentHome)
                                .commit();
                        active = fragmentHome;

                        return true;

                    case R.id.list_button:
                        // Handle list fragment

                        appBar.setTitle("My Lists");

//                        Fragment fragment = new ListChooserFragment();
                        //                    fm.beginTransaction()
                        //                            .replace(R.id.list_fragment_container, fragment)
                        //                            .addToBackStack("ListChooserFragment")
                        //                            .commit();
                        fm.beginTransaction()
                                .hide(active)
                                .show(fragmentListChooser)
                                .commit();
                        active = fragmentListChooser;

                        return true;
                    case R.id.cart_button:
                        // Handle cart fragment

                        appBar.setTitle("My Cart");

//                    Fragment fragment = new CartFragment();

                        fm.beginTransaction()
                                .hide(active)
                                .show(fragmentCart)
                                .commit();
                        active = fragmentCart;

                        return true;

                    case R.id.profile_button:

                        appBar.setTitle("Profile");

//                    Fragment fragment = new ProfileFragment();
//                    fm.beginTransaction()
//                            .replace(R.id.list_fragment_container, fragment)
//                            .commit();
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

//        mAddNewFAB.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                addDialog();
//            }
//        });
    }

    public void setActiveList(long listId) {
        FragmentManager fm = getSupportFragmentManager();
        Fragment fragment = new ActiveListFragment();
        Bundle args = new Bundle();
        args.putLong("listId", listId);
        fragment.setArguments(args);
//        fm.beginTransaction()
//                .replace(R.id.list_fragment_container, fragment)
//                .addToBackStack("ActiveListFragment")
//                .commit();
        fm.beginTransaction()
                .hide(active)
                .add(R.id.list_fragment_container, fragment, "2_2")
                .show(fragment)
                .commit();
        active = fragment;

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_back);
    }

    // Back button behaviour - TODO: refactor ///////
    @Override
    public void onBackPressed() {
        FragmentManager fm = getSupportFragmentManager();
        if (fm.getBackStackEntryCount() > 0) {
            fm.popBackStack();
            getSupportActionBar().setDisplayHomeAsUpEnabled(false);
            getSupportActionBar().setDisplayShowHomeEnabled(false);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onSupportNavigateUp() {
        if (getSupportFragmentManager().getBackStackEntryCount() > 0) {
            onBackPressed();
            return true;
        } else {
            return false;
        }
    }

    ////////////////////////////////////////////////////////////////

//    public void replaceFragment(Fragment newFragment) {
//        Fragment fragment = null;
//        try {
//            fragment = (Fragment) newFragment.newInstance();
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//
//        FragmentManager fm = getSupportFragmentManager();
//        fm.beginTransaction()
//                .replace(R.id.list_fragment_container, fragment)
//                .commit();
//    }

    private void addDialog() {
        android.app.AlertDialog.Builder mBuilder = new AlertDialog.Builder(this);

        View mView = getLayoutInflater().inflate(R.layout.dialog_add_new_item, null);

        EditText mItemName = (EditText) mView.findViewById(R.id.addItemName);
        EditText mItemQuantity = (EditText) mView.findViewById(R.id.addItemQuantity);
        EditText mItemPrice = (EditText) mView.findViewById(R.id.addItemPrice);
        Button mAddButton = (Button) mView.findViewById(R.id.add_list_add_btn);
        Button mCancelButton = (Button) mView.findViewById(R.id.add_list_cancel_btn);
        mBuilder.setView(mView);
        final AlertDialog dialog = mBuilder.create();

        mAddButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
//                if (!mItemName.getText().toString().isEmpty() && !mItemQuantity.getText().toString().isEmpty()){
//                    Item newItem;
//                    if (!mItemPrice.getText().toString().isEmpty()) {
//                        newItem = new Item(mItemName.getText().toString(), Integer.parseInt(mItemQuantity.getText().toString()), 0, Float.parseFloat(mItemPrice.getText().toString()));
//                    } else {
//                        newItem = new Item(mItemName.getText().toString(), Integer.parseInt(mItemQuantity.getText().toString()), 0, 0.0f);
//                    }
//
//                    mItemVM.insertToShop(newItem);
//
//                    mItemName.setText("");
//                    mItemQuantity.setText("");
//                    mItemPrice.setText("");
//                    Toast.makeText(ListActivity.this, "You added "+ newItem.getName() + " successfully", Toast.LENGTH_SHORT).show();
//                    dialog.dismiss();
//                } else if (mItemName.getText().toString().isEmpty()) {
//                    Toast.makeText(ListActivity.this, "Please enter the name of the item", Toast.LENGTH_SHORT).show();
//                } else if (mItemQuantity.getText().toString().isEmpty()) {
//                    Toast.makeText(ListActivity.this, "Please enter the number of items you need", Toast.LENGTH_SHORT).show();
//                }
            }
        });

        mCancelButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });

        // round corners
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
//        int width = (int)(getResources().getDisplayMetrics().widthPixels*0.80);
//        int height = (int)(getResources().getDisplayMetrics().heightPixels*0.80);
//        dialog.getWindow().setLayout(width,height);

        dialog.show();
    }
}