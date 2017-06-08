package com.vumobile.shaboxbuddy.holder;

import java.util.Vector;

import com.vumobile.shaboxbuddy.model.AdBannerplayListModel;
import com.vumobile.shaboxbuddy.model.AdplayListModel;
import com.vumobile.shaboxbuddy.model.AdplayTextListModel;
import com.vumobile.shaboxbuddy.model.AdplayVideoListModel;

public class AllAdPlayTextList {

	public static Vector<AdplayTextListModel> adplayTextListModels = new Vector<AdplayTextListModel>();

	public static Vector<AdplayTextListModel> getAllAdplayTextList() {
		return adplayTextListModels;
	}

	public static void setAllAdplayTextList(Vector<AdplayTextListModel> adplayTextListModels) {
		AllAdPlayTextList.adplayTextListModels = adplayTextListModels;
	}

	public static AdplayTextListModel getAdplayTextList(int pos) {
		return adplayTextListModels.elementAt(pos);
	}

	public static void setAdplayTextList(AdplayTextListModel adplayTextListModels) {
		AllAdPlayTextList.adplayTextListModels.addElement(adplayTextListModels);
	}

	public static void removeAdplayTextList() {
		AllAdPlayTextList.adplayTextListModels.removeAllElements();
	}

}
