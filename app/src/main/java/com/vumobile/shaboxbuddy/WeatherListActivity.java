package com.vumobile.shaboxbuddy;

import com.android.volley.toolbox.NetworkImageView;
import com.vumobile.shaboxbuddy.app.AppController;
import com.vumobile.shaboxbuddy.util.CacheImageDownloader;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.ImageView;
import android.widget.TextView;

public class WeatherListActivity extends Activity {
	Context context;
	TextView push_text;
	public NetworkImageView weather_header;
	public CacheImageDownloader cacheImageDownloader;
	public String Header_Image_Url="http://wap.shabox.mobi/CMS/UIHeader/D480x800/Shabox_Header.png";
	public com.android.volley.toolbox.ImageLoader imageLoader = AppController.getInstance().getImageLoader();

	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.weatherlist_activity);
		cacheImageDownloader=new CacheImageDownloader();
		initUI();
        Log.i("Tracker", "This is weatherLockActivity");
		

	}
	/**
	 * 
	 */
	private void initUI() {
		// TODO Auto-generated method stub
		imageLoader = AppController.getInstance().getImageLoader();
		weather_header=(NetworkImageView)findViewById(R.id.weather_header);
		weather_header.setImageUrl("http://wap.shabox.mobi/CMS/UIHeader/D480x800/Shabox_Header.png".replaceAll(" ", "%20"),imageLoader);
		
	}
	public void Dhaka(View v){
		Intent intent=new Intent(this,SeviceResultWeather.class);
		intent.putExtra("ServiceResultWeatherId", 1);
		startActivity(intent);
		
	}
	public void Chitagong(View v){
		Intent intent=new Intent(this,SeviceResultWeather.class);
		intent.putExtra("ServiceResultWeatherId", 2);
		startActivity(intent);
		
	}
	public void Rajshahi(View v){
		Intent intent=new Intent(this,SeviceResultWeather.class);
		intent.putExtra("ServiceResultWeatherId", 3);
		startActivity(intent);
		
	}
	public void Khulna(View v){
		Intent intent=new Intent(this,SeviceResultWeather.class);
		intent.putExtra("ServiceResultWeatherId", 4);
		startActivity(intent);
		
	}
	public void Barishal(View v){
		Intent intent=new Intent(this,SeviceResultWeather.class);
		intent.putExtra("ServiceResultWeatherId", 5);
		startActivity(intent);
		
	}
	public void Shylet(View v){
		Intent intent=new Intent(this,SeviceResultWeather.class);
		intent.putExtra("ServiceResultWeatherId", 6);
		startActivity(intent);
		
	}
	public void Rangpur(View v){
		Intent intent=new Intent(this,SeviceResultWeather.class);
		intent.putExtra("ServiceResultWeatherId", 7);
		startActivity(intent);
		
	}
	public void onHomeButtonClicked(View arg0) {
		this.finish();
	}

	public void onFaqButtonClicked(View arg0) {
		Intent start = new Intent(getApplicationContext(),
				ServiceResultFaq.class);
		startActivity(start);
	}

	public void onAboutButtonClicked(View arg0) {
		Intent start = new Intent(getApplicationContext(),
				ServiceResultHelp.class);
		startActivity(start);
	}

	

}
