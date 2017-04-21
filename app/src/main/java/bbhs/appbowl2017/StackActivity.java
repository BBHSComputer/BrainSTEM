package bbhs.appbowl2017;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
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
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class StackActivity extends AppCompatActivity {

    public static final int SIZE_X = 5; // This is the number of tiles across the game board is
    public static final int SIZE_Y = 8; // This is the number of tiles top to bottom the game board is - subtract one to get grid size
    public static final int TEXT_SIZE = 36;

    private static boolean initializationComplete = false; // Just makes sure that the game start only happens once
    private static boolean fallingAnim = false; // Check if animations are currently running

    private static List<List<View>> grid = new ArrayList<>(); // This is the 2D game field, the tiles in relative grid positions
    private static List<View> buttons = new ArrayList<>(); // This is the list of gameButtons, which is animated after the blocks are
    private static Point[][] coordinates; // This is a 2D array of the coordinates corresponding to the game field positions [y][x]
    private static List<Animator> animations = new ArrayList<>(); // List of animations that need to be done
    private static List<Pair> rules = new ArrayList<>();
    private static Set<View> remove = new HashSet<>(); // Sets of views to remove

    private static RelativeLayout.LayoutParams lp; // This is useful in setting the size of the tiles, initialized in onWindowFocusChanged
    private View gameTile; // This is the gameTile, or the View that the player places
    private static RelativeLayout field; // This is the playing field

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stackgame); // Sets view layout to activity_stackgame.xml

        for (int column = 0; column < SIZE_X; column++) { // Initializes the size of the grid ArrayList, or the number of columns
            grid.add(new ArrayList<View>()); // Adds column to grid ArrayList
        }

        coordinates = new Point[SIZE_Y][SIZE_X]; // Initializes the coordinates grid

        Log.d("BrainSTEM S", "onCreate() finished");
    }

    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        if (!initializationComplete) {
            initializationComplete = true; // Makes sure this only runs once

            field = (RelativeLayout) findViewById(R.id.game2_field); // Gets the playing field relative view

            int scaleX = field.getWidth() / SIZE_X; // Gets the size of the tiles based on the game field width
            int scaleY = field.getHeight() / SIZE_Y; // Gets the size of the tiles based on the game field height

            for (int column = 0; column < SIZE_X; column++) { // For loop to set coordinate X values
                int x = scaleX * column; // Sets coordinates for X so the row loop doesn't have to calculate it every time
                for (int row = 0; row < SIZE_Y; row++) { // For loop to set coordinate Y values
                    coordinates[row][column] = new Point(x, field.getHeight() - (scaleY * (row + 1))); // Sets Point to corresponding X & Y
                    //Explanation of Y calculation: Android has the max Y coordinate at the bottom
                    //Since the game has the first elements of the grid ArrayList at the bottom, the subtraction of field.getHeight() is needed
                    //Android calculates coordinate by the top left of the View, so the int row needs to start at 1, not 0
                }
            }

            Log.d("BrainSTEM S", "The size of the game field is X: " + field.getWidth() + " & Y: " + field.getHeight());

            lp = new RelativeLayout.LayoutParams(scaleX, scaleY);

            gameStart(); // Set up the game playing field with buttons
        }
    }

    private void gameStart() {
        newGameTile();
        //TODO figure out the changing values of gameButtons
        for (int column = 0; column < SIZE_X; column++) { // Sets up the buttons that place the gameTile in each row
            View gameButton = new TextView(getApplicationContext());
            gameButton.setLayoutParams(lp);
            gameButton.setAlpha(0.8F);
            gameButton.setTag(1, gameTile.getTag(1));
            gameButton.setTag(2, column);

            //((TextView)gameButton).setText((int)gameButton.getTag(1)); // Set up the text of the buttons to match the gameTile
            //TODO move this to the newGameTile method somehow

            buttons.add(gameButton);
            grid.get(column).add(gameButton);
            field.addView(gameButton);

            gameButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) { // When gameButton is clicked - v is the gameButton that is clicked
                    if (!fallingAnim) {
                        AnimatorSet fade = new AnimatorSet();
                        for (View button : buttons) {
                            button.setEnabled(false);
                            ObjectAnimator fadeOut = ObjectAnimator.ofFloat(button, "alpha", 0f);
                            fade.play(fadeOut);
                        }
                        fade.start();

                        int column = (int) v.getTag(2);
                        gameTile.setTag(2, column); // Sets the column tag of the gameTile
                        grid.get(column).add(grid.get(column).indexOf(v), gameTile);
                        //This places gameTile in the row that the gameButton is in, at the index that gameButton is in

                        updatePositions();
                    }
                }
            });
        }
    }

    private static void updatePositions() {
        AnimatorSet as = new AnimatorSet();
        for (int x = 0; x < grid.size(); x++) {
            List<View> column = grid.get(x);
            for (int y = 0; y < column.size(); y++) {
                View v = column.get(y);
                if (Math.abs(v.getLeft() - coordinates[y][x].x) > 1 || Math.abs(v.getTop() - coordinates[y][x].y) > 1) {
                    individualMove(v, x, y);
                }
            }
        }
        as.playTogether(animations);
        as.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                animations.clear();
                if (brokenRule()) {

                }
            }
        });
        as.start();

        //TODO add newGameTile after recursion of checking and falling is done
    }

    private static boolean brokenRule() { // This checks that each View does not break a rule
        /*  The method will iterate through every single View on the game board, including the gameButtons.
        *   It will then check if the View is not the top most view on the board, not including gameButtons (hence, the -2 on grid.size())
        *   Then, it checks that the View is not in the leftmost or rightmost column
        */

        for (int column = 0; column < SIZE_X; column++) {
            List<View> list = grid.get(column);

            for (View v : list) {
                int pos = list.indexOf(v);
                if(list.size() - 2 > pos){
                    if(ruleCheck(v, list.get(pos + 1))){
                        addRemove(column, pos);
                        addRemove(column, pos + 1);
                    }
                }
                if(SIZE_X - 1 > column){
                    if((int)v.getTag(1) != -1 && grid.get(column + 1).size() - 1 > pos){
                        if(ruleCheck(v, grid.get(column + 1).get(pos))){
                            addRemove(column, pos);
                            addRemove(column + 1, pos);
                        }
                    }
                }
            }
        }
        if (!remove.isEmpty()) {
            return true;
        }
        return false;
    }

    private static boolean ruleCheck(View v, View check){ // False if no broken rules, True if there are broken rules
        for(Pair pair : rules){
            if(pair.equals(new Pair((int) v.getTag(1), (int) check.getTag(1)))) {
                return true;
            }
        }
        return false;
    }
    private static void addRemove(int column, int pos){
        remove.add(grid.get(column).get(pos));
        if(column > 0){
            if(column < SIZE_X){

            }
        }
    }

    private static void individualMove(View v, int x, int y) { // Moves an individual View
        AnimatorSet blockMove = new AnimatorSet();
        ObjectAnimator moveX = ObjectAnimator.ofFloat(v, "x", coordinates[y][x].x);
        ObjectAnimator moveY = ObjectAnimator.ofFloat(v, "y", coordinates[y][x].y);
        blockMove.playSequentially(moveX, moveY);
        animations.add(blockMove);
    }

    private void newGameTile() {
        gameTile = new TextView(getApplicationContext()); // Initializes the gameTile, or the Tile that is falling down
        gameTile.setLayoutParams(lp); // Set the size of the gameTile
        gameTile.setTag(1, (int) (Math.random() * 9) + 1); // Sets value of the gameTile to value from 1 to 9
        gameTile.setTag(2, -1); // -1 means no column yet
        gameTile.setTop(coordinates[7][2].y); // Sets position of block to top middle
        gameTile.setLeft(coordinates[7][2].x);

        ((TextView) gameTile).setGravity(Gravity.CENTER_VERTICAL | Gravity.CENTER_HORIZONTAL); // Centers the text
        ((TextView) gameTile).setTextSize(TEXT_SIZE); // Sets text size
        ((TextView) gameTile).setTextColor(Color.BLACK); // Sets text color
        ((TextView) gameTile).setText((String) gameTile.getTag(1)); // Sets the text on the block to the tag
    }

}
