package com.bignerdranch.android.listitup.fragments;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.RecyclerView.Adapter;

import com.bignerdranch.android.listitup.R;
import com.bignerdranch.android.listitup.activities.ItemPagerActivity;
import com.bignerdranch.android.listitup.room.Item;
import com.bignerdranch.android.listitup.room.ItemVM;

import java.text.DecimalFormat;
import java.util.List;
import java.util.Observable;
import java.util.Observer;

import static com.bignerdranch.android.listitup.activities.ItemPagerActivity.EXTRA_ITEM_ID;

/**
 * This fragment hosts "To Buy" and "In Cart" lists
 */

public class CartListFragment extends Fragment implements Observer {

    public static final String ARG_OBJECT = "object";
    private static final String TAG = "price";

    @Override
    public void update(Observable o, Object arg) {
        /////
    }

    private CartItemAdapter mAdapter;

    private RecyclerView mItemRecyclerView;
    private TextView testText;
    private int tabPosition;
    private TextView mPriceText;
    private float totalPrice;

    private ItemVM mItemVM;

    private DecimalFormat decimalFormat;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        tabPosition = getArguments().getInt(ARG_OBJECT);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        Log.d(TAG, "CartListFragment onCreateView was called");
        View view = inflater.inflate(R.layout.fragment_cartlist, container, false);

        decimalFormat = new DecimalFormat("0.00");

        mPriceText = view.findViewById(R.id.price_text);

        mAdapter = new CartItemAdapter(getActivity());

        mItemVM = new ViewModelProvider(this).get(ItemVM.class);
//        mItemVM.getAllItems().observe(getViewLifecycleOwner(), new androidx.lifecycle.Observer<List<ShopItem>>() {
//
//            @Override
//            public void onChanged(List<ShopItem> shopItems) {
//                mAdapter.setItems(shopItems);
//            }
//        });
//        mItemVM.getAllCartItems().observe(getViewLifecycleOwner(), cartItems -> {
//            mAdapter.setItems(cartItems);
//            for (Item item : cartItems) {
//                totalPrice += item.getPrice();
//                Log.d(TAG, "Item price: " + item.getPrice());
//                Log.d(TAG, "Total price: " + totalPrice);
//            }
//            mPriceText = view.findViewById(R.id.price_text);
//            mPriceText.setText(decimalFormat.format(totalPrice));
//            Log.d(TAG, "Total price set");
//        });
        updateUI();



        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        Log.d(TAG, "CartListFragment onViewCreated was called");

        mItemRecyclerView = view.findViewById(R.id.items_recycler_view);
        mItemRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        mItemRecyclerView.setAdapter(mAdapter); // added

        testText = view.findViewById(R.id.test_text);
        if (tabPosition > 1) {
            testText.setText("In Cart List");
        } else {
            testText.setText("To Buy List");
        }

        updateUI();

