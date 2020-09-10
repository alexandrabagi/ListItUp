package com.bignerdranch.android.listitup.activities;


import androidx.fragment.app.Fragment;

import com.bignerdranch.android.listitup.ItemFragment;
import com.bignerdranch.android.listitup.ReuseFragmentManager;

public class ItemActivity extends ReuseFragmentManager {

    @Override
    protected Fragment createFragment() {
        return new ItemFragment();
    }

    }
