//^(?!.*(nativeGetEnabledTags)).*$
/*
 * author: Md.Nazmul Hasan masum
 * VU Mobile Litd.
 * BanglaBeats Music Apps Streaming music player
 */

package com.vumobile.shaboxbuddy;

import android.Manifest;
import android.accounts.Account;
import android.accounts.AccountManager;
import android.annotation.TargetApi;
import android.app.Activity;
import android.app.AlarmManager;
import android.app.AlertDialog;
import android.app.Application;
import android.app.Dialog;
import android.app.PendingIntent;
import android.app.ProgressDialog;
import android.app.TabActivity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.graphics.Bitmap;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.SystemClock;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.telephony.TelephonyManager;
import android.util.DisplayMetrics;
import android.util.Log;
import android.util.Patterns;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.view.animation.TranslateAnimation;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TabHost;
import android.widget.TextView;

import com.android.volley.toolbox.NetworkImageView;
import com.bugsense.trace.BugSenseHandler;
import com.example.adplaybind.AdPlayBind;
import com.facebook.FacebookSdk;
import com.facebook.LoggingBehavior;
import com.facebook.appevents.AppEventsLogger;
import com.facebook.applinks.AppLinkData;
import com.google.android.gcm.GCMRegistrar;
import com.google.android.gms.ads.identifier.AdvertisingIdClient;
import com.google.android.gms.ads.identifier.AdvertisingIdClient.Info;
import com.google.android.gms.analytics.HitBuilders;
import com.google.android.gms.analytics.Tracker;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesNotAvailableException;
import com.google.android.gms.common.GooglePlayServicesRepairableException;
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.mobileapptracker.MobileAppTracker;
import com.tjeannin.apprate.AppRate;
import com.vumobile.network.FileCache;
import com.vumobile.network.MemoryCache;
import com.vumobile.network.NetworkChecker;
import com.vumobile.network.ServerSideActivity;
import com.vumobile.shaboxbuddy.app.AppController;
import com.vumobile.shaboxbuddy.app.AppController.TrackerName;
import com.vumobile.shaboxbuddy.holder.AllAdPlayTextList;
import com.vumobile.shaboxbuddy.model.AdplayTextListModel;
import com.vumobile.shaboxbuddy.parser.AdBannerplayListParser;
import com.vumobile.shaboxbuddy.util.AlertMessage;
import com.vumobile.shaboxbuddy.util.BusyDialog;
import com.vumobile.shaboxbuddy.util.HttpRequest;
import com.vumobile.shaboxbuddy.util.MyReceiver;
import com.vumobile.shaboxbuddy.util.SharedPreferencesHelper;
import com.vumobile.shaboxbuddy.webservice.Caller;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;
import java.util.regex.Pattern;

import static com.vumobile.shaboxbuddy.CommonUtilities.DISPLAY_MESSAGE_ACTION;
import static com.vumobile.shaboxbuddy.CommonUtilities.EXTRA_MESSAGE;
import static com.vumobile.shaboxbuddy.CommonUtilities.SENDER_ID;

//import com.flurry.android.FlurryAgent;

//import com.vumobile.network.ImageLoader;

@SuppressWarnings("deprecation")
public class ShaboxBuddyMainActivity extends TabActivity {
	// ----For Mobile Apps Tracking SDK Integration -----------/
	// ----For Mobile Apps Tracking SDK Integration -----------/
	static final Integer ACCOUNTS = 0x6;
	PHPRequest phpRequest=new PHPRequest();
	String number;
	final private int REQUEST_CODE_ASK_MULTIPLE_PERMISSIONS = 124;
	View tabView;
	public static String resultMno;
	public MobileAppTracker mobileAppTracker = null;
	protected static final int TIMER_RUNTIME = 10; // in ms --> 10s
	protected boolean mbActive;

	public static ProgressDialog progDialog1;
	private static final String TAG = TopCategories.class.getSimpleName();
	////
	private PendingIntent pendingIntent;
	String[] serviceID;
	String[] serviceTitle;
	String[] imageName;
	String[] numberOfLike;
	public static String name;
	public static String email;
	public static String model, reponse_results;
	public static String brand;
	private BusyDialog busyNow;
	public String updateReasonString;
	public static String MSISDN = null;
	public static Activity mainActivity;

	public static String ResultMno = null;
	public static String resultTopCategory = null;
	public static String resultMoreCategory = null;
	public String UpdateString = "";
	Context context;
	public static String tag_submit_pin = "SUBMIT_PINCODE";
	public static String tag_submit_msisdn = "SUBMIT_MSISDN";
	public static String tag_get_buddy_category = "";
	public static String tag_get_buddy_service_text = "";
	public static String tag_process = "";
	public static boolean active_activity = false;

	public static String r = "0";
	public NetworkChecker internetConnection = new NetworkChecker();
	private Application shaboxBuddymainActivity;
	TextView text_add;
	final Handler handler = new Handler();
	AlertDialogManager alert = new AlertDialogManager();
	TextView adplay_banner_ad, adplay_link;
	ImageView close, adplay_image, adplay_image_;
	private TimerTask sostt;
	private final long period = 5000;
	private final int delay = 1000;
	private Timer sostimer;
	// label to display GCM messages
	AsyncTask<Void, Void, Void> mRegisterTask;
	RelativeLayout adplay_linear;
	// =====Start For Like Button======/
	public static int dimWidth;
	public static int dimHeight;
	public static String mmodel;
	public static String manufacture;
	public static String bbrand;
	public static String ipAddress;
	public static String mmno;
	public static String IMEI;
	int action = 0;
	MemoryCache memoryCache = new MemoryCache();
	FileCache fileCache;
	RelativeLayout RL;
	Bitmap bitmap;
	NetworkImageView header_image;
	public String pushResponseUrl = "http://www.vumobile.biz/sbgcm_server/push_response.php";
	public String emailID;

	private static final int READ_PHONE_STATE_PERMISSION_CODE = 1111;

	com.android.volley.toolbox.ImageLoader imageLoader = AppController.getInstance().getImageLoader();

