package bbhs.appbowl2017;

import android.app.Activity;
import android.content.Intent;
import android.support.design.widget.Snackbar;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;

import static bbhs.appbowl2017.R.mipmap.ic_launcher;

public class MainActivity extends AppCompatActivity {


    TextView hello;

    Button game1, game2, game3, game4;

    public RelativeLayout layout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        layout = (RelativeLayout) findViewById(R.id.mainLayout);

        hello = (TextView) findViewById(R.id.textView);
        game1 = (Button) findViewById(R.id.game1);
        game2 = (Button) findViewById(R.id.game2);
        game3 = (Button) findViewById(R.id.game3);
        game4 = (Button) findViewById(R.id.game4);

        hello.setText("Welcome to Brain-STEM! Select one of the games below to play!");
        gamesSetOnClick();


    }

    public void gamesSetOnClick() {
        game1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                hello.setText("Loading up Matching Game...");
                startActivity( new Intent(getApplicationContext(),Game1SettingsActivity.class));

            }
        });

        game2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                hello.setText("Loading up Game 2...");
                Snackbar snackbar = Snackbar
                        .make(layout, "Games 2-4 are disabled for this demo.", Snackbar.LENGTH_LONG);

                snackbar.show();
            }
        });

        game3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                hello.setText("Loading up Game 3...");
                Snackbar snackbar = Snackbar
                        .make(layout, "Games 2-4 are disabled for this demo.", Snackbar.LENGTH_LONG);

                snackbar.show();
            }
        });

        game4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                hello.setText("Loading up Game 4...");
                startActivity( new Intent(getApplicationContext(),Game4.class));

            }
        });
    }

}
