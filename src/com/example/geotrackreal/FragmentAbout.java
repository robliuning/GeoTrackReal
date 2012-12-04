package com.example.geotrackreal;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class FragmentAbout extends Fragment {
	private EditText mail_content;
	private Button bt_send;
	private String content;
	//in case someone try to spam the mail function, a number is set up so that send button will be disabled after 10 clicks in this session
	private int counter;
	
	public View onCreateView(LayoutInflater inflater,ViewGroup container,Bundle savedInstanceState){
		View view = inflater.inflate(R.layout.fragment_about, container, false);	
		mail_content = (EditText)view.findViewById(R.id.mail_content);
		bt_send = (Button)view.findViewById(R.id.bt_send_mail);	
		mail_content.requestFocus();
		bt_send.setOnClickListener(btSend);
		bt_send.setEnabled(true);
		return view;
	}
	
	private OnClickListener btSend = new OnClickListener(){

		@Override
		public void onClick(View v) {
			content = mail_content.getText().toString().trim();
			//If content is not empty, send the mail
			if(content.length()!=0){
				Intent emailIntent = new Intent(android.content.Intent.ACTION_SEND);
				String[] recipients = new String[]{"robliuning@gmail.com", "colinjaeger@gmail.com"};
				emailIntent.putExtra(android.content.Intent.EXTRA_EMAIL, recipients);
				emailIntent.putExtra(android.content.Intent.EXTRA_SUBJECT, "Contact from GeoTrack");
				emailIntent.putExtra(android.content.Intent.EXTRA_TEXT, content);
				emailIntent.setType("text/plain");
				startActivity(Intent.createChooser(emailIntent, "Send mail client :"));
				counter++;
				if(counter==10){
					bt_send.setEnabled(false);
				}
				}else{
					Toast.makeText(getActivity().getBaseContext(), getActivity().getString(R.string.error_mail_empty), Toast.LENGTH_LONG).show();
			}
		}
	};
}
