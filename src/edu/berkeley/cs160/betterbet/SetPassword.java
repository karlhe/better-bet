package edu.berkeley.cs160.betterbet;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.content.Intent;

public class SetPassword extends Activity {
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.setpass);
        
        Button submitButton = (Button)findViewById(R.id.submit_button);
        submitButton.setOnClickListener(submitButtonListener);
    }
    
    private OnClickListener submitButtonListener = new OnClickListener () {
    	public void onClick(View v) {
    		Intent intent = new Intent();
    		setResult(RESULT_OK, intent);
    		finish();
    	}
    };
    
}
