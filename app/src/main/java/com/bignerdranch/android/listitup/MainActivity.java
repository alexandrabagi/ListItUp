package com.bignerdranch.android.listitup;

import android.content.Intent;
import android.os.Handler;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

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

        getSupportActionBar().hide();

        mHandler = new Handler();
        mHandler.postDelayed(mLaunchTask, 2000);
    }
}
