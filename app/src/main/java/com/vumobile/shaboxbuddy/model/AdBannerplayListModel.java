package com.vumobile.shaboxbuddy.model;

import android.util.Log;

public class AdBannerplayListModel {

	private String AppID;
	private String URL;
	private String BannaerImg;
	public String getAppID() {
		return AppID;
	}
	public void setAppID(String appID) {
		AppID = appID;
	}
	public String getURL() {
		return URL;
	}
	public void setURL(String uRL) {
		URL = uRL;
	}
	public String getBannaerImg() {
		return BannaerImg;
	}
	public void setBannaerImg(String bannaerImg) {
		BannaerImg = bannaerImg;
	}
	@Override
	public String toString() {
        Log.e("Tracker", "This is  Add Banner Play list model");
		return "AdplayListModel [AppID=" + AppID + ", URL=" + URL
				+ ", BannaerImg=" + BannaerImg + "]";
	}
	



}
