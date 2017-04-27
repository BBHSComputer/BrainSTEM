package bbhs.appbowl2017;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class SummationGame extends AppCompatActivity implements QuestionListener {

    private List<Question> questions = new ArrayList<>();
    private int questionNumber = 0;
    private Button playButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_summation);

        // Setup questions
        questions.add(new SimpleQuestion(this, "On which parking space is the car parked?", new String[]{"78", "89", "87", "97"}, 2, getResources().getIdentifier("parking" , "drawable", getPackageName())));
        questions.add(new SimpleQuestion(this, "A and C but not B", new String[]{"A: A", "B: B, A, and D", "C: C and D", "D: D and A"}, 2));
        questions.add(new SimpleQuestion(this, "Where is I?", new String[]{"Here", "There", "Hair", "Anywhere"}, 2));
        questions.add(new SimpleQuestion(this, "Choose the answer closest to the correct answer", new String[]{"the answer", "the answer", "the answer", "the answer"}, 0));
        questions.add(new BiggestQuestion(this, "Click on the biggest answer", new String[]{"Answer!", "Answer.", "Big", "Answer?"}));
        questions.add(new TimedQuestion(this, "Run out of time", new String[]{"Done", "Run", "No", "What?"}, 0, 5000));
        Collections.shuffle(questions);

        playButton = (Button) findViewById(R.id.playButton);

        playButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                questions.get(questionNumber).initiate();
            }
        });

    }
 
    @Override
    public void onAnswered(boolean correct) {
        if (correct) {
            if (++questionNumber == questions.size()) {
                setContentView(R.layout.activity_summation); // Win
                questionNumber = 0;
            } else {
                questions.get(questionNumber).initiate(); // Next question
            }
        } else {
            setContentView(R.layout.activity_summation); // Loss
            questionNumber = 0;
        }
    }

}
