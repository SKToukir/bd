package com.vumobile.shaboxbuddy.parser;

import java.io.IOException;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.content.Context;
import android.util.Log;

import com.vumobile.shaboxbuddy.holder.AllAdBannerPlayList;
import com.vumobile.shaboxbuddy.holder.AllAdPlayList;
import com.vumobile.shaboxbuddy.holder.AllAdPlayTextList;
import com.vumobile.shaboxbuddy.holder.AllAdPlayVideoList;
import com.vumobile.shaboxbuddy.model.AdBannerplayListModel;
import com.vumobile.shaboxbuddy.model.AdplayListModel;
import com.vumobile.shaboxbuddy.model.AdplayTextListModel;
import com.vumobile.shaboxbuddy.model.AdplayVideoListModel;

public class AdplayTextListParser {
	public static boolean connect(Context con, String result)throws JSONException, IOException {

		AllAdPlayTextList.removeAdplayTextList();
		if (result.length() < 1) {
			return false;

		}

		final JSONArray mainJsonObject = new JSONArray(result);
		Log.w("respons Jeson", ""+mainJsonObject);
        Log.e("Tracker", "This addplayTextListparser");

//		final JSONObject banner = mainJsonObject.getJSONObject("banner");
//		final JSONArray banner_list = mainJsonObject.getJSONArray(result);

		AdplayTextListModel adplayTextListModel;
		for (int i = 0; i < mainJsonObject.length(); i++) {
			JSONObject banner_list_jsonObject = mainJsonObject.getJSONObject(i);

			adplayTextListModel = new AdplayTextListModel();
			AllAdPlayTextList adPlayTextList = new AllAdPlayTextList();
			adplayTextListModel.setLink(banner_list_jsonObject.getString("link"));
			adplayTextListModel.setImgsrc(banner_list_jsonObject.getString("imgsrc"));
			adplayTextListModel.setWidth(banner_list_jsonObject.getString("width"));
			adplayTextListModel.setVdlink(banner_list_jsonObject.getString("vdlink"));
			adplayTextListModel.setTitle(banner_list_jsonObject.getString("title"));
			adplayTextListModel.setNavurl(banner_list_jsonObject.getString("navurl"));
	
			Log.w("respons BannaerImg", ""+banner_list_jsonObject.getString("navurl"));
			
			AllAdPlayTextList.setAdplayTextList(adplayTextListModel);
			adplayTextListModel = null;
		}

		
		return true;
	}
}
