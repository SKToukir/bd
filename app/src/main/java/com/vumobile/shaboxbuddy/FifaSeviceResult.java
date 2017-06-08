package com.vumobile.shaboxbuddy;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.vumobile.network.ImageLoader;
import com.vumobile.network.ServerSideActivity;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class FifaSeviceResult extends Activity {
	// For Creating ImageResize Object
	ImageResize mImageResize;
	// For Creating Object
	SingleNodeXMLParser singleNodeparser;
	public static TextView textResult, txtHeader;
	ImageView headerImage;
	public static int dimWidth, dimHeight;
	String serviceTitle;
	ImageView imageLebel;
	Context context;
	String imgUrl;
	public ImageLoader imageLoader;

	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
        setContentView(R.layout.fifa_service_text_page);
        this.setTitle("Shabox Buddy");
        textResult = (TextView) findViewById(R.id.textView1);
        context = this;
        imageLoader = new ImageLoader(context);

        Log.i("Tracker", "This is fifaS4erviceResult");
		/*
		 * Typeface font = Typeface.createFromAsset(getAssets(),
		 * "SolaimanLipi-8-Jan-2011.ttf"); textResult.setTypeface(font);
		 */

		txtHeader = (TextView) findViewById(R.id.textLebelHeader);
		headerImage = (ImageView) findViewById(R.id.imageView1);
		initUi();

		try {
			DisplayMetrics dms = new DisplayMetrics();
			getWindowManager().getDefaultDisplay().getMetrics(dms);

			dimWidth = dms.widthPixels;
			dimHeight = dms.heightPixels;

			mImageResize = new ImageResize();
			// Get Layout valye from ImageResizer Class
			headerImage.setLayoutParams(mImageResize.getlayoutType(dimWidth));

			// For Creating Object of Parsing
			singleNodeparser = new SingleNodeXMLParser();

			Intent inn = getIntent();
			String ServiceResult = inn.getStringExtra("ServiceResult");

			Log.i("Result ", "" + ServiceResult);
			// textResult.setText(ServiceResult);

			String delims = "[|]";

			String bodyText, headerImage;

			String[] tokens = ServiceResult.split(delims);

			serviceTitle = tokens[0]; // Title
			bodyText = tokens[1]; // Body Text
			headerImage = tokens[2]; // Content Image

			Log.i("Result Token", "" + bodyText + serviceTitle + headerImage);
			String string = bodyText.replaceAll(serviceTitle, "");
			string = string.replaceAll(":", ":\n").replaceAll("\\.", "\n\n").replaceAll("<br/>","");
			// string = string
			// .replaceAll(":", ":\n")
			// .replaceAll("Tomorrow ", "\nTomorrow ")
			// .replaceAll("Match Result", "\nMatch Result")
			// .replaceAll("Today match Schedule",
			// "\nToday match Schedule");
			textResult.setText(string);
			txtHeader.setText(serviceTitle);
			String HeaderimageURL = headerImage;
			HeaderimageURL=HeaderimageURL.replaceAll(" ", "%20");

			// Load Header Image
			new getHeaderimageAsyncTask().execute(HeaderimageURL);

			// Put success log
			new putSuccessLog().execute("");

		} catch (Exception e) {

		}

	}

	public void initUi() {
		imageLoader = new ImageLoader(context);
		imageLebel = (ImageView) findViewById(R.id.textHeadLebel);
		//imgUrl = "http://202.164.213.242/CMS/UIHeader/D480x800/Shabox_Header.png";
		imgUrl = "http://wap.shabox.mobi/CMS/UIHeader/D480x800/Shabox_Header.png";

		imgUrl = imgUrl.replaceAll(" ", "%20");
		Log.w("imgUrl", imgUrl);
		imageLoader.DisplayImage(imgUrl, R.drawable.ad_image, imageLebel);
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

	// Methods for Creating Bitmap Image
	private Bitmap downloadUrl(String strUrl) throws IOException {
		Bitmap bitmap = null;
		InputStream iStream = null;

		try {
			URL url = new URL(strUrl);
			/** Creating an http connection to communcate with url */
			HttpURLConnection urlConnection = (HttpURLConnection) url
					.openConnection();
			/** Connecting to url */
			urlConnection.connect();
			/** Reading data from url */
			iStream = urlConnection.getInputStream();
			/** Creating a bitmap from the stream returned from the url */
			bitmap = BitmapFactory.decodeStream(iStream);
		} catch (Exception e) {
			Log.d("Exception while downloading url", e.toString());
		} finally {
			iStream.close();
		}

		return bitmap;
	}

	// For getHeaderimage
	class getHeaderimageAsyncTask extends AsyncTask<String, String, Bitmap> {

		Bitmap bitmap = null;

		@Override
		protected void onPreExecute() {
			super.onPreExecute();

		}

		@Override
		protected Bitmap doInBackground(String... aurl) {
			try {

				bitmap = downloadUrl(aurl[0]);

			} catch (Exception e) {
				Log.d("Background Task", e.toString());
			}

			return bitmap;

		}

		protected void onProgressUpdate(String... progress) {
			Log.d("ANDRO_ASYNC", progress[0]);
		}

		@Override
		protected void onPostExecute(Bitmap resultImage) {
			headerImage.setImageBitmap(resultImage);
		}

	}

	// Put Success log asynchronous task
	class putSuccessLog extends AsyncTask<String, String, String> {

		Bitmap bitmap = null;

		@Override
		protected void onPreExecute() {
			super.onPreExecute();

		}

		@Override
		protected String doInBackground(String... aurl) {
			try {
				// Start Putting success log
				DisplayMetrics dms = new DisplayMetrics();
				getWindowManager().getDefaultDisplay().getMetrics(dms);

				ServerSideActivity ssa = new ServerSideActivity();
				RequiredUserInfo userinfo = new RequiredUserInfo();

				String HS_MANUFAC = userinfo
						.deviceMANUFACTURER(getApplication());
				String HS_MOD = userinfo.deviceModel(getApplication());
				String HS_DIM = dms.widthPixels + "x" + dms.heightPixels;
				String APN = "";
				String PORTAL_FULLnSHORT = "SB_GGL_AND_APP";
				String SERVICE_REQUEST = serviceTitle;
				String CMPAIN_KEY = "";
				String OS = "Android";

				try {
					ssa.httpServerPostSuccessLog("success_log",
							"SB_GGL_AND_APP",
							ShaboxBuddyMainActivity.ResultMno, SERVICE_REQUEST,
							HS_MANUFAC, HS_MOD, HS_DIM, APN, PORTAL_FULLnSHORT,
							CMPAIN_KEY, OS);
				} catch (Exception e) {
					Log.i("SERVER_POST_ERROR", "" + e.getMessage());
				}
				// End Putting success log

			} catch (Exception e) {
				Log.d("Background Task", e.toString());
			}

			return null;
		}

		protected void onProgressUpdate(String... progress) {
			Log.d("ANDRO_ASYNC", progress[0]);
		}

		@Override
		protected void onPostExecute(String resultImage) {

		}

	}

	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		overridePendingTransition(0, 0);
	}

}
