package com.bignerdranch.android.listitup.activities;

import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;


import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.ViewModelProviders;

import com.bignerdranch.android.listitup.R;
import com.bignerdranch.android.listitup.fragments.CartListFragment;
import com.bignerdranch.android.listitup.fragments.ShoppingListFragment;
import com.bignerdranch.android.listitup.room.Item;
import com.bignerdranch.android.listitup.room.ItemVM;
import com.google.android.material.appbar.MaterialToolbar;
import com.google.android.material.bottomappbar.BottomAppBar;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

/**
 * This activity hosts ListDemoFragment which operates the tabs of "To Buy" and "In Cart" lists
 */

public class ListActivity extends AppCompatActivity {

    MaterialToolbar appBar;
    BottomAppBar bottomAppBar;
    BottomNavigationView bottomNavView;
//    TabLayout tabLayout;
    private FloatingActionButton mAddNewFAB;
//    private int tabPosition;
    private ItemVM mItemVM;

//    private TextView mSubtitle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);

        mItemVM = ViewModelProviders.of(this).get(ItemVM.class);
//        mItemVM.getAllItemsByShops().observe(getViewLifecycleOwner(), shopItems -> mAdapter.setItems(shopItems));

        appBar = findViewById(R.id.top_tool_bar);
        // TODO have the toolbar too

        mAddNewFAB = findViewById(R.id.add_new_fab);

//        mSubtitle = findViewById(R.id.subtitle);


        FragmentManager fm = getSupportFragmentManager();
//        bottomAppBar = findViewById(R.id.bottom_app_bar); // do we need it?\

        Bundle args = new Bundle();

        Fragment fragment = new ShoppingListFragment();
        args.putInt(ShoppingListFragment.ARG_OBJECT, 0); // check
        fragment.setArguments(args);
        fm.beginTransaction()
                .replace(R.id.list_fragment_container, fragment)
                .commit();

        bottomNavView = findViewById(R.id.bottom_navigation);
        bottomNavView.setSelectedItemId(R.id.list_button);
        bottomNavView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                if (item.getItemId() == R.id.list_button) {
                    // Handle list fragment
                    mAddNewFAB.setVisibility(View.VISIBLE);

                    Fragment fragment = new ShoppingListFragment();
                    Bundle args = new Bundle();
                    args.putInt(ShoppingListFragment.ARG_OBJECT, 0); // check
                    fragment.setArguments(args);
                    fm.beginTransaction()
                            .replace(R.id.list_fragment_container, fragment)
                            .commit();

                    return true;
                } else if (item.getItemId() == R.id.cart_button) {
                    // Handle cart fragment
                    mAddNewFAB.setVisibility(View.INVISIBLE);

                    Fragment fragment = new ShoppingListFragment();
                    Bundle args = new Bundle();
                    args.putInt(ShoppingListFragment.ARG_OBJECT, 1);
                    fragment.setArguments(args);
                    fm.beginTransaction()
                            .replace(R.id.list_fragment_container, fragment)
                            .commit();

                    return true;
                } else {
                    return false;
                }
            }
        });

        mAddNewFAB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getDialog();
            }
        });
    }

    private void getDialog() {
        android.app.AlertDialog.Builder mBuilder = new android.app.AlertDialog.Builder(this);
        // 1. parameter: Resource File, 2. view group --> null to be changed later on, in example
        //view group is in other dialog
        View mView = getLayoutInflater().inflate(R.layout.dialog_entername, null);
        final EditText mItemName = (EditText) mView.findViewById(R.id.itemName);
        //final EditText mShop = (EditText) mView.findViewById(R.id.ShopName);
        final EditText mQuantity = (EditText) mView.findViewById(R.id.Quantity);


        //static spinner experiment begin
        Spinner staticSpinner = (Spinner) mView.findViewById(R.id.static_spinner2);

        // Create an ArrayAdapter using the string array and a default spinner
        ArrayAdapter<CharSequence> staticAdapter = ArrayAdapter
                .createFromResource(this, R.array.brew_array,
                        android.R.layout.simple_spinner_item);

        // Specify the layout to use when the list of choices appears
        staticAdapter
                .setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        // Apply the adapter to the spinner
        staticSpinner.setAdapter(staticAdapter);
        final Spinner mShop = (Spinner) mView.findViewById(R.id.static_spinner2);

        // static spinner experiment end

        //final Item newItem = new Item(mItemName.toString(), mShop.toString(), mQuantity.toString());
        Button mOkButton = (Button) mView.findViewById(R.id.ok_button);
        mBuilder.setView(mView);
        final android.app.AlertDialog dialog = mBuilder.create();

        mOkButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                if (!mItemName.getText().toString().isEmpty()){
                    //Toast.makeText(ListActivity.this, "you added successfully", Toast.LENGTH_SHORT).show();
                    Item newItem = new Item(mItemName.getText().toString(), mShop.getSelectedItem().toString(), Integer.parseInt(mQuantity.getText().toString()), 0);
                    mItemVM.insertToShop(newItem);

                    mItemName.setText("");
                    //mShop.setText("");
                    mQuantity.setText("");
                    dialog.dismiss();
//                    updateUI();
                }
                else{
                    Toast.makeText(getApplicationContext(), "Please enter name of item", Toast.LENGTH_SHORT).show();
                }
            }
        });

        dialog.show();
    }
}