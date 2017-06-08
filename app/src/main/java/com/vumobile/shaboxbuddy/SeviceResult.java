package com.vumobile.shaboxbuddy;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.toolbox.NetworkImageView;
import com.flurry.android.FlurryAgent;
import com.vumobile.network.NetworkChecker;
import com.vumobile.network.ServerSideActivity;
import com.vumobile.shaboxbuddy.app.AppController;
import com.vumobile.shaboxbuddy.holder.AllAdPlayList;
import com.vumobile.shaboxbuddy.parser.AdplayListParser;
import com.vumobile.shaboxbuddy.util.AlertMessage;
import com.vumobile.shaboxbuddy.util.BusyDialog;
import com.vumobile.shaboxbuddy.util.HttpRequest;
import com.vumobile.shaboxbuddy.util.SharedPreferencesHelper;

import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class SeviceResult extends Activity {
	// For Creating ImageResize Object
	ImageResize mImageResize;
	private BusyDialog busyNow;
	private ProgressDialog pdialog;
	// For Creating Object
	SingleNodeXMLParser singleNodeparser;
	public static TextView textResult, txtHeader;
	NetworkImageView imageLebel;
	Bitmap archiveImageHeader;
	public static int dimWidth, dimHeight;
	String serviceTitle="Service Name";
	ImageView headerImage;
	Button readMoreButton;
	Context context;
	String reponse_results;
	
	String imgUrl;
	Intent archivelistIntent;
	 Activity SRActivity=this;
	
	public Activity getInstanceSR(){
		   return   SRActivity;
		 }

	
	
	public com.android.volley.toolbox.ImageLoader imageLoader = AppController.getInstance().getImageLoader();
	public NetworkChecker internetConnection = new NetworkChecker();
	//Intent serviceArchiveintent = new Intent(SeviceResult.this, ServiceResultArchive.class);
	public void onCreate(Bundle savedInstanceState) {
        Log.i("Tracker", "This is ServiceResult");
		super.onCreate(savedInstanceState);
		setContentView(R.layout.service_text_page);
		this.setTitle("Shabox Buddy");
		textResult = (TextView) findViewById(R.id.textView1);
		readMoreButton = (Button) findViewById(R.id.readMoreButton);
		context = this;
		AdPlay();
		
		imageLoader = AppController.getInstance().getImageLoader();
		
		/*
		 * Typeface font = Typeface.createFromAsset(getAssets(),
		 * "SolaimanLipi-8-Jan-2011.ttf"); textResult.setTypeface(font);
		 */
		archivelistIntent=new Intent(SeviceResult.this, ServiceArchiveList.class);
		txtHeader = (TextView) findViewById(R.id.textLebelHeader);
		headerImage = (ImageView) findViewById(R.id.imageView1);
		initUi();
		
		if(TopCategories.service_Id.equals("222"))
		{
			readMoreButton.setVisibility(View.GONE);
		}
		readMoreButton.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				
				
				startActivity(archivelistIntent);
				
							}
		});

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
			
			
			
			archivelistIntent.putExtra("bodytext",bodyText);
			archivelistIntent.putExtra("headerImage",headerImage );
			archivelistIntent.putExtra("serviceTitle",serviceTitle);
			
			Log.i("Result Token", "" + bodyText + serviceTitle + headerImage);

			textResult.setText(bodyText.replace("&&&", "\n\n").replaceAll("<br/>",""));
			txtHeader.setText(serviceTitle);
			String HeaderimageURL = headerImage;
			HeaderimageURL = HeaderimageURL.replaceAll(" ", "%20");
			// Load Header Image
			new getHeaderimageAsyncTask().execute(HeaderimageURL);

			// Put success log
			new putSuccessLog().execute("");

		} catch (Exception e) {

		}

	}

	public void AdPlay() {
		if (AppConstant.adpalyFlag.equalsIgnoreCase("true")) {
			String mobile_no = "";
			String IP_ = internetConnection.getLocalIpAddress();

			String rul = "http://wap.shabox.mobi/adplayapi/AdPlay.aspx?pos=4&pid=9DD6E43F-54F6-487E-A604-C8854728D53B"
					+ "&mno="
					+ SplashActivity.resultMno_splash
					+ "&hma="
					+ AppConstant.HS_MANUFAC.replaceAll(" ", "%20")
					+ "&hmo="
					+ AppConstant.HS_MOD.replaceAll(" ", "%20")
					+ "&hdim="
					+ AppConstant.HS_DIM.replaceAll(" ", "%20")
					+ "&hos=ANDROID" + "&ip=" + IP_.replaceAll(" ", "%20");
			AdplayData(rul);

		}
	}

	private void AdplayData(final String url_string) {
		// TODO Auto-generated method stub

		if (!SharedPreferencesHelper.isOnline(this))
			AlertMessage.showMessage(this, "Status", "No Internet Connection");

		busyNow = new BusyDialog(context, true);
		busyNow.show();

		final Thread d = new Thread(new Runnable() {

			@Override
			public void run() {

				try {

					reponse_results = HttpRequest.GetText(HttpRequest
							.getInputStreamForGetRequest(url_string));
					final JSONObject json_object = new JSONObject(
							reponse_results);
					AppConstant.video_response = json_object.getString("link");
					if (AdplayListParser.connect(getApplicationContext(),
							reponse_results))
						;

				} catch (final Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

				runOnUiThread(new Runnable() {

					@Override
					public void run() {
						if (busyNow != null) {
							busyNow.dismis();
						}
						AppConstant.adpalyFlag = "false";
						if (AllAdPlayList.getAllAdplayList().size() == 0) {

						} else {
							if (AppConstant.video_response
									.equalsIgnoreCase("NOK")) {

							} else {
								Intent intent = new Intent(SeviceResult.this,
										AdplayActivity.class);
								startActivity(intent);
							}

						}

						// hotelSearch();
						//
						// HindiAdapter adapter = new HindiAdapter(context);
						// list.setAdapter(adapter);
						// adapter.notifyDataSetChanged();

					}

				});

			}
		});

		d.start();
	}

	public void initUi() {
		
		imageLebel = (NetworkImageView) findViewById(R.id.textHeadLebel);
		imageLebel.setImageUrl("http://wap.shabox.mobi/CMS/UIHeader/D480x800/Shabox_Header.png".replaceAll(" ", "%20"),imageLoader);
		
	}

	public void onHomeButtonClicked(View arg0) {
		this.finish();
	}

	public void onFaqButtonClicked(View arg0) {
		Intent start = new Intent(getApplicationContext(),
				ServiceResultFaq.class);
		startActivity(start);
	}

	public void onHelpButtonClicked(View arg0) {
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
			//archiveImageHeader=bitmap;
			
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

	public void Share(View v) {
		String URL = "Hey! Read this in Buddy!  "
				+ "http://wap.shabox.mobi/Buddy/DetailsPage.aspx?sid="
				+ ServiceQuery.serviceTextID;
		Intent share = new Intent(android.content.Intent.ACTION_SEND);
		share.setType("text/plain");
		share.addFlags(Intent.FLAG_ACTIVITY_CLEAR_WHEN_TASK_RESET);

		// Add data to the intent, the receiving app will decide
		// what to do with it.

		share.putExtra(Intent.EXTRA_SUBJECT, "ShaboxBuddy Apps");
		share.putExtra(Intent.EXTRA_TEXT, URL);

		startActivity(Intent.createChooser(share, "Share link!"));

	}

	@Override
	protected void onStart() {
		super.onStart();
		FlurryAgent.onStartSession(this, AppConstant.FLURRY_API_KEY);
	}

	@Override
	protected void onStop() {
		super.onStop();
		FlurryAgent.onEndSession(this);
	}

	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		overridePendingTransition(0, 0);
	}

}
