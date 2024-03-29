package edu.berkeley.cs160.betterbet;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.Button;
import android.widget.Toast;
import android.content.Intent;

public class Login extends Activity {
	// I'll put this in another class later. -ML
    /** Called when the activity is first created. */
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
    		setContentView(R.layout.main);
    	}
    };
    
    private OnClickListener passwordButtonListener = new OnClickListener () {
    	public void onClick(View v) {
    		Intent myIntent = new Intent(v.getContext(), SetPassword.class);
    		startActivityForResult(myIntent,0);
    	}
    };
    
    public class MyOnItemSelectedListener implements OnItemSelectedListener {

        public void onItemSelected(AdapterView<?> parent,
            View view, int pos, long id) {
          Toast.makeText(parent.getContext(), "The group is " +
              parent.getItemAtPosition(pos).toString(), Toast.LENGTH_LONG).show();
        }
		public void onNothingSelected(AdapterView<?> arg0) {
			// TODO Auto-generated method stub			
		}
    }
    
    
}