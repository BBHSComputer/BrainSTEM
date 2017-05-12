package bbhs.appbowl2017.tile;

import android.content.ContentResolver;
import android.content.Intent;
import android.content.res.Resources;
import android.net.Uri;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import bbhs.appbowl2017.R;

public class TileSettingsActivity extends AppCompatActivity {

    public static Uri[] cards;
    public static Uri[] defaultCards;

    public static  ImageView imageView;
    private Button chooser;
    private Button play;
    private EditText getPairs;
    private Uri selectedImage;
    private Uri image;
    private Button useDefaults;

    private RelativeLayout imageDisplay; //ONLY PLACES WHERE IMAGES WILL BE DISPLAYED ARE ACCEPTABLE TO PUT IN HERE

    public static ImageView[] displayedImages;

    public static int count;

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

        defaultCards = new Uri[10];
        Resources resources = getApplicationContext().getResources();

        defaultCards[0] = Uri.parse(ContentResolver.SCHEME_ANDROID_RESOURCE + "://" + resources.getResourcePackageName(R.drawable.img1) + '/' + resources.getResourceTypeName(R.drawable.img1) + '/' + resources.getResourceEntryName(R.drawable.img1));
        defaultCards[1] = Uri.parse(ContentResolver.SCHEME_ANDROID_RESOURCE + "://" + resources.getResourcePackageName(R.drawable.img2) + '/' + resources.getResourceTypeName(R.drawable.img2) + '/' + resources.getResourceEntryName(R.drawable.img2));
        defaultCards[2] = Uri.parse(ContentResolver.SCHEME_ANDROID_RESOURCE + "://" + resources.getResourcePackageName(R.drawable.img3) + '/' + resources.getResourceTypeName(R.drawable.img3) + '/' + resources.getResourceEntryName(R.drawable.img3));
        defaultCards[3] = Uri.parse(ContentResolver.SCHEME_ANDROID_RESOURCE + "://" + resources.getResourcePackageName(R.drawable.img4) + '/' + resources.getResourceTypeName(R.drawable.img4) + '/' + resources.getResourceEntryName(R.drawable.img4));
        defaultCards[4] = Uri.parse(ContentResolver.SCHEME_ANDROID_RESOURCE + "://" + resources.getResourcePackageName(R.drawable.img5) + '/' + resources.getResourceTypeName(R.drawable.img5) + '/' + resources.getResourceEntryName(R.drawable.img5));
        defaultCards[5] = Uri.parse(ContentResolver.SCHEME_ANDROID_RESOURCE + "://" + resources.getResourcePackageName(R.drawable.img6) + '/' + resources.getResourceTypeName(R.drawable.img6) + '/' + resources.getResourceEntryName(R.drawable.img6));
        defaultCards[6] = Uri.parse(ContentResolver.SCHEME_ANDROID_RESOURCE + "://" + resources.getResourcePackageName(R.drawable.img7) + '/' + resources.getResourceTypeName(R.drawable.img7) + '/' + resources.getResourceEntryName(R.drawable.img7));
        defaultCards[7] = Uri.parse(ContentResolver.SCHEME_ANDROID_RESOURCE + "://" + resources.getResourcePackageName(R.drawable.img8) + '/' + resources.getResourceTypeName(R.drawable.img8) + '/' + resources.getResourceEntryName(R.drawable.img8));
        defaultCards[8] = Uri.parse(ContentResolver.SCHEME_ANDROID_RESOURCE + "://" + resources.getResourcePackageName(R.drawable.img9) + '/' + resources.getResourceTypeName(R.drawable.img9) + '/' + resources.getResourceEntryName(R.drawable.img9));
        defaultCards[9] = Uri.parse(ContentResolver.SCHEME_ANDROID_RESOURCE + "://" + resources.getResourcePackageName(R.drawable.img10) + '/' + resources.getResourceTypeName(R.drawable.img10) + '/' + resources.getResourceEntryName(R.drawable.img10));

        useDefaults = (Button) findViewById(R.id.playDefault);


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


        useDefaults.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (getPairs.getText().equals("") || getPairs.getText().equals(null)) {
                    Snackbar snackbar = Snackbar.make(imageDisplay, "No cards entered. Please select some images.", Snackbar.LENGTH_LONG); //if no cards entered

                    snackbar.show();

                } else { //relevant images into cards
                    try {
                        cards = new Uri[Integer.parseInt(getPairs.getText().toString()) > 10 ? 10 : Integer.parseInt(getPairs.getText().toString())]; // make sure num of cards is <= 10
                        for (int i = 0; i < cards.length; i++) {
                            displayedImages[i].setImageURI(defaultCards[i]);
                            cards[i] = defaultCards[i];
                        }
                        startActivity(new Intent(getApplicationContext(), TileGameActivity.class)); //loadu p the game
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
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent imageReturnedIntent) {
        super.onActivityResult(requestCode, resultCode, imageReturnedIntent);
        switch (requestCode) {
            case 0:
                if (resultCode == RESULT_OK) {
                    selectedImage = imageReturnedIntent.getData();
                    imageView.setImageURI(selectedImage);

                    displayedImages[count].setImageURI(image);
                    cards[count] = image;
                    count++;

                }

                break;
            case 1:
                if (resultCode == RESULT_OK) {
                    selectedImage = imageReturnedIntent.getData();
                    image = selectedImage;


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
