package bbhs.appbowl2017.tile;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.ContentResolver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Resources;
import android.net.Uri;
import android.support.v4.app.DialogFragment;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import bbhs.appbowl2017.MainActivity;
import bbhs.appbowl2017.R;

import static android.R.attr.parentActivityName;
import static android.R.attr.popupLayout;
import static android.app.Activity.RESULT_OK;
import static bbhs.appbowl2017.tile.TileSettingsActivity.cards;
import static bbhs.appbowl2017.tile.TileSettingsActivity.defaultCards;
import static bbhs.appbowl2017.tile.TileSettingsActivity.displayedImages;


/**
 * Created by Ethan Tillison on 11 May. 2017.
 */

public class ChooseImagesDialog extends DialogFragment {
    private Uri selectedImage;
    private Uri image;


    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        // Inflate the custom layout/view
        View view = inflater.inflate(R.layout.popup_tile_imageselect, null);
        Button customImages = (Button) view.findViewById(R.id.customB);
        Button defaultImages = (Button) view.findViewById(R.id.defaultB);
        Bundle b = getArguments();

        final int tileNumPairs = b.getInt("tileNumPairs");


        builder.setMessage(R.string.tileChangeImages).setView(view).setNeutralButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
            }
        });

        TileSettingsActivity.displayedImages = new ImageView[10];

        customImages.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cards = new Uri[tileNumPairs]; // make sure num of cards is <= 10

                TileSettingsActivity.count = 0;

			/*	for (int i = 0; i < displayedImages.length; i++) {
                    displayedImages[i].setImageURI(null);
				}*/

                for (int i = 0; i < cards.length; i++) {
                    getImage();

                }
            }
        });

        defaultCards = new Uri[10];
        Resources resources = MainActivity.appCont.getResources();


        defaultCards[0] = Uri.parse(ContentResolver.SCHEME_ANDROID_RESOURCE + "://" + resources.getResourcePackageName(R.drawable.img1) + '/' + resources.getResourceTypeName(R.drawable.img1) + '/' + resources.getResourceEntryName(R.drawable.img1));
        defaultCards[1] = Uri.parse(ContentResolver.SCHEME_ANDROID_RESOURCE + "://" + resources.getResourcePackageName(R.drawable.img2) + '/' + resources.getResourceTypeName(R.drawable.img2) + '/' + resources.getResourceEntryName(R.drawable.img2));
        defaultCards[2] = Uri.parse(ContentResolver.SCHEME_ANDROID_RESOURCE + "://" + resources.getResourcePackageName(R.drawable.img3) + '/' + resources.getResourceTypeName(R.drawable.img3) + '/' + resources.getResourceEntryName(R.drawable.img3));
        defaultCards[3] = Uri.parse(ContentResolver.SCHEME_ANDROID_RESOURCE + "://" + resources.getResourcePackageName(R.drawable.img4) + '/' + resources.getResourceTypeName(R.drawable.img4) + '/' + resources.getResourceEntryName(R.drawable.img4));
        defaultCards[4] = Uri.parse(ContentResolver.SCHEME_ANDROID_RESOURCE + "://" + resources.getResourcePackageName(R.drawable.img5) + '/' + resources.getResourceTypeName(R.drawable.img5) + '/' + resources.getResourceEntryName(R.drawable.img5));
        defaultCards[5] = Uri.parse(ContentResolver.SCHEME_ANDROID_RESOURCE + "://" + resources.getResourcePackageName(R.drawable.img6) + '/' + resources.getResourceTypeName(R.drawable.img6) + '/' + resources.getResourceEntryName(R.drawable.img6));
        defaultCards[6] = Uri.parse(ContentResolver.SCHEME_ANDROID_RESOURCE + "://" + resources.getResourcePackageName(R.drawable.img7) + '/' + resources.getResourceTypeName(R.drawable.img7) + '/' + resources.getResourceEntryName(R.drawable.img7));
        defaultCards[7] = Uri.parse(ContentResolver.SCHEME_ANDROID_RESOURCE + "://" + resources.getResourcePackageName(R.drawable.img8) + '/' + resources.getResourceTypeName(R.drawable.img8) + '/' + resources.getResourceEntryName(R.drawable.img8));
        defaultCards[8] = Uri.parse(ContentResolver.SCHEME_ANDROID_RESOURCE + "://" + resources.getResourcePackageName(R.drawable.img9) + '/' + resources.getResourceTypeName(R.drawable.img9) + '/' + resources.getResourceEntryName(R.drawable.img9));
        defaultCards[9] = Uri.parse(ContentResolver.SCHEME_ANDROID_RESOURCE + "://" + resources.getResourcePackageName(R.drawable.img10) + '/' + resources.getResourceTypeName(R.drawable.img10) + '/' + resources.getResourceEntryName(R.drawable.img10));


        defaultImages.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cards = new Uri[tileNumPairs];
                for (int i = 0; i < cards.length; i++) {
                    try {
                        displayedImages[i].setImageURI(defaultCards[i]);
                    } catch (NullPointerException n) {
                    }
                    cards[i] = defaultCards[i];
                }
                startActivity(new Intent(getContext(), TileGameActivity.class)); //loadu p the game
            }
        });

        return builder.create();
    }

    public void getImage() { //simplifies image retrival calls
        Intent pickPhoto = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(pickPhoto, 1);//one can be replaced with any action code

    }

    public void onActivityResult(int requestCode, int resultCode, Intent imageReturnedIntent) {
        super.onActivityResult(requestCode, resultCode, imageReturnedIntent);
        switch (requestCode) {
            case 0:
                if (resultCode == RESULT_OK) {
                    selectedImage = imageReturnedIntent.getData();
                    TileSettingsActivity.imageView.setImageURI(selectedImage);

                    TileSettingsActivity.displayedImages[TileSettingsActivity.count].setImageURI(image);
                    cards[TileSettingsActivity.count] = image;
                    TileSettingsActivity.count++;

                }

                break;
            case 1:
                if (resultCode == RESULT_OK) {
                    selectedImage = imageReturnedIntent.getData();
                    image = selectedImage;


                    try {
                        displayedImages[TileSettingsActivity.count].setImageURI(image);
                    } catch (NullPointerException n) {
                    }
                    cards[TileSettingsActivity.count] = image;
                    TileSettingsActivity.count++;

                }
                break;
        }

    }
}
