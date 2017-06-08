package com.vumobile.shaboxbuddy.webservice;
import android.util.Log;

import com.vumobile.shaboxbuddy.PushActivity;
import com.vumobile.shaboxbuddy.ShaboxBuddyMainActivity;

public class Caller  extends Thread 
{

	//public AlertDialog ad;
	public CallSoap cs;
	public int a,b;

	public void run()
	{
		try
		{
			cs = new CallSoap();
			//For geting Mobile Number or MSISDN Number
			String resp = cs.Call();
			ShaboxBuddyMainActivity.ResultMno = resp;
			PushActivity.resultMno=resp;
			Log.i("MNO","" + resp);
		}catch(Exception ex)
		{
			String errMsg = "Error";
			//ClubzAppExtend.rsltNumber=errMsg.toString();	
			PushActivity.resultMno=errMsg.toString();
		}

	
    }
}