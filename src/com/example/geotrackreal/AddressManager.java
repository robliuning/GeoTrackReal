package com.example.geotrackreal;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import android.content.Context;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;

public class AddressManager {
	/**
	 * Reusable address finder 
	 */
	private Geocoder geo;
	private List<Address> addresses;
	private ArrayList<String> stringAddresses;
	private String stringAddress;
	
	public ArrayList<String> getStringAddresses(Context context,ArrayList<Location> listLocations){
		for(Location location:listLocations){
			stringAddress = getSingleAddress(context,location);
			stringAddresses.add(stringAddress);
		}
		return stringAddresses;
	}
	
	public String getSingleAddress(Context context,Location location){
		 try{
		        geo = new Geocoder(context,Locale.getDefault());
		        addresses = geo.getFromLocation(location.getLatitude(),location.getLongitude(), 1);
				 if(addresses.isEmpty()){
			        	stringAddress = "Latitude: "+ location.getLatitude() + "and Longitude: "+location.getLongitude();
			        }else{
			        	if(addresses.size() > 0){
			        		stringAddress = addresses.get(0).getAddressLine(0) +", "+addresses.get(0).getAddressLine(1)+", "+addresses.get(0).getAddressLine(2);
			        	}
			        }	 
		 		}catch(Exception e){
		        	stringAddress = context.getString(R.string.address_system_error);
		        }
		return stringAddress;
	}
}
