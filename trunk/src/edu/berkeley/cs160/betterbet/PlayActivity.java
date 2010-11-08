package edu.berkeley.cs160.betterbet;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
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
	

	boolean isGroupSelected = false;
	String selectedGroup, winner;
	
	public class groupSelectedListener implements OnItemSelectedListener {

	    public void onItemSelected(AdapterView<?> parent, View view, int pos, long id) {
	    	selectedGroup = parent.getItemAtPosition(pos).toString();
	    	if (selectedGroup != null) {
	        	Toast.makeText(parent.getContext(), "You selected the group " + selectedGroup, Toast.LENGTH_SHORT).show();
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
	
	/*public void goToInstr() {
		setContentView(R.layout.instructions);
		Button backButton = (Button) findViewById(R.id.backToPlay1Button);
		backButton.setOnClickListener(new OnClickListener() {
			public void onClick(View arg0) {
				//new Intent().setClass(getApplicationContext(), PlayActivity.class);
				setContentView(R.layout.play1);
			}
		});
	}*/

	Chronometer gameTimer;
	Chronometer roundTimer;
	Button gameButton;
	Button roundButton;
	Button pauseButton;
	long pauseTime;
	boolean isStarted = false;
	boolean isRunning = false;
	int albert, karthik, karl, melissa, samantha;

	
	public void setTimerMode() {
		setContentView(R.layout.timer);
		gameTimer = (Chronometer) findViewById(R.id.game_timer);
		roundTimer = (Chronometer) findViewById(R.id.round_timer);
		gameButton = (Button) findViewById(R.id.game_button);
		roundButton = (Button) findViewById(R.id.round_button);
		pauseButton = (Button) findViewById(R.id.pause_button);
		roundButton.setEnabled(false);
		pauseButton.setEnabled(false);
		
		final CharSequence[] items = (CharSequence[]) getResources().getTextArray(R.array.collegeMembers);

		AlertDialog.Builder builder = new AlertDialog.Builder(this);
		builder.setTitle("Select Round Winner");
		builder.setItems(items, new DialogInterface.OnClickListener() {
		public void onClick(DialogInterface dialog, int item) {
			Toast.makeText(getApplicationContext(), items[item], Toast.LENGTH_SHORT).show();
			restartRound();
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
	}

	protected void startGame() {
		roundButton.setEnabled(true);
		pauseButton.setEnabled(true);
		pauseButton.setText("Pause");
		gameButton.setText("End Game");
		
		Spinner spinner = (Spinner) findViewById(R.id.winnnerSpinner);
		ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(
				this, R.array.collegeMembers, android.R.layout.simple_spinner_item);
		adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		spinner.setAdapter(adapter);	
		spinner.setOnItemSelectedListener(new winnerSelectedListener());		
		isRunning = true;
		isStarted = true;
	}
	
	public class winnerSelectedListener implements OnItemSelectedListener {

	    public void onItemSelected(AdapterView<?> parent, View view, int pos, long id) {
	    	winner = parent.getItemAtPosition(pos).toString();
        	Toast.makeText(parent.getContext(), "You selected " + winner + "as the winner", Toast.LENGTH_SHORT).show();
	    	if (winner == "Albert") {
	    		albert++;
	    	} else {
	    		if (winner == "Karthik") {
	    			karthik++;
	    		} else {
	    			if (winner == "Karl") {
						karl++;
	    			} else {
	    				if (winner == "Melissa") {
	    					melissa++;
	    				} else {
	    					if (winner == "Samantha") {
	    						samantha++;
	    					}
	    				}
	    			}
	    		}
	    	}
	    }

	    public void onNothingSelected(AdapterView<?> parent) {
	    	// do nothing
	    }
	}
	
	protected void pauseGame() {
		roundTimer.stop();
		gameTimer.stop();
		
		pauseTime = SystemClock.elapsedRealtime();
		pauseButton.setText("Resume");
		isRunning = false;
	}
	
	protected void resumeGame() {
		long difference = SystemClock.elapsedRealtime() - pauseTime;
		roundTimer.setBase(roundTimer.getBase() + difference);
		roundTimer.start();
		gameTimer.setBase(gameTimer.getBase() + difference);
		gameTimer.start();
		
		pauseButton.setText("Pause");
		isRunning = true;		
	}
	
	protected void restartRound() {
		pauseButton.setText("Pause");
		gameTimer.start();
		roundTimer.setBase(SystemClock.elapsedRealtime());
		roundTimer.start();
		
		isRunning = true;
	}
}
