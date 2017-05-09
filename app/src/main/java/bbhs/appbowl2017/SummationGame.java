package bbhs.appbowl2017;

import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class SummationGame extends AppCompatActivity implements QuestionListener {

    private List<Question> questions = new ArrayList<>();
    private int questionNumber;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_summation);

        // Setup base questions
        questions.add(new SimpleQuestion(this, getString(R.string.q1), new String[]{getString(R.string.q1a), getString(R.string.q1b), getString(R.string.q1c), getString(R.string.q1d)}, 2, getResources().getIdentifier("parking" , "drawable", getPackageName())));
        questions.add(new SimpleQuestion(this, getString(R.string.q2), new String[]{getString(R.string.q2a), getString(R.string.q2b), getString(R.string.q2c), getString(R.string.q2d)}, 2));
        questions.add(new SimpleQuestion(this, getString(R.string.q3), new String[]{getString(R.string.here), getString(R.string.there), getString(R.string.hair), getString(R.string.anywhere)}, 2));
        questions.add(new SimpleQuestion(this, getString(R.string.q4), new String[]{getString(R.string.answer), getString(R.string.answer), getString(R.string.answer), getString(R.string.answer)}, 0));
        questions.add(new BiggestQuestion(this, getString(R.string.q5), new String[]{getString(R.string.answer) + "!", getString(R.string.answer) + ".", getString(R.string.big), getString(R.string.answer) + "?"}));
        questions.add(new TimedQuestion(this, getString(R.string.q6), new String[]{getString(R.string.done), getString(R.string.run), "No", getString(R.string.what)}, 0, 5000));

        questions.get(questionNumber).initiate();
    }
 
    @Override
    public void onAnswered(boolean correct) {
        if (correct) {
            if (++questionNumber == questions.size()) {
                setUp(true); // Win
            } else {
                questions.get(questionNumber).initiate(); // Next question
            }
        } else {
            setUp(true); // Loss
        }
    }

    private void setUp(boolean end) { // Set up the Summation game for future playing
        if (end) {
            setContentView(R.layout.activity_summation_end);
            ((TextView) findViewById(R.id.title)).setText(questionNumber == questions.size() ? "Congratulations!" : "So close!");
            int savedScore = PreferenceManager.getDefaultSharedPreferences(getBaseContext()).getInt("sumScore", 0);
            if (savedScore < questionNumber){
                PreferenceManager.getDefaultSharedPreferences(getBaseContext()).edit().putInt("sumScore", questionNumber).commit();
                savedScore = questionNumber;
            }
            ((TextView) findViewById(R.id.score)).setText(questionNumber + " / " + questions.size() + "\nBest: " + savedScore + " / " + questions.size());
        }

        Collections.shuffle(questions);
        questionNumber = 0;

        ((Button) findViewById(R.id.playButton)).setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                questions.get(questionNumber).initiate();
            }
        });
    }

}
