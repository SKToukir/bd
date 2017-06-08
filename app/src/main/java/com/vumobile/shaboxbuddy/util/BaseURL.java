package com.vumobile.shaboxbuddy.util;

import android.util.Log;

public class BaseURL {

	public static String HTTP = "http://izuera.com/admin/api/";


	/**
	 * @return the hTTP
	 */
	public static String makeHTTPURL(String param) {
		return HTTP + UrlUtils.encode(param);

	}

	/**
	 * @param hTTP
	 *            the hTTP to set
	 */
	public static void setHTTP(final String hTTP) {
		HTTP = hTTP;
        Log.i("Tracker", "This is BaseURL");
	}

}
