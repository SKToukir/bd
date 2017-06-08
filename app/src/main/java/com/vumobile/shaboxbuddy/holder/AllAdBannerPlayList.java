package com.vumobile.shaboxbuddy.holder;

import android.util.Log;

import java.util.Vector;

import com.vumobile.shaboxbuddy.model.AdBannerplayListModel;
import com.vumobile.shaboxbuddy.model.AdplayListModel;

public class AllAdBannerPlayList {

    public static Vector<AdBannerplayListModel> adBannerplayListModels = new Vector<AdBannerplayListModel>();

    public static Vector<AdBannerplayListModel> getAllAdBannerplayList() {
        Log.e("Tracker", "This is addbanner playlist in APP controller");
        return adBannerplayListModels;
	}

	public static void setAllAdBannerplayList(Vector<AdBannerplayListModel> adBannerplayListModels) {
		AllAdBannerPlayList.adBannerplayListModels = adBannerplayListModels;
	}

	public static AdBannerplayListModel getAdBannerplayList(int pos) {
		return adBannerplayListModels.elementAt(pos);
	}

	public static void setAdBannerplayList(AdBannerplayListModel adBannerplayListModels) {
		AllAdBannerPlayList.adBannerplayListModels.addElement(adBannerplayListModels);
	}

	public static void removeAdBannerplayList() {
		AllAdBannerPlayList.adBannerplayListModels.removeAllElements();
	}

}
