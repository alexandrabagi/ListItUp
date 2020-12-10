package com.bignerdranch.android.listitup.fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import android.view.LayoutInflater;
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

        mItemVM = new ViewModelProvider(requireActivity()).get(ItemVM.class);
//        System.out.println("ViewModel in CartFragment: " + mItemVM.toString());
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_cartlist, container, false);
    }
}
