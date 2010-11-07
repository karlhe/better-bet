package edu.berkeley.cs160.betterbet;
import android.app.Activity;
import android.os.Bundle;
import android.widget.TextView;


public class StatsActivity extends Activity {
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        TextView textview = new TextView(this);
        textview.setText("This is the Stats tab");
        setContentView(textview);
    }
}
