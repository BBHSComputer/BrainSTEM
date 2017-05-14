package bbhs.appbowl2017.tile;

import android.content.Intent;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import bbhs.appbowl2017.MainActivity;
import bbhs.appbowl2017.R;

public class TileWinActivity extends AppCompatActivity {
    public static double score, length, moves;
    private TextView tScore,tTime,tMoves;
    private Button mainMenu,playAgain;
    public static double fTime, fMoves, fScore; //F meaning final, post Weiss' algorithms
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tile_win);

        tScore = (TextView) findViewById(R.id.score); //time moves
        tTime = (TextView) findViewById(R.id.time);
        tMoves = (TextView) findViewById(R.id.moves);

        mainMenu = (Button) findViewById(R.id.goHome);
        playAgain = (Button) findViewById(R.id.again);


        mainMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), MainActivity.class)); //if main menu go to main menu
            }
        });

        playAgain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // startActivity(new Intent(getApplicationContext(), TileSettingsActivity.class)); // if play again load p tile
            }
        });


        //calculate time to mm:ss
        int seconds = (int)length/1000; //ms to s
        int minutes = seconds/60; //s to m
        seconds = seconds %60; //clean up seconds

        tTime.setText(tTime.getText().toString() + minutes + ":" + seconds);
        tMoves.setText(tMoves.getText().toString() + moves);
        tScore.setText(tScore.getText().toString() + (int)(score*-100));
        fTime = minutes + seconds;
        fMoves = moves;
        fScore = score*-100;
        int savedTime = PreferenceManager.getDefaultSharedPreferences(getBaseContext()).getInt("tileTime", Integer.MAX_VALUE);
        if (savedTime > fTime){
            PreferenceManager.getDefaultSharedPreferences(getBaseContext()).edit().putInt("tileTime", (int)fTime).commit();
            savedTime = (int)fTime;
        }
        int savedMoves = PreferenceManager.getDefaultSharedPreferences(getBaseContext()).getInt("tileMoves", Integer.MAX_VALUE);
        if (savedMoves > fMoves){
            PreferenceManager.getDefaultSharedPreferences(getBaseContext()).edit().putInt("tileMoves", (int)fMoves).commit();
            savedMoves = (int)fMoves;
        }
        int savedScore = PreferenceManager.getDefaultSharedPreferences(getBaseContext()).getInt("tileScore", 0);
        if (savedScore < fScore){
            PreferenceManager.getDefaultSharedPreferences(getBaseContext()).edit().putInt("tileScore", (int)fScore).commit();
            savedScore = (int)fScore;
        }
        tTime.setText(tTime.getText().toString() + "\nBest: " + savedTime);
        tMoves.setText(tMoves.getText().toString() + "\nBest: " + savedMoves);
        tScore.setText(tScore.getText().toString() + "\nBest: " + savedScore);



    }
}
