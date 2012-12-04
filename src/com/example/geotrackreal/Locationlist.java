package com.example.geotrackreal;

import java.util.ArrayList;

import android.app.Activity;
import android.content.Intent;
import android.location.Location;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.TextView;

public class Locationlist extends Activity {
	
	private ArrayList<Location> trackerList;
	private LocationlistAdapter locationlistAdapter;
	private Location selectedLocation;
	private ListView listView;
	
	private TextView tv_interval;
	private TextView tv_distance;
	private TextView tv_start;
	private TextView tv_end;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_locationlist);		
		//Retrieve passed list of locations
        Intent intent = getIntent();
        trackerList = new ArrayList<Location>();
        trackerList = intent.getParcelableArrayListExtra(StaticName.EXTRA_TRACKERS);
        
        //Set up adapter for the list view
        locationlistAdapter = new LocationlistAdapter(this,R.layout.locationlist_row,trackerList);        
        listView = (ListView)findViewById(R.id.tracker_list);
        listView.setClickable(true);
        listView.setOnItemClickListener(myClickListener);       
        View header = (View)getLayoutInflater().inflate(R.layout.locationlist_header_row,null);
        listView.addHeaderView(header);
        listView.setAdapter(locationlistAdapter);

        //Initialize layout elements
        tv_interval = (TextView)findViewById(R.id.tv_interval);
        tv_distance = (TextView)findViewById(R.id.tv_distance);
        tv_start = (TextView)findViewById(R.id.tv_start);
        tv_end = (TextView)findViewById(R.id.tv_end);
        
        //Assign value to layout elements
        tv_interval.setText(intent.getStringExtra(StaticName.EXTRA_INTERVAL));
        tv_distance.setText(intent.getStringExtra(StaticName.EXTRA_DISTANCE));
        tv_start.setText(intent.getStringExtra(StaticName.EXTRA_START));
        tv_end.setText(intent.getStringExtra(StaticName.EXTRA_END));
	}
	
    public OnItemClickListener myClickListener = new OnItemClickListener(){
		@Override
		public void onItemClick(AdapterView<?> parent, View view, int position,
				long id) {
			//send user selected location to mapview
			selectedLocation = trackerList.get(position-1);		
			Intent intent = new Intent(Locationlist.this,Mymap.class);
			intent.putExtra(StaticName.EXTRA_LOCATION, selectedLocation);
			startActivity(intent);
		}
    };
}
