package bbhs.appbowl2017.summation;

import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.Timer;
import java.util.TimerTask;

import bbhs.appbowl2017.R;

/**
 * Created by Matias on 4/26/2017.
 */

public class TimedQuestion extends Question {
    private String question;
    private String[] answers;
    private Button[] answerButtons = new Button[4];
    private TextView text;
    private long wait;
    private int correctAnswer;
    private boolean waited;

    protected TimedQuestion(SummationGame game, String question, String[] answers, int correctAnswer, long wait) {
        super(R.layout.activity_summation_simple, game);

        this.question = question;
        this.answers = answers;
        this.wait = wait;
        this.correctAnswer = correctAnswer;
        waited = false;
    }

    public void initiate() {
        game.setContentView(layoutResID);

        answerButtons[0] = (Button) game.findViewById(R.id.answer1);
        answerButtons[1] = (Button) game.findViewById(R.id.answer2);
        answerButtons[2] = (Button) game.findViewById(R.id.answer3);
        answerButtons[3] = (Button) game.findViewById(R.id.answer4);

        text = (TextView) game.findViewById(R.id.title);
        text.setText(question);

        for (int i = 0; i < answerButtons.length; i++) {
            setupButton(answerButtons[i], answers[i], i);
        }

        // Sets timer to "run out of time"
        new Timer().schedule(new TimerTask() {
            @Override
            public void run() {
                waited = true;
                game.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        answerButtons[correctAnswer].setTextSize(answerButtons[correctAnswer].getTextSize() * 1.1f);
                    }
                });
            }
        }, wait);
    }

    private void setupButton(Button button, String text, final int buttonIndex) {
        button.setText(text);
        button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                game.onAnswered(buttonIndex == correctAnswer && waited);
            }
        });
    }
}
