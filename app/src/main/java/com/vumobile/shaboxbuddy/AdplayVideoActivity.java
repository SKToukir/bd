package com.vumobile.shaboxbuddy;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.media.MediaPlayer;
import android.media.MediaPlayer.OnPreparedListener;
import android.media.MediaPlayer.OnVideoSizeChangedListener;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.ImageView;
import android.widget.MediaController;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.VideoView;

import com.vumobile.network.FileCache;
import com.vumobile.network.ImageLoader;
import com.vumobile.network.MemoryCache;
import com.vumobile.shaboxbuddy.holder.AllAdPlayVideoList;
import com.vumobile.shaboxbuddy.model.AdplayVideoListModel;
import com.vumobile.shaboxbuddy.util.VideoBusyDialog;

public class AdplayVideoActivity extends Activity implements OnClickListener {
	// For Creating ImageResize Object
	ImageResize mImageResize;
	private VideoBusyDialog busyNow;
	// For Creating Object
	SingleNodeXMLParser singleNodeparser;
	public static TextView textResult, txtHeader;
	ImageView headerImage;
	public static int dimWidth, dimHeight;
	String serviceTitle;
	ImageView imageLebel,play_icon;
	Context context;
	String reponse_results;
	String imgUrl,video_url_;
	public ImageLoader imageLoader;
	ImageView ad_image,imageView1;
	ImageView cancel_button;
	private MediaController controller;
	MemoryCache memoryCache = new MemoryCache();
	FileCache fileCache;
	RelativeLayout main_linear;
//	private static ProgressDialog progressDialog;
	
	VideoView view1;

	// http://203.76.126.210/ad_play/closebox.png
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.adplay_video_activity);
		this.setTitle("Shabox Buddy");
        Log.i("Tracker", "This is AdplayVideoActivity");
		// textResult = (TextView) findViewById(R.id.textView1);
		context = this;
		busyNow = new VideoBusyDialog(context, true);
		main_linear = (RelativeLayout) findViewById(R.id.main_linear);

		imageLoader = new ImageLoader(context);

		initUI();

	}

	public void initUI() {
		
		cancel_button = (ImageView) findViewById(R.id.cancel_button);
		imageView1=(ImageView)findViewById(R.id.imageView1);
		play_icon=(ImageView)findViewById(R.id.play_icon);
		  view1 = (VideoView) findViewById(R.id.ad_image); 
		  
		  view1.setVisibility(View.GONE);
        Log.i("Tracker", "This is initUI in AdplayActivity");
		
		if (AllAdPlayVideoList.getAllAdplayVideoList().size() == 0) {

		} else {
			final AdplayVideoListModel query = AllAdPlayVideoList.getAllAdplayVideoList().elementAt(
					0);
			 video_url_=query.getLink(); 
			 imageLoader.DisplayImage(query.getImgsrc(), R.drawable.ad_image,
					 imageView1);
			 
		}
		
		
		
		cancel_button.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				AdplayVideoActivity.this.finish();

			}
		});
		
//		imageView1.setOnClickListener(new OnClickListener() {
//
//			@Override
//			public void onClick(View v) {
//				view1.setVisibility(View.VISIBLE);
//				//cancel_button.setVisibility(View.GONE);
//				imageView1.setVisibility(View.GONE);
//				Log.i(video_url_, video_url_);
//				if(!video_url_.equalsIgnoreCase(""))
//				{
//					playvideo("rtsp://46.249.213.87/broadcast/zing-tablet.3gp");
//				}
//
//			}
//		});

	}

	
	public void PlayVideo(View v){
		
		//cancel_button.setVisibility(View.GONE);
        Log.i("Tracker", "This is PlayVideo in AdplayActivity");
//		progressDialog = ProgressDialog.show(this, "", "Buffering video...",true);
		busyNow.show();
		Log.i(video_url_, video_url_);
		if(!video_url_.equalsIgnoreCase(""))
		{
			playvideo(video_url_);
		}
		
	}
	
	
	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		overridePendingTransition(0, 0);
	}
	@SuppressLint("ResourceAsColor")
	void playvideo(String url) 
	{
        Log.i("Tracker", "This is Playvideo in  AdplayActivity");
	     String  link=url;
	     Log.e("url",link);
	   
	    
	     view1.setVisibility(View.VISIBLE);
	     view1.setBackgroundColor(getResources().getColor(R.color.white));
	    
//	 	progressDialog.setCancelable(true);
	 	//view1 = (VideoView) findViewById(R.id.view1); 
		controller = new MediaController(AdplayVideoActivity.this);
		view1.setMediaController(controller);
		view1.setVideoPath(link);
		view1.requestFocus();
		view1.start();

		view1.setOnPreparedListener(new OnPreparedListener() {
			@Override
			public void onPrepared(MediaPlayer mp) {
				// TODO Auto-generated method stub
				
				mp.start();
//				progressDialog.dismiss();
				busyNow.dismis();
				 view1.setBackground(null);
				imageView1.setVisibility(View.GONE);
				play_icon.setVisibility(View.GONE);
				
				mp.setOnVideoSizeChangedListener(new OnVideoSizeChangedListener() {
					@Override
					public void onVideoSizeChanged(MediaPlayer mp, int arg1,
							int arg2) {
//						progressDialog.dismiss();
						busyNow.dismis();
						mp.start();
					}
				});

			}
		});
	 }
	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub

	}

}
