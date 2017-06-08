package com.vumobile.shaboxbuddy;

import android.app.Application;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.webkit.JavascriptInterface;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.vumobile.network.NetworkChecker;
import com.vumobile.shaboxbuddy.holder.AllAdBannerPlayList;
import com.vumobile.shaboxbuddy.model.AdBannerplayListModel;
import com.vumobile.shaboxbuddy.webservice.CallerAll;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class AppJavascriptInterface{
	
	Context mContext;
	Application mApp;
	String serviceText = "RUNNING";
	private Handler handler=new Handler();

	public static boolean stopLoadingState = false;
	public static Utilities utils;

	public static String CheckUserStatus = null;
	public static String SubscriberStatus = null;
	
	
	//Start For Like Button
	public NetworkChecker internetConnection = new NetworkChecker();
		public static int dimWidth;
		public static int dimHeight;	
		public static String mmodel;
		public static String manufacture = null;
		public static String bbrand = null;
		public static String ipAddress = null;
		public static String mmno = null;
		public static String IMEI = null;		
		//End For Like Button
	
	
	public static String serviceTextID = null;
	public static String banner_imgUrl = null;
	public static String banner_ReUrl = null;
	public static String serviceTextWeatherID = null;
	public static String result = null;	
	public static ProgressDialog progDialog, progressMnoDetecting,progPlainText;
	public static Dialog dialogEnterMSISDN, dialogPincode, dialogConfirmatin,dialogReConfirmatin; 
    
	AppJavascriptInterface(Context c, Application apps) {
        mContext = c;
        mApp = apps;
        Log.i("Tracker", "This is AdplayJavascripInterface");
        progDialog = new ProgressDialog(mContext);
        progDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        //@Anis
        progDialog.setMessage("VU JavaScript Loading...");
        //progDialog.setMessage("Loading...");
    }
    
	
    //Open music player from UI List
	@JavascriptInterface 
    public String getMSISDN(){
      //ShaboxBuddyMainActivity.ResultMno = "01611527806";
      return ShaboxBuddyMainActivity.ResultMno;
      //return "ERROR";
    }
    
    //Open music player from UI List
	@JavascriptInterface 
    public String getCategory(String contentID){
    	if(contentID.equals("5")){
    		return ShaboxBuddyMainActivity.resultTopCategory;
    	}else
    		return ShaboxBuddyMainActivity.resultMoreCategory;
    }
	@JavascriptInterface 
    public String getCategoryMore(String contentID){
    	return ShaboxBuddyMainActivity.resultMoreCategory;
    }
	@JavascriptInterface 
    public void droidMessage(String text){
    	Toast.makeText(mContext,"" + text, Toast.LENGTH_SHORT).show();
    	Log.i("RESULT ",""+ text);
    }
	@JavascriptInterface 
    public void showPanel(String text){
    	//mApp.viewPanel.setVisibility(View.VISIBLE);
    }
	@JavascriptInterface 
    public void getHelpPage(){
	  Intent mIntent = new Intent(mContext,ServiceResultHelp.class);
	  mContext.startActivity(mIntent);  
    }
    //=========Start For Like Button ===================/
    //These function call from HTML UI. please go to end of the file index.html and see the javascript part
	@JavascriptInterface 
    public String getMyValue(){
    	mmno = AppConstant.mno.replace(" ", "%20");
		return mmno;
    }
	@JavascriptInterface 
    public String HS_MANUFAC(){
    	manufacture =  Build.MANUFACTURER.replace(" ", "%20");
		return manufacture;
    }
	@JavascriptInterface 
    public String HS_BRAND(){
    	bbrand =  Build.BRAND.replace(" ", "%20");
		return bbrand;
    }
	@JavascriptInterface 
    public String HS_MOD(){
    	mmodel =  Build.MODEL.replace(" ", "%20");
		return mmodel;
    } 
	@JavascriptInterface 
    public String GetUserIP(){
    	ipAddress = internetConnection.getLocalIpAddress().replace(" ", "%20");
		return ipAddress;
    }
	@JavascriptInterface 
    public String getIMEI(){    	
    	IMEI = AppConstant.IMEI.replace(" ", "%20");
		return IMEI;
    }
	@JavascriptInterface 
    public String HS_DIM(){
    	return AppConstant.HS_DIM.replace(" ", "%20");
    }
    
    
    //=========End For Like Button ===================/
    
	@JavascriptInterface 
    public String getServiceText(String id,String wetherID)
    {
        Log.i("Tracker", "This is gerServiceText in appJavascriptInterface");
    	serviceTextID = id;
    	serviceTextWeatherID = wetherID;
    	Log.i("Waether ID", wetherID);
    	
    	//SubscriberStatus = "Y";
    	try{
    		if(serviceTextID.equals("27") && SubscriberStatus.equals("Y")){
    			new getValueAsyncTask().execute("GET_BUDDY_TOP_CHART_SERVICE_TEXT",serviceTextID,"");
    			return null;
    		}if(serviceTextID.equals("7") && SubscriberStatus.equals("Y")){
    			new getValueAsyncTask().execute("GET_BUDDY_SERVICE_TEXT",serviceTextID,"");
    			return null;
    		}else if(SubscriberStatus.equals("Y")){
    			//new getValueAsyncTask().execute("GET_BUDDY_SERVICE_TEXT",serviceTextID,"");
    			new getPlainServiceAsyncTask().execute(serviceTextID);
    			return null;
    		}
    		
    	}catch(Exception e){
    		Log.i("START_VIEW","" + e.getMessage());
    	}
		
    	//Toast.makeText(mContext,"SERVICE ID W " + serviceTextWeatherID, Toast.LENGTH_SHORT).show();
    	
    	Log.i("WEATHER_ID","" + serviceTextWeatherID);
    	
    	Utilities verifyMSISDN = new Utilities();
		ShaboxBuddyMainActivity.MSISDN = verifyMSISDN.msisdnVerification(ShaboxBuddyMainActivity.ResultMno);
    	new getValueAsyncTask().execute("CHECK_STATUS", ShaboxBuddyMainActivity.MSISDN, serviceTextID);
    	
    	return "";
    }
	@JavascriptInterface 
    public String getServiceText(){
    	//mApp.viewPanel.setVisibility(View.VISIBLE);
    	return serviceText;
    }
	@JavascriptInterface 
    public String getMsisdnSession(){
    	//mApp.viewPanel.setVisibility(View.VISIBLE);
    	return ShaboxBuddyMainActivity.ResultMno;
    }
	@JavascriptInterface 
    public String msisdnDetectionRunning(String id,String wetherID)
    {
    	progressMnoDetecting = new ProgressDialog(mContext);
        progressMnoDetecting.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progressMnoDetecting.setCancelable(true);
        progressMnoDetecting.setMessage("Please wait....");
        progressMnoDetecting.show();
        
    	serviceTextID = id;
    	serviceTextWeatherID = wetherID;
    	
    	handler.postDelayed(timedTask, 100);

    	return null;
    }
	@JavascriptInterface 
    public String entryMSISDN(String id,String wetherID){
    	serviceTextID = id;
    	serviceTextWeatherID = wetherID;
    	//mApp.viewPanel.setVisibility(View.VISIBLE);
    	dialogEnterMSISDN = new Dialog(mContext,R.style.MyDialog);
		dialogEnterMSISDN.setContentView(R.layout.msisdn_form);
		dialogEnterMSISDN.setCanceledOnTouchOutside(true);
		Button btnSubmit = (Button) dialogEnterMSISDN.findViewById(R.id.btnOk);
		final EditText txtMNO = (EditText) dialogEnterMSISDN.findViewById(R.id.txtMno);
		AppConstant.mno_wifi=txtMNO.getText().toString().trim();
		btnSubmit.setOnClickListener(new OnClickListener() {
			public void onClick(View v) 
			{
				ShaboxBuddyMainActivity.MSISDN = "" + txtMNO.getText();
				new getValueAsyncTask().execute(ShaboxBuddyMainActivity.tag_submit_msisdn, ShaboxBuddyMainActivity.MSISDN, "");			
			}
	    });
		
		dialogEnterMSISDN.show();
		
    	return null;
    }
    public class getValueAsyncTask extends AsyncTask<String, Integer, String>
	{	
		@Override
	    protected void onPreExecute(){
	        super.onPreExecute();
	        
	        progDialog = new ProgressDialog(mContext);
	        progDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
	        progDialog.setCancelable(true);
	        progDialog.setMessage("Loading...");
	        progDialog.show();
	    }
		
	    @Override
		protected String doInBackground(String... url) 
		{	
	    	String contentID = url[0];
	    	String currentProcess = null;
	 
    		try{
    			currentProcess = url[0];
				Thread.sleep(1000);
	    		CallerAll ws = new CallerAll();
	    		result = "RUNNING";
		    	ws.serviceID = url[1]; 	// MSISDN or Service Text Id
		    	ws.criteria = url[0]; 	// Process name
		    	ws.pin = url[2]; 		// no need to pass pin code
				ws.join();
				ws.start();
			
				while(result=="RUNNING")
			    {
			    	try{
			    		Thread.sleep(100);
			    	}catch(Exception ex){
			    	}
			    }
				
			   //Log.i("Charging Status","" + result);
			}catch(Exception e){
				Log.d("Background Task", e.toString());
			}
	    		
			return url[0];
		}
			
		@Override
		protected void onPostExecute(String processResult){
			progDialog.dismiss();
			serviceText = result;
			Log.d("TEXT", result);
            //=====================Check the Operator for set the Operator wise content============
            TelephonyManager tm = (TelephonyManager)mContext.getSystemService(Context.TELEPHONY_SERVICE);
            String number = tm.getNetworkOperatorName().toLowerCase();
            Log.e("sim",number);



            try
			{
				if(processResult.equals(ShaboxBuddyMainActivity.tag_submit_msisdn) && result.equals("1"))
				{
					dialogEnterMSISDN.dismiss();
					
					dialogPincode = new Dialog(mContext,R.style.MyDialog);
					dialogPincode.setContentView(R.layout.pincode_form);
					dialogPincode.setCanceledOnTouchOutside(false);
					Button btnSubmit = (Button) dialogPincode.findViewById(R.id.btnOk);
					TextView txtClickHere = (TextView) dialogPincode.findViewById(R.id.btn_click_here);
					final EditText txtPincode = (EditText) dialogPincode.findViewById(R.id.txtPinCode);
					
					btnSubmit.setOnClickListener(new OnClickListener() {
						public void onClick(View v) 
						{
							dialogEnterMSISDN.dismiss();
							txtPincode.getText();
							Utilities verifyMSISDN = new Utilities();
							ShaboxBuddyMainActivity.MSISDN = verifyMSISDN.msisdnVerification(ShaboxBuddyMainActivity.MSISDN);
							new getValueAsyncTask().execute("SUBMIT_PINCODE",ShaboxBuddyMainActivity.MSISDN,"" + txtPincode.getText());			
						}
				    });
					
					txtClickHere.setOnClickListener(new OnClickListener() {
						public void onClick(View v) 
						{
							try{
								dialogEnterMSISDN.dismiss();
								Utilities verifyMSISDN = new Utilities();
								ShaboxBuddyMainActivity.MSISDN = verifyMSISDN.msisdnVerification(ShaboxBuddyMainActivity.MSISDN);
								new getValueAsyncTask().execute("FORGOT_PIN_CODE",ShaboxBuddyMainActivity.MSISDN,"" + txtPincode.getText().toString());
							}catch(Exception e){
								Log.i("Exception Forget Pin code","" + e.getMessage());
							}
							
						}
					});
					
					dialogPincode.show();
					
				}else if(processResult.equals(ShaboxBuddyMainActivity.tag_submit_pin) && result.equals("1"))
				{
					//dialogEnterMSISDN.dismiss();
					dialogPincode.dismiss();
					//Toast.makeText(mContext,"Successfully completed", Toast.LENGTH_SHORT).show();
					Utilities verifyMSISDN = new Utilities();
					ShaboxBuddyMainActivity.MSISDN = verifyMSISDN.msisdnVerification(ShaboxBuddyMainActivity.MSISDN);
					ShaboxBuddyMainActivity.ResultMno = ShaboxBuddyMainActivity.MSISDN;
					new getValueAsyncTask().execute("CHECK_STATUS",ShaboxBuddyMainActivity.MSISDN,serviceTextID);
			    	
				}else if(processResult.equals("CHECK_STATUS") && result.equals("0"))
				{

					//dialog.dismiss();
                    try{
                        dialogConfirmatin = new Dialog(mContext,R.style.MyDialog);
                        dialogConfirmatin.setContentView(R.layout.confirmation);
                        dialogConfirmatin.setCanceledOnTouchOutside(false);
                        Button btnRegister = (Button) dialogConfirmatin.findViewById(R.id.btnRegister);





                        dialogReConfirmatin = new Dialog(mContext,R.style.MyDialog);
                        dialogReConfirmatin.setContentView(R.layout.re_confirmation);
                        dialogReConfirmatin.setCanceledOnTouchOutside(false);
                        final Button reconfirn_btnRegister = (Button) dialogReConfirmatin.findViewById(R.id.reconfirm_btnRegister);
                        //TextView txtClickHere = (TextView) dialog.findViewById(R.id.);
                        //final EditText txtPincode = (EditText) dialog.findViewById(R.id.txtPinCode);
                        btnRegister.setOnClickListener(new OnClickListener() {
                            public void onClick(View v)
                            {

                                dialogReConfirmatin.show();
                                dialogConfirmatin.dismiss();
                                reconfirn_btnRegister.setOnClickListener(new OnClickListener() {

                                    @Override
                                    public void onClick(View v) {
                                        // TODO Auto-generated method stub
                                        Utilities verifyMSISDN = new Utilities();
                                        ShaboxBuddyMainActivity.MSISDN = verifyMSISDN.msisdnVerification(ShaboxBuddyMainActivity.MSISDN);
                                        new getValueAsyncTask().execute("GET_SUBSCRIBER_STATUS",ShaboxBuddyMainActivity.MSISDN,serviceTextID);
                                        dialogReConfirmatin.dismiss();
                                    }
                                });


                            }
                        });

                        dialogConfirmatin.setOnCancelListener(new DialogInterface.OnCancelListener() {
                            @Override
                            public void onCancel(DialogInterface dialog) {
                                //mContext.dialog1.dismiss();
                            }
                        });

                        dialogConfirmatin.show();

                    }catch(Exception e){
                        Log.i("Dilog Exception","" + e.getMessage());
                    }
					
			    	
				}else if(processResult.equals("CHECK_STATUS") && result.equals("1"))
				{
					//dialog.dismiss();
					CheckUserStatus = "1";
					Utilities verifyMSISDN = new Utilities();
					ShaboxBuddyMainActivity.MSISDN = verifyMSISDN.msisdnVerification(ShaboxBuddyMainActivity.MSISDN);
					
					SubscriberStatus = "Y";
					if(serviceTextID.equals("27")){
						new getValueAsyncTask().execute("GET_BUDDY_TOP_CHART_SERVICE_TEXT",serviceTextID,"");
						
					}else if(serviceTextID.equals("7")){
						
						Intent intent = new Intent(mContext,WeatherListActivity.class);
				    	Bundle cShufflePlaylist = new Bundle();
//				    	cShufflePlaylist.putString("ServiceResult", result);
//				    	cShufflePlaylist.putString("ServiceResultWeatherId", serviceTextWeatherID);
//				    	
//
//			    	    intent.putExtras(cShufflePlaylist);
			    	    mContext.startActivity(intent);
			    	    
					}else{
						//new getValueAsyncTask().execute("GET_BUDDY_SERVICE_TEXT",serviceTextID,"");
						new  getPlainServiceAsyncTask().execute(serviceTextID);
					}
						
		    	
				}else if(processResult.equals("GET_SUBSCRIBER_STATUS") && result.equals("Y"))
				{
					SubscriberStatus = "Y";
					if(serviceTextID.equals("27")){
						new getValueAsyncTask().execute("GET_BUDDY_TOP_CHART_SERVICE_TEXT",serviceTextID,"");
					}else
						new getValueAsyncTask().execute("GET_BUDDY_SERVICE_TEXT",serviceTextID,"");
					
				}else if(processResult.equals("GET_SUBSCRIBER_STATUS") && result.equals("N")){
					//Toast.makeText(mContext," " + result, Toast.LENGTH_SHORT).show();
					Toast.makeText(mContext,"Service cannot perform due to insufficient balance", Toast.LENGTH_SHORT).show();
					
				}else if(processResult.equals("GET_BUDDY_SERVICE_TEXT"))
				{
					if(serviceTextID.equals("7")){
						
						Intent intent = new Intent(mContext,WeatherListActivity.class);
//				    	Bundle cShufflePlaylist = new Bundle();
//				    	cShufflePlaylist.putString("ServiceResult", result);
//				    	cShufflePlaylist.putString("ServiceResultWeatherId", serviceTextWeatherID);
//			    	    intent.putExtras(cShufflePlaylist);
			    	    mContext.startActivity(intent);
			    	    
					}else{
						
						// Using http post
						new getPlainServiceAsyncTask().execute(serviceTextID);
					}
					
				}else if(processResult.equals("GET_BUDDY_TOP_CHART_SERVICE_TEXT"))
				{
						Intent intent = new Intent(mContext,ServiceResultTopChart.class);
				    	Bundle bandle = new Bundle();
				    	bandle.putString("ServiceResultTopChart", result);
			    	    intent.putExtras(bandle);
			    	    mContext.startActivity(intent);
					
				}else{
					Toast.makeText(mContext," " + result, Toast.LENGTH_SHORT).show();
				}
					
					
			}catch(Exception e){
				Toast.makeText(mContext,result + " For " + processResult + "  " + e.getMessage(), Toast.LENGTH_SHORT).show();
				Log.i("XCEPTION ON POST","" + result + " For " + processResult + "  " + e.getMessage());
			}
			//Toast.makeText(mContext,"" + result, Toast.LENGTH_SHORT).show();
			
        }
	 }
    
    
 
    
    
    
    
    
   
	 
	 class getPlainServiceAsyncTask extends AsyncTask<String, String, String> {
		 
		    Bitmap bitmap = null;
		    String haha;
		    
			@Override
			protected void onPreExecute() {
				super.onPreExecute();
				progPlainText = new ProgressDialog(mContext);
				progPlainText.setProgressStyle(ProgressDialog.STYLE_SPINNER);
				progPlainText.setCancelable(true);
				progPlainText.setMessage("Loading....");
				progPlainText.show();
				
			}

			@Override
			protected String doInBackground(String... aurl) 
			{	
				StringBuilder total = new StringBuilder();
				
				try {
					
					HttpClient httpclient = new DefaultHttpClient();
				    HttpPost httppost = new HttpPost("http://203.76.126.210/shaboxbuddy/index.php");
                    Log.d("httppost","appjavascriptinterface in getPlainserver asycktask");
					// Add your data
			    	
			        List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>(2);
			        nameValuePairs.add(new BasicNameValuePair("ServiceId", "" + aurl[0]));
haha=aurl[0];
			        httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs));

			        // Execute HTTP Post Request
			        HttpResponse response = httpclient.execute(httppost);
			        
			        BufferedReader r = new BufferedReader(new InputStreamReader(response.getEntity().getContent()));

			        String line = null;

			        while ((line = r.readLine()) != null) {
			           total.append(line);
			        }
			        
			        //return total.toString();
			        
			        if(total.toString().trim().equals("1")){
			        	Log.i("You cross your streaming limit.","Message");
			        }
			        
			        Log.i("RESPONSE"," " + total.toString().trim());
			        
			        
			    } catch (Exception e) {
			    	Log.i("Error TEXT LOAD","" + e.getMessage());
			    	return "Error in loading. Please try again";
			        // TODO Auto-generated catch block
			    }
				   
				return total.toString().trim();
				 
			}
			protected void onProgressUpdate(String... progress) {
				 Log.d("ANDRO_ASYNC",progress[0]);	
			}
			@Override
			protected void onPostExecute(String result) 
			{
				//textResult.setText(""+ result);
				progPlainText.dismiss();
				Toast.makeText(mContext,haha,Toast.LENGTH_LONG).show();
				
				try
				{
					
					if(progDialog.isShowing()){
						progDialog.dismiss();
					}
					
					if(dialogEnterMSISDN.isShowing()){
						dialogEnterMSISDN.dismiss();
					}
					
				}catch(Exception e){
					
				}
			
				if(serviceTextID.equals("7")){
					
					Intent intent = new Intent(mContext,WeatherListActivity.class);
//			    	Bundle cShufflePlaylist = new Bundle();
//			    	cShufflePlaylist.putString("ServiceResult", result);
//			    	cShufflePlaylist.putString("ServiceResultWeatherId", serviceTextWeatherID);
//		    	    intent.putExtras(cShufflePlaylist);
		    	    mContext.startActivity(intent);
		    	    return;
				}else if(serviceTextID.equals("44")){
					Intent intent = new Intent(mContext,FifaSeviceResult.class);
			    	Bundle valuesBundle = new Bundle();
			    	valuesBundle.putString("ServiceResult", result);
		    	    intent.putExtras(valuesBundle);
		    	    mContext.startActivity(intent);
				}else {
					Intent intent = new Intent(mContext,SeviceResult.class);
			    	Bundle valuesBundle = new Bundle();
			    	valuesBundle.putString("ServiceResult", result);
		    	    intent.putExtras(valuesBundle);
		    	    mContext.startActivity(intent);
				}
				
			
				
			}

		}
	 
	    public Runnable timedTask = new Runnable(){
			  @Override	
			  public void run(){
				  
				  try{
		    		
						if(!ShaboxBuddyMainActivity.ResultMno.equals("START")){
							
							progressMnoDetecting.dismiss();
							handler.removeCallbacks(timedTask);
							
							if(ShaboxBuddyMainActivity.ResultMno.equals("ERROR")){
								entryMSISDN(serviceTextID, serviceTextWeatherID);
							}else{
								getServiceText(serviceTextID,serviceTextWeatherID);
							}
							
						}else{
							
							handler.postDelayed(timedTask, 100);
						}
						
						
					}catch(Exception e){
						Log.d("Background Task", e.toString());
					}
				  
				  
		       }
			  
		 };
		 @JavascriptInterface 
		  public String getBannerImage(){
			  final AdBannerplayListModel model = AllAdBannerPlayList.getAdBannerplayList(0);
//				imgUrl = "http://202.164.213.242/CMS/UIHeader/D480x800/Shabox_Header.png";
				banner_imgUrl=model.getBannaerImg();
				banner_imgUrl =  banner_imgUrl.replace(" ", "%20");
				return banner_imgUrl;
		    } 
		  @JavascriptInterface 
		  public String getBannerReUrl(){
			  final AdBannerplayListModel model = AllAdBannerPlayList.getAdBannerplayList(0);
//				imgUrl = "http://202.164.213.242/CMS/UIHeader/D480x800/Shabox_Header.png";
			  banner_ReUrl=model.getURL();
			  banner_ReUrl =  banner_ReUrl.replace(" ", "%20");
				return banner_ReUrl;
		    } 
     
}
