package edu.berkeley.cs160.betterbet;
import java.util.HashMap;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
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
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

public class PlayActivity extends Activity {
	
	boolean isGroupSelected = false;
	String selectedGroup, winner;
	boolean defaultGroupChanged = false;
	
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setGroupSelectMode();
	}
	
	public void setGroupSelectMode() {
		TextView textview = new TextView(this);
		textview.setText("This is the Play tab");
		setContentView(R.layout.play1);
		
		Spinner spinner = (Spinner) findViewById(R.id.GroupSpinner);
		ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(
				this, R.array.groups, android.R.layout.simple_spinner_item);
		adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		spinner.setAdapter(adapter);
		defaultGroupChanged = false;
		spinner.setOnItemSelectedListener(new groupSelectedListener());

		Button instr = (Button) findViewById(R.id.instrButton);
		Button play = (Button) findViewById(R.id.playButton);

		instr.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				Intent i = new Intent().setClass(PlayActivity.this, InstructionsActivity.class);
				startActivity(i);
			}
		});
		play.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				if (isGroupSelected) {
					setTimerMode();
				} else {
					Toast.makeText(getApplicationContext(), "Please indicate the group you are playing with.", Toast.LENGTH_SHORT).show();
				}
			}
		});
	}
	
	public class groupSelectedListener implements OnItemSelectedListener {

	    public void onItemSelected(AdapterView<?> parent, View view, int pos, long id) {
	    	isGroupSelected = false;
	    	selectedGroup = parent.getItemAtPosition(pos).toString();
	    	if (selectedGroup != null) {
	    		if (defaultGroupChanged) {
	    			Toast.makeText(parent.getContext(), "You selected the group " + selectedGroup, Toast.LENGTH_SHORT).show();
	    		} else {
	    			defaultGroupChanged = true;
	    		}
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
	
//	public void goToInstr() {
//		setContentView(R.layout.instructions);
//		Button backButton = (Button) findViewById(R.id.backToPlay1Button);
//		backButton.setOnClickListener(new OnClickListener() {
//			public void onClick(View arg0) {
//				//new Intent().setClass(getApplicationContext(), PlayActivity.class);
//				setContentView(R.layout.play1);
//			}
//		});
//	}

	Chronometer gameTimer;
	Button gameButton;
	Button roundButton;
	Button pauseButton;
	Button instructionsButton;
	TextView runningStats;
	long pauseTime;
	boolean isStarted = false;
	boolean isRunning = false;
	int albert, karthik, karl, melissa, samantha;
	CharSequence[] playerList;
	HashMap<CharSequence, Integer> scores;

	public void setTimerMode() {
		setContentView(R.layout.timer);
		gameTimer = (Chronometer) findViewById(R.id.game_timer);
		gameButton = (Button) findViewById(R.id.game_button);
		roundButton = (Button) findViewById(R.id.round_button);
		pauseButton = (Button) findViewById(R.id.pause_button);
		instructionsButton = (Button) findViewById(R.id.help_button);
		runningStats = (TextView) findViewById(R.id.running_stats);
		roundButton.setEnabled(false);
		pauseButton.setEnabled(false);
		
		playerList = (CharSequence[]) getResources().getTextArray(R.array.collegeMembers);
		scores = new HashMap<CharSequence, Integer>();

		AlertDialog.Builder builder = new AlertDialog.Builder(this);
		builder.setTitle("Select Round Winner");
		builder.setItems(playerList, new DialogInterface.OnClickListener() {
		public void onClick(DialogInterface dialog, int player) {
			Toast.makeText(getApplicationContext(), "You have selected "+playerList[player]+" as the winner", Toast.LENGTH_SHORT).show();
			int current = 0;
			if (scores.containsKey(playerList[player])) current = scores.get(playerList[player]);
			scores.put(playerList[player], current+1);
			restartRound();
			runningStats.setText(formatScores(false));
		}
		});
		final AlertDialog selectWinnerAlert = builder.create();
		
		gameButton.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				// End the game
				if (isStarted) {
					endGame();
					
				// Start the game
				} else {
					startGame();
				}
			}
		});
		roundButton.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				selectWinnerAlert.show();
			}
		});
		
		pauseButton.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				// Pause Game
				if (isRunning) {
					pauseGame();
					
				// Resume Game
				} else {
					resumeGame();
				}
			}
		});
		
		instructionsButton.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
	    		Intent myIntent = new Intent(v.getContext(), InstructionsActivity.class);
	    		startActivityForResult(myIntent,0);
			}
		});
		
	}

	protected String formatScores(boolean full) {
		Object[] keys = scores.keySet().toArray();
		Object[] values = scores.values().toArray();
		Integer totalScore = 0;
		String rtn = "";
		if (keys.length > 0) {
			//rtn += "Player Scores:\n";
			for (int i=0; i<keys.length; i++) {
				rtn += keys[i].toString() + " : " + values[i].toString() + "\n";
				totalScore += Integer.parseInt(values[i].toString());
			}
			rtn = "Total Wins: "+totalScore.toString()+"\n" + rtn;
		} else {
			rtn += "No scores were recorded.\n";
		}
		
		if (full) rtn += "\nTotal Game Time: "+gameTimer.getText();
		return rtn;
	}

	protected void startGame() {
		roundButton.setEnabled(true);
		pauseButton.setEnabled(true);
		pauseButton.setText("Pause");
		gameButton.setText("End Game");
		
		gameTimer.setBase(SystemClock.elapsedRealtime());
		gameTimer.start();
		
		isRunning = true;
		isStarted = true;
	}
	
	protected void endGame() {
		roundButton.setEnabled(false);
		pauseButton.setEnabled(false);
		gameButton.setText("Start Game");
		
		gameTimer.stop();
		
		isRunning = false;
		isStarted = false;
		
		Toast.makeText(getApplicationContext(), formatScores(true), Toast.LENGTH_LONG).show();
		setGroupSelectMode();
	}
	
	protected void pauseGame() {
		gameTimer.stop();
		
		pauseTime = SystemClock.elapsedRealtime();
		pauseButton.setText("Resume");
		isRunning = false;
	}
	
	protected void resumeGame() {
		long difference = SystemClock.elapsedRealtime() - pauseTime;
		gameTimer.setBase(gameTimer.getBase() + difference);
		gameTimer.start();
		
		pauseButton.setText("Pause");
		isRunning = true;		
	}
	
	protected void restartRound() {
		pauseButton.setText("Pause");
		gameTimer.start();
		
		isRunning = true;
	}
}
