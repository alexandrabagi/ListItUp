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

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_delete:
                // User chose the "Settings" item, show the app settings UI...
                return true;

            case R.id.action_save:
                // User chose the "Favorite" action, mark the current item
                // as a favorite...
                return true;

            default:
                // If we got here, the user's action was not recognized.
                // Invoke the superclass to handle it.
                return super.onOptionsItemSelected(item);

        }
    }

    // Back button behaviour - TODO: refactor ///////
    @Override
    public void onBackPressed() {
        FragmentManager fm = getSupportFragmentManager();
        if (listSecondaryScreen) { // go back to list chooser
//            activeList = new ListChooserFragment();
//        }
//        if (activeList == fragmentListChooser) {
//            super.onBackPressed();
//        } else if (activeList == fragmentActiveList) {
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