	// ======End For Like Button=====/

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		// ======Start for BugSence===========/
		BugSenseHandler.initAndStartSession(ShaboxBuddyMainActivity.this, "c4cab3ae");

		// ======End for BugSence===========//
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.shabox_buddy_main);

		context = ShaboxBuddyMainActivity.this;


		int currentapiVersion = android.os.Build.VERSION.SDK_INT;


		if (currentapiVersion >= 23) {
			// Do something for 14 and above versions
			if (isReadStorageAllowed()) {
				//If permission is already having then showing the toast
				//Toast.makeText(SplashActivity.this,"You already have the permission",Toast.LENGTH_LONG).show();
				//Existing the method with return
				context = ShaboxBuddyMainActivity.this;
				new detectMSISDNAsync().execute("");
				//return;
			} else {

				insertDummyContactWrapper();
			}

		}
		else {
			context = ShaboxBuddyMainActivity.this;
			// do something for phones running an SDK before 14
			new detectMSISDNAsync().execute("");
		}


		FacebookSdk.sdkInitialize(getApplicationContext());
		FacebookSdk.setIsDebugEnabled(true);
		FacebookSdk.addLoggingBehavior(LoggingBehavior.APP_EVENTS);





		//-------------Call method for push notification------------------
		initPushNotification();

		mainActivity = ShaboxBuddyMainActivity.this;

		imageLoader = AppController.getInstance().getImageLoader();
		header_image = (NetworkImageView) findViewById(R.id.header_image);
		header_image.setImageUrl(
				"http://wap.shabox.mobi/CMS/UIHeader/D480x320/Shabox_Header.png"
						.replaceAll(" ", "%20"), imageLoader);

		// imageLoader.DisplayImage("http://202.164.213.242/CMS/UIHeader/D480x320/Shabox_Header.png".replaceAll(" ",
		// "%20"), header_image);

		TabHost tabHost = getTabHost(); // The activity TabHost
		TabHost.TabSpec spec; // Resusable TabSpec for each tab
		Intent intent; // Reusable Intent for each tab


		//===============Code For Tab in the First Activity===============
		intent = new Intent().setClass(this, TopCategories.class);
		tabView = createTabView(this, "Top Categories");
		spec = tabHost.newTabSpec("Top Categories").setIndicator(tabView)
				.setContent(intent);
		Log.d("Tracker", "Category Called");
		tabHost.addTab(spec);

		intent = new Intent().setClass(this, MoreCategories.class);
		tabView = createTabView(this, "More Categories");
		spec = tabHost.newTabSpec("More Categories").setIndicator(tabView)
				.setContent(intent);
		tabHost.addTab(spec);
		Log.d("Tracker", "Tab Called");

		tabHost.setCurrentTab(0);
		RL = (RelativeLayout) findViewById(R.id.RL);

		RL.setVisibility(View.VISIBLE);
		AdPlayBind addPlay = new AdPlayBind();
		RL.addView(addPlay.BannerAd("F2ECCC2B-82B1-41EE-B5E8-D00ACC6EB673", "4088796A-01BD-4506-9AD1-31FD5E34DDB5", "1", ShaboxBuddyMainActivity.this));

		//context = this;

		// ==============Start for ShaboxBuddy Apps Rate =================/
		new AppRate(this).init();
		// ==============End for ShaboxBuddy Apps Rate =================/

		// ==============Start for ShaboxBuddy Apps Google Analytics For Each

		//===========Log for click notification=====================================================//

		try {
			Intent getIntents = getIntent();
			String notificAction = getIntents.getStringExtra("DO");

			Log.d("notificAction",notificAction);

			if (notificAction.matches("2")) {
				new SendLaunchPushResponses("launch").execute();
			}

		}catch (Exception e){
			Log.d("Error",e.getMessage());
		}
//		String serviceidfrompush = getPushIntent.getStringExtra("serviceid");
//
//		if (action == 2){
//			Log.d("Clicked","Clicked");
//			new SendLaunchPushResponse(getApplicationContext(),"launch").execute();
//		}

		// Activity =================/
		try {
			Tracker t = ((AppController) ShaboxBuddyMainActivity.this
					.getApplication()).getTracker(TrackerName.APP_TRACKER);
			// Set screen nam0e.
			// Where path is a String representing the screen name.
			t.setScreenName("ShaboxBuddy Main Activity");
			//Log.i("ShaboxBuddy Analytics True", "True");
			// Send a screen view.
			t.send(new HitBuilders.AppViewBuilder().build());
		} catch (Exception ex) {
			// TODO: handle exception
			//Log.i("ShaboxBuddy Analytics Error", ex.getMessage());
		}
		// ==============End for ShaboxBuddy Apps Google Analytics For Each


		//============================Start Ad play============================

		active_activity = true;
		adplay_banner_ad = (TextView) findViewById(R.id.adplay_banner_ad);
		close = (ImageView) findViewById(R.id.close);
		// adplay_link=(TextView)findViewById(R.id.adplay_link);
		// adplay_link.setVisibility(View.GONE);

		// adplay_image=(ImageView)findViewById(R.id.adplay_image);
		// adplay_image_=(ImageView)findViewById(R.id.adplay_image_);
		// adplay_image_.setVisibility(View.GONE);
//		adplay_linear = (RelativeLayout) findViewById(R.id.adplay_linear);
//		adplay_linear.setVisibility(View.GONE);
//        Log.i("Tracker","Ad Play Called");

