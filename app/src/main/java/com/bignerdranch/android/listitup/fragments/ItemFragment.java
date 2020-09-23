package com.bignerdranch.android.listitup.fragments;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;

import android.widget.TextView;

import androidx.core.content.FileProvider;
import androidx.fragment.app.Fragment;

//import com.bignerdranch.android.listitup.ListDB;
import com.bignerdranch.android.listitup.PictureUtils;
import com.bignerdranch.android.listitup.R;
import com.bignerdranch.android.listitup.activities.LocationActivity;
import com.bignerdranch.android.listitup.room.Item;

import java.io.File;
import java.util.List;
import java.util.UUID;

/*
need to add database wherever stuff is red to retrieve an item
 */

public class ItemFragment extends Fragment {

    private static final String ARG_THING_ID = "thingID";

    private Item mItem;
    private TextView mWhat;
    private TextView mShopName;
    private TextView mQuantity;
    private ImageButton mPhotoButton;
    private ImageView mPhotoView;
    private File mPhotoFile;
    private static final int REQUEST_PHOTO= 2;
    private Button goToShop;
    private Button showMap;

    private View mScraperView;

    private static final String EXTRA_SHOP_NAME = "com.bignerdranch.android.listitup.shop_name";

    /*
    Configures item for Pager
     */
    public static ItemFragment newInstance(int thingID) {
        Bundle args = new Bundle();
        args.putSerializable(ARG_THING_ID, thingID);
        ItemFragment fragment = new ItemFragment();
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

        int thingID = getArguments().getInt(ARG_THING_ID);

        //get item from database
//        mItem = ListDB.get(getActivity()).getItemWithUUID(thingID);
//        mPhotoFile = ListDB.get(getActivity()).getPhotoFile(mItem); //saves pointer to where photo is stored
    }

    @Override
    public void onPause() {
        super.onPause();
//        ListDB.get(getActivity())
//                .updateItem(mItem);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState)  {
        View v = inflater.inflate(R.layout.fragment_item, container, false);


        mWhat = (TextView) v.findViewById(R.id.item_name);
        mWhat.setText(mItem.getName());
        mShopName = (TextView) v.findViewById(R.id.item_shop);
        mShopName.setText(mItem.getShopName());
        mQuantity = (TextView) v.findViewById(R.id.item_quantity);
        mQuantity.setText(mItem.getQuantity());
        mPhotoButton = (ImageButton) v.findViewById(R.id.photo_button);
        goToShop =(Button) v.findViewById(R.id.goToShop_button);
        showMap = (Button) v.findViewById(R.id.showMap_button);

        //open website
        goToShop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent Getintent = new Intent(Intent.ACTION_VIEW,Uri.parse("https://www."+ mItem.getShopName() + ".dk"));
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

        return v;
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
