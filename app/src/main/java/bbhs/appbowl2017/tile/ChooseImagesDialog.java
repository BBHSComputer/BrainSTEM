package bbhs.appbowl2017.tile;

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.ContentResolver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.res.Resources;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.DialogFragment;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageView;

import com.bumptech.glide.Glide;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import bbhs.appbowl2017.MainActivity;
import bbhs.appbowl2017.R;

/**
 * Created by Ethan Tillison on 11 May. 2017.
 *
 */

public class ChooseImagesDialog extends DialogFragment {

	public static final int MAX_CARDS = MainActivity.tileNumPairs;

	public static final int[] DEFAULT_IDS = {R.drawable.defaulttile01, R.drawable.defaulttile02, R.drawable.defaulttile03, R.drawable.defaulttile04, R.drawable.defaulttile05, R.drawable.defaulttile06, R.drawable.defaulttile07, R.drawable.defaulttile08, R.drawable.defaulttile09, R.drawable.defaulttile10, R.drawable.defaulttile11, R.drawable.defaulttile12, R.drawable.defaulttile13, R.drawable.defaulttile14, R.drawable.defaulttile15, R.drawable.defaulttile16};

	public static Uri[] cards = new Uri[MAX_CARDS];

	private ImageView[] imageViews = new ImageView[MAX_CARDS];

	private final Set<Integer> selectedIndices = new HashSet<>();

	/**
	 * Maps the index of all grid view positions containing default images to the index in {@link ChooseImagesDialog#DEFAULT_IDS} of that image
	 */
	private final Map<Integer, Integer> currentlyUsedDefaultImages = new HashMap<>();

    @NonNull
	@Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

		Bundle b = getArguments();
		final int tileNumPairs = b.getInt("tileNumPairs");

		LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        // Inflate the custom layout/view
        View view = inflater.inflate(R.layout.popup_tile_imageselect, null);

		GridView grid = (GridView) view.findViewById(R.id.imageGrid);
		grid.setAdapter(new BaseAdapter() {
			@Override
			public int getCount() {
				return MAX_CARDS;
			}

			@Override
			public Object getItem(int position) {
				return null;
			}

			@Override
			public long getItemId(int position) {
				return 0;
			}

			@Override
			public View getView(int position, View convertView, ViewGroup parent) {
				if (convertView == null) {
					imageViews[position] = new ImageView(getContext());
					imageViews[position].setLayoutParams(new GridView.LayoutParams(100, 100));
					imageViews[position].setScaleType(ImageView.ScaleType.FIT_CENTER);
				} else {
					imageViews[position] = (ImageView) convertView;
				}

				Glide.with(getContext()).load(DEFAULT_IDS[position]).into(imageViews[position]);

				imageViews[position].setImageAlpha(position >= tileNumPairs ? 128 : 255);

				return imageViews[position];
			}
		});
		grid.setOnItemClickListener(new AdapterView.OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
				if (selectedIndices.contains(position)) selectedIndices.remove(position);
				else selectedIndices.add(position);
			}
		});

		for (int i = 0; i < MAX_CARDS; i++) {
			currentlyUsedDefaultImages.put(i, i);
			cards[i] = uriForDrawable(DEFAULT_IDS[i]);
		}


        Button customImages = (Button) view.findViewById(R.id.customB);
		customImages.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				if (ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {

					// Should we show an explanation?
					if (ActivityCompat.shouldShowRequestPermissionRationale(getActivity(), Manifest.permission.READ_EXTERNAL_STORAGE)) {
						// TODO (from tutorial: https://developer.android.com/training/permissions/requesting.html)?
						// Show an explanation to the user *asynchronously* -- don't block
						// this thread waiting for the user's response! After the user
						// sees the explanation, try again to request the permission.
					} else {
						// No explanation needed, we can request the permission.
						ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, 11610);
						// 11610 is an app-defined int constant. The callback method gets the result of the request.
					}
				} else {
					getCustomImages();
				}
			}
		});

        final Button defaultImages = (Button) view.findViewById(R.id.defaultB);
		defaultImages.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				for (Integer ix : selectedIndices) {
					currentlyUsedDefaultImages.remove(ix);

					int imageIndex = 0;
					while (currentlyUsedDefaultImages.containsValue(imageIndex)) {
						imageIndex++;
					}

					currentlyUsedDefaultImages.put(ix, imageIndex);
					Glide.with(getContext()).load(DEFAULT_IDS[imageIndex]).into(imageViews[imageIndex]);
					cards[imageIndex] = uriForDrawable(DEFAULT_IDS[imageIndex]);
				}
				selectedIndices.clear();
			}
		});

        builder.setMessage(R.string.tileChangeImages).setView(view).setNeutralButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {}
        });

        return builder.create();
    }

    private int currentIndex;
    public void getImage(int index) { //simplifies image retrival calls
		currentIndex = index;
        Intent pickPhoto = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(pickPhoto, 1);//one can be replaced with any action code
    }

    public void onActivityResult(int requestCode, int resultCode, Intent imageReturnedIntent) {
        super.onActivityResult(requestCode, resultCode, imageReturnedIntent);
        switch (requestCode) {
            case 0:
            case 1:
                if (resultCode == Activity.RESULT_OK) {
                    Uri selectedImage = imageReturnedIntent.getData();

					Log.d("TILE_SELECT", currentIndex + ", " + selectedImage);

					this.cards[currentIndex] = selectedImage;

					// Glide.with(getContext()).load(selectedImage).into(this.imageViews[currentIndex]);
					imageViews[currentIndex].setImageURI(selectedImage);
                }
                break;
        }

    }

	@Override
	public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
		switch (requestCode) {
			case 11610:
				Log.d("PERMISSIONS", "RESPONSE"); // TODO: This is never called; it probably should go in the MainActivity class, but the MainActivity class cannot access the getCustomImages() method.
				// If request is cancelled, the result arrays are empty.
				if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
					// permission was granted, yay! Do the
					// task you need to do.
					getCustomImages();
				} else {
					// permission denied, boo! Disable the
					// functionality that depends on this permission.
				}
				break;
			// other 'case' lines to check for other
			// permissions this app might request
		}
	}

	private void getCustomImages() {
		for (Integer ix : selectedIndices) {
			currentlyUsedDefaultImages.remove(ix);
			getImage(ix);
		}
		selectedIndices.clear();
	}

    private Uri uriForDrawable(int resId) {
		Resources resources = getContext().getResources();
		return Uri.parse(ContentResolver.SCHEME_ANDROID_RESOURCE + "://" + resources.getResourcePackageName(resId) + '/' + resources.getResourceTypeName(resId) + '/' + resources.getResourceEntryName(resId));
	}
}
