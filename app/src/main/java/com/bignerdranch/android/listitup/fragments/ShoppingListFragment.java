package com.bignerdranch.android.listitup.fragments;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.ColorFilter;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.core.content.FileProvider;
import androidx.core.view.ViewCompat;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.RecyclerView.Adapter;

import com.bignerdranch.android.listitup.PictureUtils;
import com.bignerdranch.android.listitup.R;
import com.bignerdranch.android.listitup.activities.ItemDetailActivity;
//import com.bignerdranch.android.listitup.activities.ItemPagerActivity;
import com.bignerdranch.android.listitup.room.Item;
import com.bignerdranch.android.listitup.room.ItemVM;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

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

        ItemTouchHelper myITH = new ItemTouchHelper(new MyItemTouchCallback(mAdapter));
        myITH.attachToRecyclerView(mItemRecyclerView);

//        ItemTouchHelper itemTouchHelperR = new ItemTouchHelper(simpleItemTouchCallbackR);
//        ItemTouchHelper itemTouchHelperL = new ItemTouchHelper(simpleItemTouchCallbackL);
//
//        itemTouchHelperR.attachToRecyclerView(mItemRecyclerView);
//        itemTouchHelperL.attachToRecyclerView(mItemRecyclerView);
    }

    @Override
    public void onResume() {
        super.onResume();
        updateUI();
    }

    /*private void getDialog() {
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
                    Item newItem = new Item(mItemName.getText().toString(), mShop.getSelectedItem().toString(), Integer.parseInt(mQuantity.getText().toString()), 0);
                    mItemVM.insertToShop(newItem);

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
    }*/

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

        private Item mItem;
        private TextView itemName;
        private TextView quantity;

        public ShopItemHolder(LayoutInflater inflater, ViewGroup parent) {
            super(inflater.inflate(R.layout.card_item, parent, false));
            itemName = itemView.findViewById(R.id.what_item);
            quantity = itemView.findViewById(R.id.quantity_item);
            itemView.setOnClickListener(this);
        }

        public void bind(Item item, int position) {
            mItem = item;
            itemName.setText(mItem.getName());
            quantity.setText(Integer.toString(mItem.getQuantity()));

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
    }

    private class ShopItemAdapter extends Adapter<ShopItemHolder> {

        private List<Item> mItems;

        public ShopItemAdapter(Context context) {
//            LayoutInflater inflater = LayoutInflater.from(context);
        }

        @Override
        public ShopItemHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            LayoutInflater layoutInflater = LayoutInflater.from(getActivity());
            return new ShopItemHolder(layoutInflater, parent);
        }

        @Override
        public void onBindViewHolder(@NonNull ShopItemHolder holder, int position) {
            Item item = mItems.get(position);
            holder.bind(item, position);
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


    /*ItemTouchHelper.SimpleCallback simpleItemTouchCallbackR = new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.RIGHT) {
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
            Item itemToChange = mAdapter.mItems.get(position);
            mItemVM.putToCart(itemToChange);

            mAdapter.notifyDataSetChanged();
            getPriceDialog(itemToChange);

//            Intent intent = new Intent(getActivity(), ItemPagerActivity.class);
//            intent.putExtra(EXTRA_ITEM_ID, Integer.valueOf(itemToChange.getId()));
//            startActivity(intent);

//            updateUI();
        }
    }; */

    /*ItemTouchHelper.SimpleCallback simpleItemTouchCallbackL = new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT) {
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
            Item itemToRemove = mAdapter.mItems.get(position);
            mItemVM.deleteFromShop(itemToRemove);

            mAdapter.notifyDataSetChanged();
//            updateUI();
        }
    }; */

    // https://stackoverflow.com/questions/34609191/why-itemtouchhelper-callbacks-onchilddraw-will-be-called-after-clearview
    // source: https://github.com/kitek/android-rv-swipe-delete
    private class MyItemTouchCallback extends ItemTouchHelper.Callback {

        boolean viewBeingCleared = false;
        ShopItemAdapter adapter;

        MyItemTouchCallback(ShopItemAdapter adapter) {
            this.adapter = adapter;
        }

        @Override
        public int getMovementFlags(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder) {
            return makeFlag(ItemTouchHelper.ACTION_STATE_SWIPE, ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT);
        }

        @Override
        public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {
            return false;
        }

        @Override
        public void onSwiped(RecyclerView.ViewHolder viewHolder, int swipeDir) {
            //Remove swiped item from list and notify the RecyclerView
            //But where to remove from???
            int position = viewHolder.getAdapterPosition();
            Item itemToRemove = adapter.mItems.get(position);
            mItemVM.deleteFromShop(itemToRemove);
            adapter.notifyDataSetChanged();
        }

        @Override
        public void onChildDraw(Canvas c, RecyclerView recyclerView,
                                RecyclerView.ViewHolder viewHolder,
                                float dX, float dY, int actionState, boolean isCurrentlyActive) {

            View itemView = viewHolder.itemView;
            int itemHeight = itemView.getBottom() - itemView.getTop();
            boolean isCanceled = dX == 0f && !isCurrentlyActive;
            Drawable deleteIcon = ContextCompat.getDrawable(getActivity(), R.drawable.ic_bin);

            if (isCanceled) {
                clearCanvas(c, itemView.getRight() + dX, (float) itemView.getTop(), (float) itemView.getRight(), (float) itemView.getBottom());
                super.onChildDraw(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive);
                return;
            }

            ColorDrawable background = new ColorDrawable();
            int color = Color.parseColor("#FF0C3E");
            background.setColor(color);
            // L, T, R, B
            background.setBounds(
                    itemView.getRight() + (int) dX,
                    itemView.getTop(),
                    itemView.getRight(),
                    itemView.getBottom()
            );
            background.draw(c);

            // Calculate position of delete icon
            int inHeight = deleteIcon.getIntrinsicHeight();
            int inWidth = deleteIcon.getIntrinsicWidth();
            int deleteIconTop = itemView.getTop() + (itemHeight - inHeight) / 2;
            int deleteIconMargin = (itemHeight - inHeight) / 2;
            int deleteIconLeft = itemView.getRight() - deleteIconMargin - inWidth;
            int deleteIconRight = itemView.getRight() - deleteIconMargin;
            int deleteIconBottom = deleteIconTop + inHeight;

            // Draw the delete icon
            deleteIcon.setBounds(deleteIconLeft, deleteIconTop, deleteIconRight, deleteIconBottom);
            deleteIcon.draw(c);
            super.onChildDraw(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive);
        }

        private void clearCanvas(Canvas c, Float left, Float top, Float right, Float bottom) {
            c.drawRect(left, top, right, bottom, clearPaint());
        }
        
        private Paint clearPaint() {
            Paint paint = new Paint();
            paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.CLEAR));
            return paint;
        }

        @Override
        public void clearView(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder) {
            super.clearView(recyclerView, viewHolder);
            ViewCompat.setElevation(viewHolder.itemView, 0);
            viewBeingCleared = true;
        }

    }
}