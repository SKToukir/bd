package com.vumobile.shaboxbuddy;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.util.Log;

public class AlertDialogManager {
	/**
	 * Function to display simple Alert Dialog
	 * @param context - application context
	 * @param title - alert dialog title
	 * @param message - alert message
	 * @param status - success/failure (used to set icon)
	 * 				 - pass null if you don't want icon
	 * */
	@SuppressWarnings("deprecation")
	public void showAlertDialog(Context context, String title, String message,
			Boolean status) {
        Log.i("Tracker", "This is alert dialog manager AdplayActivity");
		
		AlertDialog alertDialog = new AlertDialog.Builder(context).create();

		// Setting Dialog Title
		alertDialog.setTitle(title);

		// Setting Dialog Message
		alertDialog.setMessage(message);

		if(status != null)
			// Setting alert dialog icon
			alertDialog.setIcon((status) ? R.drawable.icon : R.drawable.icon);

		// Setting OK Button
		alertDialog.setButton("Yes", new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog, int which) {
				
			}
		});
		
		alertDialog.setButton("No", new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog, int which) {
				
			}
		});
		// Showing Alert Message
		alertDialog.show();
	}
	
	@SuppressWarnings("deprecation")
	public void showAlertMessage(Context context, String title, String message,
			Boolean status) {
		
		AlertDialog.Builder alertDialog;
		alertDialog = new AlertDialog.Builder(context);
		// Setting Dialog Title
		//alertDialog.setTitle(title);

		// Setting Dialog Message
		alertDialog.setMessage(message);

		if(status != null)
			// Setting alert dialog icon
			//alertDialog.setIcon((status) ? R.drawable.btn_stop : R.drawable.btn_play);

		// Setting OK Button
		alertDialog.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog, int which) {

				System.exit(1);
			}
		});
		
		alertDialog.setNegativeButton("No", new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog, int which) {
				
			}
		});
		
		// Showing Alert Message
		alertDialog.show();
	}
}