//		close.setOnClickListener(new View.OnClickListener() {
//
//			@Override
//			public void onClick(View v) {
//				adplay_linear.setVisibility(View.GONE);
//				stopTimer();
//
//			}
//		});
		// ---- Start For Mobile Apps Tracking SDK Integration -----------/

		MobileAppTracker.init(getApplicationContext(), "16226",
				"70bf368c86d171b9740407e564337920");
		mobileAppTracker = MobileAppTracker.getInstance();
		mobileAppTracker.setReferralSources(this);

		AppConstant.GetUserIP = internetConnection.getLocalIpAddress();

		// Initialize MAT

		/*
		 * boolean isExistingUser = false; if (isExistingUser) {
		 * mobileAppTracker.setExistingUser(true); }
		 */

		// Collect Google Play Advertising ID
		new Thread(new Runnable() {
			@Override
			public void run() {
				// See sample code at
				// http://developer.android.com/google/play-services/id.html
				try {
					Log.i("Tracker", "Ad Play Called");
					Info adInfo = AdvertisingIdClient
							.getAdvertisingIdInfo(getApplicationContext());
					mobileAppTracker.setGoogleAdvertisingId(adInfo.getId(),
							adInfo.isLimitAdTrackingEnabled());
				} catch (IOException e) {
					Log.i("ErrorFinding", "IoException occured in Shabox Main Activity");
					// Unrecoverable error connecting to Google Play services
					// (e.g.,
					// the old version of the service doesn't support getting
					// AdvertisingId).
				} catch (GooglePlayServicesNotAvailableException e) {
					Log.i("ErrorFinding", "GooglePlayServicesNotAvailableException first");
					// Google Play services is not available entirely.
				} catch (GooglePlayServicesRepairableException e) {
					// Encountered a recoverable error connecting to Google Play
					// services.
					Log.i("ErrorFinding", "GooglePlayServicesNotAvailableException second");
				}
			}
		}).start();

		// ----End For Mobile Apps Tracking SDK Integration -----------/
		this.setTitle("Shabox Buddy");

		// For detect Mobile Number or MSISDN

		shaboxBuddymainActivity = this.getApplication();
		// initPushNotification();
		AdPlay(); //==================================comment by Faisal==============
		Log.i("ErrorFinding", "Add play finished");


		//======In app push notification Action=============


		try {
			Intent getPushIntent = getIntent();
			String serviceidfrompush = getPushIntent.getStringExtra("serviceid");



			if (serviceidfrompush != null) {
				pushactivityAction(serviceidfrompush);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		AppLinkData.fetchDeferredAppLinkData(this,
				new AppLinkData.CompletionHandler() {
					@Override
					public void onDeferredAppLinkDataFetched(AppLinkData appLinkData) {
						// Process app link data
					}
				}
		);


		try {

			Intent serviceIntent = new Intent(ShaboxBuddyMainActivity.this, GCMIntentService.class);
			startService(serviceIntent);
			Intent myIntent = new Intent(ShaboxBuddyMainActivity.this, MyReceiver.class);
			pendingIntent = PendingIntent.getBroadcast(ShaboxBuddyMainActivity.this, 0, myIntent, 0);

			AlarmManager alarmManager = (AlarmManager)getSystemService(ALARM_SERVICE);
			alarmManager.setRepeating(AlarmManager.ELAPSED_REALTIME_WAKEUP, SystemClock.elapsedRealtime(), 90 * 1000, pendingIntent);

		} catch (Exception e) {
			e.printStackTrace();
		}
	}


	private void showMessageOKCancel(String message, DialogInterface.OnClickListener okListener, DialogInterface.OnClickListener CancelListener) {
		new AlertDialog.Builder(ShaboxBuddyMainActivity.this)
				.setMessage(message)
				.setCancelable(false)
				.setPositiveButton("OK", okListener)
				.setNegativeButton("Cancel", CancelListener)
				.create()

				.show();
	}


	@TargetApi(23)
	private boolean addPermission(List<String> permissionsList, String permission) {
		if (checkSelfPermission(permission) != PackageManager.PERMISSION_GRANTED) {
			permissionsList.add(permission);
			// Check for Rationale Option
			if (!shouldShowRequestPermissionRationale(permission))
				return false;
		}
		return true;
	}

	private boolean isReadStorageAllowed() {
		//Getting the permission status
		int result = ContextCompat.checkSelfPermission(this, Manifest.permission.READ_PHONE_STATE);

		//If permission is granted returning true
		if (result == PackageManager.PERMISSION_GRANTED)
			return true;

		//If permission is not granted returning false
		return false;
	}

	@TargetApi(23)
	private void insertDummyContactWrapper() {
		List<String> permissionsNeeded = new ArrayList<String>();

		final List<String> permissionsList = new ArrayList<String>();
		if (!addPermission(permissionsList, Manifest.permission.READ_PHONE_STATE))
			permissionsNeeded.add("READPHONESTATE");
		if (!addPermission(permissionsList, Manifest.permission.GET_ACCOUNTS))
			permissionsNeeded.add("GET_ACCOUNTS");
//		if (!addPermission(permissionsList, Manifest.permission.READ_EXTERNAL_STORAGE))
//			permissionsNeeded.add("READSTORATGE");
//		if (!addPermission(permissionsList, Manifest.permission.WRITE_EXTERNAL_STORAGE))
//			permissionsNeeded.add("WRITE EXTERNAL STORAGE");
//		if (!addPermission(permissionsList, Manifest.permission.CAMERA))
//			permissionsNeeded.add("CAMERA");
//		if (!addPermission(permissionsList, Manifest.permission.WRITE_CONTACTS))
//			permissionsNeeded.add("Write Contacts");

		Log.d("permissionsNeeded", String.valueOf(permissionsNeeded.size()));
		if (permissionsList.size() > 0) {
			if (permissionsNeeded.size() > 0) {
				// Need Rationale
				String message = "You need to grant access to " + permissionsNeeded.get(0);
				for (int i = 1; i < permissionsNeeded.size(); i++)
					message = message + ", " + permissionsNeeded.get(i);
				showMessageOKCancel("You need to grant access",
						new DialogInterface.OnClickListener() {
							@Override
							public void onClick(DialogInterface dialog, int which) {

								Log.d("dialog click", String.valueOf(which));
								requestPermissions(permissionsList.toArray(new String[permissionsList.size()]),
										REQUEST_CODE_ASK_MULTIPLE_PERMISSIONS);
							}
						}, new DialogInterface.OnClickListener() {
							@Override
							public void onClick(DialogInterface dialog, int which) {

								dialog.dismiss();


							}
						});
				return;
			}
			requestPermissions(permissionsList.toArray(new String[permissionsList.size()]),
					REQUEST_CODE_ASK_MULTIPLE_PERMISSIONS);
			return;
		}

		//insertDummyContact();
	}




//	private boolean isReadStorageAllowed() {
//		//Getting the permission status
//		int result = ContextCompat.checkSelfPermission(ShaboxBuddyMainActivity.this, Manifest.permission.READ_PHONE_STATE);
//
//		//If permission is granted returning true
//		if (result == PackageManager.PERMISSION_GRANTED)
//			return true;
//
//		//If permission is not granted returning false
//		return false;
//	}

	private void askForPermission(String permission, Integer requestCode) {
		if (ContextCompat.checkSelfPermission(ShaboxBuddyMainActivity.this, permission) != PackageManager.PERMISSION_GRANTED) {

			// Should we show an explanation?
			if (ActivityCompat.shouldShowRequestPermissionRationale(ShaboxBuddyMainActivity.this, permission)) {

				//This is called if user has denied the permission before
				//In this case I am just asking the permission again
				ActivityCompat.requestPermissions(ShaboxBuddyMainActivity.this, new String[]{permission}, requestCode);

			} else {

				ActivityCompat.requestPermissions(ShaboxBuddyMainActivity.this, new String[]{permission}, requestCode);
			}
		} else {
			//Toast.makeText(this, "" + permission + " is already granted.", Toast.LENGTH_SHORT).show();
		}
	}

	//Requesting permission
	private void requestStoragePermission() {

		if (ActivityCompat.shouldShowRequestPermissionRationale(ShaboxBuddyMainActivity.this, Manifest.permission.READ_PHONE_STATE)) {
			//If the user has denied the permission previously your code will come to this block
			//Here you can explain why you need this permission
			//Explain here why you need this permission

		}


		//And finally ask for the permission
		ActivityCompat.requestPermissions(ShaboxBuddyMainActivity.this, new String[]{Manifest.permission.READ_PHONE_STATE}, READ_PHONE_STATE_PERMISSION_CODE);
	}

//	//This method will be called when the user will tap on allow or deny
//	@Override
//	public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
//
//		//Checking the request code of our request
//		if (requestCode == READ_PHONE_STATE_PERMISSION_CODE) {
//
//			//If permission is granted
//			if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
//
//				//Displaying a toast
//				new detectMSISDNAsync().execute("");
//				//Toast.makeText(ShaboxBuddyMainActivity.this, "Permission granted now you can read the storage", Toast.LENGTH_LONG).show();
//				context = ShaboxBuddyMainActivity.this;
//			} else {
//				//Displaying another toast if permission is not granted
//				//Toast.makeText(ShaboxBuddyMainActivity.this, "Oops you just denied the permission", Toast.LENGTH_LONG).show();
//			}
//			//checking account permission for acces user mail address
//		}else if (requestCode == ACCOUNTS){
//			AccountManager manager = (AccountManager) getSystemService(ACCOUNT_SERVICE);
//			Account[] list = manager.getAccounts();
//			//Toast.makeText(this,""+list[0].name,Toast.LENGTH_SHORT).show();
//			for(int i=0; i<list.length;i++){
//				Log.e("Account "+i,""+list[i].name);
//			}
//		}
//	}

	//This method will be called when the user will tap on allow or deny
	@TargetApi(23)
	@Override
	public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
		switch (requestCode) {
			case REQUEST_CODE_ASK_MULTIPLE_PERMISSIONS:
			{
				Map<String, Integer> perms = new HashMap<String, Integer>();
				// Initial
				perms.put(Manifest.permission.READ_PHONE_STATE, PackageManager.PERMISSION_GRANTED);
				perms.put(Manifest.permission.GET_ACCOUNTS, PackageManager.PERMISSION_GRANTED);
//				perms.put(Manifest.permission.READ_CONTACTS, PackageManager.PERMISSION_GRANTED);
//				perms.put(Manifest.permission.WRITE_CONTACTS, PackageManager.PERMISSION_GRANTED);
//				perms.put(Manifest.permission.READ_EXTERNAL_STORAGE, PackageManager.PERMISSION_GRANTED);
//				perms.put(Manifest.permission.WRITE_EXTERNAL_STORAGE, PackageManager.PERMISSION_GRANTED);

				// Fill with results
				for (int i = 0; i < permissions.length; i++)
					perms.put(permissions[i], grantResults[i]);
				// Check for ACCESS_FINE_LOCATION
				if (perms.get(Manifest.permission.READ_PHONE_STATE) == PackageManager.PERMISSION_GRANTED
						&& perms.get(Manifest.permission.GET_ACCOUNTS) == PackageManager.PERMISSION_GRANTED) {
					// All Permissions Granted
					// insertDummyContact();

                 /*   initPushNotification();

                    initUi();
                    register();
*///Displaying a toast
					new detectMSISDNAsync().execute("");
					//Toast.makeText(ShaboxBuddyMainActivity.this, "Permission granted now you can read the storage", Toast.LENGTH_LONG).show();
					context = ShaboxBuddyMainActivity.this;
				} else {

				}
			}
			break;
			default:
				super.onRequestPermissionsResult(requestCode, permissions, grantResults);
		}
	}


