package com.bignerdranch.android.listitup.fragments;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bignerdranch.android.listitup.Item;
import com.bignerdranch.android.listitup.ListDB;
import com.bignerdranch.android.listitup.R;
import com.bignerdranch.android.listitup.activities.ItemPagerActivity;

import java.util.List;
import java.util.Observable;
import java.util.Observer;

/**
 * This fragment hosts "To Buy" and "In Cart" lists
 */

public class ShoppingListFragment extends Fragment implements Observer {

//    public static final String ARG_OBJECT = "object";
//    private TextView myText;

    private Button mAddNewButton;

    private ItemAdapter mAdapter;

    private RecyclerView mItemRecyclerView;
    private ListDB listDB;
    private List<Item> mItems;

//    @Nullable
//    @Override
//    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
//                             @Nullable Bundle savedInstanceState) {
//        return inflater.inflate(R.layout.fragment_shoppinglist, container, false);
//    }

//    @Override
//    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
//        Bundle args = getArguments();
//        System.out.println(args);
////        myText = ((TextView) view.findViewById(android.R.id.text1));
////        myText.setText(Integer.toString(args.getInt(ARG_OBJECT)));
//    }

    @Override
    public void update(Observable observable, Object data) {
        mAdapter.notifyDataSetChanged();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        listDB = ListDB.get(getActivity());
        mItems = listDB.getListDB();
        listDB.addObserver(this);
        System.out.println("ShoppingListFragment onCreate was called");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_shoppinglist, container, false);
        System.out.println("ShoppingListFragment onCreateView was called");

        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        System.out.println("ShoppingListFragment onViewCreated was called");

        mItemRecyclerView = view.findViewById(R.id.items_recycler_view);
        mItemRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        mAddNewButton = (Button) view.findViewById(R.id.addnew_button);
        mAddNewButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder mBuilder = new AlertDialog.Builder(getActivity());
                // 1. parameter: Resource File, 2. view group --> null to be changed later on, in example
                //view group is in other dialog
                View mView = getLayoutInflater().inflate(R.layout.dialog_entername, null);
                final EditText mItemName = (EditText) mView.findViewById(R.id.itemName);
                //final EditText mShop = (EditText) mView.findViewById(R.id.ShopName);
                final EditText mQuantity = (EditText) mView.findViewById(R.id.Quantity);


                //static spinner experiment begin
                Spinner staticSpinner = (Spinner) mView.findViewById(R.id.static_spinner2);

                // Create an ArrayAdapter using the string array and a default spinner
                ArrayAdapter<CharSequence> staticAdapter = ArrayAdapter
                        .createFromResource(getContext(), R.array.brew_array,
                                android.R.layout.simple_spinner_item);

                // Specify the layout to use when the list of choices appears
                staticAdapter
                        .setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

                // Apply the adapter to the spinner
                staticSpinner.setAdapter(staticAdapter);
                final Spinner mShop = (Spinner) mView.findViewById(R.id.static_spinner2);

                // static spinner experiment end

                //final Item newItem = new Item(mItemName.toString(), mShop.toString(), mQuantity.toString());
                Button mOkButton = (Button) mView.findViewById(R.id.ok_button);
                mBuilder.setView(mView);
                final AlertDialog dialog = mBuilder.create();

                mOkButton.setOnClickListener(new View.OnClickListener(){
                    @Override
                    public void onClick(View view) {
                        if (!mItemName.getText().toString().isEmpty()){
                            //Toast.makeText(ListActivity.this, "you added successfully", Toast.LENGTH_SHORT).show();
                            Item newItem = new Item(mItemName.getText().toString(), mShop.getSelectedItem().toString(), mQuantity.getText().toString());
                            listDB.addToDB(newItem);

                            mItemName.setText("");
                            //mShop.setText("");
                            mQuantity.setText("");
                            dialog.dismiss();
                            updateUI();
                        }
                        else{
                            Toast.makeText(getActivity(), "Please enter name of item", Toast.LENGTH_SHORT).show();
                        }
                    }
                });

                dialog.show();
            }
        });

        updateUI();

        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(simpleItemTouchCallback);
        itemTouchHelper.attachToRecyclerView(mItemRecyclerView);
    }

    @Override
    public void onResume() {
        super.onResume();
        updateUI();
    }

    private void updateUI() {
        ListDB listDB = ListDB.get(getActivity());
        List<Item> items = listDB.getListDB();

        if (mAdapter == null) {
            mAdapter = new ItemAdapter(mItems);
            mItemRecyclerView.setAdapter(mAdapter);
        }
        else {
            mAdapter.setList(items);
            mAdapter.notifyDataSetChanged();
        }
    }


    private class ItemHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private Item mItem;
        private TextView mThingNo;
        private TextView itemName;
        private TextView shopName;
        private TextView quantity;

        public ItemHolder(LayoutInflater inflater, ViewGroup parent) {
            super(inflater.inflate(R.layout.list_item, parent, false));
            itemName = itemView.findViewById(R.id.what_item);
            mThingNo = itemView.findViewById(R.id.no_item);
            shopName = itemView.findViewById(R.id.where_item);
            quantity = itemView.findViewById(R.id.quantity_item);
            itemView.setOnClickListener(this);
        }

        public void bind(Item item, int position) {
            mItem = item;
            mThingNo.setText(" "+ (position+1) +" "); // returns index+1 of item

            itemName.setText(mItem.getWhat());
            shopName.setText(mItem.getShop());
            quantity.setText(mItem.getQuantity());
        }

        /*
        When clicking on list item we start instance of ItemPagerActivity
         */
        @Override
        public void onClick(View view) {
            Intent intent = ItemPagerActivity.newIntent(getActivity(), mItem.getItemUUID());
            startActivity(intent);
        }
    }

    private class ItemAdapter extends RecyclerView.Adapter<ItemHolder> {

        private List<Item> mList;

        public ItemAdapter(List<Item> items) {
            mList = items;
        }

        @Override
        public ItemHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            LayoutInflater layoutInflater = LayoutInflater.from(getActivity());
            return new ItemHolder(layoutInflater, parent);
        }

        @Override
        public void onBindViewHolder(@NonNull ItemHolder holder, int position) {
            Item item = ListDB.get(getContext()).getListDB().get(position);
            holder.bind(item, position);

            switch (item.getShop()) {
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
            return ListDB.get(getContext()).getListDB().size();
        }

        public void setList(List<Item> items) {
            mList = items;
        }
    }


    ItemTouchHelper.SimpleCallback simpleItemTouchCallback = new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT) {
        // other dirs:  | ItemTouchHelper.RIGHT | ItemTouchHelper.DOWN | ItemTouchHelper.UP

        @Override
        public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {
            return false;
        }

        @Override
        public void onSwiped(RecyclerView.ViewHolder viewHolder, int swipeDir) {
            //Remove swiped item from list and notify the RecyclerView
            //But where to remove from???
            int position = viewHolder.getAdapterPosition();
            Item itemToRemove = mAdapter.mList.get(position);
            listDB.deleteItem(itemToRemove.getWhat());
            mAdapter.notifyDataSetChanged();

            updateUI();
        }
    };
}