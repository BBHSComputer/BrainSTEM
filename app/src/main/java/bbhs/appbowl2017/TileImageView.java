package bbhs.appbowl2017;

import android.content.Context;
import android.support.v7.widget.AppCompatImageView;
import android.widget.ImageView;

/**
 * Created by Ethan Tillison on 23 Apr. 2017.
 */

public class TileImageView extends AppCompatImageView {
	public final int imageId;
	public boolean flipped = false;
	public boolean matched = false;

	public TileImageView(Context context, int imageId) {
		super(context);

		this.imageId = imageId;
	}
	public void setImageAlpha(float alpha){
		super.setImageAlpha((int) alpha);
	}
}
