package bbhs.appbowl2017;


import android.content.Intent;
import android.os.Bundle;
import android.preference.PreferenceManager;
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

        int savedPlaced = PreferenceManager.getDefaultSharedPreferences(getBaseContext()).getInt("placed", 0);
        if (savedPlaced < placed){
            PreferenceManager.getDefaultSharedPreferences(getBaseContext()).edit().putInt("placed", placed).commit();
            savedPlaced = placed;
        }
        int savedRules = PreferenceManager.getDefaultSharedPreferences(getBaseContext()).getInt("broken", 0);
        if (savedRules < rulesBroken){
            PreferenceManager.getDefaultSharedPreferences(getBaseContext()).edit().putInt("broken", rulesBroken).commit();
            savedRules = rulesBroken;
        }
        int savedNum = PreferenceManager.getDefaultSharedPreferences(getBaseContext()).getInt("rules", 0);
        if (savedNum < numRules){
            PreferenceManager.getDefaultSharedPreferences(getBaseContext()).edit().putInt("rules", numRules).commit();
            savedNum = numRules;
        }

        statistics = (TextView) findViewById(R.id.stack_stat);
        String rules = getApplicationContext().getString(R.string.rules);
        String blockPlaced = getApplicationContext().getString(R.string.placed);
        String broken = getApplicationContext().getString(R.string.broken);

        statistics.setText(rules + " " + numRules + "\n" + blockPlaced + " " + placed + "\n" + broken + " " + rulesBroken + "\n\nRecord Num of Rules: " + savedNum + "\nRecord Blocks Placed: " + savedPlaced + "\nMost Rules Broken: " + savedRules);

        back = (RelativeLayout) findViewById(R.id.stack_win_back);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), MainActivity.class));
            }
        });
    }
}
