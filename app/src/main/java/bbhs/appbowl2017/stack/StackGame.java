package bbhs.appbowl2017.stack;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.graphics.Color;
import android.graphics.Point;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Set;

import bbhs.appbowl2017.R;

public class StackGame extends AppCompatActivity {
    public final int SIZE_Y = 7;
    public final int SIZE_X = 5;
    public final int TEXT_SIZE = 36;

    private int fieldSizeX;
    private int fieldSizeY;
    private boolean tellRules;
    private boolean hasBeenInitialized;

    private ArrayList<View>[] grid;
    private View[] buttons;
    private Point[][] coordinates;
    private ArrayList<Rule> rules;

    private TextView gameTile;
    private RelativeLayout.LayoutParams lp;
    private RelativeLayout field;
    private TextView welcomeText;
    private ProgressBar life;
    private Button play;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stackgame);

        buttons = new View[SIZE_X];
        grid = new ArrayList[SIZE_X];//Initialize grid
        for(int i = 0; i < SIZE_X; i++) {
            grid[i] = new ArrayList<View>();
        }

        coordinates = new Point[SIZE_X][SIZE_Y];

        field = (RelativeLayout) findViewById (R.id.stack_field);
        life = (ProgressBar) findViewById(R.id.stack_life);
        welcomeText = (TextView) findViewById(R.id.rule_Text);
        play = (Button) findViewById(R.id.stack_ruleAccept); //Initiate the game
        play.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                for(View button : buttons) {
                    if(grid[(int) button.getTag(R.id.stack_column)].size() < SIZE_Y){//TODO add tags
                        button.setEnabled(true);
                    }
                    if(!hasBeenInitialized) {
                        hasBeenInitialized = true;
                        startGame();
                    }else{
                        //TODO add resume function
                    }
                }
            }
        });

        life.setScaleY(3.0f);

        int initNumRules = getIntent().getExtras().getInt("ruleNum");
        createRules(initNumRules);

        tellRules = getIntent().getExtras().getBoolean("tellRules");
        if(tellRules) {
            String listOfRules = "";
            for(Rule rule : rules){
                listOfRules = listOfRules + rule.toString() + "\n";
            }
            welcomeText.setText("Welcome to Stack!\n" + getApplicationContext().getString(R.string.rule_prefix) + "\n" + listOfRules);
            //Set to welcome user and tell them what rules they have
        }else {
            welcomeText.setText("Welcome to Stack!\nYou have " + rules.size() + " new rules");
            //Set to welcome user and tell how many rules they have
        }
    }

    @Override
    public void onWindowFocusChanged(boolean hasFocus){
        super.onWindowFocusChanged(hasFocus);

        if(hasFocus && (fieldSizeX != field.getWidth() || fieldSizeY != field.getHeight())){
            fieldSizeX = field.getWidth();
            fieldSizeY = field.getHeight();

            int scale = Math.min(fieldSizeX / SIZE_X, fieldSizeY / SIZE_Y);
            int margins = (int) (scale * 0.05);
            int shiftX = (fieldSizeX - (scale * SIZE_X)) / 2;//To center the playing field

            lp = new RelativeLayout.LayoutParams(scale - margins, scale - margins);
            lp.setMargins(margins, margins, margins, margins);

            for(int x = 0; x < SIZE_X; x++) {
                int xCoordinate = x * scale;//So the loop doesn't have to do the multiplication all the time
                for(int y = 0; y < SIZE_Y; y++) {
                    coordinates[x][y] = new Point(xCoordinate + shiftX, fieldSizeY - (y * scale));
                }
            }

            if(gameTile != null) gameTile.setLayoutParams(lp);//Update the size of each stacked block if needed
            for(int i = 0; i < SIZE_X; i++) {
                for(View v : grid[i]) v.setLayoutParams(lp);
            }
        }
    }

    private void startGame(){
        for(int i = 0; i < SIZE_X; i++){//Column of added gameButton
            TextView gameButton = new TextView(getApplicationContext());
            gameButton.setLayoutParams(lp);

            gameButton.setTag(R.id.stack_value, -1);//Indicates it is a gameButton
            gameButton.setTag(R.id.stack_column, i);//Indicates the column it is in
            gameButton.setFocusableInTouchMode(false);//Stops double tapping to place blocks
            gameButton.setGravity(Gravity.CENTER_VERTICAL | Gravity.CENTER_HORIZONTAL); // Centers the text
            gameButton.setTextSize(TEXT_SIZE); // Sets text size
            gameButton.setTextColor(Color.BLACK); // Sets text color
            gameButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    grid[(int) view.getTag(R.id.stack_column)].add(gameTile);
                    gameTile.setTag(R.id.stack_column, view.getTag(R.id.stack_column));

                    AnimatorSet fadeOut = new AnimatorSet();
                    for(View button : buttons){
                        button.setEnabled(false);

                        ObjectAnimator fadeObject = ObjectAnimator.ofFloat(button, "alpha", 0f);
                        fadeOut.setDuration(100);
                        fadeOut.play(fadeObject);
                        //TODO add life animation
                    }

                    updatePositions();
                }
            });

            gameButton.setX(coordinates[i][0].x);
            gameButton.setY(coordinates[i][0].y);

            buttons[i] = gameButton;
        }
    }

    private void updatePositions(){

    }

    private void createRules(int num){
        for(int i = 0; i < num; i++){//For how many rules are generated
            boolean unique = false;//Make sure each rule is unique
            while(!unique){
                unique = true;
                Rule rule = new Rule((int) (Math.random() * 9) + 1, (int) (Math.random() * 9) + 1);
                for(Rule compare : rules){//Comparison of rules to ones already in list
                    if(compare.equals(rule)){
                        unique = false;
                        break;
                    }
                }
                if(unique) rules.add(rule);
            }
        }
        if(hasBeenInitialized){//Set text if game has been initialized already (new level)
            String listOfRules = "Next Level!";
            if(tellRules){
                listOfRules = listOfRules + "\nNew Rules:";
                for(int i = 0; i < num; i++){//Add rules to texView
                    listOfRules = listOfRules + "\n" + rules.get((rules.size() - num) + i);
                }
            }else{
                listOfRules = listOfRules + "\n" + num + " rules have been added!";
            }
            welcomeText.setText(listOfRules);
        }
    }


}
