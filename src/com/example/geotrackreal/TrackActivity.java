package com.example.geotrackreal;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

public class TrackActivity extends Activity implements LocationListener{
	/**
	 * Location used for test:
	 * Location 1: Loukkukorventie 6 - 62.2206082 and 25.7050364
	 * Location 2: Myllylammentie - 62.22234631646009 and 25.70354461669922
	 */
	//Local variables
	private int interval;
	private int distance;
	private TextView tv_interval;
	private TextView tv_distance;
	private TextView tv_time_start;
	private TextView tv_time_now;
	private TextView tv_tracker_number;
	private TextView tv_last_tracker;
	private Button bt_manual;
	private Button bt_finish;
	private LocationManager locationManager;
	private Location location;
	private String provider;
	private ArrayList<Location> trackerList;
	private Criteria criteria;
	private int trackerNumber;
	private boolean recType;
	private AddressManager amanager;
	private String stringAddress;
	private String startTime;
	private String endTime;
	
	//for mock location
	//private MockGpsProvider mMock = null;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_track);
		
		//Retrieve values passed in
		Intent intent = getIntent();
		interval = intent.getIntExtra(StaticName.EXTRA_INTERVAL, 0);
		distance = intent.getIntExtra(StaticName.EXTRA_DISTANCE, 0);
		
		//Initialize values
		locationManager = (LocationManager)getSystemService(Context.LOCATION_SERVICE);
	    //for test on real device, enable mock locations
		
		
		criteria = new Criteria();
	    provider = locationManager.getBestProvider(criteria, false);
	    trackerList = new ArrayList<Location>();
	    trackerNumber = 0;
	    recType = true;
	    startTime = getCustomTime();
		
		//link layout elements
		tv_interval = (TextView)findViewById(R.id.tv_interval);
		tv_interval.setText(getString(R.string.track_interval)+interval);
		tv_distance = (TextView)findViewById(R.id.tv_distance);
		tv_distance.setText(getString(R.string.track_distance)+distance);
		tv_time_start = (TextView)findViewById(R.id.tv_time_start);
		tv_time_start.setText(getString(R.string.track_start)+startTime);
		tv_time_now = (TextView)findViewById(R.id.tv_time_now);
		tv_time_now.setText(getString(R.string.track_now)+startTime);
		tv_tracker_number = (TextView)findViewById(R.id.tv_tracker_number);
		tv_last_tracker = (TextView)findViewById(R.id.tv_last_tracker);    
        bt_manual = (Button)findViewById(R.id.bt_manual);
		bt_manual.setOnClickListener(manualAdd);
		bt_finish = (Button)findViewById(R.id.bt_finish);
		bt_finish.setOnClickListener(trackFinish);
	}
	private OnClickListener manualAdd = new OnClickListener(){		
			@Override
			public void onClick(View v) {
				location = locationManager.getLastKnownLocation(provider);
				if(location != null){
					recType = false;
					onLocationChanged(location);
					}
			}
   };
   
   private OnClickListener trackFinish = new OnClickListener(){		
		@Override
		public void onClick(View v) {
			if(trackerList.size() != 0){
				finish();
				endTime = getCustomTime();
				Intent parcelIntent = new Intent(TrackActivity.this,Locationlist.class);
				parcelIntent.putParcelableArrayListExtra(StaticName.EXTRA_TRACKERS, trackerList);
				parcelIntent.putExtra(StaticName.EXTRA_DISTANCE, distance);
				parcelIntent.putExtra(StaticName.EXTRA_INTERVAL, interval);
				parcelIntent.putExtra(StaticName.EXTRA_START,startTime);
				parcelIntent.putExtra(StaticName.EXTRA_END,endTime);	
				startActivity(parcelIntent);
				
			}
		}
   };
	
    @Override
    protected void onResume(){
    	super.onResume();
    	int tmp = interval*1000;
    	locationManager.requestLocationUpdates(provider,tmp,distance, this);
    	}

    @Override
    protected void onPause(){
    	super.onPause();
    	locationManager.removeUpdates(this);
    }
    
	@Override
	public void onLocationChanged(Location location) {
		//Get relevant values
		trackerNumber++;
		String stringTime = getCustomTime();
		amanager = new AddressManager();
		stringAddress = amanager.getSingleAddress(this, location);
		
		//Set up displays
		tv_last_tracker.setText(stringAddress + "Recorded at: "+ stringTime);
		tv_tracker_number.setText(String.valueOf(trackerNumber));

		//Set up list to be transfered
		Bundle bundle = new Bundle();
		bundle.putString(StaticName.EXTRA_RECDATE,stringTime);
		bundle.putBoolean(StaticName.EXTRA_RECTYPE,recType);
		bundle.putString(StaticName.EXTRA_ADDRESS,stringAddress);
		bundle.putInt(StaticName.EXTRA_POSITION, trackerNumber);
		location.setExtras(bundle);
		trackerList.add(location);	
		
		//Reset values
		recType = true;
	}
	
	//Reusable method for getting current time
	public String getCustomTime(){
		Date date = new Date();
		SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss");
		return sdf.format(date);
	}
	
	@Override
	public void onProviderDisabled(String provider) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onProviderEnabled(String provider) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onStatusChanged(String provider, int status, Bundle extras) {
		// TODO Auto-generated method stub
		
	}
}
