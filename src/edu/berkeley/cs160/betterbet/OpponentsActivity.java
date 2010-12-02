package edu.berkeley.cs160.betterbet;
import android.app.Activity;
import android.app.ListActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;


public class OpponentsActivity extends ListActivity {
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        ListView lv = getListView();
        lv.setTextFilterEnabled(true);
         
        String[] countries = getResources().getStringArray(R.array.opponentsArray);
        //setListAdapter(new ArrayAdapter<String>(this, R.layout.opponents, countries));
        
        lv.setOnItemClickListener(new OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View view,
                int position, long id) {
              // When clicked, show a toast with the TextView text
              Toast.makeText(getApplicationContext(), ((TextView) view).getText(),
                  Toast.LENGTH_SHORT).show();
            }
          });
        }
}

 
