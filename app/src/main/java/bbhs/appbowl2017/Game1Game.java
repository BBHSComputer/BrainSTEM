package bbhs.appbowl2017;

import android.app.Activity;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Handler;
import android.os.SystemClock;
import android.provider.MediaStore;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.bumptech.glide.Glide;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class Game1Game extends AppCompatActivity {

	int taps = 0;

	private RelativeLayout layout;
	private ImageView[] imageHolders;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		layout = new RelativeLayout(this.getApplicationContext());
		setContentView(layout);

		layout.addOnLayoutChangeListener(new View.OnLayoutChangeListener() {
			@Override
			public void onLayoutChange(View view, int left, int top, int right, int bottom, int leftWas, int topWas, int rightWas, int bottomWas) {
				if (left == leftWas && right == rightWas && top == topWas && bottom == bottomWas) return; // If the view hasn't changed, don't perform any changes (prevents infinite recursion)

				final int w = layout.getWidth(); // The width of the screen
				final int h = layout.getHeight(); // The height of the screen

				// if (w * h == 0 || (w == prevW && h == prevH)) return; // If the layout has not been resized, to not layout again. This prevents infinite looping.

				int cellSize, numCols; // The length of the side of a cell, and the number of columns of cells, respectively. To be calculated.

				final int n = Game1.cards.length * 2; // The number of images to create (2x the requested number, so each has a pair)

				// The number of squares with which we can fill the x axis, given the maximum side of a
				// square must be (width * height / num). w/sqrt(wh/n)=sqrt(wn/h)
				final int numSqWideFitX = (int) Math.ceil(Math.sqrt(w * n / h));
				double sqSizeFitX = w / numSqWideFitX; // The side length of a square given the x axis is filled
				final int numSqTallFitX = (int) Math.ceil((double) n / numSqWideFitX); // The number of squares that will be fit vertically, given the x axis is filled.
				if (sqSizeFitX * numSqTallFitX > h) sqSizeFitX = -1; // If there are too many squares vertically, set to -1 to ensure this value is not used.

				// Repeat to fit y axis instead of x
				final int numSqTallFitY = (int) Math.ceil(Math.sqrt(h * n / w));
				double sqSizeFitY = h / numSqTallFitY;
				final int numSqWideFitY = (int) Math.ceil((float) n / numSqTallFitY);
				if (sqSizeFitY * numSqWideFitY > w) sqSizeFitY = -1;

				if (sqSizeFitX > sqSizeFitY) { // Pick the size with the bigger square size
					cellSize = (int) sqSizeFitX;
					numCols = numSqWideFitX;
				} else {
					if (sqSizeFitY == -1) return; // If both were invalid, don't do anything.
					cellSize = (int) sqSizeFitY;
					numCols = numSqWideFitY;
				}

				final int dx = (w - cellSize * numCols) / 2; // The x offset, to center the tiles.

				// Create a shuffled list of cards
				final List<Integer> cards = new ArrayList<>(n);
				for (int i = 0; i < Game1.cards.length; i++) {
					cards.add(i);
					cards.add(i);
				}
				Collections.shuffle(cards);

				imageHolders = new ImageView[n];

				for (int i = 0; i < n; i++) { // Create the image views
					// Create a card
					imageHolders[i] = new ImageView(Game1Game.this.getApplicationContext());
					// Set the image
					Glide.with(Game1Game.this).load(Game1.cards[cards.get(i)]).into(imageHolders[i]);

					int x = i % numCols; // Calculate x and y based on i
					int y = i / numCols;
					// Position the card
					RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(cellSize - 16, cellSize - 16);
					params.leftMargin = dx + x * cellSize + 8;
					params.topMargin = y * cellSize + 8;

					// Add the card to the layout
					layout.addView(imageHolders[i], params);
				}


			}
		});
	}

	public void flip(ImageView imageView, boolean flipped) {
		if (flipped) {
			imageView.setImageAlpha(255);
		} else {
			imageView.setImageAlpha(0);
		}
	}

	public void checkRotation() {
		if (taps == 2) {
			ImageView a = null, b = null;
			for (int i = 0; i < imageHolders.length; i++) {
				ImageView imageView = imageHolders[i];

				if (imageView.getImageAlpha() == 255 && !imageView.getTag().toString().equals("Flipped")) {
					Log.d("iuo", imageView.getContentDescription().toString());
					if (a == null) {
						a = imageView;
					} else {
						b = imageView;
					}
				}
			}
			a.getContentDescription();
			if (a.getContentDescription().toString().equals(b.getContentDescription().toString())) {
				// TODO
			} else {
				a.setImageAlpha(0);
				b.setImageAlpha(0);
				a.setTag("Flipped");
				b.setTag("Flipped");
			}
			taps = 0;
		}
	}

	public static int calculateInSampleSize(BitmapFactory.Options options, int reqWidth, int reqHeight) {
		// Raw height and width of image
		final int height = options.outHeight;
		final int width = options.outWidth;
		int inSampleSize = 1;

		if (height > reqHeight || width > reqWidth) {
			final int halfHeight = height / 2;
			final int halfWidth = width / 2;

			// Calculate the largest inSampleSize value that is a power of 2 and keeps both
			// height and width larger than the requested height and width.
			while ((halfHeight / inSampleSize) > reqHeight && (halfWidth / inSampleSize) > reqWidth) {
				inSampleSize *= 2;
			}
		}

		return inSampleSize;
	}

	public static Bitmap decodeSampledBitmapFromResource(Resources res, int resId, int reqWidth, int reqHeight) {
		// First decode with inJustDecodeBounds=true to check dimensions
		final BitmapFactory.Options options = new BitmapFactory.Options();
		options.inJustDecodeBounds = true;
		BitmapFactory.decodeResource(res, resId, options);

		// Calculate inSampleSize
		options.inSampleSize = calculateInSampleSize(options, reqWidth, reqHeight);

		// Decode bitmap with inSampleSize set
		options.inJustDecodeBounds = false;
		return BitmapFactory.decodeResource(res, resId, options);
	}
}
