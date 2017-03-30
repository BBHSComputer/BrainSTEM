package bbhs.appbowl2017;

import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import org.w3c.dom.Text;

import javax.xml.datatype.Duration;

public class Game2 extends AppCompatActivity {

    public FloatingActionButton settings;
    public FloatingActionButton play;
    public LinearLayout layout;
    public TextView title;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game2);
        /*settings = (FloatingActionButton) findViewById(R.id.game2_settings);
        play = (FloatingActionButton) findViewById(R.id.game2_play);
        layout = (LinearLayout) findViewById(R.id.game2InfoLayout);
        title = (TextView) findViewById(R.id.game2_title);
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
        title.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                Snackbar snackbar =  Snackbar.make(layout, "Did you know that the Neostriatum is important to motor and reward functions?", Snackbar.LENGTH_LONG);
                snackbar.show();
            }
        });
        */
    }


}
