package com.bignerdranch.android.listitup.fragments;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.transition.AutoTransition;
import android.transition.TransitionManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.content.FileProvider;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bignerdranch.android.listitup.R;
import com.bignerdranch.android.listitup.activities.ListActivity;
import com.bignerdranch.android.listitup.room.Item;
import com.bignerdranch.android.listitup.room.ItemVM;
import com.bignerdranch.android.listitup.room.TotalPrice;
import com.bignerdranch.android.listitup.utilities.MyItemTouchCallback;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;
import java.util.Observable;
import java.util.Observer;

public class ActiveListFragment extends Fragment implements Observer {

    private ItemVM mItemVM;
    private TextView mActiveListNameText;
    private List<Item> mListItems = new ArrayList<>();
    private FloatingActionButton mAddNewFAB;

    private TextView mTotalPriceText;
    private float mTotalPriceValue;
    private TotalPrice mTotalPriceHolder;

    int chosenList = 0;

    private ShopItemAdapter mAdapter;


    @Override
    public void update(Observable observable, Object data) {
        mAdapter.notifyDataSetChanged();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_active_list, container, false);

        long listId = this.getArguments().getLong("listId");
        mActiveListNameText = v.findViewById(R.id.active_list_name_text);

        mItemVM = new ViewModelProvider(requireActivity()).get(ItemVM.class);
//        mItemVM.getListName(listId).observe(this, listName -> {
//            mActiveListNameText.setText(listName);
//        });
        mItemVM.getListWithItems(listId).observe(getActivity(), listWithItems -> {
            mActiveListNameText.setText(listWithItems.getListInfo().getListName());
            mListItems = listWithItems.getListItems();
            mAdapter.setItems(mListItems);
        });

        mAdapter = new ShopItemAdapter(getActivity());


        // get total price from shared preferences
        SharedPreferences sharedPref = getActivity().getPreferences(Context.MODE_PRIVATE);
        mTotalPriceValue = sharedPref.getFloat("totalPrice", 0.0f);
        mTotalPriceHolder = TotalPrice.getInstance();
        // set total price value in singleton
        mTotalPriceHolder.setTotalPrice(mTotalPriceValue);

