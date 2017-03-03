package bbhs.appbowl2017;

import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;

public class Game1Game extends AppCompatActivity {


    RelativeLayout imageDisplay;
    ImageView[] imageHolders = new ImageView[20];
    int taps = 0;


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
                boolean flipped = true;
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


        for (int i = 0; i <= Game1.cards.length * 2; i++) {

            try {
                imageHolders[i].setImageURI(Game1.cards[i / 2]);


            } catch (ArrayIndexOutOfBoundsException a) {
                imageHolders[i - 1].setImageURI(Game1.cards[(i - 1) / 2]);

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
            for(ImageView imageView : imageHolders){
               if(imageView.getImageAlpha() == 255){
                   for(int i = 0; i <  10000; i++){
                      int j = i * i;
                   }
               }
                imageView.setImageAlpha(0);

            }
     taps =0;
        }
    }
}