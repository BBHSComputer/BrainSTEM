package bbhs.appbowl2017;

import android.graphics.Color;
import android.graphics.Point;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.ArrayList;

public class Game2Activity extends AppCompatActivity {

    int sizeX;//This is the number of tiles across the game board is
    int sizeY;//This is the number of tiles top to bottom the game board is
    int textSize = 36;

    boolean initializationComplete = false;//Just makes sure that the game start only happens once

    ArrayList<ArrayList<View>> grid = new ArrayList<>();//This is the 2D game field, the tiles in relative grid positions
    Point[][] coordinates;//This is a 2D array of the coordinates corresponding to the game field positions [y][x]

    RelativeLayout.LayoutParams lp;//This is useful in setting the size of the tiles, initialized in onWindowFocusChanged
    View gameTile;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game2game);//Sets view layout to activity_game2game.xml

        for (int column = 0; column < sizeX; column++){//Initializes the size of the grid ArrayList, or the number of columns
            grid.add(new ArrayList<View>());//Adds column to grid ArrayList
        }

        coordinates = new Point[sizeY][sizeX];//Initializes the coordinates grid

        Log.d("BrainSTEM S", "onCreate() finished");
    }

    @Override
    public void onWindowFocusChanged(boolean hasFocus){
        if(!initializationComplete){
            initializationComplete = true;//Makes sure this only runs once

            RelativeLayout field = (RelativeLayout) findViewById(R.id.game2_field);//Gets the playing field relative view

            int scaleX = field.getWidth()/sizeX;//Gets the size of the tiles based on the game field width
            int scaleY = field.getHeight()/sizeY;//Gets the size of the tiles based on the game field height

            for(int column = 0; column < sizeX; column++){//For loop to set coordinate X values
                int x = scaleX * column;//Sets coordinates for X so the row loop doesn't have to calculate it every time
                for(int row = 0; row < sizeY; row++){//For loop to set coordinate Y values
                    coordinates[row][column] = new Point(x, field.getHeight() - (scaleY * (row + 1)));//Sets Point to corresponding X & Y
                    //Explanation of Y calculation: Android has the max Y coordinate at the bottom
                    //Since the game has the first elements of the grid ArrayList at the bottom, the subtraction of field.getHeight() is needed
                    //Android calculates coordinate by the top left of the View, so the int row needs to start at 1, not 0
                }
            }

            Log.d("BrainSTEM S", "The size of the game field is X: " + field.getWidth() + " & Y: " + field.getHeight());

            lp = new RelativeLayout.LayoutParams(scaleX, scaleY);

            gameStart();//Set up the game playing field with buttons
        }
    }

    private void gameStart(){
        newGameTile();
    }

    private void newGameTile(){
        gameTile = new TextView(getApplicationContext());//Initializes the gameTile, or the Tile that is falling down
        gameTile.setLayoutParams(lp);//Set the size of the gameTile
        gameTile.setTag(1, (int)(Math.random() * 9) + 1);//Sets value of the gameTile to value from 1 to 9

        ((TextView)gameTile).setGravity(Gravity.CENTER_VERTICAL | Gravity.CENTER_HORIZONTAL);
        ((TextView)gameTile).setTextSize(textSize);
        ((TextView)gameTile).setTextColor(Color.BLACK);
        ((TextView)gameTile).setText((String) gameTile.getTag(1));
    }

}
