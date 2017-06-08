package com.vumobile.network;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;

import android.util.Log;

public class ServerSideActivity {
	
	//Method for http post to the server
	public void httpServerPost(
		  String url,
  		  String SOURCE_URL,
  		  String MSISDN,
  		  String UAPROFILE_URL,
  		  String HS_MANUFAC,
  		  String HS_MOD,
  		  String HS_DIM,
  		  String APN, 
  		  String PORTAL_FULLnSHORT,
  		  String IP,
  		  String OS)
    {
  	  
  	    StringBuilder total = new StringBuilder();
  	
		try {
            Log.e("Tracker", "This is Server side activity");
			
//			if(url.equals("success_log"))
//				url = "http://203.76.126.210/shaboxbuddy/user_success_log_shaboxbuddy.php";
//			else
			url = "http://203.76.126.210/shaboxbuddy/user_access_log_shaboxbuddy.php";
			
			HttpClient httpclient = new DefaultHttpClient();
			HttpPost httppost = new HttpPost(url);
			
			// Add your data
	        List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>(2);
	        nameValuePairs.add(new BasicNameValuePair("SOURCE_URL", SOURCE_URL));
	        nameValuePairs.add(new BasicNameValuePair("MNO", MSISDN));
	        nameValuePairs.add(new BasicNameValuePair("SERVICE_REQUEST", UAPROFILE_URL));
	        nameValuePairs.add(new BasicNameValuePair("HS_MANUFAC", HS_MANUFAC));
	        nameValuePairs.add(new BasicNameValuePair("email", SOURCE_URL));
	        nameValuePairs.add(new BasicNameValuePair("HS_MOD", HS_MOD));
	        nameValuePairs.add(new BasicNameValuePair("HS_DIM", "" + HS_DIM));
	        nameValuePairs.add(new BasicNameValuePair("APN", "" + APN));
	        nameValuePairs.add(new BasicNameValuePair("PORTAL_FULLnSHORT", "" + PORTAL_FULLnSHORT));
	        nameValuePairs.add(new BasicNameValuePair("IP", "" + IP));
	        nameValuePairs.add(new BasicNameValuePair("OS", "" + OS));
	       // nameValuePairs.add(new BasicNameValuePair("ACCESS_ID", "" + ACCESS_ID));
	        httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs));
            Log.d("Post ssaft","HttpPost Executed");
            Log.d("Post ssaft","Source URL"+SOURCE_URL);
            Log.d("Post ssaft","Manufac"+HS_MANUFAC);
            Log.d("Post ssaft","HS_Mod"+HS_MOD);
            Log.d("Post ssaft","OS"+OS);
            Log.d("Post ssaft","MSISDN"+MSISDN);
            Log.d("Post ssaft","HS_DIM"+HS_DIM);

	        // Execute HTTP Post Request
	        HttpResponse response = httpclient.execute(httppost);
	        
	        BufferedReader r = new BufferedReader(new InputStreamReader(response.getEntity().getContent()));

	        String line = null;

	        while ((line = r.readLine()) != null) {
	           total.append(line);
	        }
	        
	        //return total.toString();
	        
	        if(total.toString().trim().equals("1")){
	        	Log.i("Access successfully saved.","" + total.toString().trim());
	        }
	        
	        Log.i("RESPONSE"," " + total.toString().trim());
	        
	        
	    } catch (ClientProtocolException e) {
	        Log.d("Tracker","Exception in ssa"+e);
	    } catch (IOException e) {
            Log.d("Tracker","Exception in ssa"+e);
	    }
    }
	
	public void httpServerPostSuccessLog(
			  String url,
	  		  String SOURCE_URL,
	  		  String MSISDN,
	  		  String UAPROFILE_URL,
	  		  String HS_MANUFAC,
	  		  String HS_MOD,
	  		  String HS_DIM,
	  		  String APN, 
	  		  String PORTAL_FULLnSHORT,
	  		  String CMPAIN_KEY,
	  		  String OS)
	    {
	  	  
	  	    StringBuilder total = new StringBuilder();
	  	
			try {
                Log.e("Tracker", "This is server side activity httpserverpostsuccesslog");
				HttpClient httpclient = new DefaultHttpClient();
			    HttpPost httppost = new HttpPost("http://203.76.126.210/shaboxbuddy/user_success_log_shaboxbuddy.php");
				// Add your data
		    	
		        List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>(2);
		        nameValuePairs.add(new BasicNameValuePair("SOURCE_URL", SOURCE_URL));
		        nameValuePairs.add(new BasicNameValuePair("MNO", MSISDN));
		        nameValuePairs.add(new BasicNameValuePair("SERVICE_REQUEST", UAPROFILE_URL));
		        nameValuePairs.add(new BasicNameValuePair("HS_MANUFAC", HS_MANUFAC));
		        nameValuePairs.add(new BasicNameValuePair("email", SOURCE_URL));
		        nameValuePairs.add(new BasicNameValuePair("HS_MOD", HS_MOD));
		        nameValuePairs.add(new BasicNameValuePair("HS_DIM", "" + HS_DIM));
		        nameValuePairs.add(new BasicNameValuePair("APN", "" + APN));
		        nameValuePairs.add(new BasicNameValuePair("PORTAL_FULLnSHORT", "" + PORTAL_FULLnSHORT));
		        nameValuePairs.add(new BasicNameValuePair("CMPAIN_KEY", ""));
		        nameValuePairs.add(new BasicNameValuePair("OS", "" + OS));
		
		        httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs,"UTF-8"));

                Log.d("Post ssa","HttpPost Executed");
                Log.d("Post ssa","Source URL"+SOURCE_URL);
                Log.d("Post ssa","Manufac"+HS_MANUFAC);
                Log.d("Post ssa","HS_Mod"+HS_MOD);
                Log.d("Post ssa","OS"+OS);
                Log.d("Post ssa","MSISDN"+MSISDN);
                Log.d("Post ssa","HS_DIM"+HS_DIM);

		        // Execute HTTP Post Request
		        HttpResponse response = httpclient.execute(httppost);
		        
		        BufferedReader r = new BufferedReader(new InputStreamReader(response.getEntity().getContent()));

		        String line = null;

		        while ((line = r.readLine()) != null) {
		           total.append(line);
		        }
		        //return total.toString();
		        
		        if(total.toString().trim().equals("1")){
		        	Log.i("Access log successfully saved. ","" + total.toString().trim());
		        }
		        
		        Log.i("RESPONSE"," " + total.toString().trim());
		        
		        
		    } catch (ClientProtocolException e) {
		       Log.d("Tracker","Exception"+e);
		    } catch (IOException e) {
                Log.d("Tracker","Exception"+e);
		    }
	    }
}
