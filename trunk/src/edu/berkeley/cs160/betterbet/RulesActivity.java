package edu.berkeley.cs160.betterbet;
import android.app.Activity;
import android.os.Bundle;
import android.widget.TextView;


public class RulesActivity extends Activity {
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        TextView textview = new TextView(this);
        textview.setText("This is the Rules tab");
        setContentView(textview);
    }
}
