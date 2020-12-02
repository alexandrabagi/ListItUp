package com.bignerdranch.android.listitup.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;

import com.bignerdranch.android.listitup.R;
import com.bignerdranch.android.listitup.fragments.ActiveListFragment;
import com.bignerdranch.android.listitup.room.ItemVM;

public class ActiveListActivity extends AppCompatActivity {

    private ItemVM mItemVM;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_active_list);

        FragmentManager fm = getSupportFragmentManager();
        Fragment fragment = new ActiveListFragment();
        fm.beginTransaction()
                .add(R.id.active_list_fragment_container, fragment)
                .commit();
    }
}
