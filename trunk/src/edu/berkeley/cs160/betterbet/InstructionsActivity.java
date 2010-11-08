package edu.berkeley.cs160.betterbet;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import edu.berkeley.cs160.betterbet.R;

public class InstructionsActivity extends Activity {
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.instructions);
        
        Button backButton = (Button) findViewById(R.id.backToPlay1Button);
		backButton.setOnClickListener(new OnClickListener() {
			public void onClick(View arg0) {
				Intent i = new Intent().setClass(InstructionsActivity.this, PlayActivity.class);
				startActivity(i);
				//setContentView(R.layout.play1);
			}
		});
    }
}