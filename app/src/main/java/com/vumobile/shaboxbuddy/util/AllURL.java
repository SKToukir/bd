package com.vumobile.shaboxbuddy.util;

import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;

import android.util.Log;

public class AllURL {

	private static String getcommonURLWithParamAndAction(String action,
			Vector<KeyValue> params) {

		if (params == null || params.size() == 0) {
			return BaseURL.HTTP + action;
		} else {
			String pString = "";

			for (final KeyValue obj : params) {

				pString += obj.getKey().trim() + "=" + obj.getValue().trim()
						+ "&";
			}

			if (pString.endsWith("&")) {
				pString = pString.subSequence(0, pString.length() - 1)
						.toString();
			}

			return BaseURL.HTTP + action + "?" + UrlUtils.encode(pString);
		}

	}

	
	public static HTTPPostHelper sendDownloadLog(String Portal, String Page,
			String URL, String CategoryCode_, String CategoryTitle,
			String ContentCode,String ContentTitle_ ) {
        Log.i("Tracker","This is HTTPPostHelper");
		String join_BaseUrl = "http://203.76.126.210/clubz_android/download_log.php";
		final List<NameValuePair> nvps = new ArrayList<NameValuePair>();
		nvps.add(new BasicNameValuePair("Portal", "" + Portal));
		nvps.add(new BasicNameValuePair("Page", "" + Page));
		nvps.add(new BasicNameValuePair("URL", "" + URL));
		nvps.add(new BasicNameValuePair("CategoryCode_", "" + CategoryCode_));
		nvps.add(new BasicNameValuePair("CategoryTitle", "" + CategoryTitle));
		nvps.add(new BasicNameValuePair("ContentCode", "" + ContentCode));
		nvps.add(new BasicNameValuePair("ContentTitle_", "" + ContentTitle_));
		Log.i("ContentTitle_", ContentTitle_);

		final HTTPPostHelper helper = new HTTPPostHelper(join_BaseUrl, nvps);
		return helper;

	}

	
}
