//package com.bignerdranch.android.listitup.activities;
//
//import androidx.annotation.NonNull;
//import androidx.appcompat.app.AppCompatActivity;
//import androidx.fragment.app.Fragment;
//import androidx.fragment.app.FragmentManager;
//import androidx.fragment.app.FragmentTransaction;
//import androidx.lifecycle.LifecycleOwner;
//import androidx.lifecycle.ViewModelProviders;
//
//import android.os.Bundle;
//import android.widget.TextView;
//
//import com.bignerdranch.android.listitup.R;
//import com.bignerdranch.android.listitup.fragments.ActiveListFragment;
//import com.bignerdranch.android.listitup.room.Item;
//import com.bignerdranch.android.listitup.room.ItemVM;
//
//import java.util.List;
//
//public class ActiveListActivity extends AppCompatActivity {
//
//    private ItemVM mItemVM;
//    private TextView mActiveListNameText;
//    private List<Item> mListItems;
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_active_list);
//
//        long listId = getIntent().getLongExtra("listId", 0L);
//        mActiveListNameText = findViewById(R.id.active_list_name_text);
//
//        mItemVM = ViewModelProviders.of(this).get(ItemVM.class);
////        mItemVM.getListName(listId).observe(this, listName -> {
////            mActiveListNameText.setText(listName);
////        });
//        mItemVM.getListWithItems(listId).observe(this, listWithItems -> {
//            mActiveListNameText.setText(listWithItems.getListInfo().getListName());
//            mListItems = listWithItems.getListItems();
//        });
//    }
//}
