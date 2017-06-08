package com.vumobile.shaboxbuddy;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.annotation.SuppressLint;
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
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.android.volley.toolbox.NetworkImageView;
import com.flurry.android.FlurryAgent;
import com.vumobile.network.ImageLoader;
import com.vumobile.network.NetworkChecker;
import com.vumobile.network.ServerSideActivity;
import com.vumobile.shaboxbuddy.app.AppController;
import com.vumobile.shaboxbuddy.holder.AllAdPlayList;
import com.vumobile.shaboxbuddy.parser.AdplayListParser;
import com.vumobile.shaboxbuddy.parser.JsonParser;
import com.vumobile.shaboxbuddy.util.AlertMessage;
import com.vumobile.shaboxbuddy.util.BusyDialog;
import com.vumobile.shaboxbuddy.util.HttpRequest;
import com.vumobile.shaboxbuddy.util.SharedPreferencesHelper;

public class ServiceArchiveList extends Activity {

	ListView archiveList;
	String[] itemNameArray;
	String[] itemCodeArray;
	String[] itemDateArray;

	ImageResize mImageResize;
	private BusyDialog busyNow;
	
	// For Creating Object
	SingleNodeXMLParser singleNodeparser;
	public static TextView textResult, txtHeader;
	ImageView headerImage;
	Bitmap archiveImageHeader;
	public static int dimWidth, dimHeight;
	public static String serviceTitle = "Service Name";
	public static String contentCode = "18278";
	NetworkImageView imageLebel;
	Button readMoreButton;
	Context context;
	public static String bodyText, headerImageUrl;
	String reponse_results;
	String archiveURL = "http://203.76.126.210/shaboxbuddy/get_archivelist.php?cat=";
	String imgUrl;
	public com.android.volley.toolbox.ImageLoader imageLoader = AppController.getInstance().getImageLoader();
	public NetworkChecker internetConnection = new NetworkChecker();

	Activity SRLActivity = this;

	public Activity getInstanceSAL() {
		return SRLActivity;
	}

	// Intent serviceArchiveintent = new Intent(SeviceResult.this,
	// ServiceResultArchive.class);
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.servicearchivelist);
		this.setTitle("Shabox Buddy");
        Log.i("Tracker", "This is ServiceArchiveList");
		archiveList = (ListView) findViewById(R.id.archivelist);

		context = this;

		imageLoader =  AppController.getInstance().getImageLoader();

		/*
		 * Typeface font = Typeface.createFromAsset(getAssets(),
		 * "SolaimanLipi-8-Jan-2011.ttf"); textResult.setTypeface(font);
		 */

		txtHeader = (TextView) findViewById(R.id.textLebelHeaderarchive);
		headerImage = (ImageView) findViewById(R.id.imageViewheader);
		initUi();

		new ArchiveAsyncTask().execute(archiveURL + TopCategories.service_Id);
		try {
			DisplayMetrics dms = new DisplayMetrics();
			getWindowManager().getDefaultDisplay().getMetrics(dms);

			dimWidth = dms.widthPixels;
			dimHeight = dms.heightPixels;

			mImageResize = new ImageResize();
			// Get Layout valye from ImageResizer Class
			headerImage.setLayoutParams(mImageResize.getlayoutType(dimWidth));

			Intent inn = getIntent();

			serviceTitle = inn.getStringExtra("serviceTitle");
			// Title
			bodyText = inn.getStringExtra("bodytext"); // Body Text
			headerImageUrl = inn.getStringExtra("headerImage");// Content Image

			Log.i("Oka Oka", "" + bodyText + serviceTitle + headerImage);

			txtHeader.setText(serviceTitle);
			String HeaderimageURL = headerImageUrl;
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
								Intent intent = new Intent(
										ServiceArchiveList.this,
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
		
//		imageLoader = new ImageLoader(context);
//		imageLebel = (ImageView) findViewById(R.id.textHeadLebel);
//		imgUrl = "http://202.164.213.242/CMS/UIHeader/D480x800/Shabox_Header.png";
//
//		imgUrl = imgUrl.replaceAll(" ", "%20");
//		Log.w("imgUrl", imgUrl);
//		imageLoader.DisplayImage(imgUrl, R.drawable.ad_image, imageLebel);

	}

	public void onHomeButtonClicked(View arg0) {
		Intent start = new Intent(getApplicationContext(),
				ShaboxBuddyMainActivity.class);
		start.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
		startActivity(start);
		
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
			// archiveImageHeader=bitmap;

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

	public void Back(View v) {

		this.finish();
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

	class ArchiveAsyncTask extends AsyncTask<String, Void, Boolean> {
		ProgressDialog pdialog;

		@Override
		protected void onPreExecute() {
			super.onPreExecute();

			pdialog = new ProgressDialog(ServiceArchiveList.this);
			pdialog.setMessage("Loading, please wait");
			pdialog.setCancelable(false);
			pdialog.show();

		}

		@Override
		protected Boolean doInBackground(String... urls) {
			JsonParser jParser = new JsonParser();
			// Getting JSON from URL
			JSONObject json = jParser.getJSONFromUrl(urls[0]);
			Log.e("ServiceArchive",urls[0]);

			JSONArray jarray;
			try {
				jarray = json.getJSONArray("archive");
				itemNameArray = new String[jarray.length()];
				itemCodeArray = new String[jarray.length()];
				itemDateArray =new String[jarray.length()];
				for (int i = 0; i < jarray.length(); i++) {
					JSONObject object = jarray.getJSONObject(i);

					itemNameArray[i] = object.getString("TitleSimilarPost");
					itemCodeArray[i] = object.getString("ContentID");
					itemDateArray[i] = object.getString("ContentDate");
					itemNameArray[i]=itemDateArray[i]+" "+serviceTitle;
					Log.i("oka", itemCodeArray[i]);
				}
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			// ------------------>>

			return true;
		}

		@SuppressLint("InlinedApi")
		@Override
		protected void onPostExecute(Boolean result) {
			// TODO Auto-generated method stub
			super.onPostExecute(result);
			pdialog.dismiss();

			ArrayAdapter<String> archiveListAdapter = new ArrayAdapter<String>(
					ServiceArchiveList.this, R.layout.single_list_item,
					itemNameArray);

			archiveList.setAdapter(archiveListAdapter);

			archiveList.setOnItemClickListener(new OnItemClickListener() {

				@Override
				public void onItemClick(AdapterView<?> parent, View view,
						int position, long id) {
					// TODO Auto-generated method stub
					contentCode = itemCodeArray[position];
					Intent arc = new Intent(ServiceArchiveList.this,
							ServiceResultArchive.class);
					startActivity(arc);
				}
			});

		}
	}
}
