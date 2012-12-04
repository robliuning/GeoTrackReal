package com.example.geotrackreal;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

public class MainActivity extends FragmentActivity {
	private int currentFrag;
	private int selectFrag;
	private FragmentTransaction transaction;
	private FragmentAbout fragmentAbout;
	private FragmentMain fragmentMain;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		//FragmentMain will be displayed by default
		FragmentMain fragmentMain = new FragmentMain();
		getSupportFragmentManager().beginTransaction().add(R.id.fragment_container,fragmentMain).commit();
		//Use the currentFrag to identify if user chose to navigate to a fragment that he/she has already in.
		currentFrag = R.id.main_main;
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu){
		MenuInflater inflater = getMenuInflater();
		inflater.inflate(R.menu.activity_main, menu);
		return true;
	}
	
	public boolean onOptionsItemSelected(MenuItem item){
		selectFrag = item.getItemId();
		if(selectFrag != currentFrag){
			currentFrag = selectFrag;
			//If user chose a fragment other then the one he/she already in, start the transaction
			fragmentChange(selectFrag);
			}
		return true;
	}
	
	public void fragmentChange(int fragId){
		transaction = getSupportFragmentManager().beginTransaction();

		if(fragId == R.id.main_main){
			//use replace to replace the existing one
			fragmentMain = new FragmentMain();
			transaction.replace(R.id.fragment_container, fragmentMain);

		}else if(fragId == R.id.main_about){
			fragmentAbout = new FragmentAbout();
			transaction.replace(R.id.fragment_container, fragmentAbout);
		}
		//In case user click back
		transaction.addToBackStack(null);
		transaction.commit();	
	}
}
