package com.vumobile.shaboxbuddy;

import com.android.volley.toolbox.NetworkImageView;
import com.vumobile.shaboxbuddy.app.AppController;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.view.View;

public class ServiceResultHelp extends Activity{
	NetworkImageView imageLebel;
	Context context;
	String imgUrl;
	public com.android.volley.toolbox.ImageLoader imageLoader = AppController.getInstance().getImageLoader();
	
	public void onCreate(Bundle savedInstanceState) 
	{
        Log.i("Tracker", "This is ServiceResultHelp");
        super.onCreate(savedInstanceState);
       /* TelephonyManager tm = (TelephonyManager) getSystemService(Context.TELEPHONY_SERVICE);
        String number = tm.getNetworkOperatorName().toLowerCase();
        Log.e("sim",number);
*/
        if (ShaboxBuddyMainActivity.ResultMno.startsWith("88018")||ShaboxBuddyMainActivity.ResultMno.startsWith("018")) {
            setContentView(R.layout.robihelp);
        }
        else if(ShaboxBuddyMainActivity.ResultMno.startsWith("88017")||ShaboxBuddyMainActivity.ResultMno.startsWith("017")) {
            setContentView(R.layout.gphelp);
        }
        else if(ShaboxBuddyMainActivity.ResultMno.startsWith("88019")||ShaboxBuddyMainActivity.ResultMno.startsWith("019")) {
            setContentView(R.layout.banglalinkhelp);
        }
        else if(ShaboxBuddyMainActivity.ResultMno.startsWith("88016")||ShaboxBuddyMainActivity.ResultMno.startsWith("016")) {
            setContentView(R.layout.airtelhelp);
        }
        else if(ShaboxBuddyMainActivity.ResultMno.startsWith("88015")||ShaboxBuddyMainActivity.ResultMno.startsWith("015")) {
            setContentView(R.layout.teletalkhelp);
        }
        else {
            setContentView(R.layout.help);
        }
        this.setTitle("Shabox Buddy");
        context = this;

		initUi();
	}
	public void initUi() {
		imageLoader = AppController.getInstance().getImageLoader();
		
		imageLebel = (NetworkImageView) findViewById(R.id.textHeadLebel);
		imageLebel.setImageUrl("http://wap.shabox.mobi/CMS/UIHeader/D480x800/Shabox_Header.png".replaceAll(" ", "%20"),imageLoader);
		
	}

	public void onHomeButtonClicked(View arg0) {
		this.finish();
	}
	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		overridePendingTransition(0, 0);
	}

}
