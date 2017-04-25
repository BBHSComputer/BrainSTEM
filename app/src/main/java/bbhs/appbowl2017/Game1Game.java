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
import android.provider.Settings;
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


    RelativeLayout imageDisplay; //place for image holders
    ImageView[] imageHolders = new ImageView[20]; //premake all image holders
    String identity = "abcdefghijklmnopqrstuvwxyz";


    int taps = 0;   //0 1 2 taps for flipping, total taps for scoring.
    int totalTaps = 0;
    boolean[] activated = new boolean[20];
    long start = System.currentTimeMillis();
    @Override
    protected void onCreate(Bundle savedInstanceState) {


        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game1_game);
        ViewTarget.setTagId(R.id.glide_id);
        imageDisplay = (RelativeLayout) findViewById(R.id.images);


        Log.d("op", "" + imageDisplay.getChildCount());


        for (int i = 0; i < imageDisplay.getChildCount(); i++) { //Give all image views their respective images, and set them transparent
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
                    totalTaps++;
                    checkRotation();
                }

            });


        }


        for (int i = 0; i <= Game1.cards.length * 2; i++) {

            try {
                Glide.with(this).load(Game1.cards[i / 2]).into(imageHolders[i]);

                imageHolders[i].setContentDescription(identity.charAt(i / 2) + "");
                imageHolders[i].setBackgroundColor(Color.parseColor("#ff0000")); //load image, set color, give it a tag indicating it hasnt been flipped



            } catch (ArrayIndexOutOfBoundsException a) {
                Glide.with(this).load(Game1.cards[(i - 1) / 2]).into(imageHolders[i - 1]);
                imageHolders[i - 1].setContentDescription(identity.charAt((i - 1) / 2) + "");

                imageHolders[i].setBackgroundColor(Color.parseColor("#ff0000"));

            }


        }

        for(ImageView iv : imageHolders){
            if(iv!= null){
                iv.setTag("cffsa");
            }
        }
    }


    public void flip(ImageView imageView, boolean flipped) { //flips image by turning alpha 0 or 255
        if (flipped) { //if not transparent make transparent vice versa
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


                if (imageView != null) {
                    if (imageView.getImageAlpha() == 255 && !imageView.getTag().toString().equals("Flipped")) {
                        Log.d("iuo", imageView.getContentDescription().toString());
                        if (a == null) {
                            a = imageView;
                        } else {
                            b = imageView;
                        }
                    }
                }
                if (a != null && b != null) {
                    // if a and b are both permaflippped
                    if(a.getTag().toString().equals(b.getTag().toString()) && a.getTag().toString().equals("Flipped")){
                        //do nothing
                    }

                    //if none are permaflipped
                  else  if(!a.getTag().toString().equals("Flipped") && !b.getTag().toString().equals("Flipped")){
                        //If they have the same image
                        if(a.getContentDescription().toString().equals(b.getContentDescription().toString())){
                            //get rid of onclick listener, and permaflip them
                            b.setOnClickListener(null);
                            a.setOnClickListener(null);

                            a.setTag("Flipped");
                            b.setTag("Flipped");

                        }

                        //if they don't have the same image
                        if(!a.getContentDescription().toString().equals(b.getContentDescription().toString())){
                            //make transparent and do nothing
                            a.setImageAlpha(0);
                            b.setImageAlpha(0);
                        }
                    }

                    else if(a.getTag().toString().equals("Flipped")){ //If only a is permaflipped
                        //do nothing to a, make b transparent again

                        b.callOnClick();
                    }
                    else if(b.getTag().toString().equals("Flipped")){ //If only b is permaflipped
                        //do nothing to a, make b transparent again
                       b.callOnClick();

                    }



                }


            }


            taps = 0;
        }


        endGame(calculateScore());
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

    public double calculateScore(){
        return 500.0/Math.log(totalTaps/(Math.pow(.25,((double)Game1.cards.length*2)) - Game1.cards.length)) + 500/Math.log(System.currentTimeMillis() - start);


    }

    public boolean gameOver(){ //if a single one isn't flipped return false and exit
        for(ImageView i : imageHolders){
            if(!i.getTag().toString().equals("Flipped")){
                return false;
            }
        }
    return true;
    }
    public void endGame(double score){
        //end gamey stuff
    }
}
