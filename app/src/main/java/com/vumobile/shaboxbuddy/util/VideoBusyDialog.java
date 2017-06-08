package com.vumobile.shaboxbuddy.util;

import android.app.Dialog;
import android.content.Context;
import android.view.Window;
import android.widget.TextView;

import com.vumobile.shaboxbuddy.R;

public class VideoBusyDialog {

	private final Dialog dialog;
	private TextView busyText;

	public VideoBusyDialog(Context c, boolean cancelable, String text) {
		dialog = new Dialog(c, android.R.style.Theme_Translucent);
		dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
		// here we set layout of progress dialog
		dialog.setContentView(R.layout.custom_video_progress_dialog);
		dialog.setCancelable(cancelable);
//		busyText = (TextView) dialog.findViewById(R.id.busytextview);
//		busyText.setText(text + "");
	}

	public VideoBusyDialog(Context c, boolean cancelable) {
		dialog = new Dialog(c, android.R.style.Theme_Translucent);
		dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
		// here we set layout of progress dialog
		dialog.setContentView(R.layout.custom_video_progress_dialog);
		dialog.setCancelable(true);
	}

	public void show() {
		dialog.show();
	}

	public void dismis() {
		dialog.cancel();
	}

}
