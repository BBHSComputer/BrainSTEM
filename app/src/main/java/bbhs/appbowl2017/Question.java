package bbhs.appbowl2017;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;

public abstract class Question {

    protected final int layoutResID;
    protected final Game4 game;

    protected Question(int layoutResID, Game4 game) {
        this.layoutResID = layoutResID;
        this.game = game;
    }

    public abstract void initiate();

}
