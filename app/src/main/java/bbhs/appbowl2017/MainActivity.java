package bbhs.appbowl2017;

import android.content.Intent;
import android.graphics.Rect;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.RadioButton;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

	private boolean expanded[];
	private ImageButton[] dropdownButtons;
	private TextView[] descriptions;
	private View[] settings;
	private boolean tellRules;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		expanded = new boolean[4];
		dropdownButtons = new ImageButton[] {(ImageButton) findViewById(R.id.stackDropdown), (ImageButton) findViewById(R.id.tileDropdown), (ImageButton) findViewById(R.id.sumDropdown), (ImageButton) findViewById(R.id.musicDropdown)};
		descriptions = new TextView[] {(TextView) findViewById(R.id.stackDesc), (TextView) findViewById(R.id.tileDesc), (TextView) findViewById(R.id.sumDesc), (TextView) findViewById(R.id.musicDesc)};
		settings = new View[] {findViewById(R.id.stackSettings), findViewById(R.id.tileSettings), findViewById(R.id.sumSettings), findViewById(R.id.musicSettings)};

		Button stackPlay = (Button) findViewById(R.id.stackPlay);
		Button tilePlay = (Button) findViewById(R.id.tilePlay);
		Button sumPlay = (Button) findViewById(R.id.sumPlay);
		Button musicPlay = (Button) findViewById(R.id.musicPlay);

		Button stackAdd = (Button) findViewById(R.id.stackAdd);
		Button stackSubtract = (Button) findViewById(R.id.stackSubtract);
		final TextView stackValue = (TextView) findViewById(R.id.stackValue);
		final RadioButton stackGuess = (RadioButton) findViewById(R.id.stackGuess);
		final RadioButton stackTell = (RadioButton) findViewById(R.id.stackTell);

		Button tileAdd = (Button) findViewById(R.id.tileAdd);
		Button tileSubtract = (Button) findViewById(R.id.tileSubtract);
		final TextView tileValue = (TextView) findViewById(R.id.tileValue);

		tellRules = true;

		for (int i = 0; i < dropdownButtons.length; i++) {
			final int n = i;

			dropdownButtons[i].setOnClickListener(new View.OnClickListener() {
				@Override
				public void onClick(View v) {
					if (expanded[n]) {
						dropdownButtons[n].setRotation(0);
						descriptions[n].setMinLines(2);
						descriptions[n].setMaxLines(2);
						settings[n].setVisibility(View.GONE);
						expanded[n] = false;
					} else {
						dropdownButtons[n].setRotation(-180);
						descriptions[n].setMinLines(3);
						descriptions[n].setMaxLines(Integer.MAX_VALUE);
						settings[n].setVisibility(View.VISIBLE);
						expanded[n] = true;
					}
				}
			});
			stackPlay.setOnClickListener(new View.OnClickListener() {
				@Override
				public void onClick(View v) {
					Intent stackIntent = new Intent(getApplicationContext(), StackActivity.class);
					stackIntent.putExtra("tellRules", tellRules);
					System.out.println("BBDEBUG: " + tellRules);
					startActivity(stackIntent);
				}
			});
			tilePlay.setOnClickListener(new View.OnClickListener() {
				@Override
				public void onClick(View v) {
					//Start tile
				}
			});
			sumPlay.setOnClickListener(new View.OnClickListener() {
				@Override
				public void onClick(View v) {
					startActivity( new Intent(getApplicationContext(), SummationGame.class));

				}
			});
			musicPlay.setOnClickListener(new View.OnClickListener() {
				@Override
				public void onClick(View v) {
					startActivity( new Intent(getApplicationContext(), MusicActivity.class));

				}
			});
			stackGuess.setOnClickListener(new View.OnClickListener() {
				@Override
				public void onClick(View v) {
					if (stackTell.isChecked())
						stackTell.setChecked(false);
					tellRules = false;
				}
			});
			stackTell.setOnClickListener(new View.OnClickListener() {
				@Override
				public void onClick(View v) {
					if (stackGuess.isChecked())
						stackGuess.setChecked(false);
					tellRules = true;
				}
			});
			stackAdd.setOnClickListener(new View.OnClickListener() {
				@Override
				public void onClick(View v) {
					stackValue.setText(Integer.toString(Integer.parseInt(stackValue.getText().toString()) + 1));
				}
			});
			stackSubtract.setOnClickListener(new View.OnClickListener() {
				@Override
				public void onClick(View v) {
					stackValue.setText(Integer.toString(Integer.parseInt(stackValue.getText().toString()) - 1));
				}
			});
			tileAdd.setOnClickListener(new View.OnClickListener() {
				@Override
				public void onClick(View v) {
					if (Integer.parseInt(tileValue.getText().toString()) < 8)
						tileValue.setText(Integer.toString(Integer.parseInt(tileValue.getText().toString()) + 1));
				}
			});
			tileSubtract.setOnClickListener(new View.OnClickListener() {
				@Override
				public void onClick(View v) {
					if (Integer.parseInt(tileValue.getText().toString()) > 1)
						tileValue.setText(Integer.toString(Integer.parseInt(tileValue.getText().toString()) - 1));
				}
			});
		}
	}
}
