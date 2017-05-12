package bbhs.appbowl2017.tile;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.support.v4.app.DialogFragment;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;

import bbhs.appbowl2017.R;

import static android.R.attr.popupLayout;

/**
 * Created by Ethan Tillison on 11 May. 2017.
 */

public class ChooseImagesDialog extends DialogFragment {

	@Override
	public Dialog onCreateDialog(Bundle savedInstanceState) {
		AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

		LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		// Inflate the custom layout/view
		View view = inflater.inflate(R.layout.popup_tile_imageselect, null);

		builder.setMessage(R.string.tileChangeImages).setView(view).setNeutralButton("OK", new DialogInterface.OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int which) {}
		});
		return builder.create();
	}
}
