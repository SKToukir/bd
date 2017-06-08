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

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.entity.BufferedHttpEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class ServiceResultArchive extends Activity {
	// For Creating ImageResize Object
	ImageResize mImageResize;
	private BusyDialog busyNow;
	private ProgressDialog pdialog;
	// For Creating Object
	SingleNodeXMLParser singleNodeparser;
	public static TextView textResult, txtHeader;
	ImageView headerImage;
	Bitmap archiveImageHeader;
	public static int dimWidth, dimHeight;
	String contentTitle="Name";
	NetworkImageView imageLebel;
	Button readMoreButton;
	String ServiceResult;
	Context context;
	String reponse_results;
	TextView serviceTitleTextBox;
	String contentUrl="http://203.76.126.210/shaboxbuddy/get_archivecontent?ServiceId=";
	public com.android.volley.toolbox.ImageLoader imageLoader = AppController.getInstance().getImageLoader();
	String imgUrl;
	Intent archivelistIntent;
	
	public NetworkChecker internetConnection = new NetworkChecker();
	//Intent serviceArchiveintent = new Intent(SeviceResult.this, ServiceResultArchive.class);
	public void onCreate(Bundle savedInstanceState) {
        Log.i("Tracker", "This is ServiceResultArchive");
		super.onCreate(savedInstanceState);
		new getContentRequestTask().execute(contentUrl+ServiceArchiveList.contentCode);
		setContentView(R.layout.serviceresultarchive);
		this.setTitle("Shabox Buddy");
		textResult = (TextView) findViewById(R.id.bodytextView);
		serviceTitleTextBox = (TextView) findViewById(R.id.servicetitle);
		serviceTitleTextBox.setText(ServiceArchiveList.serviceTitle);
		context = this;
		AdPlay();
		
		imageLoader = AppController.getInstance().getImageLoader();
		
		
		txtHeader = (TextView) findViewById(R.id.titleHeaderTextView);
		//headerImage = (ImageView) findViewById(R.id.titleHeaderimageView);
		initUi();
		

		

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
								Intent intent = new Intent(ServiceResultArchive.this,
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
		
		imageLebel = (NetworkImageView) findViewById(R.id.archivebanner);
		imageLebel.setImageUrl("http://wap.shabox.mobi/CMS/UIHeader/D480x800/Shabox_Header.png".replaceAll(" ", "%20"),imageLoader);
		
		

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


    //=========================Comment by Faisal==================
	/*public void onAboutButtonClicked(View arg0) {
		Intent start = new Intent(getApplicationContext(),
				ServiceResultAbout.class);
		startActivity(start);
	}*/
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
		//	headerImage.setImageBitmap(resultImage);
			
			
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
				String SERVICE_REQUEST = contentTitle;
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
	class getContentRequestTask extends AsyncTask<String, Void, String> {

	  
	    String result;
	    @Override
	    protected void onPreExecute() {
	        super.onPreExecute();
	    	pdialog = new ProgressDialog(ServiceResultArchive.this);
			pdialog.setMessage("Loading, please wait");
			pdialog.setCancelable(false);
			pdialog.show();
	       
	    }

	    @Override
	    protected String doInBackground(String... params) {

	        // @BadSkillz codes with same changes
	        try {
	            DefaultHttpClient httpClient = new DefaultHttpClient();
	            HttpGet httpGet = new HttpGet(params[0]);
	            HttpResponse response = httpClient.execute(httpGet);
	            HttpEntity entity = response.getEntity();

	            BufferedHttpEntity buf = new BufferedHttpEntity(entity);

	            InputStream is = buf.getContent();

	            BufferedReader r = new BufferedReader(new InputStreamReader(is));

	            StringBuilder total = new StringBuilder();
	            String line;
	            while ((line = r.readLine()) != null) {
	                total.append(line + "\n");
	            }
	             result = total.toString();
	            Log.i("Get URL", "Downloaded string: " + result);
	            return result;
	        } catch (Exception e) {
	            Log.e("Get Url", "Error in downloading: " + e.toString());
	        }
	        return null;
	    }

	    @Override
	    protected void onPostExecute(String result) {
	        super.onPostExecute(result);
	        ServiceResult= result;
	        try {
				

				// For Creating Object of Parsing
				singleNodeparser = new SingleNodeXMLParser();

				
				//ServiceResult ="If your ex does ask for you to give|If your ex does ask for you to give him another chance, then you need to make sure that the same thing won't happen again; tell him what you want out of the relationship and see if he agrees to it. Of course, this means you'll have to meet his terms as well.|http://202.164.213.242/CMS/BuddyContentImage/130704496710800429_love tips 44.jpg";

				Log.i("Result", "" + ServiceResult);
				// textResult.setText(ServiceResult);

				String delims = "[|]";

				String bodyText, headerImage;

				String[] tokens = ServiceResult.split(delims);

				contentTitle = tokens[0]; // Title
				bodyText = tokens[1]; // Body Text
				headerImage = tokens[2]; // Content Image
				
				
				Log.i("Result Token", "" + bodyText + contentTitle + headerImage);

				textResult.setText(bodyText);

				txtHeader.setText(contentTitle);
				String HeaderimageURL = headerImage;
				HeaderimageURL = HeaderimageURL.replaceAll(" ", "%20");
				// Load Header Image
			//	new getHeaderimageAsyncTask().execute(HeaderimageURL);

				// Put success log
				new putSuccessLog().execute("");

			} catch (Exception e) {

			}
	       pdialog.dismiss();
	        
	    }
	}
}
