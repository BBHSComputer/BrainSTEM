package bbhs.appbowl2017;

import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.RelativeLayout;

public class Game1Game extends AppCompatActivity {


    RelativeLayout imageDisplay;
    ImageView[] imageHolders = new ImageView[20];


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game1_game);
        imageDisplay = (RelativeLayout) findViewById(R.id.images);


        Log.d("op", "" + imageDisplay.getChildCount());



        for (int i = 0; i < imageDisplay.getChildCount(); i++) {
            imageHolders[i] = (ImageView) imageDisplay.getChildAt(i);


        }


        for (int i = 0; i <= Game1.cards.length * 2; i++) {
            try {
                imageHolders[i].setImageURI(Game1.cards[i / 2]);
            } catch (ArrayIndexOutOfBoundsException a) {
                imageHolders[i-1].setImageURI(Game1.cards[(i-1) / 2]);
            }
        }


    }


}
