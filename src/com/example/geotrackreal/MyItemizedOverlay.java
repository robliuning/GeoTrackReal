package com.example.geotrackreal;

import java.util.ArrayList;

import android.app.AlertDialog;
import android.content.Context;
import android.graphics.drawable.Drawable;

import com.google.android.maps.ItemizedOverlay;
import com.google.android.maps.OverlayItem;

public class MyItemizedOverlay extends ItemizedOverlay {
	private ArrayList<OverlayItem> myOverlays = new ArrayList<OverlayItem>();
	private Context mcontext;

	public MyItemizedOverlay(Drawable defaultMarker,Context context) {
		  super(boundCenterBottom(defaultMarker));
		  mcontext = context;
		}
	
	public void addOverlay(OverlayItem overlay){
		myOverlays.add(overlay);
		populate();
	}
	
	@Override
	protected OverlayItem createItem(int i) {
		return myOverlays.get(i);
	}

	@Override
	public int size() {
		return myOverlays.size();
	}
	
	@Override
	protected boolean onTap(int i){
		OverlayItem item = myOverlays.get(i);
		AlertDialog.Builder dialog = new AlertDialog.Builder(mcontext);
		dialog.setTitle(item.getTitle());
		dialog.setMessage(item.getSnippet());
		dialog.show();
		return true;
	}
}
