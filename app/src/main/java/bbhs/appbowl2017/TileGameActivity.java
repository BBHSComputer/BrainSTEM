package bbhs.appbowl2017;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.bumptech.glide.Glide;

public class TileGameActivity extends AppCompatActivity {


    RelativeLayout imageDisplay;
    ImageView[] imageHolders = new ImageView[20];
	Uri[] imageMatches = new Uri[20];
    int taps = 0;

    boolean[] activated = new boolean[20];
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tile_game);
        imageDisplay = (RelativeLayout) findViewById(R.id.images);


        Log.d("op", "" + imageDisplay.getChildCount());


        for (int i = 0; i < imageDisplay.getChildCount(); i++) {
            imageHolders[i] = (ImageView) imageDisplay.getChildAt(i);

            imageHolders[i].setImageAlpha(0);
            final int n = i;
            imageHolders[i].setOnClickListener(new View.OnClickListener() {
               public boolean flipped = true;
                ImageView imgv = imageHolders[n];

                @Override
                public void onClick(View v) {
                    flip(imgv, flipped);
                    flipped = !flipped;
                    taps++;
                    checkRotation();
                }

            });

        }


        for (int i = 0; i <= TileActivity.cards.length * 2; i++) {

            try {
                Glide.with(this).load(TileActivity.cards[i / 2]).into(imageHolders[i]);
                imageHolders[i].setBackgroundColor(Color.parseColor("#ff0000"));
                imageMatches[i] = TileActivity.cards[i/2];


            } catch (ArrayIndexOutOfBoundsException a) {
                Glide.with(this).load(TileActivity.cards[(i - 1) / 2]).into(imageHolders[i - 1]);
                imageMatches[i-1] = TileActivity.cards[(i-1)/2];

            }
        }
    }


    public void flip(ImageView imageView, boolean flipped) {
        if (flipped) {
            imageView.setImageAlpha(255);
        } else {
            imageView.setImageAlpha(0);
        }
    }



    public void checkRotation(){

        if(taps == 2){
        ImageView a= null,b = null;
            for(int i = 0; i < imageHolders.length; i++){
             ImageView imageView = imageHolders[i];



                try {
                 if (imageView.getImageAlpha() == 255) {
                    try{a.setImageAlpha(0);}
                    catch (NullPointerException n){
                        a = imageView;
                    }
                     try{b.setImageAlpha(0);}
                     catch (NullPointerException n){
                         b = imageView;

                     }
                 }




             }
             catch(NullPointerException n){

             }

            }
            if(a.getDrawable().equals(b.getDrawable())){
                    a.setVisibility(View.INVISIBLE);
                      b.setVisibility(View.INVISIBLE);
            }
     taps =0;
        }
    }
    public static int calculateInSampleSize(
            BitmapFactory.Options options, int reqWidth, int reqHeight) {
        // Raw height and width of image
        final int height = options.outHeight;
        final int width = options.outWidth;
        int inSampleSize = 1;

        if (height > reqHeight || width > reqWidth) {

            final int halfHeight = height / 2;
            final int halfWidth = width / 2;

            // Calculate the largest inSampleSize value that is a power of 2 and keeps both
            // height and width larger than the requested height and width.
            while ((halfHeight / inSampleSize) > reqHeight
                    && (halfWidth / inSampleSize) > reqWidth) {
                inSampleSize *= 2;
            }
        }

        return inSampleSize;
    }
    public static Bitmap decodeSampledBitmapFromResource(Resources res, int resId,
                                                         int reqWidth, int reqHeight) {

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