package com.bignerdranch.android.listitup.activities;

import android.content.Intent;
import android.os.Handler;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import com.bignerdranch.android.listitup.R;

public class MainActivity extends AppCompatActivity {

    Handler mHandler;

    private Runnable mLaunchTask = new Runnable() {
        @Override
        public void run() {
            Intent i = new Intent(getApplicationContext(), LoginActivity.class);
            startActivity(i);
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

//        getSupportActionBar().hide();
        getWindow().setStatusBarColor(ContextCompat.getColor(this,R.color.Primary_500)); //status bar or the time bar at the top


        mHandler = new Handler();
        mHandler.postDelayed(mLaunchTask, 1000);
    }
}
