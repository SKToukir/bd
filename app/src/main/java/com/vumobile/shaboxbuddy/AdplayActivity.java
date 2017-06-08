package com.vumobile.shaboxbuddy;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.vumobile.network.FileCache;
import com.vumobile.network.ImageLoader;
import com.vumobile.network.MemoryCache;
import com.vumobile.shaboxbuddy.holder.AllAdPlayList;
import com.vumobile.shaboxbuddy.model.AdplayListModel;
import com.vumobile.shaboxbuddy.util.BusyDialog;

public class AdplayActivity extends Activity implements OnClickListener {
	// For Creating ImageResize Object
	ImageResize mImageResize;
	private BusyDialog busyNow;
	// For Creating Object
	SingleNodeXMLParser singleNodeparser;
	public static TextView textResult, txtHeader;
	ImageView headerImage;
	public static int dimWidth, dimHeight;
	String serviceTitle;
	ImageView imageLebel;
	Context context;
	String reponse_results;
	String imgUrl;
	public ImageLoader imageLoader;
	ImageView ad_image;
	ImageView cancel_button;
	MemoryCache memoryCache = new MemoryCache();
	FileCache fileCache;
	LinearLayout main_linear;
//	http://203.76.126.210/ad_play/closebox.png
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.adplay_activity);
		this.setTitle("Shabox Buddy");
        Log.i("Tracker", "This is AdplayActivity");
		//textResult = (TextView) findViewById(R.id.textView1);
		context = this;
		main_linear=(LinearLayout)findViewById(R.id.main_linear);
		
		imageLoader = new ImageLoader(context);
		
		initUI();

	}

	public void initUI() {
		ad_image = (ImageView) findViewById(R.id.ad_image);
		cancel_button = (ImageView) findViewById(R.id.cancel_button);

		final AdplayListModel model = AllAdPlayList.getAdplayList(0);

		Log.w("size", "" + AllAdPlayList.getAllAdplayList().size());
		Log.w("Hello ", model.getImgsrc());
		String imageUrl = "";
		imageUrl = model.getImgsrc().toString().trim();
		imageUrl = imageUrl.replace(" ", "%20");
		Log.w("Image Url", imageUrl);
		fileCache=new FileCache(context);
		 memoryCache.clear();
		 fileCache.clear();
		 ImageLoadFirst imageLoader_ = new ImageLoadFirst(context);
		 ad_image.setTag(imageUrl);
		 imageLoader_.DisplayImage(imageUrl,AdplayActivity.this, ad_image);
		cancel_button.setImageResource( R.drawable.closebox);
		// }
		ad_image
		.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				String adLink = model.getLink().toString().trim();
				String url = adLink;
				Intent i = new Intent(
						Intent.ACTION_VIEW);
				i.setData(Uri.parse(url));
				startActivity(i);
			}
		});
		cancel_button.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				AdplayActivity.this.finish();

			}
		});
	}

	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		overridePendingTransition(0, 0);
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub

	}

}
