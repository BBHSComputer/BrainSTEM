package  bbhs.appbowl2017;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class Game2Settings extends AppCompatActivity{
    
	private Button home;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_game2settings);//activity_game2settings.xml doesn't exist yet
		
		//home = (Button) getViewById(R.id.game2_settingHome);//TODO add button game2_settingHome
		
		buttonsClicked();
    }

	private void buttonsClicked(){
		home.setOnClickListener(new View.OnClickListener(){
			@Override
			public void onClick(View v){
			 startActivity(new Intent(getApplicationContext(), MainActivity.class));
			}
		});
	}
    
}