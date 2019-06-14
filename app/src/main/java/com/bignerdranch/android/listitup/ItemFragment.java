package com.bignerdranch.android.listitup;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v4.app.Fragment;
import android.support.v4.content.FileProvider;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import java.io.File;
import java.util.List;
import java.util.UUID;

/*
need to add database wherever stuff is red to retrieve an item
 */

public class ItemFragment extends Fragment {

    private Item mItem;
    private static final String ARG_THING_ID = "thingID";
    private TextView mWhat;
    private TextView mShopName;
    private TextView mQuantity;
    private ImageButton mPhotoButton;
    private ImageView mPhotoView;
    private File mPhotoFile;
    private static final int REQUEST_PHOTO= 2;
    private Button goToShop;
    private Button showMap;

    private static final String EXTRA_SHOP_NAME = "com.bignerdranch.android.listitup.shop_name";

    /*
    Configures item for Pager
     */
    public static ItemFragment newInstance(UUID thingID) {
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

        UUID thingID = (UUID) getArguments().getSerializable(ARG_THING_ID);
        //get item from database
        mItem = ListDB.get(getActivity()).getItemWithUUID(thingID);
        mPhotoFile = ListDB.get(getActivity()).getPhotoFile(mItem); //saves pointer to where photo is stored
    }

    @Override
    public void onPause() {
        super.onPause();
        ListDB.get(getActivity())
                .updateItem(mItem);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_item, container, false);

        /*//static spinner experiment begin
        Spinner staticSpinner = (Spinner) v.findViewById(R.id.static_spinner);

        // Create an ArrayAdapter using the string array and a default spinner
        ArrayAdapter<CharSequence> staticAdapter = ArrayAdapter
                .createFromResource(getContext(), R.array.brew_array,
                        android.R.layout.simple_spinner_item);

        // Specify the layout to use when the list of choices appears
        staticAdapter
                .setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        // Apply the adapter to the spinner
        staticSpinner.setAdapter(staticAdapter);

    // static spinner experiment end*/

        mWhat = (TextView) v.findViewById(R.id.item_name);
        mWhat.setText(mItem.getWhat());
        mShopName = (TextView) v.findViewById(R.id.item_shop);
        mShopName.setText(mItem.getShop());
        mQuantity = (TextView) v.findViewById(R.id.item_quantity);
        mQuantity.setText(mItem.getQuantity());
        mPhotoButton = (ImageButton) v.findViewById(R.id.photo_button);
        goToShop =(Button) v.findViewById(R.id.goToShop_button);
        showMap = (Button) v.findViewById(R.id.showMap_button);

        //open website
        goToShop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent Getintent = new Intent(Intent.ACTION_VIEW,Uri.parse("https://www."+ mItem.getShop() + ".dk"));
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
