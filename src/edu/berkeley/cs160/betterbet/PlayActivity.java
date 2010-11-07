package edu.berkeley.cs160.betterbet;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.SystemClock;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Chronometer;
import android.widget.Chronometer.OnChronometerTickListener;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;



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
		spinner.setOnItemSelectedListener(new MyOnItemSelectedListener());

		Button instr = (Button) findViewById(R.id.instrButton);
		Button play = (Button) findViewById(R.id.playButton);

		instr.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				goToInstr(); 
			}
		});
		play.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				if (isGroupSelected) {
					startGame();
				} else {
					Toast.makeText(getApplicationContext(), "Please indicate the group you are playing with.", Toast.LENGTH_SHORT).show();
				}
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
	boolean isGroupSelected = false;
	String selectedGroup;
	
	public class MyOnItemSelectedListener implements OnItemSelectedListener {

	    public void onItemSelected(AdapterView<?> parent, View view, int pos, long id) {
	    	isGroupSelected = false;
	    	selectedGroup = parent.getItemAtPosition(pos).toString();
	    	if (selectedGroup != null) {
	    		isGroupSelected = true;
	    	}
	    	if (selectedGroup == "Create New Group") {
	    		createNewGroup();
	    	}
	    }

	    public void onNothingSelected(AdapterView<?> parent) {
	    	isGroupSelected = false;
	    }
	}
	
	public void createNewGroup() {
		// do stuff
	}
	
	public void goToInstr() {
		setContentView(R.layout.instructions);
		Button backButton = (Button) findViewById(R.id.backToPlay1Button);
		backButton.setOnClickListener(new OnClickListener() {
			public void onClick(View arg0) {
				//new Intent().setClass(getApplicationContext(), PlayActivity.class);
				setContentView(R.layout.play1);
			}
		});
	}
	
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
		
		Spinner spinner = (Spinner) findViewById(R.id.winnnerSpinner);
		ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(
				this, R.array.collegeMembers, android.R.layout.simple_spinner_item);
		adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		spinner.setAdapter(adapter);	
		//spinner.setOnItemSelectedListener(new MyOnItemSelectedListener());
		
	}


}
