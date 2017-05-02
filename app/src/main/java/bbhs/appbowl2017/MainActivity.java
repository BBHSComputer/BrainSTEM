package bbhs.appbowl2017;

import android.content.Intent;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;

public class MainActivity extends AppCompatActivity {


    TextView hello;

    Button game1, game2, game3, game4;

    public RelativeLayout layout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        layout = (RelativeLayout) findViewById(R.id.mainLayout);

        game1 = (Button) findViewById(R.id.game1);
        game2 = (Button) findViewById(R.id.game2);
        game3 = (Button) findViewById(R.id.game3);
        game4 = (Button) findViewById(R.id.game4);
        ImageView stackImage = (ImageView) findViewById(R.id.stackImage);
        ImageView tileImage = (ImageView) findViewById(R.id.tileImage);
        ImageView sumImage = (ImageView) findViewById(R.id.sumImage);
        ImageView musicImage = (ImageView) findViewById(R.id.musicImage);

        Glide.with(this).load(R.drawable.stack).into(stackImage);
        Glide.with(this).load(R.drawable.tile).into(tileImage);
        Glide.with(this).load(R.drawable.summation).into(sumImage);
        Glide.with(this).load(R.drawable.music).into(musicImage);

        gamesSetOnClick();


    }

    public void gamesSetOnClick() {
        game1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Snackbar snackbar = Snackbar
                        .make(layout, "Loading up Stack...", Snackbar.LENGTH_LONG);

                snackbar.show();
                startActivity( new Intent(getApplicationContext(), StackActivity2.class));

            }
        });

        game2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Snackbar snackbar = Snackbar
                        .make(layout, "Loading up Tile...", Snackbar.LENGTH_LONG);

                snackbar.show();
                startActivity(new Intent(getApplicationContext(), TileSettingsActivity.class));
            }
        });

        game3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Snackbar snackbar = Snackbar
                        .make(layout, "Loading up Summation...", Snackbar.LENGTH_LONG);

                snackbar.show();
                startActivity(new Intent(getApplicationContext(), SummationGame.class));
            }
        });

        game4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Snackbar snackbar = Snackbar
                        .make(layout, "Loading up Music...", Snackbar.LENGTH_LONG);

                snackbar.show();
                startActivity(new Intent(getApplicationContext(), MusicActivity.class));
            }
        });
    }

}
