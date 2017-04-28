package bbhs.appbowl2017;

import android.view.View;
import android.widget.Button;
import android.widget.TextView;

/**
 * Created by Matias on 4/18/2017.
 */

public class BiggestQuestion extends Question {

    private String question;
    private String[] answers;
    private Button[] answerButtons = new Button[4];
    private int big = 0;

    public BiggestQuestion(SummationGame game, String question, String[] answers) {
        super(R.layout.activity_summation_biggest, game);

        this.question = question;
        this.answers = answers;
    }

    public void initiate() {
        game.setContentView(layoutResID);

        ((TextView) game.findViewById(R.id.title)).setText(question);

        answerButtons[0] = (Button) game.findViewById(R.id.answer1);
        answerButtons[1] = (Button) game.findViewById(R.id.answer2);
        answerButtons[2] = (Button) game.findViewById(R.id.answer3);
        answerButtons[3] = (Button) game.findViewById(R.id.answer4);

        answerButtons[0].setText(answers[0]);
        answerButtons[1].setText(answers[1]);
        answerButtons[2].setText(answers[2]);
        answerButtons[3].setText(answers[3]);

        answerButtons[0].setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                press(0);
            }
        });
        answerButtons[1].setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                press(1);
            }
        });
        answerButtons[2].setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                press(2);
            }
        });
        answerButtons[3].setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                press(3);
            }
        });
    }

    private void press(int index) { // Press an answer to make it big, and then press it again to proceed
        if (big == 0) {
            big = index + 1;
            answerButtons[index].setTextSize(answerButtons[index].getTextSize() * 1.1f);
        } else {
            game.onAnswered(index + 1 == big);
        }

    }

}
