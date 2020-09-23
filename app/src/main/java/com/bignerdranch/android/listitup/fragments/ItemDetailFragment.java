package com.bignerdranch.android.listitup.fragments;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.FileProvider;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentStatePagerAdapter;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelProviders;

import com.bignerdranch.android.listitup.PictureUtils;
import com.bignerdranch.android.listitup.R;
import com.bignerdranch.android.listitup.activities.LocationActivity;
import com.bignerdranch.android.listitup.room.Item;
import com.bignerdranch.android.listitup.room.ItemVM;

import java.io.File;
import java.util.List;

public class ItemDetailFragment extends Fragment {

    private static final String ARG_THING_ID = "itemID";
    private static final String ARG_THING_NAME = "itemName";
    private static final String ARG_THING_SHOP = "itemShop";
    private static final String ARG_THING_QUA = "itemQuantity";

    private int itemID;
    private String itemName;
    private String itemShop;
    private int itemQuantity;

    private Item mItem;
    private LiveData<Item> mLiveItem;
    private TextView mWhat;
    private TextView mShopName;
    private TextView mQuantity;
    private ImageButton mPhotoButton;
    private ImageView mPhotoView;
    private File mPhotoFile;
    private static final int REQUEST_PHOTO= 2;
    private Button goToShop;
    private Button showMap;


    private ItemVM mItemVM;

    private static final String EXTRA_SHOP_NAME = "com.bignerdranch.android.listitup.shop_name";
    private static final String TAG = "itemDetail";

    /*
    Configures item for Pager
     */
    public static ItemDetailFragment newInstance(int thingID, String name, String shop, int quantity) {
        Bundle args = new Bundle();
        args.putInt(ARG_THING_ID, thingID);
        args.putString(ARG_THING_NAME, name);
        args.putString(ARG_THING_SHOP, shop);
        args.putInt(ARG_THING_QUA, quantity);
        ItemDetailFragment fragment = new ItemDetailFragment();
        fragment.setArguments(args);
        return fragment;
    }

    private void updatePhotoView() {
        if (mPhotoFile == null || !mPhotoFile.exists()) {
            mPhotoView.setImageDrawable(null);
        } else {
            Bitmap bitmap = PictureUtils.getScaledBitmap(
                    mPhotoFile.getPath(), getActivity());
            mPhotoView.setImageBitmap(bitmap);
        }
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.i(TAG, "OnCreate was called");

        itemID = getArguments().getInt(ARG_THING_ID);
        itemName = getArguments().getString(ARG_THING_NAME);
        itemShop = getArguments().getString(ARG_THING_SHOP);
        itemQuantity = getArguments().getInt(ARG_THING_QUA);

        //get item from database
//        mItem = ListDB.get(getActivity()).getItemWithUUID(thingID);
//        mPhotoFile = ListDB.get(getActivity()).getPhotoFile(mItem); //saves pointer to where photo is stored
    }

    @Override
    public void onPause() {
        super.onPause();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState)  {
        View v = inflater.inflate(R.layout.fragment_item, container, false);

        mWhat = (TextView) v.findViewById(R.id.item_name);
        mWhat.setText(itemName);
        mShopName = (TextView) v.findViewById(R.id.item_shop);
        mShopName.setText(itemShop);
        mQuantity = (TextView) v.findViewById(R.id.item_quantity);
        mQuantity.setText(Integer.toString(itemQuantity));

        return v;
    }

    @Override
    public void onViewCreated(@NonNull View v, @Nullable Bundle savedInstanceState) {
        Log.i(TAG, "OnViewCreated was called");

        mPhotoButton = (ImageButton) v.findViewById(R.id.photo_button);
        goToShop =(Button) v.findViewById(R.id.goToShop_button);
        showMap = (Button) v.findViewById(R.id.showMap_button);

        //open website
        goToShop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent Getintent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://www."+ mItem.getShopName() + ".dk"));
                startActivity(Getintent);}
        });


        PackageManager packageManager = getActivity().getPackageManager();

        final Intent captureImage = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        boolean canTakePhoto = mPhotoFile != null &&
                captureImage.resolveActivity(packageManager) != null;
        mPhotoButton.setEnabled(canTakePhoto);
        mPhotoButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
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
        mPhotoView = (ImageView) v.findViewById(R.id.item_photo);
        updatePhotoView();

        showMap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String shopName = getShopName();
                Intent intent = new Intent(getActivity(), LocationActivity.class);
                intent.putExtra("name of shop", shopName);
                startActivity(intent);}
        });
    }

    //gives us result from child activity to update foto and link it to its item
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode != Activity.RESULT_OK) {
            return; }
        if (requestCode == REQUEST_PHOTO) {
            Uri uri = FileProvider.getUriForFile(getActivity(),
                    "com.bignerdranch.android.listitup.fileprovider",
                    mPhotoFile);
            getActivity().revokeUriPermission(uri,
                    Intent.FLAG_GRANT_WRITE_URI_PERMISSION);
            updatePhotoView();
        }

    }



    public String getShopName() {
        return mShopName.getText().toString();
    }
}