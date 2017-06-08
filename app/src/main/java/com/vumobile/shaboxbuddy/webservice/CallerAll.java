package com.vumobile.shaboxbuddy.webservice;

import android.util.Log;

import com.vumobile.shaboxbuddy.ServiceQuery;
import com.vumobile.shaboxbuddy.ShaboxBuddyMainActivity;

public class CallerAll extends Thread {
	public CallSoap cs;
	public String serviceID, criteria;
	public String pin;

	public void run() {
		try {
			cs = new CallSoap();
			
			
			String resp = cs.Call_WS(serviceID, criteria, pin);
            Log.d("Tracker","Caller All run");
			
			if (criteria.equals("GET_BUDDY_SERVICE_TEXT")
					|| criteria.equals("SUBMIT_MSISDN")
					|| criteria.equals("SUBMIT_PINCODE")
					|| criteria.equals("FORGOT_PIN_CODE")
					|| criteria.equals("GET_SUBSCRIBER_STATUS")
					|| criteria.equals("CHECK_STATUS")
					|| criteria.equals("UN_SUBSCRIBE_ROBI_DAILY")
					|| criteria.equals("UN_SUBSCRIBE_ROBI_WEEKLY")
					
					|| criteria.equals("GET_SUBSCRIBER_STATUS_ROBI_DAILY")
					|| criteria.equals("GET_SUBSCRIBER_STATUS_ROBI_WEEKLY")
					
					|| criteria.equals("GET_BUDDY_TOP_CHART_SERVICE_TEXT")) {
				
				ServiceQuery.result = resp;
				
			} else {
				if (serviceID.equals("6")) {
					ShaboxBuddyMainActivity.resultMoreCategory = resp;
				} else {
					ShaboxBuddyMainActivity.resultTopCategory = resp;
				}

			}

			Log.i("RUNNING_RESULT", "" + resp);
		} catch (Exception ex) {
			String errMsg = "Error";
			ShaboxBuddyMainActivity.resultTopCategory = errMsg.toString();
		}
	}
}