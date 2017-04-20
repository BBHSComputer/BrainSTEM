package bbhs.appbowl2017;

import android.app.Activity;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.SystemClock;
import android.provider.MediaStore;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.ViewTarget;

import java.io.FileNotFoundException;
import java.io.IOException;

public class Game1Game extends AppCompatActivity {


    RelativeLayout imageDisplay;
    ImageView[] imageHolders = new ImageView[20];
    String identity = "abcdefghijklmnopqrstuvwxyz";


    int taps = 0;

    boolean[] activated = new boolean[20];

    @Override
    protected void onCreate(Bundle savedInstanceState) {


        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game1_game);
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
                    flipped = !flipped; //if flippped unflip, otherwise don't, add to the amount of taps, check rotations of everything
                    taps++;
                    checkRotation();
                }

            });

        }


        for (int i = 0; i <= Game1.cards.length * 2; i++) {

            try {
                Glide.with(this).load(Game1.cards[i / 2]).into(imageHolders[i]);

                imageHolders[i].setContentDescription(identity.charAt(i/2)+"");
                imageHolders[i].setBackgroundColor(Color.parseColor("#ff0000")); //load image, set color, give it a tag indicating it hasnt been flipped
                imageHolders[i].setTag(3,"fsdfa");



            } catch (ArrayIndexOutOfBoundsException a) {
                Glide.with(this).load(Game1.cards[(i - 1) / 2]).into(imageHolders[i - 1]);
                imageHolders[i-1].setContentDescription(identity.charAt((i-1)/2)+"");
                imageHolders[i-1].setTag(3,"dfadsfd");//load image, set color, give it a tag indicating it hasnt been flipped
                imageHolders[i].setBackgroundColor(Color.parseColor("#ff0000"));

            }
        }
    }


    public void flip(ImageView imageView, boolean flipped) { //flips image by turning alpha 0 or 255
        if (flipped) {
            imageView.setImageAlpha(255);
        } else {
            imageView.setImageAlpha(0);
        }
    }



    public void checkRotation(){

        if(taps == 2){
            ImageView a = null,b = null;
            for(int i = 0; i < imageHolders.length; i++){
             ImageView imageView = imageHolders[i];




                 if (imageView.getImageAlpha() == 255 && !imageView.getTag().toString().equals("Flipped")) {
                     Log.d("iuo",imageView.getContentDescription().toString());
                     if(a==null){
                         a = imageView;
                     }
                     else{
                         b = imageView;
                     }
                 }







            }
    a.getContentDescription();
            if(a.getContentDescription().toString().equals(b.getContentDescription().toString())){

    }
            else{
        a.setImageAlpha(0);
        b.setImageAlpha(0);
        a.setTag("Flipped");
        b.setTag("Flipped");
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