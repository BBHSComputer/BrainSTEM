package bbhs.appbowl2017;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.graphics.Point;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashSet;

public class StackGameActivity extends AppCompatActivity {

    private int textSize = 36;
    private int gridSizeX = 5;
    private int gridSizeY = 7;

    private boolean animationRunning = false;
    private boolean hasInit = false;

    private ArrayList<Animator> animations = new ArrayList<>();
    private ArrayList<ArrayList<Block>> grid;
    private Point[][] coordinates;
    private AnimatorSet as;
    private HashSet<Block> remove;

    private RelativeLayout.LayoutParams lp;
    private RelativeLayout field;
    private Button home;
    private Button pause;
    private Block gameBlock;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        //requestWindowFeature(Window.FEATURE_NO_TITLE);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stackgame);
        //getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        Log.d("Test", "post SetContentView");

        field = (RelativeLayout) findViewById(R.id.game2_field);
        home = (Button) findViewById(R.id.game2_home);
        pause = (Button) findViewById(R.id.game2_pause);
        Log.d("Test", "post find Home and Pause");

        grid = new ArrayList<>();
        for(int i = 0; i < gridSizeX; i++){
            grid.add(new ArrayList<Block>());
        }

    }

    @Override
    public void onWindowFocusChanged(boolean hasFocus){

        int scaleX = field.getWidth()/gridSizeX;
        int scaleY = field.getHeight()/gridSizeY;

        coordinates = new Point[gridSizeY][gridSizeX];
        for(int i = 0; i < gridSizeY; i++){
            for(int k = 0; k < gridSizeX; k++){
                coordinates[i][k] = new Point(scaleX * k, field.getHeight() - (scaleY * (i+1)));
            }
        }
        Log.d("Test", "size is" + scaleX + "," + scaleY + "," + field.getWidth());

        lp = new RelativeLayout.LayoutParams(scaleX, scaleY);

        if(!hasInit){
            hasInit = true;
            gameStart();
        }
    }
    private void gameStart(){
        gameBlock = new Block((int) (10 * Math.random()));
        gameBlock.initialize();
        //TODO set imageView position

        for(int column = 0; column < 5; column++) {
            Block col = new Block(-1);
            col.initialize();
            grid.get(column).add(col);

            col.image.setOnClickListener(new View.OnClickListener(){
                @Override
                public void onClick(View view){
                    if(!animationRunning){
                        //placeBlock(gameBlock, 0, grid.get(column));
                        updatePositions();
                        gameBlock = new Block((int) (10 * Math.random()));
                        gameBlock.initialize();
                    }
                }
            });

        }

        Log.d("Test", grid.toString());
        //Log.d("Test", coordinates.toString());

        updatePositions();
        buttonsClicked();//Set the click listeners
    }

    private void placeBlock(Block block, int n, Block button){
        grid.get(n).add(grid.get(n).indexOf(button), block);
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
        as.playTogether(animations);
        as.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {//TODO Fix this broken mess of code
                for (ArrayList<Block> column : grid) {
                    for (Block block : column) {
                        //block.falling = false;
                        animations.clear();
                        animationRunning = false;
                    }
                }
            }
        });
        animationRunning = true;
        as.start();
        Log.d("Test", "Update position finished");
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

        public Block(int value){
            this.value = value;
            //falling = false;
        }
        public void initialize(){
            Log.d("Test", "block created with value" + this.value);
            if(this.value == -1){
                image = new ImageView(getApplicationContext());
                image.setLayoutParams(lp);
                ((ImageView) image).setImageResource(R.mipmap.tap);
            }else {
                image = new TextView(getApplicationContext());
                image.setLayoutParams(lp);
                ((TextView)image).setGravity(Gravity.CENTER_VERTICAL | Gravity.CENTER_HORIZONTAL);
                ((TextView)image).setTextSize(textSize);
                ((TextView)image).setTextColor(Color.BLACK);
                ((TextView)image).setText("" + this.value);
                //image.generateViewId();
            }
            //this.image = image;
            field.addView(this.image);
        }

        public boolean finishAnim(int x, int y){
            return !(Math.abs(image.getLeft() - coordinates[y][x].x) > 1 || Math.abs(image.getTop() - coordinates[y][x].y) > 1);
        }

        public void initAnimation(int x, int y){
            Log.d("Test", "" + (image.getLeft() - coordinates[y][x].x) + "," + (image.getTop() - coordinates [y][x].y));
            if(!finishAnim(x,y)) {
                AnimatorSet blockMove = new AnimatorSet();
                ObjectAnimator moveX = ObjectAnimator.ofFloat(image, "x", coordinates[y][x].x);
                ObjectAnimator moveY = ObjectAnimator.ofFloat(image, "y", coordinates[y][x].y);
                blockMove.playSequentially(moveX, moveY);
                animations.add(blockMove);
            }
            //this.falling = true;
            Log.d("Test", "post AnimInit");
        }

        @Override
        public String toString(){
            return "" + value;
        }

    }

}
