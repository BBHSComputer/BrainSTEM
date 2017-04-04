package bbhs.appbowl2017;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.graphics.Point;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.ArrayList;

public class Game2Game extends AppCompatActivity {

    private int textSize = 36;
    private int gridSizeX = 5;
    private int gridSizeY = 7;
    private int scaleX;
    private int scaleY;
    private boolean animationRunning = false;

    private ArrayList<Animator> animations = new ArrayList<>();
    private ArrayList<ArrayList<Block>> grid;
    private Point[][] coordinates;
    AnimatorSet as;

    private RelativeLayout field;
    private Button home;
    private Button pause;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        //requestWindowFeature(Window.FEATURE_NO_TITLE);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game2game);
       // getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        Log.d("Test", "post SetContentView");

        field = (RelativeLayout) findViewById(R.id.game2_field);
        home = (Button) findViewById(R.id.game2_home);
        pause = (Button) findViewById(R.id.game2_pause);
        Log.d("Test", "post find Home and Pause");

        buttonsClicked();//Set the click listeners
    }
    @Override
    public void onWindowFocusChanged(boolean hasFocus){
        grid = new ArrayList<>();
        for(int i = 0; i < gridSizeX; i++){
            grid.add(new ArrayList<Block>());
        }

        scaleX = field.getWidth()/gridSizeX;
        scaleY = field.getHeight()/gridSizeY;

        coordinates = new Point[gridSizeY][gridSizeX];
        for(int i = 0; i < gridSizeY; i++){
            for(int k = 0; k < gridSizeX; k++){
                coordinates[i][k] = new Point(scaleX * k, field.getHeight() - (scaleY * (i+1)));
            }
        }
        Log.d("Test", "size is" + scaleX + "," + scaleY + "," + field.getWidth());

        RelativeLayout.LayoutParams lp = new RelativeLayout.LayoutParams(scaleX, scaleY);

        Block gameBlock = new Block((int) (10 * Math.random()));
        Log.d("Test", "block created with value" + gameBlock.value);
        TextView image = new TextView(this);
        image.setLayoutParams(lp);
        image.setGravity(Gravity.CENTER_VERTICAL | Gravity.CENTER_HORIZONTAL);
        image.setTextSize(textSize);
        image.setText("" + gameBlock.value);
        image.setTop(0);
        View.generateViewId();
        gameBlock.image = image;
        field.addView(gameBlock.image);

        //TODO set imageView position

        Block col0 = new Block(0);
        col0.image = new ImageView(getApplicationContext());
        ((ImageView) col0.image).setImageResource(R.mipmap.tap);
        col0.image.setLayoutParams(lp);
        grid.get(0).add(col0);
        field.addView(col0.image);

        Block col1 = new Block(0);
        col1.image = new ImageView(getApplicationContext());
        ((ImageView) col1.image).setImageResource(R.mipmap.tap);
        col1.image.setLayoutParams(lp);
        grid.get(1).add(col1);
        field.addView(col1.image);

        Block col2 = new Block(0);
        col2.image = new ImageView(getApplicationContext());
        ((ImageView) col2.image).setImageResource(R.mipmap.tap);
        col2.image.setLayoutParams(lp);
        grid.get(2).add(col2);
        field.addView(col2.image);

        Block col3 = new Block(0);
        col3.image = new ImageView(getApplicationContext());
        ((ImageView) col3.image).setImageResource(R.mipmap.tap);
        col3.image.setLayoutParams(lp);
        grid.get(3).add(col3);
        field.addView(col3.image);

        Block col4 = new Block(0);
        col4.image = new ImageView(getApplicationContext());
        ((ImageView) col4.image).setImageResource(R.mipmap.tap);
        col4.image.setLayoutParams(lp);
        grid.get(4).add(col4);
        field.addView(col4.image);

        Log.d("Test", grid.toString());

        updatePositions();

        col1.image.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){

            }
        });
    }

    private void updatePositions(){
        as = new AnimatorSet();
        for(int i = 0; i < grid.size(); i++){
            ArrayList<Block> column = grid.get(i);
            for(int k = 0; k < column.size(); k++){
                Block block = column.get(k);
                if(!block.finishAnim(i,k )) {
                    block.initAnimation(i, k);
                }
            }
        }
        as.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                for (ArrayList<Block> column : grid) {
                    for (Block block : column) {
                        block.falling = false;
                        animations.clear();
                    }
                }
            }
        });
        animationRunning = true;
        as.start();
        Log.d("Test", "True");
    }


    private void buttonsClicked(){
        home.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                //paused = true;
                //TODO the card thing asking for confirmation
                startActivity(new Intent(getApplicationContext(), MainActivity.class));
            }
        });
        pause.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                    //paused = !paused;
            }
        });
    }

    private class Block{
        int value;
        View image;
        //Point target;
        boolean falling;

        public Block(int value){
            this.value = value;
            falling = false;
        }

        public boolean finishAnim(int x, int y){
            return !(Math.abs(image.getLeft() - coordinates[y][x].x) > 1 || Math.abs(image.getTop() - coordinates[y][x].y) > 1);
        }

        public void initAnimation(int x, int y){
            Log.d("Test", "" + (image.getLeft() - coordinates[y][x].x) + "," + (image.getTop() - coordinates [y][x].y));
            if(!finishAnim(x,y)) {
                ObjectAnimator moveX = ObjectAnimator.ofFloat(image, "x", coordinates[y][x].x);
                ObjectAnimator moveY = ObjectAnimator.ofFloat(image, "y", coordinates[y][x].y);
                as.playSequentially(moveX, moveY);
                as.start();
            }
            falling = true;
            Log.d("Test", "post AnimInit");
        }

        @Override
        public String toString(){
            return "" + value;
        }

    }
}