        return v;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {

        RecyclerView mItemRecyclerView = view.findViewById(R.id.active_list_items);
        mItemRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        mItemRecyclerView.setAdapter(mAdapter); // added
        mTotalPriceText = view.findViewById(R.id.sum_price);

        mAddNewFAB = view.findViewById(R.id.add_new_fab);
        mAddNewFAB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addDialog();
            }
        });

        if (chosenList == 0) {
            ItemTouchHelper myITH = new ItemTouchHelper(new MyItemTouchCallback(getActivity(), mAdapter, mItemVM, 0, mTotalPriceHolder));
            myITH.attachToRecyclerView(mItemRecyclerView);
        }
    }

    private void addDialog() {
        android.app.AlertDialog.Builder mBuilder = new AlertDialog.Builder(getActivity());

        View mView = getLayoutInflater().inflate(R.layout.dialog_add_new_item, null);

        EditText mItemName = (EditText) mView.findViewById(R.id.addItemName);
        EditText mItemQuantity = (EditText) mView.findViewById(R.id.addItemQuantity);
        EditText mItemPrice = (EditText) mView.findViewById(R.id.addItemPrice);
        Button mAddButton = (Button) mView.findViewById(R.id.add_item_add_btn);
        Button mCancelButton = (Button) mView.findViewById(R.id.add_item_cancel_btn);
        mBuilder.setView(mView);
        final AlertDialog dialog = mBuilder.create();

        mAddButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                if (!mItemName.getText().toString().isEmpty() && !mItemQuantity.getText().toString().isEmpty()){
                    Item newItem;
                    int quantity = Integer.parseInt(mItemQuantity.getText().toString());
                    if (!mItemPrice.getText().toString().isEmpty()) {
                        newItem = new Item(mItemName.getText().toString(), quantity, Double.parseDouble(mItemPrice.getText().toString()));
                    } else {
                        newItem = new Item(mItemName.getText().toString(), quantity, 0.0f);
                    }

                    mListItems.add(newItem);
                    mAdapter.notifyDataSetChanged();

                    mItemName.setText("");
                    mItemQuantity.setText("");
                    mItemPrice.setText("");
                    Toast.makeText(getActivity(), "You added "+ newItem.getItemName() + " successfully", Toast.LENGTH_SHORT).show();
                    dialog.dismiss();
                } else if (mItemName.getText().toString().isEmpty()) {
                    Toast.makeText(getActivity(), "Please enter the name of the item", Toast.LENGTH_SHORT).show();
                } else if (mItemQuantity.getText().toString().isEmpty()) {
                    Toast.makeText(getActivity(), "Please enter the number of items you need", Toast.LENGTH_SHORT).show();
                }

            }
        });

        mCancelButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });

        // round corners
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));

        dialog.show();
    }

    private void updateUI() {
        mTotalPriceValue = mTotalPriceHolder.getTotalPrice();
        mTotalPriceText.setText(String.format ("%.2f", mTotalPriceValue));
    }


    // RecyclerView
    private class ShopItemHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private CardView cardView;
        private Item mItem;
        private TextView itemName;
        private TextView piecesText;
        private TextView itemQuantity;
        private TextView priceText;
        private TextView itemPrice;
        private ImageView editButtonImg;
        private FrameLayout bigEditButton;

        private ConstraintLayout expandedCard;
        private EditText editItemName;
        private TextView quantitySetter;
        private TextView priceSetter;

        private boolean isExpanded = false;

        private int currentQuantity;
        private float currentPrice;

        public ShopItemHolder(LayoutInflater inflater, ViewGroup parent) {
            super(inflater.inflate(R.layout.card_item_exp_new, parent, false));
            cardView = itemView.findViewById(R.id.card_item);
            itemName = itemView.findViewById(R.id.what_item);
            piecesText = itemView.findViewById(R.id.pieces_text);
            itemQuantity = itemView.findViewById(R.id.quantity_item);
            priceText = itemView.findViewById(R.id.currency_text);
            itemPrice = itemView.findViewById(R.id.price_item);
            editButtonImg = itemView.findViewById(R.id.card_edit_button_img);
            bigEditButton = itemView.findViewById(R.id.card_edit_button);

            expandedCard = itemView.findViewById(R.id.expanded_card);
            editItemName = itemView.findViewById(R.id.edit_what_item);
            quantitySetter = itemView.findViewById(R.id.quantity_setter);
            priceSetter = itemView.findViewById(R.id.price_setter);
            ImageButton reduceButton = itemView.findViewById(R.id.reduce_button);
            ImageButton addButton = itemView.findViewById(R.id.add_button);
            Button setPriceButton = itemView.findViewById(R.id.price_setter_button);

            reduceButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (currentQuantity > 0) {
                        currentQuantity--;
                    } else currentQuantity = 0;
                    quantitySetter.setText(Integer.toString(currentQuantity));
                }
            });

            addButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (currentQuantity < 100) {
                        currentQuantity++;
                    } else currentQuantity = 100;
                    quantitySetter.setText(Integer.toString(currentQuantity));
                }
            });

            setPriceButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    getPriceDialog(mItem);
                }
            });


            itemView.setOnClickListener(this);
        }

        public void bind(Item item, int position) {
            mItem = item;
            itemName.setText(mItem.getItemName());
            itemQuantity.setText(Integer.toString(mItem.getItemQty()));
            itemPrice.setText(String.format("%.2f", mItem.getItemPrice()));
            quantitySetter.setText(itemQuantity.getText().toString());
            priceSetter.setText(itemPrice.getText().toString());
            currentQuantity = Integer.parseInt(itemQuantity.getText().toString());
            currentPrice = Float.parseFloat(itemPrice.getText().toString());
            isExpanded = false;
        }

        @Override
        public void onClick(View view) {

        }

        private void expandCard() {
            TransitionManager.beginDelayedTransition(cardView, new AutoTransition());
            expandedCard.setVisibility(View.VISIBLE);
            editItemName.setVisibility(View.VISIBLE);
            editItemName.setText(itemName.getText());
            itemName.setVisibility(View.GONE);
            piecesText.setVisibility(View.GONE);
            itemQuantity.setVisibility(View.GONE);
            priceText.setVisibility(View.GONE);
            itemPrice.setVisibility(View.GONE);
            editButtonImg.setBackgroundResource(R.drawable.ic_done);
            isExpanded = true;
        }

        private void collapseCard() {
            TransitionManager.beginDelayedTransition(cardView, new AutoTransition());
            expandedCard.setVisibility(View.GONE);
            editItemName.setVisibility(View.GONE);
            itemName.setText(editItemName.getText());
            itemName.setVisibility(View.VISIBLE);
            piecesText.setVisibility(View.VISIBLE);
            itemQuantity.setVisibility(View.VISIBLE);
            priceText.setVisibility(View.VISIBLE);
            itemPrice.setVisibility(View.VISIBLE);
            itemQuantity.setText(Integer.toString(currentQuantity)); // TODO: handle db?
            System.out.println("Current price2: " + currentPrice);
            itemPrice.setText(String.format("%.2f", currentPrice));
            editButtonImg.setBackgroundResource(R.drawable.ic_edit);
            isExpanded = false;
        }

        private void getPriceDialog(Item item) {
            AlertDialog.Builder mBuilder = new AlertDialog.Builder(getActivity());

            View mView = getLayoutInflater().inflate(R.layout.dialog_enterprice, null);

            EditText mItemPrice = (EditText) mView.findViewById(R.id.itemPrice);
//            mPhotoView = (ImageView) mView.findViewById(R.id.item_photo);
            ImageButton photoButton = (ImageButton) mView.findViewById(R.id.photo_button);
            Button mOkButton = (Button) mView.findViewById(R.id.ok_button);
            Button mCancelButton = (Button) mView.findViewById(R.id.cancel_button);
            mBuilder.setView(mView);
            final AlertDialog dialog = mBuilder.create();

            mOkButton.setOnClickListener(new View.OnClickListener(){
                @Override
                public void onClick(View view) {
                    String priceS = mItemPrice.getText().toString();
                    if (priceS.contains(",")) priceS = priceS.replace(",", ".");
                    float priceF = Float.parseFloat(priceS);
                    currentPrice = Float.parseFloat(priceS);
//                    mItemVM.setPrice(item, priceF);
                    System.out.println("Current price1: " + currentPrice);
                    priceSetter.setText(String.format("%.2f",currentPrice));
//                    mAdapter.notifyDataSetChanged();
                    dialog.dismiss();
                }
            });

            mCancelButton.setOnClickListener(new View.OnClickListener(){
                @Override
                public void onClick(View view) {
                    dialog.dismiss();
                }
            });

            PackageManager packageManager = getActivity().getPackageManager();
            final Intent captureImage = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
//            updatePhotoView();
//        boolean canTakePhoto = mPhotoFile != null &&
//                captureImage.resolveActivity(packageManager) != null;
//            Log.d("photo", Boolean.toString(mPhotoFile != null)); //--> false
//            Log.d("photo", Boolean.toString(captureImage.resolveActivity(packageManager) != null));


            photoButton.setEnabled(true);
            photoButton.setOnClickListener(new View.OnClickListener(){
                @Override
                public void onClick(View v) {
//                    Log.d("photo", "Photo button clicked");
//                    Uri uri = FileProvider.getUriForFile(getActivity(),
//                            "com.bignerdranch.android.listitup.fileprovider",
//                            mPhotoFile);
//                    captureImage.putExtra(MediaStore.EXTRA_OUTPUT, uri);
//                    List<ResolveInfo> cameraActivities = getActivity()
//                            .getPackageManager().queryIntentActivities(captureImage,
//                                    PackageManager.MATCH_DEFAULT_ONLY);
//                    for (ResolveInfo activity : cameraActivities) {
//                        getActivity().grantUriPermission(activity.activityInfo.packageName,
//                                uri, Intent.FLAG_GRANT_WRITE_URI_PERMISSION);}
//                    startActivityForResult(captureImage, REQUEST_PHOTO);
                }
            });

//            updatePhotoView();

            dialog.show();

        }
    }

    public class ShopItemAdapter extends RecyclerView.Adapter<ShopItemHolder> {

        private List<Item> mItems;

        private ShopItemHolder prevHolder = null;

        public ShopItemAdapter(Context context) {
        }

        @Override
        public ShopItemHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            LayoutInflater layoutInflater = LayoutInflater.from(getActivity());
            return new ShopItemHolder(layoutInflater, parent);
        }

        @Override
        public void onBindViewHolder(@NonNull ShopItemHolder holder, int position) {
            Item item = mItems.get(position);

            holder.cardView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    if (!holder.isExpanded) {
                        holder.isExpanded = true;
                        closeInactive();
                        prevHolder = holder;
                        holder.expandCard();
                    } else {
                        holder.isExpanded = false;
                        holder.collapseCard();
                        // save changes into DB
//                        int itemId = holder.mItem.getId();
//                        String itemName = holder.itemName.getText().toString();
//                        int itemQuantity = Integer.parseInt(holder.itemQuantity.getText().toString());
//                        float itemPrice = Float.parseFloat(holder.itemPrice.getText().toString());
//                        mItemVM.updateShopItem(itemId, itemName, itemQuantity, itemPrice);
                    }
                }
            });

            holder.bind(item, position);
        }

        private void closeInactive() {
            if (prevHolder != null && prevHolder.isExpanded) {
                prevHolder.collapseCard();
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
            System.out.println("notifyDataSetChanged()");
//            System.out.println("totalPrice: " + mTotalPriceValue);
//            updateUI();
        }

        public List<Item> getItems() {
            return mItems;
        }
    }


}
