package com.bignerdranch.android.listitup.fragments;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.graphics.Bitmap;
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

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.content.FileProvider;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.RecyclerView.Adapter;

import com.bignerdranch.android.listitup.PictureUtils;
import com.bignerdranch.android.listitup.R;
//import com.bignerdranch.android.listitup.activities.ItemPagerActivity;
import com.bignerdranch.android.listitup.room.Item;
import com.bignerdranch.android.listitup.room.ItemVM;
import com.bignerdranch.android.listitup.utilities.MyItemTouchCallback;

import java.io.File;
import java.util.List;
import java.util.Observable;
import java.util.Observer;

//import static com.bignerdranch.android.listitup.activities.ItemPagerActivity.EXTRA_ITEM_ID;

/**
 * This fragment hosts "To Buy" and "In Cart" lists
 */

public class ShoppingListFragment extends Fragment implements Observer {

    public static final String ARG_OBJECT = "object";


    private ShopItemAdapter mAdapter;

    private RecyclerView mItemRecyclerView;
    private List<Item> mItems;
//    private TextView testText;


    private ItemVM mItemVM;

    // FOR PHOTO //
    private File mPhotoFile;
    private ImageView mPhotoView;
    private static final int REQUEST_PHOTO= 2;

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

        View view = inflater.inflate(R.layout.fragment_shoppinglist, container, false);

        mAdapter = new ShopItemAdapter(getActivity());

        mItemVM = ViewModelProviders.of(this).get(ItemVM.class);
//        mItemVM.getAllItems().observe(getViewLifecycleOwner(), new androidx.lifecycle.Observer<List<ShopItem>>() {
//
//            @Override
//            public void onChanged(List<ShopItem> shopItems) {
//                mAdapter.setItems(shopItems);
//            }
//        });
        mItemVM.getAllItemsByShops().observe(getViewLifecycleOwner(), shopItems -> mAdapter.setItems(shopItems));

        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        System.out.println("ShoppingListFragment onViewCreated was called");

        mItemRecyclerView = view.findViewById(R.id.items_recycler_view);
        mItemRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        mItemRecyclerView.setAdapter(mAdapter); // added

        updateUI();

        ItemTouchHelper myITH = new ItemTouchHelper(new MyItemTouchCallback(getActivity(), mAdapter, mItemVM));
        myITH.attachToRecyclerView(mItemRecyclerView);
    }

    @Override
    public void onResume() {
        super.onResume();
        updateUI();
    }

    private void getPriceDialog(Item item) {
        AlertDialog.Builder mBuilder = new AlertDialog.Builder(getActivity());

        View mView = getLayoutInflater().inflate(R.layout.dialog_enterprice, null);

        EditText mItemPrice = (EditText) mView.findViewById(R.id.itemPrice);
        mPhotoView = (ImageView) mView.findViewById(R.id.item_photo);
        ImageButton photoButton = (ImageButton) mView.findViewById(R.id.photo_button);
        Button mOkButton = (Button) mView.findViewById(R.id.ok_button);
        Button mCancelButton = (Button) mView.findViewById(R.id.cancel_button);
        mBuilder.setView(mView);
        final AlertDialog dialog = mBuilder.create();

        mOkButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                Log.d("photo", "OK button clicked");
                String priceS = mItemPrice.getText().toString();
                if (priceS.contains(",")) priceS = priceS.replace(",", ".");
                Log.d("inputprice", priceS);
                float priceF = Float.parseFloat(priceS);
                mItemVM.setPrice(item, priceF);
                mAdapter.notifyDataSetChanged();
                dialog.dismiss();
            }
        });

        mCancelButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                Log.d("photo", "Cancel button clicked");
                mItemVM.putToShop(item);
                dialog.dismiss();
            }
        });

        PackageManager packageManager = getActivity().getPackageManager();
        final Intent captureImage = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        updatePhotoView();
