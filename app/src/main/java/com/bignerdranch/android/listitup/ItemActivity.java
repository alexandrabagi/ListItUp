package com.bignerdranch.android.listitup;


import androidx.fragment.app.Fragment;

public class ItemActivity extends ReuseFragmentManager {

    @Override
    protected Fragment createFragment() {
        return new ItemFragment();
    }

    }
