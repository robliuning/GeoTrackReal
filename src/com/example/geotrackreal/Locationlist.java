package com.example.geotrackreal;

import java.util.ArrayList;

import android.location.Location;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.AdapterView.OnItemClickListener;
import android.app.Activity;
import android.content.Intent;

public class Locationlist extends Activity {
	
	private ArrayList<Location> trackerList;
	private LocationlistAdapter locationlistAdapter;
	private Location selectedLocation;
	private ListView listView;

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
        
	}
	
    public OnItemClickListener myClickListener = new OnItemClickListener(){
		@Override
		public void onItemClick(AdapterView<?> parent, View view, int position,
				long id) {
			selectedLocation = trackerList.get(position-1);		
			Intent intent = new Intent(Locationlist.this,Mymap.class);
			intent.putExtra(StaticName.EXTRA_LOCATION, selectedLocation);
			startActivity(intent);
		}
    };
}
