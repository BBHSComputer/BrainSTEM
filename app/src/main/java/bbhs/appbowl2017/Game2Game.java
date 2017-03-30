package bbhs.appbowl2017;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Display;
import android.view.Window;
import android.view.WindowManager;
import android.graphics.Point;
import android.widget.ImageView;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

public class Game2Game extends AppCompatActivity {
    private Timer timer;

    protected static final int FPS = 30;
    private int speed;
    private int gridSizeX = 5;
    private int gridSizeY = 10;
    private int scaleX;
    private int scaleY;
    private boolean falling = false;

    private ArrayList<ArrayList<Block>> grid;
    private Point[][] coordinates;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        grid = new ArrayList<>();
        for(int i = 0; i < gridSizeX; i++){
            grid.add(new ArrayList<Block>());
        }

        Point screen = new Point();
        Display display = getWindowManager().getDefaultDisplay();
        display.getSize(screen);

        scaleX = screen.x/gridSizeX;
        scaleY = screen.y/gridSizeY;

        coordinates = new Point[gridSizeY][gridSizeX];
        for(int i = 0; i < gridSizeY; i++){
            Point[] row = coordinates[i];
            for(int k = 0; k < gridSizeX; k++){
                coordinates[i][k] = new Point(scaleX * k, scaleY * i);
            }
        }

        requestWindowFeature(Window.FEATURE_NO_TITLE);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game2game);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);


        timer = new Timer();
        timer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                if(falling){
                    for(ArrayList<Block> list : grid){
                        for(Block block : list){
                            //TODO move block down
                        }
                    }
                }else{
                    //TODO Other things, such as the main piece navigation, and the check rules
                }
            }
        }, 0, 1000 / FPS );
    }

    private class Block{
        int value;
        ImageView image;

        public Block(int value){
            this.value = value;
            switch (value){
                case 0:
                    image = null;
                    break;
                default:
                    image = null;
                    break;
            }
        }
    }
}
