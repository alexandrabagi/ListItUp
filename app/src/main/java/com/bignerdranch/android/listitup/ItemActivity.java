package com.bignerdranch.android.listitup;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;

public class ItemActivity extends ReuseFragmentManager {

    @Override
    protected Fragment createFragment() {
        return new ItemFragment();
    }

    }
