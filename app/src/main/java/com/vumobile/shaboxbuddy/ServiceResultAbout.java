package com.vumobile.shaboxbuddy;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import com.android.volley.toolbox.NetworkImageView;
import com.vumobile.network.ImageLoader;
import com.vumobile.shaboxbuddy.app.AppController;

public class ServiceResultAbout extends Activity {

	NetworkImageView imageLebel;
	Context context;
	String imgUrl;
	
	public com.android.volley.toolbox.ImageLoader imageLoader = AppController.getInstance().getImageLoader();
	

	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.about);
        Log.i("Tracker", "This is ServiceResultAbout");
		context = this;
		imageLoader = AppController.getInstance().getImageLoader();

		initUi();
	}

	public void initUi() {
		
		imageLebel = (NetworkImageView) findViewById(R.id.textHeadLebel);
		imageLebel.setImageUrl("http://wap.shabox.mobi/CMS/UIHeader/D480x800/Shabox_Header.png".replaceAll(" ", "%20"),imageLoader);
		

	}

	public void onHomeButtonClicked(View arg0) {
		this.finish();
	}
	// public void onFaqButtonClicked(View arg0) {
	// this.finish();
	// }
	//
	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		overridePendingTransition(0, 0);
	}

}
