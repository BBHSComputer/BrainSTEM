package bbhs.appbowl2017;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class TileWinActivity extends AppCompatActivity {
    public static double score, length, moves;
    private TextView tScore,tTime,tMoves;
    private Button mainMenu,playAgain;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tile_win2);

        tScore = (TextView) findViewById(R.id.score); //time moves
        tTime = (TextView) findViewById(R.id.time);
        tMoves = (TextView) findViewById(R.id.moves);

        mainMenu = (Button) findViewById(R.id.goHome);
        playAgain = (Button) findViewById(R.id.again);


        mainMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), MainActivity.class));
            }
        });

        playAgain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), TileSettingsActivity.class));
            }
        });


        //calculate time to mm:ss
        int seconds = (int)length/1000; //ms to s
        int minutes = seconds/60; //s to m
        seconds = seconds %60; //clean up seconds

        tTime.setText(tTime.getText().toString() + minutes + ":" + seconds);
        tMoves.setText(tMoves.getText().toString() + moves);
        tScore.setText(tScore.getText().toString() + (int)(score*100));



    }
}
