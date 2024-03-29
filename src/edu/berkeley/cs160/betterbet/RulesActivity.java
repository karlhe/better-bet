package edu.berkeley.cs160.betterbet;
import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;


public class RulesActivity extends ListActivity {
	@Override
	public void onCreate(Bundle savedInstanceState) {
	  super.onCreate(savedInstanceState);

	  setListAdapter(new ArrayAdapter<String>(this, R.layout.rules, GAMES));

	  ListView lv = getListView();
	  lv.setTextFilterEnabled(true);
	  
	  lv.setOnItemClickListener(new OnItemClickListener() {
	    public void onItemClick(AdapterView<?> parent, View view,
	        int position, long id) {
	    	setRules(position, view);
	    }
	  });
	  
	}
	
	static final String[] GAMES = new String[] {
	    "Texas Hold'em", 
	    "Omaha Poker", 
	    "7 Card Stud", 
	    "5 Card Draw", 
	    "Caribbean Stud Poker", 
	    "Countdown", 
	    "Follow Queen" 
	  };
	
	public void setRules(int position, View v) {
    	if (position == 0) {
    		Intent i = new Intent().setClass(v.getContext(), TexasActivity.class);
    		startActivity(i);
    	} else if (position == 1) {
    		Intent i = new Intent().setClass(v.getContext(), OmahaActivity.class);
    		startActivity(i);
    	} else if (position == 2) {
    		Intent i = new Intent().setClass(v.getContext(), SevenCardActivity.class);
    		startActivity(i);
    	} else if (position == 3) {
    		Intent i = new Intent().setClass(v.getContext(), FiveCardActivity.class);
    		startActivity(i);
    	} else if (position == 4) {
    		Intent i = new Intent().setClass(v.getContext(), CaribbeanActivity.class);
    		startActivity(i);
    	} else if (position == 5) {
    		Intent i = new Intent().setClass(v.getContext(), CountdownActivity.class);
    		startActivity(i);
    	} else if (position == 6) {
    		Intent i = new Intent().setClass(v.getContext(), FollowQueenActivity.class);
    		startActivity(i);
    	} 
    }
    
}
