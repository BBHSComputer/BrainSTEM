package bbhs.appbowl2017.tile;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.support.v4.app.DialogFragment;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;

import bbhs.appbowl2017.R;

import static android.R.attr.popupLayout;
import static android.app.Activity.RESULT_OK;
import static bbhs.appbowl2017.tile.TileSettingsActivity.cards;
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
			public void onClick(DialogInterface dialog, int which) {}
		});
		customImages.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				cards = new Uri[tileNumPairs]; // make sure num of cards is <= 10

				TileSettingsActivity.count = 0;

				for (int i = 0; i < displayedImages.length; i++) {
					displayedImages[i].setImageURI(null);
				}

				for (int i = 0; i < cards.length; i++) {
					getImage();

				}
			}
		});

		defaultImages.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				cards = new Uri[tileNumPairs];
				for (int i = 0; i < cards.length; i++) {
					displayedImages[i].setImageURI(TileSettingsActivity.defaultCards[i]);
					cards[i] = TileSettingsActivity.defaultCards[i];
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


					displayedImages[TileSettingsActivity.count].setImageURI(image);
					cards[TileSettingsActivity.count] = image;
					TileSettingsActivity.count++;

				}
				break;
		}

	}
}
