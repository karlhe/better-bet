package edu.berkeley.cs160.betterbet;
import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebView;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemSelectedListener;

public class StatsActivity extends Activity {
	
	boolean defaultGroupChanged = false;
	
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.stats);
        
        Spinner spinner = (Spinner) findViewById(R.id.stats_group_spinner);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(
                this, R.array.groups, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        defaultGroupChanged = false;
        spinner.setOnItemSelectedListener(new OnGroupSelectedListener());
        
        loadCharts("college");
    }
    
    void loadCharts(String category) {
    	String[] data = getData(category);
    	int total = 100 - Integer.parseInt(data[2]);
    	
        pie_view = (WebView) findViewById(R.id.pie_graph);
        String p_url = "http://chart.apis.google.com/chart?" +
        "chs=220x150" +
        "&chd=t:" + data[2] + "," + total +
        "&cht=p" + 
        "&chl=Wins" +
        "&chld=Wins";
        pie_view.loadUrl(p_url);
        
        line_view = (WebView) findViewById(R.id.line_graph);
        String l_url = "http://chart.apis.google.com/chart?" +
        	"chs=220x150" +
        	"&chd=t:"+data[0]+"|"+data[1] +
        	"&cht=lc" + 
        	"&chdl=Wins|Total" + 
        	"&chdlp=b" + 
        	"&chco=f79c00,444444" +
        	"&chma=10,10,10,10|5,5";
        line_view.loadUrl(l_url);
        
        numerical_data = (TextView) findViewById(R.id.numerical_data);
        numerical_data.setText("Win rate: "+data[2]+"%");
    }
    
    String[] getData(String category) {
    	String[] result = new String[3];
    	if (category.equalsIgnoreCase("friends")) {
    		result[0] = friends_wins;
    		result[1] = friends_total;
    		result[2] = "65";
    	} else if (category.equalsIgnoreCase("family")) {
    		result[0] = family_wins;
    		result[1] = family_total;
    		result[2] = "20";
    	} else if (category.equalsIgnoreCase("college")) {
    		result[0] = college_wins;
    		result[1] = college_total;
    		result[2] = "70";
    	} else {
    		result[0] = coworkers_wins;
    		result[1] = coworkers_total;
    		result[2] = "84";
    	}
    	return result;
    }
    
    private String college_wins = "40,50,90,80,30,70,50,90";
    private String college_total = "80,70,100,90,60,80,70,100";
    private String friends_wins = "50,30,50,70,20,40,10,70";
    private String friends_total = "60,40,70,90,40,70,40,80";
    private String family_wins = "20,40,20,10,20,30,10,30";
    private String family_total = "60,60,90,90,80,60,60,40";
    private String coworkers_wins = "80,30,60,80,50,40,30,50";
    private String coworkers_total = "90,40,70,90,60,70,40,50";
    
    public class OnGroupSelectedListener implements OnItemSelectedListener {

        public void onItemSelected(AdapterView<?> parent,
            View view, int pos, long id) {
        	String selected = parent.getItemAtPosition(pos).toString();
        	if (defaultGroupChanged) {
        		Toast.makeText(parent.getContext(), "You selected the group " +
        				selected, Toast.LENGTH_SHORT).show();
        	} else {
        		defaultGroupChanged = true;
        	}
        	loadCharts(selected);
        }

        public void onNothingSelected(AdapterView parent) {
        }
    }
    
    private WebView pie_view;
    private WebView line_view;
    private TextView numerical_data;

}
