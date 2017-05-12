package bbhs.appbowl2017.stack;

import android.graphics.Point;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
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

    private int fieldSizeX;
    private int fieldSizeY;
    private boolean tellRules;
    private boolean hasBeenInitialized;

    private ArrayList<View>[] grid;
    private View[] buttons;
    private Point[][] coordinates;
    private Set<Rule> rules;

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
                        //TODO add game start
                    }
                }
            }
        });

        life.setScaleY(3.0f);

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




}
