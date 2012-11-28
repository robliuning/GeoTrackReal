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
		
		FragmentMain fragmentMain = new FragmentMain();
		getSupportFragmentManager().beginTransaction().add(R.id.fragment_container,fragmentMain).commit();
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
			fragmentChange(selectFrag);
			}
		return true;
	}
	
	public void fragmentChange(int fragId){
		transaction = getSupportFragmentManager().beginTransaction();

		if(fragId == R.id.main_main){
			fragmentMain = new FragmentMain();
			transaction.replace(R.id.fragment_container, fragmentMain);

		}else if(fragId == R.id.main_about){
			fragmentAbout = new FragmentAbout();
			transaction.replace(R.id.fragment_container, fragmentAbout);
		}
		transaction.addToBackStack(null);
		transaction.commit();	
	}
}
