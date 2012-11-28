package com.example.geotrackreal;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;

public class AlertDialogManager {
	/**
	 * Reusable alert box
	 * @param context application context
	 * @param title alert dialog title
	 * @param message alert message
	 */
	public void showAlertDialog(Context context,String title,String message){
		AlertDialog.Builder adb = new AlertDialog.Builder(context);
		adb.setTitle(title)
			.setMessage(message)
			.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
				
				@Override
				public void onClick(DialogInterface dialog, int which) {
					
				}
			});
		AlertDialog ad = adb.create();
		ad.show();
	}

}
