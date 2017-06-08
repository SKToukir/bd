package com.vumobile.shaboxbuddy;

import android.Manifest;
import android.app.Activity;
import android.app.AlarmManager;
import android.app.AlertDialog;
import android.app.PendingIntent;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Window;
import android.widget.Toast;

import com.vumobile.network.NetworkChecker;
import com.vumobile.shaboxbuddy.parser.AdplayTextListParser;
import com.vumobile.shaboxbuddy.parser.AdplayVideoListParser;
import com.vumobile.shaboxbuddy.util.HttpRequest;
import com.vumobile.shaboxbuddy.util.SharedPreferencesHelper;
import com.vumobile.shaboxbuddy.webservice.SplashCaller;

import org.apache.http.client.ClientProtocolException;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.net.URISyntaxException;


public class SplashActivity extends Activity {

	private Context context;
	protected static final int TIMER_RUNTIME = 10; // in ms --> 10s
	protected boolean mbActive;
	AlarmManager am;
	public static boolean flag = true;
	public static String resultMno_splash = "";
	int i = 3;
	Intent intentService;
	public String response_result;
	public String text_response_result;
	String rul = "";
	String Text_url = "";
	PendingIntent pendingIntent;
	public  String test;
	private static final int STORAGE_PERMISSION_CODE = 113;
	public NetworkChecker internetConnection = new NetworkChecker();
	public static Activity splashActivity;



	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_splash);
		splashActivity=SplashActivity.this;
		context = this;




//		if(isReadStorageAllowed()){
//			//If permission is already having then showing the toast
//			//Toast.makeText(SplashActivity.this,"You already have the permission",Toast.LENGTH_LONG).show();
//			//Existing the method with return
//			return;
//		}else{
//			requestStoragePermission();
//		}



        //========================Alert for No network Connection===============
		if (!SharedPreferencesHelper.isOnline(this)) {
			
			AlertDialog.Builder alert = new AlertDialog.Builder(this);
			alert.setIcon(R.drawable.wireless);

			alert.setTitle("Attention !");
			alert.setMessage("To Use This Application Please Connect To Internet");

			alert.setPositiveButton("Settings", new OnClickListener() {

				@Override
				public void onClick(DialogInterface dialog, int which) {
					// TODO Auto-generated method stub
					dialog.dismiss();
					Intent intent = new Intent(
							Settings.ACTION_WIRELESS_SETTINGS);
					startActivity(intent);

				}
			});
			alert.setNegativeButton("Cancel", new OnClickListener() {

				@Override
				public void onClick(DialogInterface dialog, int which) {
					// TODO Auto-generated method stub

					dialog.dismiss();
					finish();

				}
			});

			alert.show();
		} else {
			initUi();

			showTopNavigetion();

		}

