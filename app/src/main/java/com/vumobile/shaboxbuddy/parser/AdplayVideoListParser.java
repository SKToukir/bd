package com.vumobile.shaboxbuddy.parser;

import java.io.IOException;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.content.Context;
import android.util.Log;

import com.vumobile.shaboxbuddy.holder.AllAdBannerPlayList;
import com.vumobile.shaboxbuddy.holder.AllAdPlayList;
import com.vumobile.shaboxbuddy.holder.AllAdPlayVideoList;
import com.vumobile.shaboxbuddy.model.AdBannerplayListModel;
import com.vumobile.shaboxbuddy.model.AdplayListModel;
import com.vumobile.shaboxbuddy.model.AdplayVideoListModel;

public class AdplayVideoListParser {
	public static boolean connect(Context con, String result)
			throws JSONException, IOException {

		AllAdPlayVideoList.removeAdplayVideoList();
		if (result.length() < 1) {
			return false;

		}

		final JSONObject mainJsonObject = new JSONObject(result);
		Log.w("respons Jeson", ""+mainJsonObject);
        Log.e("Tracker", "This addplayVideoListparser");

//		final JSONObject banner = mainJsonObject.getJSONObject("banner");
//		final JSONArray banner_list = mainJsonObject.getJSONArray(result);

		AdplayVideoListModel adplayVideoListModel;
//		for (int i = 0; i < mainJsonObject.length(); i++) {
//			JSONObject banner_list_jsonObject = mainJsonObject.getJSONObject(i);

			adplayVideoListModel = new AdplayVideoListModel();
			AllAdPlayVideoList allAdPlayVideoList = new AllAdPlayVideoList();
			adplayVideoListModel.setLink(mainJsonObject.getString("link"));
			adplayVideoListModel.setImgsrc(mainJsonObject.getString("imgsrc"));
			adplayVideoListModel.setWidth(mainJsonObject.getString("width"));
			adplayVideoListModel.setVdlink(mainJsonObject.getString("vdlink"));
			adplayVideoListModel.setTitle(mainJsonObject.getString("title"));
			adplayVideoListModel.setNavurl(mainJsonObject.getString("navurl"));
	
			Log.w("respons BannaerImg", ""+mainJsonObject.getString("navurl"));
			
			AllAdPlayVideoList.setAdplayVideoList(adplayVideoListModel);
			adplayVideoListModel = null;
//		}

		
		return true;
	}
}
