package com.bignerdranch.android.listitup.fragments;

import android.app.AlertDialog;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.transition.AutoTransition;
import android.transition.TransitionManager;
import android.view.LayoutInflater;
import android.view.MenuItem;
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
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bignerdranch.android.listitup.R;
import com.bignerdranch.android.listitup.activities.ListActivity;
import com.bignerdranch.android.listitup.room.Item;
import com.bignerdranch.android.listitup.room.ItemVM;
import com.bignerdranch.android.listitup.room.ListInfo;
import com.bignerdranch.android.listitup.room.ListWithItems;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

public class ListChooserFragment extends Fragment {

    private TextView noListsYet;
    private List<ListWithItems> myShoppingListsWithItems;
    private ShoppingListAdapter mAdapter;
    private ItemVM mItemVM;
    private FloatingActionButton addNewListBtn;

    @Override
    public void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        setHasOptionsMenu(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_listchooser, container, false);

        noListsYet = v.findViewById(R.id.no_lists_yet);

        addNewListBtn = v.findViewById(R.id.add_new_list_fab);

        mAdapter = new ShoppingListAdapter();
//        mItemVM = ViewModelProviders.of(this).get(ItemVM.class);
        mItemVM = new ViewModelProvider(requireActivity()).get(ItemVM.class);
        System.out.println("ViewModel in ListChooserFragment: " + mItemVM.toString());

        mItemVM.getAllListsWithItems().observe(getViewLifecycleOwner(), shoppingLists -> {
            myShoppingListsWithItems = shoppingLists;
        });
        mItemVM.getAllListInfos().observe(getViewLifecycleOwner(), allMyLists -> {
            System.out.println("!!!!!!! allMyLists: " + allMyLists.size());
            mAdapter.setContent(allMyLists);
            System.out.println("!!!!!!! itemCount: " + mAdapter.getItemCount());
            if (mAdapter.getItemCount() != 0) noListsYet.setVisibility(View.GONE);
            else noListsYet.setVisibility(View.VISIBLE);
        });

        return v;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        RecyclerView listsRecyclerView = view.findViewById(R.id.my_lists_recycler_view);
        listsRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        listsRecyclerView.setAdapter(mAdapter);

        addNewListBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addNewListDialog();
            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_delete:
                // User chose the "Settings" item, show the app settings UI...
                return true;

            case R.id.action_save:
                System.out.println("!!!!!!!!!!Save pressed in ListChooserFragment");
                // User chose the "Favorite" action, mark the current item
                // as a favorite...
                return true;

            default:
                // If we got here, the user's action was not recognized.
                // Invoke the superclass to handle it.
                return super.onOptionsItemSelected(item);

        }
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
        okBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String newListName = listNameInput.getText().toString();
                ListInfo listToAdd = new ListInfo(newListName);
                mItemVM.addNewList(listToAdd);
                mAdapter.notifyDataSetChanged();
                dialog.dismiss();
            }
        });

        cancelBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

        // round corners
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));

        dialog.show();
    }


    private class ShoppingListHolder extends RecyclerView.ViewHolder {

//        private ListWithItems mListWithItems;

        private CardView cardView;
        private ConstraintLayout mainCard;
        private TextView listNameText;
        private TextView sumPriceText;

        private ConstraintLayout expandedCard;
        private TextView itemsOfList;
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
            String listNameString = listInfo.getListName();
            listNameText.setText(listNameString);
            double sumPriceValue = listInfo.getListSumPrice();
            if (sumPriceValue > 0L) sumPriceText.setText(Double.toString(sumPriceValue));
            else sumPriceText.setText("N.a.");
            // list of items
            useListBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
//                    Intent newIntent = new Intent(getActivity(), ActiveListActivity.class);
//                    newIntent.putExtra("listId", listInfo.getListId());
//                    startActivity(newIntent);
                    ((ListActivity)getActivity()).setActiveList(listInfo.getListId());
                    collapseCard();
                }
            });
            isExpanded = false;
        }

        public void expandCard(ListInfo listInfo) {
            TransitionManager.beginDelayedTransition(cardView, new AutoTransition());
            expandedCard.setVisibility(View.VISIBLE);
            itemsOfList.setText(buildItemListText(listInfo));
            isExpanded = true;
        }

        public void collapseCard() {
            TransitionManager.beginDelayedTransition(expandedCard, new AutoTransition());
            expandedCard.setVisibility(View.GONE);
            isExpanded = false;
        }

        private String buildItemListText(ListInfo listInfo) {
            String itemListText = "";
            StringBuilder sb = new StringBuilder();
            List<Item> listItems = new ArrayList<>();
            for (ListWithItems element : myShoppingListsWithItems) {
                if (element.listInfo.getListId() == listInfo.getListId()) {
                    listItems = element.getListItems();
                }
            }
            for (Item item : listItems) {
                sb.append("- ");
                sb.append(item.getItemName());
                sb.append("/n");
            }
            itemListText = sb.toString();

            return itemListText;
        }
    }


    private class ShoppingListAdapter extends RecyclerView.Adapter<ShoppingListHolder> {

        private List<ListInfo> mAllLists;

        private ShoppingListHolder prevHolder = null;


        @NonNull
        @Override
        public ShoppingListHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            LayoutInflater inflater = LayoutInflater.from(getActivity());
            return new ShoppingListHolder(inflater, parent);
        }

        @Override
        public void onBindViewHolder(@NonNull ShoppingListHolder holder, int position) {
            ListInfo listInfo = mAllLists.get(position);

            holder.cardView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    if (!holder.isExpanded) {
                        holder.isExpanded = true;
                        closeInactive();
                        prevHolder = holder;
                        holder.expandCard(listInfo);
                    } else {
                        holder.isExpanded = false;
                        holder.collapseCard();
                    }
                }
            });

            holder.bind(listInfo, position);
        }

        @Override
        public int getItemCount() {
            if (mAllLists != null) return mAllLists.size();
            else return 0;
        }

        void setContent(List<ListInfo> lists) {
            mAllLists = lists;
            notifyDataSetChanged();
        }

        private void closeInactive() {
            if (prevHolder != null && prevHolder.isExpanded) {
                prevHolder.collapseCard();
            }
        }
    }
}
