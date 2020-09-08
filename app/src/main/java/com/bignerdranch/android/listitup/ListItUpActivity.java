package com.bignerdranch.android.listitup;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

/**
 * not used at the moment (old start activity)
 * here we only had "show all my lists" button
 */

public class ListItUpActivity extends AppCompatActivity {

    private Button mMyListsButton;
    //private Button mAddNewListButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_listitup);

        mMyListsButton = (Button) findViewById(R.id.mylists_button);
        mMyListsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ListItUpActivity.this, ListActivity.class);
                startActivity(intent);
            }
        });

        /*mAddNewListButton = (Button) findViewById(R.id.addnewlist_button);
        mAddNewListButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });*/
    }
}
