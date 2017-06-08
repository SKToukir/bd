package com.vumobile.shaboxbuddy.webservice;

import android.app.AlertDialog;
import android.util.Log;

import com.vumobile.shaboxbuddy.ShaboxBuddyMainActivity;

public class CallerCheckExistUser extends Thread {
	
	public AlertDialog ad;
	public AlertDialog ad1;
	public CallSoap cs1;
	public String a,b;

	public void run()
	{
		try{
			cs1=new CallSoap();	
	        Log.i("CHGECK_NO","" + a);
			String resp1=cs1.Call(a); 	//Checking user is new or old
			ShaboxBuddyMainActivity.resultTopCategory= resp1;
			
		}catch(Exception ex){
			String errMsg = "Error";
			ShaboxBuddyMainActivity.resultTopCategory=errMsg.toString();	
		}
    }
}
