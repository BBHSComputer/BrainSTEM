package bbhs.appbowl2017;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {


    TextView hello;

    Button game1, game2, game3, game4;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);



        hello = (TextView) findViewById(R.id.textView);
        game1 = (Button) findViewById(R.id.game1);
        game2 = (Button) findViewById(R.id.game2);
        game3 = (Button) findViewById(R.id.game3);
        game4 = (Button) findViewById(R.id.game4);

        hello.setText("i work ?");
        gamesSetOnClick();


    }

    public void gamesSetOnClick() {
        game1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                hello.setText("playing game 1");
            }
        });

        game2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                hello.setText("playing game 2");
                startActivity(new Intent(getApplicationContext(), StackActivity2.class));
            }
        });

        game3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                hello.setText("playing game 3");
            }
        });

        game4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                hello.setText("playing game 4");
            }
        });
    }

}
