package com.vumobile.shaboxbuddy.holder;

import java.util.Vector;

import com.vumobile.shaboxbuddy.model.AdBannerplayListModel;
import com.vumobile.shaboxbuddy.model.AdplayListModel;
import com.vumobile.shaboxbuddy.model.AdplayVideoListModel;

public class AllAdPlayVideoList {

	public static Vector<AdplayVideoListModel> adplayVideoListModels = new Vector<AdplayVideoListModel>();

	public static Vector<AdplayVideoListModel> getAllAdplayVideoList() {
		return adplayVideoListModels;
	}

	public static void setAllAdplayVideoList(Vector<AdplayVideoListModel> adplayVideoListModels) {
		AllAdPlayVideoList.adplayVideoListModels = adplayVideoListModels;
	}

	public static AdplayVideoListModel getAdplayVideoList(int pos) {
		return adplayVideoListModels.elementAt(pos);
	}

	public static void setAdplayVideoList(AdplayVideoListModel adplayVideoListModels) {
		AllAdPlayVideoList.adplayVideoListModels.addElement(adplayVideoListModels);
	}

	public static void removeAdplayVideoList() {
		AllAdPlayVideoList.adplayVideoListModels.removeAllElements();
	}

}
