package bbhs.appbowl2017;


import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class StackWin extends AppCompatActivity{
    private static int placed;
    private static int rulesBroken;
    private static int numRules;

    private TextView statistics;
    private RelativeLayout back;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stack_win);

        Bundle bundle = getIntent().getExtras();

        placed = bundle.getInt("placed");
        rulesBroken = bundle.getInt("broken");
        numRules = bundle.getInt("rules");

        statistics = (TextView) findViewById(R.id.stack_stat);
        String rules = getApplicationContext().getString(R.string.rules);
        String blockPlaced = getApplicationContext().getString(R.string.placed);
        String broken = getApplicationContext().getString(R.string.broken);

        statistics.setText(rules + " " + numRules + "\n" + blockPlaced + " " + placed + "\n" + broken + " " + rulesBroken);

        back = (RelativeLayout) findViewById(R.id.stack_win_back);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), MainActivity.class));
            }
        });
    }
}
