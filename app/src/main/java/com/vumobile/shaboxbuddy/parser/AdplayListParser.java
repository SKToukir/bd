package com.vumobile.shaboxbuddy.parser;

import java.io.IOException;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.content.Context;
import android.util.Log;

import com.vumobile.shaboxbuddy.holder.AllAdPlayList;
import com.vumobile.shaboxbuddy.model.AdplayListModel;

public class AdplayListParser {
	public static boolean connect(Context con, String result)
			throws JSONException, IOException {

		AllAdPlayList.removeAdplayList();
		if (result.length() < 1) {
			return false;

		}

	
		final JSONObject mainJsonObject = new JSONObject(result);
		Log.w("respons Jeson", ""+mainJsonObject);
        Log.e("Tracker", "This addplayListparser");

//		final JSONObject banner = mainJsonObject.getJSONObject("banner");
//		final JSONArray banner_list = mainJsonObject.getJSONArray(result);

		AdplayListModel adplayListModel;
//		for (int i = 0; i < mainJsonObject.length(); i++) {
//			JSONObject banner_list_jsonObject = mainJsonObject.getJSONObject(i);

			adplayListModel = new AdplayListModel();
			AllAdPlayList allAdPlayList = new AllAdPlayList();
			adplayListModel.setLink(mainJsonObject.getString("link"));
			adplayListModel.setImgsrc(mainJsonObject.getString("imgsrc"));
			adplayListModel.setWidth(mainJsonObject.getString("width"));
			adplayListModel.setVdlink(mainJsonObject.getString("vdlink"));
			adplayListModel.setTitle(mainJsonObject.getString("title"));
			adplayListModel.setNavurl(mainJsonObject.getString("navurl"));
			
			AllAdPlayList.setAdplayList(adplayListModel);
        Log.e("Tracker", "This addBannerplayListparser");
			adplayListModel = null;
//		}

		
		return true;
	}
}
