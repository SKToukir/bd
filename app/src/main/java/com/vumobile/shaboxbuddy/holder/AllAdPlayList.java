package com.vumobile.shaboxbuddy.holder;

import java.util.Vector;

import com.vumobile.shaboxbuddy.model.AdplayListModel;

public class AllAdPlayList {

	public static Vector<AdplayListModel> adplayListModels = new Vector<AdplayListModel>();

	public static Vector<AdplayListModel> getAllAdplayList() {
		return adplayListModels;
	}

	public static void setAllAdplayList(Vector<AdplayListModel> adplayListModels) {
		AllAdPlayList.adplayListModels = adplayListModels;
	}

	public static AdplayListModel getAdplayList(int pos) {
		return adplayListModels.elementAt(pos);
	}

	public static void setAdplayList(AdplayListModel adplayListModels) {
		AllAdPlayList.adplayListModels.addElement(adplayListModels);
	}

	public static void removeAdplayList() {
		AllAdPlayList.adplayListModels.removeAllElements();
	}

}
