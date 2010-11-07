package edu.berkeley.cs160.betterbet;
import android.app.Activity;
import android.app.Application;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
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
	
	public void startGame() {
		setContentView(R.layout.timer);
	}


}
