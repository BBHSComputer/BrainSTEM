package bbhs.appbowl2017;

import android.animation.ArgbEvaluator;
import android.animation.ObjectAnimator;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.Random;

/**
 * Created by adamf on 4/11/2017.
 */

public class MusicActivity extends AppCompatActivity {
    private int score = 0;
    Button song1button, song2button, song3button;
    MediaPlayer mediaPlayer;

    //Initializes variable for score, buttons to select song and the media player

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_music);
        song1button = (Button) findViewById(R.id.song1button);
        song2button = (Button) findViewById(R.id.song2button);
        song3button = (Button) findViewById(R.id.song3button);

        //Finds the buttons in the layout

        songsOnClick(); //Runs the songsOnClick method where you pick a song
    }
    public void songsOnClick(){ //Makes all buttons invisible when one is clicked and starts the song of the clicked button
        song1button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                song1button.setVisibility(View.GONE);
                song2button.setVisibility(View.GONE);
                song3button.setVisibility(View.GONE);
                startGame(1);
            }
        });

        song2button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                song1button.setVisibility(View.GONE);
                song2button.setVisibility(View.GONE);
                song3button.setVisibility(View.GONE);
                startGame(2);
            }
        });

        song3button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                song1button.setVisibility(View.GONE);
                song2button.setVisibility(View.GONE);
                song3button.setVisibility(View.GONE);
                startGame(3);
            }
        });
    }
    public void startGame(int song) { //Creates a mediaplayer of the song, playing back all of its notes through an array and a time delay handler
        if (song == 1) {
            mediaPlayer = MediaPlayer.create(this, R.raw.starspangledbanner); // Load the MIDI file
            mediaPlayer.start(); // Start playing the music

            // An array of the times (in ms) when all of the notes are to be played
            int arr[] = {0, 468, 625, 1250, 1875, 2500, 3750, 4218, 4375, 5000, 5625, 6250, 7500, 7812, 8124, 9062, 9375, 10000, 11250, 11562, 11875, 12500, 13125, 13750, 14375, 15000, 15468, 15625, 16249, 16875, 17500, 18750, 19218, 19375, 20000, 20625, 21250, 22500, 22812, 23125, 24062, 24375, 25000, 26250, 26562, 26875, 27500, 28125, 28750, 29375, 30000, 30468, 30625, 31250, 31875, 32499, 33750, 34062, 34375, 35000, 35625, 36250, 37500, 38125, 39062, 39375, 40000, 41250, 41562, 41875, 42500, 43125, 43750, 45000, 45625, 46250, 46875, 47187, 47500, 48125, 48750, 49375, 50000, 50312, 50625, 50937, 51250, 51875, 53125, 53593, 54062, 55468, 55937, 56406, 56875, 58750, 59218, 59687, 61093, 61562, 62500};
            for (int i : arr) {
                final Handler handler = new Handler();
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        createNote();
                    }
                }, i - 250); // Create a note at the specified time (from the array)
            }
        }
        else if (song == 2) {
            mediaPlayer = MediaPlayer.create(this, R.raw.takemeout);
            mediaPlayer.start();
            int arr[] = {1138, 1897, 2276, 2655, 3035, 3414, 4552, 5691, 6449, 6829, 7208, 7588, 7967, 9864, 10243, 10623, 11002, 11382, 11761, 12140, 12520, 13279, 13658, 14796, 15555, 15934, 16314, 16693, 17073, 17452, 17831, 18211, 18590, 18970, 19349, 20108, 20487, 20867, 21246, 21625, 22764, 23902, 24661, 25040, 25419, 25799, 26178, 27696, 28075, 28455, 29593, 30731, 31110, 31490, 31869, 32249, 32628, 33007, 34146, 35284, 35663, 36043, 36422, 36801, 37181, 37560, 38698};
            for (int i : arr) {
                final Handler handler = new Handler();
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        createNote();
                    }
                }, i);
            }
        }
        else if (song == 3) {
            mediaPlayer = MediaPlayer.create(this, R.raw.furelise);
            mediaPlayer.start();
            int arr[] = {836, 1046, 1255, 1464, 1673, 1883, 2092, 2301, 2510, 2719, 2929, 3138, 3347, 3556, 3766, 3975, 4184, 4393, 4602, 4812, 5021, 5230, 5439, 5649, 5858, 6067, 6276, 6485, 6695, 6904, 7113, 7322, 7532, 7741, 7950, 8159, 8368, 8578, 8787, 8996, 9205, 9415, 9624, 9833, 10042, 10251, 10461, 10879, 11088, 11298, 11507, 11716, 11925, 12134, 12344, 12553, 12762, 12971, 13181, 13390, 13599, 13808, 14017, 14227, 14436, 14645, 14854, 15064, 15273, 15482, 15691, 15900, 16110, 16319, 16528, 16737, 16947, 17156, 17365, 17574, 17783, 17993, 18202, 18411, 18620, 18830, 19039, 19248, 19457, 19666, 19876, 20085, 20294, 20503, 20713, 20922, 21131, 21340, 21549, 21759, 21968, 22177, 22386, 22596, 22805, 23014, 23223, 23432, 23642, 23851, 24060, 24269, 24479, 24688, 24897, 25106, 25315, 25525, 25734, 25943, 26152, 26362, 26571, 26780, 26989, 27198, 27408, 27617, 27826, 28035, 28245, 28454, 28663, 28872, 29081, 29291, 29500, 29709, 29918, 30128, 30337, 30546, 30755, 30964, 31174, 31383, 31592, 31801, 32011, 32220, 32429, 32638, 32847, 33057, 33266, 33475, 33684, 33894, 34103, 34312, 34521, 34730, 34940, 35149, 35358, 35567, 35777, 35986, 36195, 36404, 36613, 36823, 37032, 37241, 37450, 37660, 37869, 38078, 38287, 38496, 38706, 38915, 39124, 39333, 39543, 39752, 39961, 40170, 40379, 40589, 40798, 41007, 41216, 41426, 41635, 41844, 42053, 42262, 42472, 42681, 42890, 43099, 43309, 43518, 43727, 43936, 44145, 44355, 44564, 44773, 44982, 45192, 45401, 45610, 45819, 46028, 46238, 46447, 46656, 46865, 47075, 47284, 47493, 47702, 47911, 48121, 48330, 48539, 48748, 48958, 49167, 49376, 49585, 49794, 50004, 50213, 50422, 50631, 50841, 51050, 51259, 51468, 51677, 51887, 52096, 52305, 52514, 52724, 52933, 53142, 53351, 53560, 53770, 53979, 54188, 54397, 54607, 54816, 55025, 55234, 55443, 55653, 55862, 56071, 56280, 56447, 56490, 56699, 56908, 57117, 57326, 57536, 57640, 57745, 57954, 58163, 58373, 58582, 58791, 58896, 59000, 59209, 59419, 59628, 59837, 60046, 60256, 60465, 60674, 60883, 61030, 61092, 61197, 61302, 61406, 61511, 61720, 61929, 62139, 62348, 62557, 62766, 62975, 63185, 63394, 63603, 63812, 64022, 64231, 64440, 64649, 64666, 64711, 64754, 64796, 64858, 65068, 65172, 65277, 65381, 65486, 65591, 65695, 65800, 65905, 66009, 66114, 66218, 66323, 66428, 66532, 66637, 66741, 66846, 66951, 67055, 67160, 67264, 67369, 67474, 67578, 67683, 67788, 67892, 67997, 68101, 68206, 68311, 68415, 68520, 68624, 68729, 68834, 68938, 69043, 69147, 69252, 69357, 69461, 69566, 69671, 69775, 69880, 69984, 70089, 70194, 70298, 70403, 70507, 70612, 70717, 70821, 70926, 71030, 71135, 71240, 71344, 71449, 71554, 72181, 72390, 72600, 72809, 73437, 73646, 73855, 74064, 74273, 74483, 74692, 74901, 75110, 75320, 75529, 75738, 75947, 76156, 76366, 76575, 76784, 76993, 77203, 77412, 77621, 77830, 78039, 78249, 78458, 78667, 78876, 79086, 79295, 79504, 79713, 79922, 80132, 80341, 80550, 80759, 80969, 81178, 81387, 81596, 81805, 82015, 82224, 82433, 82642, 82852, 83061, 83270, 83479, 83688, 83898, 84107, 84316, 84525, 84735, 84944, 85153, 85362, 85571, 85781, 85990, 86199, 86408, 86618, 86827, 87036, 87245, 87454, 87664, 87873, 88082, 88291, 88501, 88710, 88919, 89128, 89337, 89547, 89756, 89965, 90174, 90384, 90593, 90802, 91011, 91220, 91430, 91639, 91848, 92057, 92267, 92476, 92685, 92894, 93103, 93313, 93522, 93731, 93940, 94150, 94359, 94568, 94777, 94986, 95196, 95405, 95614, 95823, 96033, 96242, 96451, 96660, 96869, 97079, 97288, 97497, 97706, 97916, 98125, 98334, 98543, 98752, 98962, 99171, 99380, 99589, 99799, 100008, 100217, 100426, 100635, 100845, 101054, 101263, 101472, 101682, 101891, 102100, 102309, 102518, 102728, 102937, 103146, 103355, 103565, 103774, 103983, 104192, 104401, 104611, 104820, 105029, 105238, 105448, 105657, 105866, 106075, 106284, 106494, 106703, 106912, 107121, 107331, 107540, 107749, 107958, 108167, 108377, 108586, 108795, 109004, 109214, 109423, 109632, 109841, 110050, 110260, 110469, 110678, 110887, 111097, 111306, 111515, 111724, 111933, 112143, 112352, 112561, 112770, 112980, 113189, 113398, 113607, 113816, 114026, 114235, 114444, 114653, 114863, 115072, 115281, 115490, 115699, 115909, 116118, 116327, 116536, 116746, 116955, 117164, 117373, 117582, 117792, 118001, 118210, 118419, 118629, 118838, 119047, 119256, 119465, 119675, 119884, 120093, 120302, 120512, 120721, 120930, 121139, 121348, 121558, 121767, 123022, 124278, 124415, 124555, 124696, 124833, 124974, 125114, 125252, 125392, 125533, 125670, 125811, 125951, 126089, 126229, 126370, 126507, 126648, 126788, 126926, 127066, 127207, 127344, 127485, 127625, 127762, 127903, 128044, 128181, 128321, 128462, 128599, 128740, 128880, 129018, 129158, 129299, 129436, 129577, 129717, 129855, 129995, 130136, 130273, 130414, 130554, 130763, 130973, 131182, 131391, 131600, 131810, 132019, 132228, 132437, 132646, 132856, 133065, 133274, 133483, 133693, 133902, 134111, 134320, 134529, 134739, 134948, 135157, 135366, 135576, 135785, 135994, 136203, 136412, 136622, 136831, 137040, 137249, 137459, 137668, 137877, 138086, 138295, 138505, 138714, 138923, 139132, 139342, 139551, 139760, 139969, 140178, 140388, 140597, 140806, 141015, 141225, 141434, 141643, 141852, 142061, 142271, 142480, 142689, 142898, 143108, 143317, 143526, 143735, 143944, 144154, 144363, 144572, 144781, 144991, 145200, 145409, 145618, 145827, 146037, 146246, 146455, 146664, 146874, 147083, 147292, 147501, 147710, 147920, 148129, 148338, 148547, 148757, 148966, 149175, 149384, 149593, 149803, 150012, 150221, 150430, 150640, 150849, 151058, 151267, 151476, 151686, 151895, 152104, 152313, 152523, 152732, 152941, 153150, 153359, 153569, 153778, 153987, 154196, 154406, 154615, 154824, 155033, 155242, 155452, 155661, 155870, 156079, 156289, 156498, 156707, 156916};
            for (int i : arr) {
                final Handler handler = new Handler();
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        createNote();
                    }
                }, i);
            }
        }



    }
    public void createNote(){ //Creates a button for the user to tap in tempo
        final Button note = new Button(this);
        DisplayMetrics metrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(metrics); //Gets the window parameters
        RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(metrics.widthPixels/3, metrics.widthPixels/3); //Sets the button size responsively
        params.topMargin = new Random().nextInt(metrics.heightPixels - metrics.widthPixels/3); //Sets the margin to a random range
        params.leftMargin = new Random().nextInt(metrics.widthPixels - metrics.widthPixels/3); //Sets the margin to a random range
        note.setText("♫");
        note.setTextSize(metrics.widthPixels/3 * .2F);
        int colorFrom = 0xaaaaaaaa;
        int colorTo = 0xffFF0000;
        int duration = 2000;
        ObjectAnimator anim = ObjectAnimator.ofObject(note, "backgroundColor", new ArgbEvaluator(), colorFrom, colorTo);
        anim.setDuration(duration).start();


        final RelativeLayout layout = (RelativeLayout) findViewById(R.id.game4);
        layout.addView(note, params); //Adds the button to the layout so it is now visible

        note.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) { //When clicked, increment score and remove the button from the layout, thus it is now invisible
                ViewGroup parentView = (ViewGroup) view.getParent();
                parentView.removeView(view);
                score++;
                TextView scoreText = (TextView) findViewById(R.id.scoreText);
                scoreText.setText("Score: " + score); //Display the new score

            }
        });
        final Handler noteHandlerB = new Handler();
        noteHandlerB.postDelayed(new Runnable() {
            @Override
            public void run() {
                note.setVisibility(View.GONE);
            }
        }, 2000); //The note disappears as time has run out


    }
    public void finishGame(int score){ //Will pass score to a score activity

    }
    @Override
    public void onBackPressed() { //Stops the song when returning to a previous activity
        if (mediaPlayer != null && mediaPlayer.isPlaying()) {
            mediaPlayer.stop();
        }
        super.onBackPressed();
    }

}
