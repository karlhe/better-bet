package edu.berkeley.cs160.betterbet;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class BetterBet extends Activity {
    /** Called when the activity is first created. */
	public String name;
	public String password;
	@Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);
        
        Button submitButton = (Button)findViewById(R.id.submit_button);
        submitButton.setOnClickListener(submitButtonListener);
        
        Button passwordButton = (Button)findViewById(R.id.acct_button);
        passwordButton.setOnClickListener(passwordButtonListener);
    }
    
    private OnClickListener submitButtonListener = new OnClickListener () {
    	public void onClick(View v) {
    		Intent i = new Intent().setClass(BetterBet.this, MenuActivity.class);
    		startActivity(i);
    	}
    };
    
    private OnClickListener passwordButtonListener = new OnClickListener () {
    	public void onClick(View v) {
    		Intent myIntent = new Intent(v.getContext(), SetPassword.class);
    		startActivityForResult(myIntent,0);
    	}
    };
    
}