//=======================================Banner Ad=============================
	/*private void BannerAd() {
		startTimer();
		adplay_image.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// adplay_link.setVisibility(View.VISIBLE);
				// adplay_image.setImageResource(R.drawable.play);
				// adplay_link.setText("Ads by Adplay");
				// adplay_image_.setVisibility(View.VISIBLE);
				// adplay_image.setVisibility(View.GONE);
				TranslateAnimation slide = new TranslateAnimation(-100, 0, 0, 0);
				// TranslateAnimation slide1 = new TranslateAnimation(-120, 160,
				// 0,0 );
				slide.setDuration(1000);
				slide.setFillAfter(true);
				// slide1.setDuration(100);
				// slide1.setFillAfter(true);
				adplay_link.startAnimation(slide);
				// adplay_image.startAnimation(slide1);
				adplay_link.setOnClickListener(new OnClickListener() {

					@Override
					public void onClick(View v) {
						Intent i = new Intent(Intent.ACTION_VIEW);
						i.setData(Uri.parse("http://www.vumobile.biz"));
						startActivity(i);

					}
				});

			}
		});

		adplay_image_.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				adplay_image_.setVisibility(View.GONE);
				adplay_link.setVisibility(View.GONE);
				adplay_image.setVisibility(View.VISIBLE);
				adplay_link.setText("");

			}
		});
	}*///========================================================Comment BY Faisal

	void stopTimer() {
		try {
			if (sostimer != null) {
				sostimer.cancel();
				sostimer = null;

			}
			if (sostt != null) {
				sostt.cancel();
				sostt = null;
			}

		} catch (final Exception e) {
		}
	}

	// String[] myStringArray =
	// {"hdsjfhjds jhdfjkdhsf djkhfds  jkdhsf dsfjh jshdf jdkshf jdh fdsjh .........................","Hello there this is text ad testinh   ","c"};
	private static int countIndex = 0;

	void startTimer() {
		try {
			sostimer = new Timer();
			sostt = new TimerTask() {

				@Override
				public void run() {

					runOnUiThread(new Runnable() {
						@Override
						public void run() {
							// stopTimer();
							int size = AllAdPlayTextList.getAllAdplayTextList()
									.size();
							adplay_linear.setVisibility(View.VISIBLE);
							if (size == 0) {
								adplay_linear.setVisibility(View.GONE);
							}
							if (countIndex >= size)
								countIndex = 0;
							else
								countIndex = countIndex;
							try {
								final AdplayTextListModel query = AllAdPlayTextList
										.getAllAdplayTextList().elementAt(
												countIndex);
								adplay_banner_ad.setText(query.getTitle());

								TranslateAnimation slide = new TranslateAnimation(
										0, 0, 100, 0);
								slide.setDuration(1000);
								slide.setFillAfter(true);
								adplay_banner_ad.startAnimation(slide);
								// adplay_banner_ad.setText(myStringArray[countIndex]);

								adplay_banner_ad
										.setOnClickListener(new View.OnClickListener() {

											@Override
											public void onClick(View v) {
												String bannerLink = query
														.getNavurl();
												String url = bannerLink;
												Intent i = new Intent(
														Intent.ACTION_VIEW);
												i.setData(Uri.parse(url));
												startActivity(i);

											}
										});
							} catch (Exception e) {
								// TODO: handle exception
							}

							countIndex = countIndex + 1;

						}
					});

				}
			};
			sostimer.schedule(sostt, delay, period);

		} catch (final Exception e) {
		}

	}

	public void AdPlay() {
		DisplayMetrics dms_ = new DisplayMetrics();
		getWindowManager().getDefaultDisplay().getMetrics(dms_);

		ServerSideActivity ssa_ = new ServerSideActivity();
		RequiredUserInfo userinfo = new RequiredUserInfo();
		String HS_MANUFAC_ = userinfo
				.deviceMANUFACTURER(ShaboxBuddyMainActivity.this);
		Log.i("Tracker", "In the Adplay");

		String HS_MOD_ = userinfo.deviceModel(ShaboxBuddyMainActivity.this);

		String HS_DIM_ = dms_.widthPixels + "x" + dms_.heightPixels;
		String IP_ = internetConnection.getLocalIpAddress();

		if (AppConstant.mno.equalsIgnoreCase("error")) {
			Log.i("Tracker", "on the adplay if statement");
			AdplayData("http://203.76.126.210/ad_play/adplay.php?callback=?&cat=banner"
					+ "&pid="
					+ "9DD6E43F-54F6-487E-A604-C8854728D53B"
					+ "&mno="
					+ "Wifi".replaceAll(" ", "%20")
					+ "&HS_MANUFAC="
					+ HS_MANUFAC_.replaceAll(" ", "%20")
					+ "&HS_MOD="
					+ HS_MOD_.replaceAll(" ", "%20")
					+ "&GetUserIP="
					+ IP_.replaceAll(" ", "%20")
					+ "&HS_DIM="
					+ HS_DIM_.replaceAll(" ", "%20"));

		} else {
			Log.i("Tracker", "Adplay Else Statement");

			AdplayData("http://203.76.126.210/ad_play/adplay.php?callback=?&cat=banner"
					+ "&pid="
					+ "9DD6E43F-54F6-487E-A604-C8854728D53B"
					+ "&mno="
					+ AppConstant.mno
					+ "&HS_MANUFAC="
					+ HS_MANUFAC_.replaceAll(" ", "%20")
					+ "&HS_MOD="
					+ HS_MOD_.replaceAll(" ", "%20")
					+ "&GetUserIP="
					+ IP_.replaceAll(" ", "%20")
					+ "&HS_DIM="
					+ HS_DIM_.replaceAll(" ", "%20"));
		}

		//

	}

	//Get the mail id configured with Google pLay store
	@SuppressWarnings("MissingPermission")
	public String getMailId() {
		String strGmail = null;
		try {
			Account[] accounts = AccountManager.get(this).getAccounts();
			Log.e("PIKLOG", "Size: " + accounts.length);
			for (Account account : accounts) {

				String possibleEmail = account.name;
				String type = account.type;

				if (type.equals("com.google")) {

					strGmail = possibleEmail;
					Log.e("PIKLOG", "Emails: " + strGmail);
					break;
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		return strGmail;
	}

	//=============================Comment by faisal===========

	private void AdplayData(final String url_string) {
		// TODO Auto-generated method stub


		//==================Checking Internet Connection========================

		if (!SharedPreferencesHelper.isOnline(this))
			AlertMessage.showMessage(this, "Status", "No Internet Connection");

		busyNow = new BusyDialog(ShaboxBuddyMainActivity.this, true);
		busyNow.show();

		final Thread d = new Thread(


				new Runnable() {

					@Override
					public void run() {

						try {

							reponse_results = HttpRequest.GetText(HttpRequest
									.getInputStreamForGetRequest(url_string));
							String string = "http://www.vumobile.biz/shaboxbuddy/shaboxbuddy_version.txt";
							UpdateString = HttpRequest.GetText(HttpRequest
									.getInputStreamForGetRequest(string));
							String info_string = "http://www.vumobile.biz/shaboxbuddy/shaboxbuddy_version_reason.txt";
							updateReasonString = HttpRequest.GetText(HttpRequest
									.getInputStreamForGetRequest(info_string));


							TopCategories.updateDialogText = updateReasonString;

							Log.i("Tracker", "In the Adplay data Thread");

							Log.i("tracker", UpdateString);
							if (AdBannerplayListParser.connect(getApplicationContext(),
									reponse_results))
								;

						} catch (final Exception e) {
							Log.i("Tracker", " adplay data exception one");
							// TODO Auto-generated catch block
							e.printStackTrace();
						}

						runOnUiThread(new Runnable() {

							@Override
							public void run() {
								if (busyNow != null) {
									busyNow.dismis();
								}

								//Check Update Version

								try {
									PackageInfo pinfo;
									pinfo = getPackageManager().getPackageInfo(
											getPackageName(), 0);
									String versionName = pinfo.versionName;
									if (!versionName.equalsIgnoreCase(UpdateString
											.toString().trim())) {
										Log.i("Tracker", UpdateString);
										TopCategories.updateDialogOpenOrnotTopCategory = true;
										Update();
									}

								} catch (NameNotFoundException e1) {
									Log.i("ErrorFinding", " adplayData exception two");
									// TODO Auto-generated catch block
									e1.printStackTrace();
								}
								//Check Update Version

							}

						});

					}
				});

		d.start();
	}   //==============================Comment by Faisal==================

	/* Check Update Version Methood */
	public void Update() {

		final Dialog updateDialog = new Dialog(ShaboxBuddyMainActivity.this, android.R.style.Theme_Translucent_NoTitleBar);

		updateDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);

		updateDialog.setContentView(R.layout.update_dialog_activity);
		TextView update_text = (TextView) updateDialog
				.findViewById(R.id.update_text);
		update_text.setText(updateReasonString);
		TopCategories.updateReasonString = updateReasonString;
		MoreCategories.updateReasonString = updateReasonString;
		Button update_app = (Button) updateDialog.findViewById(R.id.update_app);
		ImageView img = (ImageView) updateDialog.findViewById(R.id.imageView1);
		Log.i("Tracker", " The Update dialog is called");

		update_app.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub

				updateDialog.dismiss();
				TopCategories.updateDialogOpenOrnotTopCategory = false;

				/**
				 * if this button is clicked, close current activity
				 */
				final String appPackageName = getPackageName();
				try {
					startActivity(new Intent(Intent.ACTION_VIEW, Uri
							.parse("market://details?id=" + appPackageName)));
				} catch (android.content.ActivityNotFoundException anfe) {
					startActivity(new Intent(
							Intent.ACTION_VIEW,
							Uri.parse("http://play.google.com/store/apps/details?id="
									+ appPackageName)));
				}
				// Post the Update Log
				try {

					emailID = getMailId();
					String updateLogString = "http://203.76.126.210/shaboxbuddy/All_AppUpdateLog.php?Email=" + emailID +
							"&MNO=" + AppConstant.mno + "&AppName=SB&AppVersion=" + UpdateString.toString().trim();
					WebView updateWebView = new WebView(ShaboxBuddyMainActivity.this);
					updateWebView.loadUrl(updateLogString);
				} catch (Exception e) {
					e.printStackTrace();
				}


			}
		});

		img.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				updateDialog.dismiss();
			}
		});

		updateDialog.show();

	}

	/* Check Update Version Methood */
	@SuppressWarnings("MissingPermission")
	private void initPushNotification() {

		model = Build.MODEL;
		brand = Build.BRAND; // like SEMC

		Log.w("MODEL ", "" + model + brand + " ");

		String possibleEmail = null;

		Pattern emailPattern = Patterns.EMAIL_ADDRESS; // API level 8+
		Account[] accounts = AccountManager.get(this).getAccountsByType("com.google");
		for (Account account : accounts) {
			if (emailPattern.matcher(account.name).matches()) {
				possibleEmail = account.name;
				Log.i("MY_EMAIL_count", "" + possibleEmail);
			}
		}

		Log.w("MY_EMAIL", "" + possibleEmail);
		// TODO Auto-generated method stub
		// ///////////////////////// Start Push Notification
		// Intitialization///////////////////////

		try {
			/*if (!internetConnection
					.isConnectingToInternet(shaboxBuddymainActivity)) {
				alert.showAlertDialog(ShaboxBuddyMainActivity.this,
						"Internet Connection Error",
						"Please connect to working Internet connection", false);
			}*/

			// Getting handset brand name, email
			name = brand;
			email = possibleEmail;
			Log.w("REG_ID", "" + name + "     " + email);
			// Make sure the device has the proper dependencies.
			GCMRegistrar.checkDevice(this);

			// Make sure the manifest was properly set - comment out this line
			// while developing the app, then uncomment it when it's ready.
			GCMRegistrar.checkManifest(this);

			registerReceiver(mHandleMessageReceiver, new IntentFilter(
					DISPLAY_MESSAGE_ACTION));

			// Get GCM registration id
			final String regId = GCMRegistrar.getRegistrationId(this);
			Log.w("REG_ID", "" + regId);
			// Check if regid already presents
			if (regId.equals("")) {
				// Registration is not present, register now with GCM
				GCMRegistrar.register(this, SENDER_ID);
			} else {
				// Device is already registered on GCM
				if (GCMRegistrar.isRegisteredOnServer(this)) {
					// Skips registration.
					// Toast.makeText(getApplicationContext(),
					// "Already registered with GCM", Toast.LENGTH_LONG).show();
				} else {
					// Try to register again, but not in the UI thread.
					// It's also necessary to cancel the thread onDestroy(),
					// hence the use of AsyncTask instead of a raw thread.
					final Context context = ShaboxBuddyMainActivity.this;
					mRegisterTask = new AsyncTask<Void, Void, Void>() {
						@Override
						protected Void doInBackground(Void... params) {
							// Register on our server
							// On server creates a new user
							ServerUtilities.register(ShaboxBuddyMainActivity.this, name, model,
									email, regId);
							return null;
						}

						@Override
						protected void onPostExecute(Void result) {
							mRegisterTask = null;
						}

					};
					mRegisterTask.execute(null, null, null);
				}
			}

		} catch (Exception e) {
			Log.w("GET_XCEP", "" + e.getMessage());
		}

		// ///////////////////////// End Push Notification Initialization
		// ///////////////

	}

	// //////////////////////////////////////
	// / Push Notification Method ////////////
	private final BroadcastReceiver mHandleMessageReceiver = new BroadcastReceiver() {
		@Override
		public void onReceive(Context context, Intent intent) {
			String newMessage = intent.getExtras().getString(EXTRA_MESSAGE);
			// Waking up mobile if it is sleeping
			WakeLocker.acquire(getApplicationContext());
			WakeLocker.release();
		}
	};

	@Override
	protected void onDestroy() {
		if (mRegisterTask != null) {
			mRegisterTask.cancel(true);
		}
		try {
			unregisterReceiver(mHandleMessageReceiver);
			GCMRegistrar.onDestroy(this);
		} catch (Exception e) {
			Log.e("UnRegister Receiver Error", "> " + e.getMessage());
		}
		super.onDestroy();
	}

	@Override
	public void onBackPressed() {
		Log.i("Back Pressed", "Back pressed OK");
		AlertDialogManager ad = new AlertDialogManager();
		ad.showAlertMessage(this, "", "Are you sure want to quit now?", true);
	}

	// -============Async task for keeping access
	// log=====================================================/
	public class serverAccessAsynTask extends
			AsyncTask<String, Integer, String> {
		ServerSideActivity ssa = new ServerSideActivity();

		@Override
		protected void onPreExecute() {
			super.onPreExecute();
		}

		@Override
		protected String doInBackground(String... url) {
			DisplayMetrics dms = new DisplayMetrics();
			getWindowManager().getDefaultDisplay().getMetrics(dms);

			ServerSideActivity ssa = new ServerSideActivity();
			RequiredUserInfo userinfo = new RequiredUserInfo();

			String HS_MANUFAC = userinfo
					.deviceMANUFACTURER(ShaboxBuddyMainActivity.this);
			AppConstant.HS_MANUFAC = HS_MANUFAC;
			String HS_MOD = userinfo.deviceModel(ShaboxBuddyMainActivity.this);
			AppConstant.HS_MOD = HS_MOD;
			String HS_DIM = dms.widthPixels + "x" + dms.heightPixels;
			AppConstant.HS_DIM = HS_DIM;
			String APN = "";
			String PORTAL_FULLnSHORT = "SB_GGL_AND_APP";
			String SERVICE_REQUEST = "Buddy:Home";
			String IP = "203.76.126.214";
			String OS = "";
			String ACCESS_ID = "";
			// 651
			TelephonyManager mngr = (TelephonyManager) getSystemService(Context.TELEPHONY_SERVICE);
			AppConstant.IMEI = mngr.getDeviceId().replace(" ", "%20");
			try {
				// This is the access log
				ssa.httpServerPost("", "SB_GGL_AND_APP", ResultMno, SERVICE_REQUEST, HS_MANUFAC, HS_MOD, HS_DIM, APN, PORTAL_FULLnSHORT, IP, OS);
				Log.d("Post", "HttpPost Executed");
				Log.d("Post", "Service Req" + SERVICE_REQUEST);
				Log.d("Post", "Manufac" + HS_MANUFAC);
				Log.d("Post", "HS_Mod" + HS_MOD);
				Log.d("Post", "OS" + OS);
				Log.d("Post", "IP" + IP);
				Log.d("Post", "HS_DIM" + HS_DIM);
				// BannerAd();

			} catch (Exception e) {
				Log.i("SERVER_POST_ERROR", "" + e.getMessage());
			}

			return null;
		}

		@Override
		protected void onPostExecute(String result) {

			Log.i("SERVER_POST_SUCCESS", "TRUE");
		}
	}

	// ==============Asyc task for MSISDN
	// tracking=================================/
	class detectMSISDNAsync extends AsyncTask<String, String, String> {

		@Override
		protected void onPreExecute() {
			super.onPreExecute();
			ShaboxBuddyMainActivity.ResultMno = "START";
		}

		@Override
		protected String doInBackground(String... aurl) {
			try {
				Thread.sleep(1000);

				Caller c = new Caller();
				// c.ad= c.ad;
				c.join();
				c.start();

				while (ShaboxBuddyMainActivity.ResultMno == "START") {
					try {
						Thread.sleep(100);
					} catch (Exception ex) {
					}
				}

				AppConstant.mno = ResultMno;
				number = AppConstant.mno;
				Log.d("Mobiles",AppConstant.mno);
			} catch (Exception ex) {

			}

			return null;

		}

		protected void onProgressUpdate(String... progress) {
			Log.d("ANDRO_ASYNC", progress[0]);
		}

		// ALL List POPUP Button setup here
		@Override
		protected void onPostExecute(String unused) {
			// progDialog1.dismiss();
			// viewPanel.setVisibility(View.VISIBLE);
			Log.i("WEBVIEW ", "True " + "VIEWED");
			new serverAccessAsynTask().execute("");

		}

	}

	/*
	 * //These function call from HTML UI. please go to end of the file
	 * index.html and see the javascript part public String HS_MANUFAC(){
	 * manufacture = Build.MANUFACTURER.replace(" ", "%20"); return manufacture;
	 * }
	 * 
	 * public String HS_BRAND(){ bbrand = Build.BRAND.replace(" ", "%20");
	 * return bbrand; }
	 * 
	 * public String HS_MOD(){ mmodel = Build.MODEL.replace(" ", "%20"); return
	 * mmodel; }
	 * 
	 * public String GetUserIP(){ ipAddress =
	 * internetConnection.getLocalIpAddress().replace(" ", "%20"); return
	 * ipAddress; }
	 * 
	 * 
	 * public String getIMEI(){ TelephonyManager mngr =
	 * (TelephonyManager)getSystemService(Context.TELEPHONY_SERVICE); IMEI =
	 * mngr.getDeviceId().replace(" ", "%20"); return IMEI; }
	 * 
	 * 
	 * public String HS_DIM(){ DisplayMetrics dms = new DisplayMetrics();
	 * getWindowManager().getDefaultDisplay().getMetrics(dms); dimWidth =
	 * dms.widthPixels; dimHeight = dms.heightPixels; return dimWidth + "x" +
	 * dimHeight; }
	 */

	@Override
	public void onResume() {
		super.onResume();
		// This code not used in Apps just check google play service, is it
		// Active?
		int status = GooglePlayServicesUtil
				.isGooglePlayServicesAvailable(getApplicationContext());

		if (status == ConnectionResult.SUCCESS) {
			System.out.println("IS GPLAY SERVICE SUCCESS ");
			// Success! Do what you want
		}

		GooglePlayServicesUtil
				.isGooglePlayServicesAvailable(getApplicationContext());
		System.out
				.println("IS GPLAY SERVICE: "
						+ GooglePlayServicesUtil
						.isGooglePlayServicesAvailable(getApplicationContext()));
		// For Mobile Apps Tracking SDK Integration
		// mobileAppTracker.measureSession();

		// for facebook tracking
		AppEventsLogger.activateApp(this); //========================Comment by Faisal
	}

	/**
	 * Flury API Intigration(Analytics)
	 */
	@Override
	protected void onStart() {
		super.onStart();
		/*ServiceQuery sq = new ServiceQuery(ShaboxBuddyMainActivity.this,
				getApplication());
		sq.startChecking();*/
		//FlurryAgent.onStartSession(context, "NYZVYPB6MGJ8PB5ZQ6T5");
	}


	/*
	 * (non-Javadoc)
	 * 
	 * @see android.app.Activity#onPause()
	 */

	@Override
	protected void onPause() {
		// TODO Auto-generated method stub
		super.onPause();
		AppEventsLogger.deactivateApp(this);
	}

	@Override
	protected void onStop() {
		super.onStop();
		//FlurryAgent.onEndSession(context);
	}

	private static View createTabView(Context context, String tabText) {
		View view = LayoutInflater.from(context).inflate(R.layout.custom_tab,
				null, false);
		TextView tv = (TextView) view.findViewById(R.id.tabTitleText);
		tv.setText(tabText);
		tv.setTypeface(null, Typeface.BOLD);
		return view;
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		MenuInflater inflater = getMenuInflater();
		inflater.inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle item selection
		switch (item.getItemId()) {
		case R.id.about:
			Intent aboutIntent = new Intent(getApplicationContext(),
					ServiceResultAbout.class);
			startActivity(aboutIntent);
			return true;

		case R.id.faq:
			Intent faqIntent = new Intent(getApplicationContext(),
					ServiceResultFaq.class);
			startActivity(faqIntent);
			return true;

		case R.id.help:
			Intent mIntent = new Intent(ShaboxBuddyMainActivity.this,
					ServiceResultHelp.class);
			startActivity(mIntent);
			return true;
		default:
			return super.onOptionsItemSelected(item);
		}
	}
