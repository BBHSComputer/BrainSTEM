package bbhs.appbowl2017;

import android.content.Intent;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.RelativeLayout;

public class StackActivity2 extends AppCompatActivity {

    public RelativeLayout layout;
    public ImageButton play;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stack);
        play = (ImageButton) findViewById(R.id.game2_gameButton);
        layout = (RelativeLayout) findViewById(R.id.game2_base);
        buttonsClicked();
    }
    public void buttonsClicked(){
        play.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                Snackbar snackbar =  Snackbar.make(layout, "Play clicked!", Snackbar.LENGTH_LONG);
                snackbar.show();
                startActivity(new Intent(getApplicationContext(), StackGameActivity.class));
            }
        });
    }


}
