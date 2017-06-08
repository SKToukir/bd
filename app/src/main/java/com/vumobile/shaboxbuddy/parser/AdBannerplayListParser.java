package com.vumobile.shaboxbuddy.parser;

import java.io.IOException;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.content.Context;
import android.util.Log;

import com.vumobile.shaboxbuddy.holder.AllAdBannerPlayList;
import com.vumobile.shaboxbuddy.holder.AllAdPlayList;
import com.vumobile.shaboxbuddy.model.AdBannerplayListModel;
import com.vumobile.shaboxbuddy.model.AdplayListModel;

public class AdBannerplayListParser {
	public static boolean connect(Context con, String result)
			throws JSONException, IOException {

		AllAdBannerPlayList.removeAdBannerplayList();
		if (result.length() < 1) {
			return false;

		}

		final JSONArray mainJsonObject = new JSONArray(result);
		Log.w("respons Jeson", ""+mainJsonObject);
        Log.e("Tracker", "This addBannerplayListparser");

//		final JSONObject banner = mainJsonObject.getJSONObject("banner");
//		final JSONArray banner_list = mainJsonObject.getJSONArray(result);

		AdBannerplayListModel adBannerplayListModel;
		for (int i = 0; i < mainJsonObject.length(); i++) {
			JSONObject banner_list_jsonObject = mainJsonObject.getJSONObject(i);

			adBannerplayListModel = new AdBannerplayListModel();
			AllAdBannerPlayList adBannerPlayList = new AllAdBannerPlayList();
			adBannerplayListModel.setAppID(banner_list_jsonObject.getString("AppID"));
			adBannerplayListModel.setURL(banner_list_jsonObject.getString("URL"));
			adBannerplayListModel.setBannaerImg(banner_list_jsonObject.getString("BannaerImg"));
			Log.w("respons AppID", ""+banner_list_jsonObject.getString("AppID"));
			Log.w("respons URL", ""+banner_list_jsonObject.getString("URL"));
			Log.w("respons BannaerImg", ""+banner_list_jsonObject.getString("BannaerImg"));
            Log.e("Tracker", "This addBannerplayListparser");
			
			AllAdBannerPlayList.setAdBannerplayList(adBannerplayListModel);
			adBannerplayListModel = null;
		}

		
		return true;
	}
}
