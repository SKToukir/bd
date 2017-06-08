package com.vumobile.shaboxbuddy;

import android.util.Log;

public class Utilities {
	
	public String msisdnVerification(String msisdn)
	{
		if(msisdn.length()!=13 && msisdn.length()==11){
			msisdn = "88" + msisdn;
            Log.i("Tracker", "This is Utilities");
		}
		
		return msisdn;
	}
}
