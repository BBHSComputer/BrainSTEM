package bbhs.appbowl2017;

import android.content.Intent;
import android.net.Uri;
import android.provider.ContactsContract;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;

public class Game1SettingsActivity extends AppCompatActivity {

    ImageView imageView;
    Button chooser;
    EditText getPairs;
    Uri selectedImage;
    Uri image;

    RelativeLayout imageDisplay; //ONLY PLACES WHERE IMAGES WILL BE DISPLAYED ARE ACCEPTABLE TO PUT IN HERE

    ImageView[] displayedImages;

    int count = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game1_settings);

       // imageView = (ImageView) findViewById(R.id.imageView);
        chooser = (Button) findViewById(R.id.chooser);
        getPairs = (EditText) findViewById(R.id.parisOfCards);
        imageDisplay = (RelativeLayout) findViewById(R.id.ImageDisplays);

        displayedImages = new ImageView[10];

        for(int i = 0; i < imageDisplay.getChildCount(); i++){
            displayedImages[i] = (ImageView) imageDisplay.getChildAt(i);
        }



        setButtonsOnClick();


    }


    public void setButtonsOnClick() {
        chooser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Game1.cards = new Uri[Integer.parseInt(getPairs.getText().toString()) > 10 ? 10 : Integer.parseInt(getPairs.getText().toString())];



                count = 0;

               for(int i = 0; i < displayedImages.length; i++){
                   displayedImages[i].setImageURI(null);
               }

                for (int i = 0; i < Game1.cards.length; i++) {
                    getImage();
                    Log.d("jkl", "ive also been called");
                    Game1.cards[i] = image;


                }
                for (int i = 0; i < Game1.cards.length; i++) {

                        displayedImages[i].setImageURI(Game1.cards[i]);



                }
            }
        });
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent imageReturnedIntent) {
        super.onActivityResult(requestCode, resultCode, imageReturnedIntent);
        switch (requestCode) {
            case 0:
                if (resultCode == RESULT_OK) {
                    selectedImage = imageReturnedIntent.getData();
                    imageView.setImageURI(selectedImage);

                }

                break;
            case 1:
                if (resultCode == RESULT_OK) {
                    selectedImage = imageReturnedIntent.getData();
                    image = selectedImage;
                    Log.d("jkl", "ive been called " + image);
                   // imageView.setImageURI(selectedImage);

                    displayedImages[count].setImageURI(image);
                    count++;

                }
                break;
        }

    }

    public void getImage() {
        Intent pickPhoto = new Intent(Intent.ACTION_PICK,
                android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(pickPhoto, 1);//one can be replaced with any action code

    }


}