//        boolean canTakePhoto = mPhotoFile != null &&
//                captureImage.resolveActivity(packageManager) != null;
        Log.d("photo", Boolean.toString(mPhotoFile != null)); //--> false
        Log.d("photo", Boolean.toString(captureImage.resolveActivity(packageManager) != null));


        photoButton.setEnabled(true);
        photoButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Log.d("photo", "Photo button clicked");
                Uri uri = FileProvider.getUriForFile(getActivity(),
                        "com.bignerdranch.android.listitup.fileprovider",
                        mPhotoFile);
                captureImage.putExtra(MediaStore.EXTRA_OUTPUT, uri);
                List<ResolveInfo> cameraActivities = getActivity()
                        .getPackageManager().queryIntentActivities(captureImage,
                                PackageManager.MATCH_DEFAULT_ONLY);
                for (ResolveInfo activity : cameraActivities) {
                    getActivity().grantUriPermission(activity.activityInfo.packageName,
                            uri, Intent.FLAG_GRANT_WRITE_URI_PERMISSION);}
                startActivityForResult(captureImage, REQUEST_PHOTO);
            }
        });

        updatePhotoView();

        dialog.show();

    }

    private void updateUI() {
//        ItemRoomDB itemDB = ItemRoomDB.getDatabase(getActivity());
//        ListDB listDB = ListDB.get(getActivity());
//        List<ShopItem> items = listDB.getListDB();
//        List<ShopItem> items = mItemVM.getAllItems();
//
//        if (mAdapter == null) {
//            mAdapter = new ShopItemAdapter(getActivity());
//            mItemRecyclerView.setAdapter(mAdapter);
//        }
//        else {
//            mAdapter.setItems(items);
//            mAdapter.notifyDataSetChanged();
//        }

//        if (mAdapter == null) {
//            mAdapter = new ShopItemAdapter(getActivity());
//            mItemRecyclerView.setAdapter(mAdapter);
//        } else {
//            mAdapter.setItems();
//        }
    }

    //// PHOTO RELATED METHODS ////
    private void updatePhotoView() {
        if (mPhotoFile == null || !mPhotoFile.exists()) {
            mPhotoView.setImageDrawable(null);
        } else {
            Bitmap bitmap = PictureUtils.getScaledBitmap(
                    mPhotoFile.getPath(), getActivity());
            mPhotoView.setImageBitmap(bitmap);
        }
    }


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
        private ImageButton addButton;
        private ImageButton reduceButton;

        private boolean isExpanded = false;

        private int currentQuantity;

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
            reduceButton = itemView.findViewById(R.id.reduce_button);
            addButton = itemView.findViewById(R.id.add_button);

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

            itemView.setOnClickListener(this);
        }

        public void bind(Item item, int position) {
            mItem = item;
            itemName.setText(mItem.getName());
            itemQuantity.setText(Integer.toString(mItem.getQuantity()));
            quantitySetter.setText(Integer.toString(mItem.getQuantity()));
            currentQuantity = Integer.parseInt(itemQuantity.getText().toString());
            isExpanded = false;
        }

        /*
        When clicking on list item we start instance of ItemPagerActivity
         */
        @Override
        public void onClick(View view) {
//            Intent intent = new Intent(getActivity(), ItemPagerActivity.class);
//            intent.putExtra(EXTRA_ITEM_ID, Integer.valueOf(mItem.getId()));
//            startActivity(intent);
//            getPriceDialog(mItem);
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
            editButtonImg.setBackgroundResource(R.drawable.ic_edit);
            isExpanded = false;
        }
    }

    public class ShopItemAdapter extends Adapter<ShopItemHolder> {

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

            holder.bigEditButton.setOnClickListener(new View.OnClickListener() {
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
                        int itemId = holder.mItem.getId();
                        String itemName = holder.itemName.getText().toString();
                        int itemQuantity = Integer.parseInt(holder.itemQuantity.getText().toString());
                        float itemPrice = Float.parseFloat(holder.itemPrice.getText().toString());
                        mItemVM.updateShopItem(itemId, itemName, itemQuantity, itemPrice);
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
        }

        public List<Item> getItems() {
            return mItems;
        }
    }
}