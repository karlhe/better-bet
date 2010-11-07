package edu.berkeley.cs160.betterbet;
import android.app.Activity;
import android.os.Bundle;
import android.os.SystemClock;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Chronometer;
import android.widget.Chronometer.OnChronometerTickListener;
import android.widget.Spinner;
import android.widget.TextView;

public class PlayActivity extends Activity {
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		TextView textview = new TextView(this);
		textview.setText("This is the Play tab");
		setContentView(R.layout.play1);
		Spinner spinner = (Spinner) findViewById(R.id.GroupSpinner);
		ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(
				this, R.array.groups, android.R.layout.simple_spinner_item);
		adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		spinner.setAdapter(adapter);

		Button instr = (Button) findViewById(R.id.instrButton);
		Button play = (Button) findViewById(R.id.playButton);

		instr.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				//setContentView(R.layout.instructions);
			}
		});
		play.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				startGame();
			}
		});
	}

	Chronometer gameTimer;
	Chronometer roundTimer;
	Button gameButton;
	Button roundButton;
	Button pauseButton;
	long pauseTime;
	boolean running = false;
	
	public void startGame() {
		setContentView(R.layout.timer);
		gameTimer = (Chronometer) findViewById(R.id.game_timer);
		roundTimer = (Chronometer) findViewById(R.id.round_timer);
		gameButton = (Button) findViewById(R.id.game_start_button);
		roundButton = (Button) findViewById(R.id.round_start_button);
		pauseButton = (Button) findViewById(R.id.pause_button);
		gameTimer.setOnChronometerTickListener(new OnChronometerTickListener(){
            public void onChronometerTick(Chronometer currentTimer) {
            }
        });
		
		gameButton.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				gameTimer.setBase(SystemClock.elapsedRealtime());
				gameTimer.start();
				roundTimer.setBase(SystemClock.elapsedRealtime());
				roundTimer.start();
				running = true;
				pauseButton.setText("Pause");
			}
		});
		roundButton.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				if(running) {
					roundTimer.setBase(SystemClock.elapsedRealtime());
					roundTimer.start();
				}
			}
		});
		pauseButton.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				if(running) {
					pauseTime = SystemClock.elapsedRealtime();
					roundTimer.stop();
					gameTimer.stop();
					pauseButton.setText("Resume");
					running = false;
				} else {
					long difference = SystemClock.elapsedRealtime() - pauseTime;
					roundTimer.setBase(roundTimer.getBase() + difference);
					roundTimer.start();
					gameTimer.setBase(gameTimer.getBase() + difference);
					gameTimer.start();
					pauseButton.setText("Pause");
					running = true;
				}
				
			}
		});
	}


}
