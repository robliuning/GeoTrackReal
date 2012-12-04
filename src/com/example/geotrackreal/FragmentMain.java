package com.example.geotrackreal;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class FragmentMain extends Fragment {
	//Local variables
	private EditText et_interval;
	private EditText et_distance;
	private Button bt_start;
	private int interval;
	private int distance;
	// identifier weather user inputs are qualified
	private boolean ready = true;

	
	public View onCreateView(LayoutInflater inflater,ViewGroup container,Bundle savedInstanceState){
	
		View view = inflater.inflate(R.layout.fragment_main, container, false);
		
		et_interval = (EditText)view.findViewById(R.id.et_interval);
		et_distance = (EditText)view.findViewById(R.id.et_distance);
		bt_start = (Button)view.findViewById(R.id.bt_start);
		bt_start.setOnClickListener(btStart);
		return view;
	}
	
	private OnClickListener btStart = new OnClickListener(){

		@Override
		public void onClick(View v) {
			//Make sure both input field are not empty
			try{
				interval = Integer.parseInt(et_interval.getText().toString());
				distance = Integer.parseInt(et_distance.getText().toString());
				//Make sure the user input meet the pre-defined logic
				if(interval >= getActivity().getResources().getInteger(R.integer.track_interval) ){ //QUESTION: Has it to be this complicate to load value from resource
					et_interval.setError(getActivity().getString(R.string.error_interval));  //QUESTION: Why it is different for retrieving string value and integer
					et_interval.requestFocus();
					ready = false;
				}
				//Make sure the user input meet the pre-defined logic
				if(distance >= getActivity().getResources().getInteger(R.integer.track_distance) ){
					et_distance.setError(getActivity().getString(R.string.error_distance));
					et_distance.requestFocus();
					ready = false;
				}
				
			}catch(NumberFormatException e){
				ready = false;
		    	Toast.makeText(getActivity().getBaseContext(), getActivity().getString(R.string.error_empty), Toast.LENGTH_LONG).show();
			}
			if(ready){
				Intent intent = new Intent(getActivity().getBaseContext(),TrackActivity.class);
				intent.putExtra(StaticName.EXTRA_INTERVAL, interval);
				intent.putExtra(StaticName.EXTRA_DISTANCE, distance);
				startActivity(intent);			
			}
			ready = true;
		}
	};
}
