package bbhs.appbowl2017;

import android.animation.Animator;
import android.animation.AnimatorInflater;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Point;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.percent.PercentRelativeLayout;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class StackActivity extends AppCompatActivity {
    private static Context context;

    public static final int SIZE_X = 5; // This is the number of tiles across the game board is
    public static final int SIZE_Y = 8; // This is the number of tiles top to bottom the game board is - subtract one to get grid size
    public static final int TEXT_SIZE = 36;
    public static int field_x;
    public static int field_y;

    private static boolean initializationComplete = false; // Just makes sure that the game start only happens once
    private static boolean fallingAnim = false; // Check if animations are currently running

    private static ArrayList<ArrayList<View>> grid = new ArrayList<>(); // This is the 2D game field, the tiles in relative grid positions
    private static ArrayList<View> buttons = new ArrayList<>(); // This is the list of gameButtons, which is animated after the blocks are
    private static Point[][] coordinates; // This is a 2D array of the coordinates corresponding to the game field positions [y][x]

    private static List<Animator> movementAnim = new ArrayList<>(); // List of animations that need to be done
    private static HashMap<View, Animator> removeAnimList = new HashMap<>();
    // private static AnimatorSet removeAnimList = new AnimatorSet();//Animation of removing Views
    private static List<Pair> rules = new ArrayList<>();//List of rules
    private static Set<View> remove = new HashSet<>(); // Sets of views to

    private static RelativeLayout.LayoutParams lp; // This is useful in setting the size of the tiles, initialized in onWindowFocusChanged
    private static View gameTile; // This is the gameTile, or the View that the player places
    private static RelativeLayout field; // This is the playing field
    private static PercentRelativeLayout pause_menu;
    private static Button home;
    private static Button pause;
    private static FloatingActionButton play;
    private static TextView ruleNotify;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        StackActivity.context = getApplicationContext();
        setContentView(R.layout.activity_stackgame); // Sets view layout to activity_stackgame.xml
        for (int column = 0; column < SIZE_X; column++) { // Initializes the size of the grid ArrayList, or the number of columns
            grid.add(new ArrayList<View>()); // Adds column to grid ArrayList
        }
        coordinates = new Point[SIZE_Y][SIZE_X]; // Initializes the coordinates grid

        ruleNotify = (TextView) findViewById(R.id.rule_Text);
        Pair a = newRule(1);
        Pair b = newRule(1);
        ruleNotify.setText(context.getString(R.string.rule_prefix) + "\n" + a.toString()+ "\n" + b.toString());

        pause_menu = (PercentRelativeLayout) findViewById(R.id.stack_pause); //Gets the pause menu
        field = (RelativeLayout) findViewById(R.id.stack_field); // Gets the playing field relative view



        home = (Button) findViewById(R.id.game2_home);
        home.setEnabled(false);
        home.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                rules.clear();
                clearBoard();
                finish();
                startActivity(new Intent(getApplicationContext(), MainActivity.class));
            }
        });
        pause = (Button) findViewById(R.id.game2_pause);
        pause.setEnabled(false);
        pause.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pauseAction();
                playInitialize();
            }
        });


        Log.d("BrainSTEM S", "onCreate() finished");
    }

    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        if(field_x != field.getWidth() || field_y != field.getHeight()){ //Checks if the size of the playing field has changed
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
        }
        for(ArrayList<View> column : grid){
            if(!column.isEmpty()){
                for(View v : column){
                    v.setLayoutParams(lp);
                }
            }
        }
    }
    private void pauseAction(){
        ObjectAnimator play_fadeIn = ObjectAnimator.ofFloat(pause_menu, "alpha", 1);
        play_fadeIn.start();
        play.setEnabled(true);
        home.setEnabled(false);
        pause.setEnabled(false);
        for(View button : buttons){
            button.setEnabled(false);
        }
    }

    private void gameStart() {
        gameTile = new TextView(getApplicationContext()); // Initializes the gameTile, or the Tile that is falling down
        for (int column = 0; column < SIZE_X; column++) { // Sets up the buttons that place the gameTile in each row
            View gameButton = new TextView(getApplicationContext());
            gameButton.setLayoutParams(lp);
            gameButton.setAlpha(0.8F);
            gameButton.setTag(R.id.stack_value, -1);
            gameButton.setTag(R.id.stack_column, column);
            ((TextView) gameButton).setGravity(Gravity.CENTER_VERTICAL | Gravity.CENTER_HORIZONTAL); // Centers the text
            ((TextView) gameButton).setTextSize(TEXT_SIZE); // Sets text size
            ((TextView) gameButton).setTextColor(Color.BLACK); // Sets text color
            //((TextView)gameButton).setText((int)gameButton.getTag(R.id.stack_value)); // Set up the text of the buttons to match the gameTile
            //TODO move this to the newGameTile method somehow

            buttons.add(gameButton);
            grid.get(column).add(gameButton);
            field.addView(gameButton);

            gameButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) { // When gameButton is clicked - v is the gameButton that is clicked
                    Log.d("BrainSTEM S", "gameButton clicked!");
                    if (!fallingAnim) {
                        AnimatorSet fade = new AnimatorSet();
                        for (View button : buttons) {
                            button.setEnabled(false);
                            ObjectAnimator fadeOut = ObjectAnimator.ofFloat(button, "alpha", 0f);
                            fade.play(fadeOut);
                        }
                        fade.start();

                        int column = (int) v.getTag(R.id.stack_column);
                        gameTile.setTag(R.id.stack_column, column); // Sets the column tag of the gameTile
                        grid.get(column).add(grid.get(column).indexOf(v), gameTile);
                        Log.d("BrainSTEM S", "GameTile added to grid at " + column + " " + (grid.get(column).size() - 2));
                        //This places gameTile in the row that the gameButton is in, at the index that gameButton is in

                        updatePositions();
                    }
                }
            });
        }
        updatePositions();
    }

    private void updatePositions() {
        AnimatorSet as = new AnimatorSet();
        for (int x = 0; x < grid.size(); x++) {
            List<View> column = grid.get(x);
            for (int y = 0; y < column.size(); y++) {
                View v = column.get(y);
                if (Math.abs(v.getX() - coordinates[y][x].x) > 2 || Math.abs(v.getY() - coordinates[y][x].y) > 2) {
                    Log.d("BrainSTEM S", "" + Math.abs(v.getX() - coordinates[y][x].x));
                    individualMove(v, x, y);
                }
            }
        }
        if (!movementAnim.isEmpty()) {
            fallingAnim = true;
            as.playTogether(movementAnim);
            as.addListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    super.onAnimationEnd(animation);
                    movementAnim.clear();
                    Log.d("BrainSTEM S", "Finished updating position animations");
                    initializeRuleCheck();
                }
            });
            as.start();
        }else{
            Log.d("BrainSTEM S", "Update recursion has finished");
            boolean testNextLevel = true;
            for(ArrayList<View> column : grid){
                if(column.size() < SIZE_Y){
                    testNextLevel = false;
                    break;
                }
            }
            if(testNextLevel){
                clearBoard();
                newRule(2);
                pauseAction();
            }else{
                if (fallingAnim) {
                    fallingAnim = false;
                    gameTile = new TextView(getApplicationContext()); // Initializes the gameTile, or the Tile that is falling down
                    newGameTile();

                    AnimatorSet fade = new AnimatorSet();
                    for (View button : buttons) {
                        ((TextView) button).setText("" + gameTile.getTag(R.id.stack_value));
                        if(grid.get((int) button.getTag(R.id.stack_column)).size() < SIZE_Y) {
                            ObjectAnimator fadeIn = ObjectAnimator.ofFloat(button, "alpha", 0.8f);
                            fade.play(fadeIn);
                        }
                    }
                    fade.start();
                    fade.addListener(new AnimatorListenerAdapter() {
                        @Override
                        public void onAnimationEnd(Animator animation) {
                            super.onAnimationEnd(animation);
                            for(View button : buttons){
                                button.setEnabled(true);
                            }
                        }
                    });
                }
            }
        }

        //Log.d("BrainSTEM S", "Finished setting up View position animations");
    }

    private void initializeRuleCheck() {
        if (checkStackRules()) {
            AnimatorSet removeAnimation = new AnimatorSet();
            for(View view : remove){
                if(!removeAnimList.containsKey(view)){
                    AnimatorSet removeNorm = (AnimatorSet) AnimatorInflater.loadAnimator(this, R.animator.stack_remove_norm);
                    removeNorm.setTarget(view);
                    removeAnimList.put(view, removeNorm);
                }
                grid.get((int) view.getTag(R.id.stack_column)).remove(view);
            }
            for(View view : removeAnimList.keySet()){
                removeAnimation.play(removeAnimList.get(view));
            }
            removeAnimation.start();
            removeAnimation.addListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    for(View view : removeAnimList.keySet()){
                        field.removeView(view);
                    }
                    removeAnimList.clear();
                    super.onAnimationEnd(animation);
                    updatePositions();
                }
            });
        }else{
            updatePositions();
        }
    }

    private boolean checkStackRules() { // This checks that each View does not break a rule
        /*  The method will iterate through every single View on the game board, including the gameButtons.
        *   It will then check if the View is not the top most view on the board, not including gameButtons (hence, the -2 on grid.size())
        *   Then, it checks that the View is not in the leftmost or rightmost column
        */
        for (int column = 0; column < SIZE_X; column++) {
            List<View> list = grid.get(column);

            for (View v : list) {
                int pos = list.indexOf(v);
                if (list.size() - 2 > pos) {
                    if (pairStackRuleCheck(v, list.get(pos + 1))) {
                        addToBeRemovedViews(column, pos);
                        addToBeRemovedViews(column, pos + 1);
                    }
                }
                if (SIZE_X - 1 > column) {
                    if ((int) v.getTag(R.id.stack_value) != -1 && grid.get(column + 1).size() - 1 > pos) {
                        if (pairStackRuleCheck(v, grid.get(column + 1).get(pos))) {
                            addToBeRemovedViews(column, pos);
                            addToBeRemovedViews(column + 1, pos);
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

    private boolean pairStackRuleCheck(View v, View check) { //Checks if the pair of Views break a rule. false if they don't, true if they do.
        for (Pair pair : rules) {
            if (pair.equals(new Pair((int) (v.getTag(R.id.stack_value)), (int) (check.getTag(R.id.stack_value))))) {
                return true;
            }
        }
        return false;
    }

    private void addToBeRemovedViews(int column, int pos) {
        AnimatorSet removeRed = (AnimatorSet) AnimatorInflater.loadAnimator(this, R.animator.stack_remove_red);
        removeRed.setTarget(grid.get(column).get(pos));
        remove.add(grid.get(column).get(pos));

        removeAnimList.put(grid.get(column).get(pos), removeRed);

        if (column < SIZE_X - 1) {
            if (grid.get(column + 1).size() - 1 > pos) remove.add(grid.get(column + 1).get(pos));
        }
        if (column > 0) {
            if (grid.get(column - 1).size() - 1 > pos) remove.add(grid.get(column - 1).get(pos));
        }
        if (pos > 0) {
            remove.add(grid.get(column).get(pos - 1));
        }
        if (pos < grid.get(column).size() - 2) {
            remove.add(grid.get(column).get(pos + 1));
        }
    }

    private void individualMove(View v, int x, int y) { // Moves an individual View
        AnimatorSet blockMove = new AnimatorSet();
        ObjectAnimator moveX = ObjectAnimator.ofFloat(v, "x", coordinates[y][x].x);
        ObjectAnimator moveY = ObjectAnimator.ofFloat(v, "y", coordinates[y][x].y);
        blockMove.playSequentially(moveX, moveY);
        movementAnim.add(blockMove);
    }

    private static void newGameTile() {
        int value = (int) (Math.random() * 9) + 1;//Sets variable value to random number from 1 to 9

        gameTile.setLayoutParams(lp); // Set the size of the gameTile
        gameTile.setTag(R.id.stack_value, value); // Sets value of the gameTile to value from 1 to 9
        gameTile.setTag(R.id.stack_column, -1); // -1 means no column yet
        gameTile.setY(coordinates[7][2].y); // Sets position of block to top middle
        gameTile.setX(coordinates[7][2].x);
        gameTile.setBackgroundColor(ContextCompat.getColor(StackActivity.getAppContext(), R.color.grey));
        Log.d("BrainSTEM S", "The gameTile created has this value: " + value + " at " + coordinates[7][2].x + " " + coordinates[7][2].y);
        ((TextView) gameTile).setGravity(Gravity.CENTER_VERTICAL | Gravity.CENTER_HORIZONTAL); // Centers the text
        ((TextView) gameTile).setTextSize(TEXT_SIZE); // Sets text size
        ((TextView) gameTile).setTextColor(Color.BLACK); // Sets text color
        ((TextView) gameTile).setText("" + value); // Sets the text on the block to the tag

        for(View button : buttons){
            ((TextView) button).setText("" + (int) gameTile.getTag(R.id.stack_value));
        }
        field.addView(gameTile);
    }

    public static Context getAppContext() {
        return StackActivity.context;
    }
    private void playInitialize(){
        play = (FloatingActionButton) findViewById(R.id.stack_ruleAccept);
        play.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                play.setEnabled(false);
                field.removeView(play);
                home.setEnabled(true);
                pause.setEnabled(true);
                ObjectAnimator play_fadeOut = ObjectAnimator.ofFloat(pause_menu, "alpha", 0);
                play_fadeOut.start();
                for(View button : buttons){
                    button.setEnabled(true);
                }
                if(!initializationComplete) {
                    initializationComplete = true;
                    gameStart(); // Set up the game playing field with buttons
                }
            }
        });
    }
    private Pair newRule(int i){
        boolean works = false;
        Pair a = new Pair((int) (Math.random() * 10) + 1, (int) (Math.random() * 10) + 1);
        while(!works){
            boolean unique = true;
            for (Pair compare : rules){
                if(a.equals(compare)){
                    unique = false;
                    a = new Pair((int) (Math.random() * 10) + 1, (int) (Math.random() * 10) + 1);
                }
            }
            if(unique) works = true;
        }
        Log.d("BrainSTEM S", "Rule created: " + a.toString());
        rules.add(a);
        switch (i){
            case 1:
                ruleNotify.setText(context.getString(R.string.rule_prefix) + "\n" + a.toString());
                break;
            case 2:
                ruleNotify.setText(context.getString(R.string.win) + "\n" +context.getString(R.string.rule_prefix) + "\n" + a.toString());
                break;
        }
        return  a;
    }
    private void clearBoard(){
        for(ArrayList<View> column : grid){
            for(View v : column){
                field.removeView(v);
            }
            column.clear();
        }
        initializationComplete = false;
    }
}
