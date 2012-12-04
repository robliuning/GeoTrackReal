package com.example.geotrackreal;

import java.util.ArrayList;

import android.app.Activity;
import android.content.Context;
import android.location.Location;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
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
		//A temporal class for easz the process of assign values to layout elements
		ImageView type;
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
			holder.type = (ImageView)row.findViewById(R.id.iv_rectype);
			holder.title = (TextView)row.findViewById(R.id.tv_title);
			holder.subtitle = (TextView)row.findViewById(R.id.tv_subtitle);
			row.setTag(holder);
		}else{
			holder = (LocationHolder)row.getTag();
		}	
		location = trackerList.get(position);
		boolean recType = location.getExtras().getBoolean(StaticName.EXTRA_RECTYPE);
		if(recType){
			holder.type.setImageResource(R.drawable.list_comp);
		}else{
			holder.type.setImageResource(R.drawable.list_hand);
		}
		holder.title.setText(location.getExtras().getString(StaticName.EXTRA_ADDRESS));
		holder.subtitle.setText(location.getExtras().getString(StaticName.EXTRA_RECDATE));
		return row;
	}
}
