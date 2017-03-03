package bbhs.appbowl2017;

import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import javax.xml.datatype.Duration;

public class Game2 extends AppCompatActivity {

    public FloatingActionButton settings;
    public FloatingActionButton play;
    public LinearLayout layout;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game2);
        settings = (FloatingActionButton) findViewById(R.id.game2_settings);
        play = (FloatingActionButton) findViewById(R.id.game2_play);
        layout = (LinearLayout) findViewById(R.id.game2InfoLayout);
        buttonsClicked();
    }
    public void buttonsClicked(){
        play.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                Snackbar snackbar =  Snackbar.make(layout, "Play clicked!", Snackbar.LENGTH_LONG);
                snackbar.show();
            }
        });
        settings.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                Snackbar snackbar =  Snackbar.make(layout, "Settings clicked!", Snackbar.LENGTH_LONG);
                snackbar.show();
            }
        });
    }


}
