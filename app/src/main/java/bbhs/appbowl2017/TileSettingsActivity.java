package bbhs.appbowl2017;

import android.content.Intent;
import android.net.Uri;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;

public class TileSettingsActivity extends AppCompatActivity {

    public static Uri[] cards;

    private ImageView imageView;
    private Button chooser;
    private Button play;
    private EditText getPairs;
    private Uri selectedImage;
    private Uri image;

    private RelativeLayout imageDisplay; //ONLY PLACES WHERE IMAGES WILL BE DISPLAYED ARE ACCEPTABLE TO PUT IN HERE

    private ImageView[] displayedImages;

    private int count = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tile_settings);

        // imageView = (ImageView) findViewById(R.id.imageView);
        chooser = (Button) findViewById(R.id.chooser);
        getPairs = (EditText) findViewById(R.id.parisOfCards);
        getPairs.setText("");
        imageDisplay = (RelativeLayout) findViewById(R.id.ImageDisplays);
        play = (Button) findViewById(R.id.play);

        displayedImages = new ImageView[10];

        for (int i = 0; i < imageDisplay.getChildCount(); i++) {
            displayedImages[i] = (ImageView) imageDisplay.getChildAt(i); // Set displayed images to all of the imageviews from the imageDisplay
        }
        setButtonsOnClick();

    }

    public void setButtonsOnClick() {
        chooser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (getPairs.getText().equals("") || getPairs.getText().equals(null)) {
                    Snackbar snackbar = Snackbar.make(imageDisplay, "Please enter a number of pairs.", Snackbar.LENGTH_LONG);

                    snackbar.show();
                } else {
                    try {
                        cards = new Uri[Integer.parseInt(getPairs.getText().toString()) > 10 ? 10 : Integer.parseInt(getPairs.getText().toString())]; // make sure num of cards is <= 10

                        count = 0;

                        for (int i = 0; i < displayedImages.length; i++) {
                            displayedImages[i].setImageURI(null);
                        }

                        for (int i = 0; i < cards.length; i++) {
                            getImage();

                        }
                    } catch (NumberFormatException e) {
                        Snackbar snackbar = Snackbar.make(imageDisplay, "Please enter a number of pairs.", Snackbar.LENGTH_LONG); //if no cards entered
                        snackbar.show();

                    } catch (NullPointerException yes) {
                        Snackbar snackbar = Snackbar.make(imageDisplay, "Please enter a number of pairs.", Snackbar.LENGTH_LONG);
                        snackbar.show();

                    }

                }

            }
        });

        play.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (cards == null || getPairs.getText().equals("") || getPairs.getText().equals(null)) {
                    Snackbar snackbar = Snackbar.make(imageDisplay, "No cards entered. Please select some images.", Snackbar.LENGTH_LONG);

                    snackbar.show();
                } else {
                    startActivity(new Intent(getApplicationContext(), TileGameActivity.class)); // load up the game
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

                    displayedImages[count].setImageURI(image);
                    cards[count] = image;
                    count++;

                }
                break;
        }

    }

    public void getImage() { //simplieifes image retrival calls
        Intent pickPhoto = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(pickPhoto, 1);//one can be replaced with any action code

    }

}