        ItemTouchHelper itemTouchHelperR = new ItemTouchHelper(simpleItemTouchCallbackR);
        ItemTouchHelper itemTouchHelperL = new ItemTouchHelper(simpleItemTouchCallbackL);
        itemTouchHelperR.attachToRecyclerView(mItemRecyclerView);
        itemTouchHelperL.attachToRecyclerView(mItemRecyclerView);
    }

    @Override
    public void onResume() {
        super.onResume();
        updateUI();
    }

    private void updateUI() {
        mItemVM.getAllCartItems().observe(getViewLifecycleOwner(), cartItems -> {
            totalPrice = 0.00f;
            mAdapter.setItems(cartItems);
            for (Item item : cartItems) {
                totalPrice += (item.getPrice() * item.getQuantity());
                Log.d(TAG, "Item price: " + item.getPrice());
                Log.d(TAG, "Total price: " + totalPrice);
            }
            mPriceText.setText(decimalFormat.format(totalPrice));
            Log.d(TAG, "Total price set");
        });
    }



    ///RECYCLERVIEW CODE///
    private class CartItemHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private Item mItem;
        private TextView mThingNo;
        private TextView itemName;
        private TextView shopName;
        private TextView quantity;
        private TextView price;

        public CartItemHolder(LayoutInflater inflater, ViewGroup parent) {
            super(inflater.inflate(R.layout.list_item, parent, false));
            itemName = itemView.findViewById(R.id.what_item);
            mThingNo = itemView.findViewById(R.id.no_item);
            shopName = itemView.findViewById(R.id.where_item);
            quantity = itemView.findViewById(R.id.quantity_item);
            price = itemView.findViewById(R.id.price_item);
            itemView.setOnClickListener(this);
        }

        public void bind(Item item, int position) {
            mItem = item;
            mThingNo.setText(" " + item.getId() + " ");

            itemName.setText(mItem.getName());
            shopName.setText(mItem.getShopName());
            quantity.setText(Integer.toString(mItem.getQuantity()));
            price.setText(decimalFormat.format(mItem.getPrice()));
        }

        /*
        When clicking on list item we start instance of ItemPagerActivity
         */
        @Override
        public void onClick(View view) {
            Intent intent = new Intent(getActivity(), ItemPagerActivity.class);
            intent.putExtra(EXTRA_ITEM_ID, Integer.valueOf(mItem.getId()));
            startActivity(intent);
        }
    }

    private class CartItemAdapter extends Adapter<CartItemHolder> {

        private List<Item> mItems;

        public CartItemAdapter(Context context) {
//            LayoutInflater inflater = LayoutInflater.from(context);
        }

        @Override
        public CartItemHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            LayoutInflater layoutInflater = LayoutInflater.from(getActivity());
            return new CartItemHolder(layoutInflater, parent);
        }

        @Override
        public void onBindViewHolder(@NonNull CartItemHolder holder, int position) {
            Item item = mItems.get(position);
            holder.bind(item, position);

            switch (item.getShopName()) {
                case "Lidl": holder.itemView.setBackgroundColor(getResources().getColor(R.color.LidlColor));
                    break;
                case "Bilka": holder.itemView.setBackgroundColor(getResources().getColor(R.color.BilkaColor));
                    break;
                case "Rema1000": holder.itemView.setBackgroundColor(getResources().getColor(R.color.RemaColor));
                    break;
                case "Netto": holder.itemView.setBackgroundColor(getResources().getColor(R.color.NettoColor));
                    break;
                case "Aldi": holder.itemView.setBackgroundColor(getResources().getColor(R.color.AldiColor));
                    break;
            }
        }

        @Override
        public int getItemCount() {
            if (mItems != null) {
                return mItems.size();
            } else return 0;

        }

        void setItems(List<Item> items) {
            mItems = items;
            notifyDataSetChanged();
        }
    }


    ItemTouchHelper.SimpleCallback simpleItemTouchCallbackR = new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.RIGHT) {
        // other dirs:  | ItemTouchHelper.RIGHT | ItemTouchHelper.DOWN | ItemTouchHelper.UP

        @Override
        public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {
            return false;
        }

        @Override
        public void onSwiped(RecyclerView.ViewHolder viewHolder, int swipeDir) {
            //Remove swiped item from list and notify the RecyclerView
            int position = viewHolder.getAdapterPosition();
            Item itemToRemove = mAdapter.mItems.get(position);
            mItemVM.deleteFromCart(itemToRemove);
            mAdapter.notifyDataSetChanged();

            totalPrice -= itemToRemove.getPrice();
            updateUI();
        }
    };

    ItemTouchHelper.SimpleCallback simpleItemTouchCallbackL = new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT) {
        // other dirs:  | ItemTouchHelper.RIGHT | ItemTouchHelper.DOWN | ItemTouchHelper.UP

        @Override
        public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {
            return false;
        }

        @Override
        public void onSwiped(RecyclerView.ViewHolder viewHolder, int swipeDir) {
            //Remove swiped item from list and notify the RecyclerView
            int position = viewHolder.getAdapterPosition();
            Item itemToRemove = mAdapter.mItems.get(position);
            mItemVM.putToShop(itemToRemove);
            mAdapter.notifyDataSetChanged();

            updateUI();
        }
    };
}