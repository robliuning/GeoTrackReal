package com.example.geotrackreal;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.location.LocationManager;
import android.os.Bundle;
import android.provider.Settings;

public class SplashActivity extends Activity {

	final Context context = this;
	private LocationManager locationManager;
	private boolean gpsEnabled;
	private boolean networkEnabled;
	
	//A separate Thread for emulate loading effect when Application is start up.	
	Thread splashThread = new Thread() {
		@Override					
	    public void run() {
			try {
				//Put activity to sleep for 3 secs
				int waited = 0;
				while (waited < 3000) {
					sleep(100);
	                 waited += 100;
	              }
	           }catch (InterruptedException e) {
	           
	           } finally {
	        	   //User will not able to back to the splash screen
	        	   finish();
	        	   //Transmit to main activity
	        	   Intent i = new Intent();
	        	   i.setClassName("com.example.geotrackreal",
	                             "com.example.geotrackreal.MainActivity"); 
	        	   startActivity(i);
	           }
	        }
	     };

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_splash);		
		locationManager = (LocationManager)getSystemService(Context.LOCATION_SERVICE);		
	}
	
	@Override
	protected void onResume(){
		super.onResume();
		//Check if location services are enabled when application is first started or application is back from pause. (User go to setting screen)
		gpsEnabled = locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);
		networkEnabled = locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER);
		
		//Show alert dialog when user have gps and network location services both disabled.
		if(!gpsEnabled && !networkEnabled){
			AlertDialog.Builder adb = new AlertDialog.Builder(context);      	  
       	  	adb.setTitle("No Avaliable Location Services");
       	  	adb.setMessage("GPS and Network Location Service are disabled!")
       	  		.setCancelable(false)
       	  		.setPositiveButton("Continue", new DialogInterface.OnClickListener(){
       	  			public void onClick(DialogInterface dialog, int id){
       	  				//If user insist to run the application without any available location services
       	  				dialog.cancel();
       	  				splashThread.start();
       	  			}
       	  		})
       	  		.setNegativeButton("Go to Setting", new DialogInterface.OnClickListener(){
       	  			public void onClick(DialogInterface dialog,int id){
       	  				//Navigate user to the system location setting
       	  				Intent intent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
       	  				context.startActivity(intent);
       	  			}
       	  		});         	  		
       	  		AlertDialog ad = adb.create();          	  		
       	  		ad.show();    			
		}else{
			//if at least one location service is enable
			splashThread.start();
		}
	}
}
