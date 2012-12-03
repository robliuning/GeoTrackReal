package com.example.geotrackreal;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.provider.Settings;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.Toast;

import com.example.geotrackreal.customfont.CustomTitleView;

public class TrackActivity extends Activity implements LocationListener{
	/**
	 * Location used for test:
	 * Location 1: Loukkukorventie 6 - 62.2206082 and 25.7050364
	 * Location 2: Myllylammentie - 62.22234631646009 and 25.70354461669922
	 */
	//Local variables
	private int interval;
	private int distance;
	private String startTime;
	private String endTime;
	private String stringAddress;

	private CustomTitleView tv_interval;
	private CustomTitleView tv_distance;
	private CustomTitleView tv_start;
	private CustomTitleView tv_provider;
	private CustomTitleView tv_tracker_number;
	private CustomTitleView tv_last_tracker_time;
	private CustomTitleView tv_last_tracker_location;
	
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
	private boolean gpsEnabled;
	private boolean networkEnabled;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_track);
		//Retrieve values passed in
		Intent intent = getIntent();
		interval = intent.getIntExtra(StaticName.EXTRA_INTERVAL, 0);
		distance = intent.getIntExtra(StaticName.EXTRA_DISTANCE, 0);
		
		//Initialize values
	    trackerNumber = 0;
	    recType = true;
	    startTime = getCustomTime();
		
		//combine local variables with layout elements
		tv_interval = (CustomTitleView)findViewById(R.id.tv_interval);
		tv_distance = (CustomTitleView)findViewById(R.id.tv_distance);
		tv_start = (CustomTitleView)findViewById(R.id.tv_start);
		tv_provider = (CustomTitleView)findViewById(R.id.tv_provider);
		tv_tracker_number = (CustomTitleView)findViewById(R.id.tv_tracker_number);
		tv_last_tracker_time = (CustomTitleView)findViewById(R.id.tv_last_tracker_time);    
		tv_last_tracker_location = (CustomTitleView)findViewById(R.id.tv_last_tracker_location);    
		
		tv_interval.setText(Integer.toString(interval));
		tv_distance.setText(Integer.toString(distance));
		tv_start.setText(startTime);

        bt_manual = (Button)findViewById(R.id.bt_manual);
		bt_manual.setOnClickListener(manualAdd);
		bt_finish = (Button)findViewById(R.id.bt_finish);
		bt_finish.setOnClickListener(trackFinish);
	}
	
	private OnClickListener manualAdd = new OnClickListener(){		
			@Override
			public void onClick(View v) {
				//When user clicked manual add button, system get last known location from location manager 
				//and add it to the location list, the boolean recType is used to identify manually added location
				//from system automatically added
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
			//Stop user from clicking finish without any tracker recorded.
			if(trackerList.size() != 0){
				//The finish method call enable this tracking activity one time usable, user
				//can not return back from next activity
				finish();
				endTime = getCustomTime();
				Intent parcelIntent = new Intent(TrackActivity.this,Locationlist.class);
				parcelIntent.putParcelableArrayListExtra(StaticName.EXTRA_TRACKERS, trackerList);
				parcelIntent.putExtra(StaticName.EXTRA_DISTANCE, distance);
				parcelIntent.putExtra(StaticName.EXTRA_INTERVAL, interval);
				parcelIntent.putExtra(StaticName.EXTRA_START,startTime);
				parcelIntent.putExtra(StaticName.EXTRA_END,endTime);	
				startActivity(parcelIntent);	
			}else{
		    	Toast.makeText(TrackActivity.this,TrackActivity.this.getString(R.string.error_empty_tracker), Toast.LENGTH_LONG).show();
			}
		}
   };
	
    @Override
    protected void onResume(){
    	super.onResume();
    	//Check location service status as it may disabled when user return to application.
		locationManager = (LocationManager)getSystemService(Context.LOCATION_SERVICE);	
    	gpsEnabled = locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);
		networkEnabled = locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER);
		
		//Show alert dialog when user have gps and network location services both disabled.
		if(!gpsEnabled && !networkEnabled){
			AlertDialog.Builder adb = new AlertDialog.Builder(TrackActivity.this);      	  
       	  	adb.setTitle("No Avaliable Location Services");
       	  	adb.setMessage("GPS and Network Location Service are disabled!")
       	  		.setCancelable(false)
       	  		.setPositiveButton("Cancel", new DialogInterface.OnClickListener(){
       	  			public void onClick(DialogInterface dialog, int id){
       	  				dialog.cancel();
       	  				finish();
       	  				Intent intent = new Intent(TrackActivity.this,MainActivity.class);
       	  				TrackActivity.this.startActivity(intent);			
       	  			}
       	  		})
       	  		.setNegativeButton("Go to Setting", new DialogInterface.OnClickListener(){
       	  			public void onClick(DialogInterface dialog,int id){
       	  				//Navigate user to the system location setting
       	  				Intent intent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
       	  				TrackActivity.this.startActivity(intent);
       	  			}
       	  		});         	  		
       	  		AlertDialog ad = adb.create();          	  		
       	  		ad.show();    			
		}else{
			//if at least one location service is enabled
			criteria = new Criteria();
		    provider = locationManager.getBestProvider(criteria, false);
		    trackerList = new ArrayList<Location>();
	    	int tmp = interval*1000;
	    	locationManager.requestLocationUpdates(provider,tmp,distance, this);	
			tv_provider.setText(provider);
		}
    }

    @Override
    protected void onPause(){
    	super.onPause();
    	//It can be remove or not, in our case, we decide stop using location service once user leave this application.
    	locationManager.removeUpdates(this);
    }
    
	@Override
	public void onLocationChanged(Location location) {
		//Retrieve needed values
		trackerNumber++;
		if(trackerNumber <= 100){	
			String stringTime = getCustomTime();
			amanager = new AddressManager();
			stringAddress = amanager.getSingleAddress(this, location);
		
			//updates display
			tv_last_tracker_time.setText(stringTime);
			tv_last_tracker_location.setText(stringAddress);
			tv_tracker_number.setText(String.valueOf(trackerNumber));

			//add up to location list
			Bundle bundle = new Bundle();
			bundle.putString(StaticName.EXTRA_RECDATE,stringTime);
			bundle.putBoolean(StaticName.EXTRA_RECTYPE,recType);
			bundle.putString(StaticName.EXTRA_ADDRESS,stringAddress);
			bundle.putInt(StaticName.EXTRA_POSITION, trackerNumber);
			location.setExtras(bundle);
			trackerList.add(location);	
		
			//Reset type values
			recType = true;
		}else{
	    	Toast.makeText(TrackActivity.this,TrackActivity.this.getString(R.string.error_number_exceed), Toast.LENGTH_LONG).show();			
		}
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
