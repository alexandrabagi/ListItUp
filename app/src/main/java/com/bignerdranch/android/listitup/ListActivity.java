package com.bignerdranch.android.listitup;

import android.app.AlertDialog;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

/**
 * should shows the current list itself
 * entry screen
 */

public class ListActivity extends ReuseFragmentManager {

    //private ListDB listDB;

    @Override
    protected Fragment createFragment() {
        return new ListFragment();
    }

   /* @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //listDB = ListDB.get(this);
        setContentView(R.layout.activity_list);



        FragmentManager fm = getSupportFragmentManager();
        Fragment fragment = fm.findFragmentById(R.id.list_fragment_container);

        if (fragment == null) {
            fragment = new ListFragment();
            fm.beginTransaction()
                    .add(R.id.list_fragment_container, fragment)
                    .commit();
        }*/
    }
