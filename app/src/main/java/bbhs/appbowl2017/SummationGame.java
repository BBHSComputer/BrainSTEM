package bbhs.appbowl2017;

import android.os.Bundle;
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
        questions.add(new SimpleQuestion(this, "On which parking space is the car parked?", new String[]{"78", "89", "87", "97"}, 2, getResources().getIdentifier("parking" , "drawable", getPackageName())));
        questions.add(new SimpleQuestion(this, "A and C but not B", new String[]{"A: A", "B: B, A, and D", "C: C and D", "D: D and A"}, 2));
        questions.add(new SimpleQuestion(this, "Where is I?", new String[]{"Here", "There", "Hair", "Anywhere"}, 2));
        questions.add(new SimpleQuestion(this, "Choose the answer closest to the correct answer", new String[]{"the answer", "the answer", "the answer", "the answer"}, 0));
        questions.add(new BiggestQuestion(this, "Click on the biggest answer", new String[]{"Answer!", "Answer.", "Big", "Answer?"}));
        questions.add(new TimedQuestion(this, "Run out of time", new String[]{"Done", "Run", "No", "What?"}, 0, 5000));

        setUp(false);
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
            ((TextView) findViewById(R.id.score)).setText(questionNumber + " / " + questions.size());
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
