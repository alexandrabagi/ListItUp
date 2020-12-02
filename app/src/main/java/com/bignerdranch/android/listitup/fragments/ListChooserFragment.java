package com.bignerdranch.android.listitup.fragments;

import android.app.AlertDialog;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.RecyclerView;

import com.bignerdranch.android.listitup.R;
import com.bignerdranch.android.listitup.room.ItemVM;
import com.bignerdranch.android.listitup.room.ListInfo;
import com.bignerdranch.android.listitup.room.ListWithItems;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.List;

public class ListChooserFragment extends Fragment {

    private TextView noListsYet;
    private List<ListInfo> myShoppingLists;
    private ShoppingListAdapter mAdapter;
    private ItemVM mItemVM;
    private FloatingActionButton addNewListBtn;

    @Override
    public void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_listchooser, container, false);

        noListsYet = v.findViewById(R.id.no_lists_yet);

        addNewListBtn = v.findViewById(R.id.add_new_list_fab);

        mAdapter = new ShoppingListAdapter();
        mItemVM = ViewModelProviders.of(this).get(ItemVM.class);
        mItemVM.getAllListsWithItems().observe(getViewLifecycleOwner(), shoppingLists -> {
            mAdapter.setLists(shoppingLists);
            if (mAdapter.getItemCount() != 0) noListsYet.setVisibility(View.GONE);
            else noListsYet.setVisibility(View.VISIBLE);
        });

        return v;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        addNewListBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addNewListDialog();
            }
        });
    }

    private void addNewListDialog() {
        android.app.AlertDialog.Builder mBuilder = new AlertDialog.Builder(getActivity());

        View mView = getLayoutInflater().inflate(R.layout.dialog_add_new_list, null);
        EditText listNameInput = mView.findViewById(R.id.add_new_list_name);
        Button okBtn = mView.findViewById(R.id.add_list_add_btn);
        Button cancelBtn = mView.findViewById(R.id.add_list_cancel_btn);
        mBuilder.setView(mView);
        final AlertDialog dialog = mBuilder.create();

        // wiring buttons


        // round corners
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));

        dialog.show();
    }


    private class ShoppingListHolder extends RecyclerView.ViewHolder {

        private ListWithItems mListWithItems;

        private CardView cardView;
        private ConstraintLayout mainCard;
        private TextView listNameText;
        private TextView sumPriceText;

        private ConstraintLayout expandedCard;
        private ListView itemsOfList;
        private Button useListBtn;

        private boolean isExpanded = false;

        public ShoppingListHolder(LayoutInflater inflater, ViewGroup parent) {
            super(inflater.inflate(R.layout.card_lists, parent, false));
            cardView = itemView.findViewById(R.id.list_card_item);
            mainCard = itemView.findViewById(R.id.list_main_card);
            listNameText = itemView.findViewById(R.id.list_name_text);
            sumPriceText = itemView.findViewById(R.id.list_sum_price_text);

            expandedCard = itemView.findViewById(R.id.expanded_card);
            itemsOfList = itemView.findViewById(R.id.items_of_list);
            useListBtn = itemView.findViewById(R.id.use_list_btn);
        }

        public void bind(ListInfo listInfo, int position) {
            String listNameString = mListWithItems.getListInfo().getListName();
            listNameText.setText(listNameString);
            double sumPriceValue = mListWithItems.getListInfo().getListSumPrice();
            if (sumPriceValue > 0L) sumPriceText.setText(Double.toString(sumPriceValue));
            else sumPriceText.setText("N.a.");
            // list of items
            isExpanded = false;
        }
    }


    private class ShoppingListAdapter extends RecyclerView.Adapter<ShoppingListHolder> {

        private List<ListWithItems> mListsWithItems;

        @NonNull
        @Override
        public ShoppingListHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            return null;
        }

        @Override
        public void onBindViewHolder(@NonNull ShoppingListHolder holder, int position) {

        }

        @Override
        public int getItemCount() {
            return 0;
        }

        void setLists(List<ListWithItems> lists) {
            mListsWithItems = lists;
            notifyDataSetChanged();
        }
    }
}
