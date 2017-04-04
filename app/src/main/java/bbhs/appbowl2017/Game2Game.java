package bbhs.appbowl2017;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.graphics.Point;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import java.util.ArrayList;

public class Game2Game extends AppCompatActivity {

    private int gridSizeX = 5;
    private int gridSizeY = 7;
    private int scaleX;
    private int scaleY;
    private boolean paused = false;

    private ArrayList<Animator> animations = new ArrayList<>();
    private ArrayList<ArrayList<Block>> grid;
    private Point[][] coordinates;

    private Button home;
    private Button pause;

    @Override
    protected void onCreate(Bundle savedInstanceState){

        grid = new ArrayList<>();
        for(int i = 0; i < gridSizeX; i++){
            grid.add(new ArrayList<Block>());
        }

        RelativeLayout field = (RelativeLayout) findViewById(R.id.game_field);
        initField(field);

        requestWindowFeature(Window.FEATURE_NO_TITLE);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game2game);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        home = (Button) findViewById(R.id.game2_home);
        pause = (Button) findViewById(R.id.game2_pause);


        buttonsClicked();//Set the click listeners
        //ArrayList<TranslateAnimation> anim = new ArrayList<>();

        while(!paused){
            for(int i = 0; i < grid.size(); i++){
                ArrayList<Block> column = grid.get(i);
                for(int k = 0; k < column.size(); k++){
                    Block block = column.get(k);
                    if(!block.falling) {
                        block.initAnimation(i, k);
                    }
                }
            }
            if(!animations.isEmpty()){
                AnimatorSet as = new AnimatorSet();
                as.playTogether(animations);
                as.addListener(new AnimatorListenerAdapter() {
                    @Override
                    public void onAnimationEnd(Animator animation) {
                        for(ArrayList<Block> column : grid){
                            for(Block block : column){
                                block.falling = false;
                                animations.clear();
                            }
                        }
                    }
                });
            }else{
                Block block = new Block((int) (10 * Math.random()));
                ImageView image = new ImageView(getApplicationContext());
                //TODO set imageView position
                field.addView(image);
            }
        }
    }


    private void buttonsClicked(){
        home.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                paused = true;
                //TODO the card thing asking for confirmation
                startActivity(new Intent(getApplicationContext(), MainActivity.class));
            }
        });
        pause.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                    paused = !paused;
            }
        });
    }

    private void initField(RelativeLayout field){
        scaleX = field.getWidth()/gridSizeX;
        scaleY = field.getHeight()/gridSizeY;

        coordinates = new Point[gridSizeY][gridSizeX];
        for(int i = 0; i < gridSizeY; i++){
            Point[] row = coordinates[i];
            for(int k = 0; k < gridSizeX; k++){
                coordinates[i][k] = new Point(scaleX * k, scaleY * i);
            }
        }
    }

    private class Block{
        int value;
        ImageView image;
        //Point target;
        boolean falling;

        public Block(int value){
            this.value = value;
            falling = false;
        }

        public void initAnimation(int x, int y){
            ObjectAnimator move = null;
            if(image.getLeft() != coordinates[y][x].x){
                move = ObjectAnimator.ofFloat(image, "x", coordinates[y][x].x);
            }else if(image.getTop() != coordinates [y][x].y){
                move = ObjectAnimator.ofFloat(image, "y", coordinates[y][x].y);
            }
            if(move != null){
                falling = true;
                animations.add(move);
            }
        }

    }
}