//==============PushActivity action Method=====================
    public void pushactivityAction(String serviceid){

        TopCategories.service_Id=serviceid;

        ServiceQuery sq = new ServiceQuery(ShaboxBuddyMainActivity.this,
                getApplication());
				/*
				 * show the please wait dialog while msisdn still not detected
				 * but user click on the content
				 */

            if (sq.getMSISDN().equalsIgnoreCase("START")) {

                    sq.msisdnDetectionRunning(serviceid);
                    return;

            }


        // If WIFI and open MSISDN entry dialog
        if (sq.getMSISDN().equalsIgnoreCase("ERROR")) {
            // Toast.makeText(getApplicationContext(),sq.getMSISDN()+serviceID[position]
            // , Toast.LENGTH_SHORT).show();

            sq.entryMSISDN(serviceid);
            return;
        }

        sq.getServiceText(serviceid);
    }

	public class SendLaunchPushResponses extends AsyncTask<String, String, String>{

		RequiredUserInfo userinfo = new RequiredUserInfo();
		String HS_MANUFAC_ = userinfo.deviceMANUFACTURER(ShaboxBuddyMainActivity.this);
		String HS_MOD_ = userinfo.deviceModel(ShaboxBuddyMainActivity.this);
		String user_email=userinfo.userEmail(ShaboxBuddyMainActivity.this);

		Context context;
		String action;

		public SendLaunchPushResponses(String action1){

			action=action1;
		}

		@Override
		protected void onPreExecute() {
			super.onPreExecute();


//			resultMno = number;
//			if (resultMno.equalsIgnoreCase("ERROR")){
//
//				resultMno = "wifi";
//			}
//
//			Log.d("MNOO",resultMno);

			try {
				Thread.sleep(1000);

				Caller c = new Caller();
				// c.ad= c.ad;
				c.join();
				c.start();

				while (ShaboxBuddyMainActivity.ResultMno == "START") {
					try {
						Thread.sleep(100);
					} catch (Exception ex) {
					}
				}

				AppConstant.mno = ResultMno;
				//resultMno = AppConstant.mno;

				if (AppConstant.mno.equals("ERROR") || resultMno!=null) {
					resultMno = "wifi";
				} else {
					resultMno = AppConstant.mno;
				}
				Log.d("Mobilee",AppConstant.mno);
			} catch (Exception ex) {

			}

		}

		@Override
		protected String doInBackground(String... args) {

			List<NameValuePair> params = new ArrayList<NameValuePair>();
			params.add(new BasicNameValuePair("email",user_email));
			params.add(new BasicNameValuePair("action", action));
			params.add(new BasicNameValuePair("handset_name", HS_MANUFAC_));
			params.add(new BasicNameValuePair("handset_model", HS_MOD_));
			params.add(new BasicNameValuePair("msisdn",resultMno));

			// getting JSON Object
			// Note that create product url accepts POST method
			phpRequest.makeHttpRequest(pushResponseUrl, "POST", params);

			return null;
		}

		@Override
		protected void onPostExecute(String result) {
			// TODO Auto-generated method stub
			super.onPostExecute(result);

		}
	}


}
