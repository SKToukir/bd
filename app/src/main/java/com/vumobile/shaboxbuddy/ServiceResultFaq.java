package com.vumobile.shaboxbuddy;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.telephony.TelephonyManager;
import android.text.Html;
import android.text.method.LinkMovementMethod;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.toolbox.NetworkImageView;
import com.vumobile.network.ImageLoader;
import com.vumobile.shaboxbuddy.app.AppController;

public class ServiceResultFaq extends Activity{
	NetworkImageView imageLebel;
	Context context;
	String imgUrl;
	TextView portalLink;
	public com.android.volley.toolbox.ImageLoader imageLoader = AppController.getInstance().getImageLoader();
	

	public void onCreate(Bundle savedInstanceState) 
	{
        Log.i("Tracker", "This is ServiceResultFaq");
        super.onCreate(savedInstanceState);

        /*TelephonyManager tm = (TelephonyManager) getSystemService(Context.TELEPHONY_SERVICE);
        String number = tm.getNetworkOperatorName().toLowerCase();
        Log.e("sim",number);*/

        if (ShaboxBuddyMainActivity.ResultMno.startsWith("88018")||ShaboxBuddyMainActivity.ResultMno.startsWith("018")) {
            setContentView(R.layout.robi_faq);
        }
        else if(ShaboxBuddyMainActivity.ResultMno.startsWith("88017")||ShaboxBuddyMainActivity.ResultMno.startsWith("017")) {
            setContentView(R.layout.gpfaq);
        }
        else if(ShaboxBuddyMainActivity.ResultMno.startsWith("88019")||ShaboxBuddyMainActivity.ResultMno.startsWith("019")) {
            setContentView(R.layout.banglalinkfaq);
        }
        else if(ShaboxBuddyMainActivity.ResultMno.startsWith("88016")||ShaboxBuddyMainActivity.ResultMno.startsWith("016")) {
            setContentView(R.layout.airtel_faq);
        }
        else if(ShaboxBuddyMainActivity.ResultMno.startsWith("88015")||ShaboxBuddyMainActivity.ResultMno.startsWith("015")) {
            setContentView(R.layout.teletalk_faq);
        }
        else {
            setContentView(R.layout.faq);
        }

        
        context = this;
        imageLoader = AppController.getInstance().getImageLoader();
    	
		portalLink=(TextView)findViewById(R.id.portalLink);
		portalLink.setMovementMethod(LinkMovementMethod.getInstance());//==========Link movement mathod is used
		String text1 = "<a href='http://wap.shabox.mobi/buddy'>http://wap.shabox.mobi/buddy</a>";
		portalLink.setText(Html.fromHtml(text1));
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
//	public void onAboutButtonClicked(View arg0) {
//		Intent start = new Intent(getApplicationContext(), ServiceResultAbout.class);
//		startActivity(start);
//    }
	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		overridePendingTransition(0, 0);
	}

}
