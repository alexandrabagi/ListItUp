package com.bignerdranch.android.listitup;

import androidx.fragment.app.Fragment;

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