//		if (isReadStorageAllowed()){
//
//		}else {
//			requestStoragePermission();
//		}

	}









	private boolean isReadStorageAllowed() {
		//Getting the permission status
		int result = ContextCompat.checkSelfPermission(this, android.Manifest.permission.GET_ACCOUNTS);

		//If permission is granted returning true
		if (result == PackageManager.PERMISSION_GRANTED)
			return true;

		//If permission is not granted returning false
		return false;
	}

	//Requesting permission
	private void requestStoragePermission(){

		if (ActivityCompat.shouldShowRequestPermissionRationale(this, android.Manifest.permission.GET_ACCOUNTS)){
			//If the user has denied the permission previously your code will come to this block
			//Here you can explain why you need this permission
			//Explain here why you need this permission
		}

		//And finally ask for the permission
		ActivityCompat.requestPermissions(this,new String[]{Manifest.permission.GET_ACCOUNTS},STORAGE_PERMISSION_CODE);
	}

	//This method will be called when the user will tap on allow or deny
	@Override
	public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {

		//Checking the request code of our request
		if(requestCode == STORAGE_PERMISSION_CODE){

			//If permission is granted
			if(grantResults.length >0 && grantResults[0] == PackageManager.PERMISSION_GRANTED){

				//Displaying a toast
				Toast.makeText(this, "Permission granted now you can read the storage", Toast.LENGTH_LONG).show();
			}else{
				//Displaying another toast if permission is not granted
				Toast.makeText(this,"Oops you just denied the permission",Toast.LENGTH_LONG).show();
			}
		}
	}







	private void initUi() {
		// TODO Auto-generated method stub

		final Thread timerThread = new Thread() {
			@Override
			public void run() {
				mbActive = true;
				try {
					int waited = 0;
					while (mbActive && (waited < TIMER_RUNTIME)) {
						sleep(5);
						if (mbActive) {
							waited += 5;
						}
					}
				} catch (InterruptedException e) {
					// do nothing
				} finally {
					onContinue();
				}
			}
		};
		timerThread.start();
	}

	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		overridePendingTransition(0, 0);
	}

	public void onContinue() {
		// perform any final actions here

		runOnUiThread(new Runnable() {

			public void run() {
				Intent intent = new Intent(SplashActivity.this,ShaboxBuddyMainActivity.class);
				startActivity(intent);
				finish();

			}
		});

	}

	String results = "", adplayResult = "";
	String keword_response = "";

	@SuppressWarnings("unchecked")
	public void showTopNavigetion() {	(new AsyncTask() {

			@Override
			protected void onPreExecute() {
				// TODO Auto-generated method stub
				super.onPreExecute();

			}

			@Override
			protected Object doInBackground(Object... params) {
				// TODO Auto-generated method stub

				try {

					try {
						Thread.sleep(1000);
						SplashActivity.resultMno_splash = "START";
						SplashCaller c = new SplashCaller();
						// c.ad= c.ad;
						c.join();
						c.start();

						while (SplashActivity.resultMno_splash == "START") {
							try {
								Thread.sleep(100);
							} catch (Exception ex) {
							}
						}

						AppConstant.mno = resultMno_splash;
						Log.i("success_log", "" + resultMno_splash);
					} catch (Exception ex) {
						Log.i("EXCEPTION EXIST USER", "" + ex.toString());
						// rslt= "Exception";

					}

					DisplayMetrics dms_ = new DisplayMetrics();
					getWindowManager().getDefaultDisplay().getMetrics(dms_);



					//ServerSideActivity ssa_ = new ServerSideActivity();
					RequiredUserInfo userinfo = new RequiredUserInfo();
					String HS_MANUFAC_ = userinfo.deviceMANUFACTURER(SplashActivity.this);
					String HS_MOD_ = userinfo.deviceModel(SplashActivity.this);
					String HS_DIM_ = dms_.widthPixels + "x" + dms_.heightPixels;
					String IP_ ="203.76.126.214";
					IP_=internetConnection.getLocalIpAddress();
					String MobileNumber=SplashActivity.resultMno_splash;
					if(MobileNumber.equalsIgnoreCase("ERROR")){
						MobileNumber="";
					}else {
						MobileNumber=SplashActivity.resultMno_splash;
					}

					/**
					 * Hit this Url For Every First Time FOr Showing Ad play
					 */
					String StringAdplay = "http://wap.shabox.mobi/adplayapi/load.aspx?pid=9DD6E43F-54F6-487E-A604-C8854728D53B"
							+ "&hma="
							+ HS_MANUFAC_.replaceAll(" ", "%20")
							+ "&hmo="
							+ HS_MOD_.replaceAll(" ", "%20")
							+ "&hdim="
							+ HS_DIM_.replaceAll(" ", "%20")
							+ "&hos=ANDROID"
							+ "&ip="
							+ IP_.replaceAll(" ", "%20")
							+ "&mno="
							+ MobileNumber;
					Log.i("StringAdplay", StringAdplay);

					adplayResult = HttpRequest.GetText(HttpRequest.getInputStreamForGetRequest(StringAdplay));
					Log.i("adplayResult", adplayResult);
					String string=adplayResult.toString().trim();
					if (string.toString().trim().equalsIgnoreCase("AdPLAY")) {


						rul = "http://wap.shabox.mobi/adplayapi/AdPlay.aspx?pos=55&pid=9DD6E43F-54F6-487E-A604-C8854728D53B"
								+ "&mno="
								+ MobileNumber
								+ "&hma="
								+  HS_MANUFAC_.replaceAll(" ", "%20")
								+ "&hmo="
								+ HS_MOD_.replaceAll(" ", "%20")
								+ "&hdim="
								+ HS_DIM_.replaceAll(" ", "%20")
								+ "&hos=ANDROID"
								+ "&ip="
								+ IP_.replaceAll(" ", "%20");


						Text_url="http://wap.shabox.mobi/adplayapi/AdPlay.aspx?pos=7&pid=9DD6E43F-54F6-487E-A604-C8854728D53B"
								+ "&mno="
								+ MobileNumber
								+ "&hma="
								+  HS_MANUFAC_.replaceAll(" ", "%20")
								+ "&hmo="
								+ HS_MOD_.replaceAll(" ", "%20")
								+ "&hdim="
								+ HS_DIM_.replaceAll(" ", "%20")
								+ "&hos=ANDROID"
								+ "&ip="
								+ IP_.replaceAll(" ", "%20");
						Log.d("Test",Text_url);


						try {
							response_result = HttpRequest.GetText(HttpRequest.getInputStreamForGetRequest(rul));

								final JSONObject mainJsonObject = new JSONObject(response_result);
								Log.w("respons mainJsonObject", ""+mainJsonObject);
								test=mainJsonObject.getString("link");

							text_response_result=HttpRequest.GetText(HttpRequest.getInputStreamForGetRequest(Text_url));
							if (AdplayVideoListParser.connect(getApplicationContext(), response_result));
							if (AdplayTextListParser.connect(getApplicationContext(), text_response_result));


						} catch (JSONException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					}

				} catch (ClientProtocolException e) {
					e.printStackTrace();
				} catch (IOException e) {
					e.printStackTrace();
				} catch (URISyntaxException e) {
					e.printStackTrace();
				}

				return null;
			}

			@Override
			protected void onPostExecute(Object result) {
				if(test.equalsIgnoreCase("NOK")){

				}else {
					Intent intent = new Intent(SplashActivity.this,AdplayVideoActivity.class);
					startActivity(intent);
				}

				super.onPostExecute(result);

			}

		}).execute();

	}
}
