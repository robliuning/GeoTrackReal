package com.example.geotrackreal;

import java.util.List;

import android.graphics.drawable.Drawable;
import android.location.Location;
import android.os.Bundle;
import android.widget.Toast;

import com.google.android.maps.GeoPoint;
import com.google.android.maps.MapActivity;
import com.google.android.maps.MapController;
import com.google.android.maps.MapView;
import com.google.android.maps.Overlay;
import com.google.android.maps.OverlayItem;

public class Mymap extends MapActivity {


	private MapView mView;
	private List<Overlay> mapOverlays;
	private Drawable drawable;
	private MyItemizedOverlay itemizedoverlay;
	private GeoPoint point;
	private OverlayItem overlayitem;
	private MapController mc;
	private Location location;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_mymap);		
        mView = (MapView)findViewById(R.id.mapview);
        mView.setBuiltInZoomControls(true); 
        //mView.setSatellite(true);
        drawable = this.getResources().getDrawable(R.drawable.logo);
        itemizedoverlay = new MyItemizedOverlay(drawable,this);
        //Toast.makeText(this, e.toString(), Toast.LENGTH_LONG).show();
	}
	public void onResume(){
		super.onResume();
        location = getIntent().getParcelableExtra(StaticName.EXTRA_LOCATION);     
		mc = mView.getController();
	    mapOverlays = mView.getOverlays();
	    point = new GeoPoint((int)(location.getLatitude()*1e6),(int)(location.getLongitude()*1e6));
	    overlayitem = new OverlayItem(point,"Tracker: "+ location.getExtras().getInt(StaticName.EXTRA_POSITION)+" Dropped at: "+location.getExtras().getString(StaticName.EXTRA_RECDATE),location.getExtras().getString(StaticName.EXTRA_ADDRESS));
	    itemizedoverlay.addOverlay(overlayitem);	        
	    mapOverlays.add(itemizedoverlay);
	    mc.setZoom(17);
	    mc.animateTo(point);	 

	}
	
	protected boolean isRouteDisplayed() {
		// TODO Auto-generated method stub
		return false;
	}

}
