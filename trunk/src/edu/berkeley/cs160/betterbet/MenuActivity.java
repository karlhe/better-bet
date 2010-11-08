package edu.berkeley.cs160.betterbet;

import android.app.Activity;
import android.app.TabActivity;
import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.widget.TabHost;

public class MenuActivity extends TabActivity{

    /** Called when the activity is first created. */
    public void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);

	    Resources res = getResources();  // Resource object to get Drawables
	    TabHost tabHost = getTabHost();  // The activity TabHost
	    TabHost.TabSpec spec;            // Reusable TabSpec for each tab
	    Intent intent;                   // Reusable Intent for each tab

	    // Create an Intent to launch an Activity for the tab (to be reused)
	    intent = new Intent().setClass(this, PlayActivity.class);

	    // Initialize a TabSpec for each tab and add it to the TabHost
	    spec = tabHost.newTabSpec("play").setIndicator("Play",
	                      res.getDrawable(R.drawable.ic_tab_play))
	                  .setContent(intent);
	    tabHost.addTab(spec);

	    // Do the same for the other tabs
	    intent = new Intent().setClass(this, StatsActivity.class);
	    spec = tabHost.newTabSpec("stats").setIndicator("Stats",
	                      res.getDrawable(R.drawable.ic_tab_stats))
	                  .setContent(intent);
	    tabHost.addTab(spec);

	    intent = new Intent().setClass(this, OpponentsActivity.class);
	    spec = tabHost.newTabSpec("opponents").setIndicator("Opponents",
	                      res.getDrawable(R.drawable.ic_tab_opponents))
	                  .setContent(intent);
	    tabHost.addTab(spec);
	    
	    intent = new Intent().setClass(this, RulesActivity.class);
	    spec = tabHost.newTabSpec("rules").setIndicator("Rules",
	                      res.getDrawable(R.drawable.ic_tab_rules))
	                  .setContent(intent);
	    tabHost.addTab(spec);
	    
	    //tabHost.setCurrentTab(0);
	}
}