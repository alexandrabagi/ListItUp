package com.bignerdranch.android.listitup.fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.bignerdranch.android.listitup.R;
import com.bignerdranch.android.listitup.room.ItemVM;

public class CartFragment extends Fragment {

    private ItemVM mItemVM;

    public CartFragment() {
        // Required empty public constructor
    }


//    public static CartFragment newInstance(String param1, String param2) {
//        CartFragment fragment = new CartFragment();
//        Bundle args = new Bundle();
//        args.putString(ARG_PARAM1, param1);
//        args.putString(ARG_PARAM2, param2);
//        fragment.setArguments(args);
//        return fragment;
//    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setHasOptionsMenu(true);

        mItemVM = new ViewModelProvider(requireActivity()).get(ItemVM.class);
//        System.out.println("ViewModel in CartFragment: " + mItemVM.toString());
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_cartlist, container, false);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_delete:
                // User chose the "Settings" item, show the app settings UI...
                return true;

            case R.id.action_save:
                System.out.println("!!!!!!!!!!Save pressed in CartFragment");
                // User chose the "Favorite" action, mark the current item
                // as a favorite...
                return true;

            default:
                // If we got here, the user's action was not recognized.
                // Invoke the superclass to handle it.
                return super.onOptionsItemSelected(item);

        }
    }
}
