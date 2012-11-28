package com.example.geotrackreal;

import java.util.ArrayList;

import android.app.Activity;
import android.content.Context;
import android.location.Location;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

public class LocationlistAdapter extends ArrayAdapter<Location>{
	private Context context;
	private int layoutResourceId;
	private ArrayList<Location> trackerList;
	private Location location;
	
	public LocationlistAdapter(Context context, int layoutResourceId, ArrayList<Location> trackerList){
		super(context,layoutResourceId,trackerList);
		
		this.layoutResourceId = layoutResourceId;
		this.context = context;
		this.trackerList = trackerList;
	}
	
	static class LocationHolder{
		TextView type;
		TextView title;
		TextView subtitle;
	}
	
	public View getView(int position, View convertView, ViewGroup parent){
		View row = convertView;
		LocationHolder holder = null;
		
		if(row == null){
			LayoutInflater inflater = ((Activity)context).getLayoutInflater();
			row = inflater.inflate(layoutResourceId, parent,false);
			
			holder = new LocationHolder();
			holder.title = (TextView)row.findViewById(R.id.tv_title);
			holder.subtitle = (TextView)row.findViewById(R.id.tv_subtitle);
			holder.type = (TextView)row.findViewById(R.id.tv_type);			
			row.setTag(holder);
		}else{
			holder = (LocationHolder)row.getTag();
		}
		
		location = trackerList.get(position);
		
		holder.type.setText(location.getExtras().getBoolean(StaticName.EXTRA_RECTYPE)?"Auto":"Manual");
		holder.title.setText(location.getExtras().getString(StaticName.EXTRA_ADDRESS));
		holder.subtitle.setText(location.getExtras().getString(StaticName.EXTRA_RECDATE));
		
		return row;
	}
	
}
