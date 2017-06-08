package com.vumobile.imageadapter;

import java.util.ArrayList;
import java.util.List;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.NetworkImageView;
import com.vumobile.shaboxbuddy.R;
import com.vumobile.shaboxbuddy.app.AppController;
import com.vumobile.shaboxbuddy.model.Categories;

@SuppressLint({ "ViewTag", "InflateParams" })
public class TopImageAdapter extends BaseAdapter {

	private Activity activity;
	List<Categories> categories = new ArrayList<Categories>();
	private LayoutInflater inflater;

	ImageLoader imageLoader = AppController.getInstance().getImageLoader();

	public TopImageAdapter(Activity activity, List<Categories> categories) {
        Log.e("Tracker", "This is  Top Image Adapter");
		this.activity = activity;
		this.categories = categories;
	}

	public TopImageAdapter() {
		// TODO Auto-generated constructor stub
	}

	@Override
	public int getCount() {
		return categories.size();
	}

	@Override
	public Object getItem(int location) {
		return categories.get(location);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {

		if (inflater == null)
			inflater = (LayoutInflater) activity
					.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		if (convertView == null)
			convertView = inflater.inflate(R.layout.grid_layout, null);

		if (imageLoader == null)
			imageLoader = AppController.getInstance().getImageLoader();
		NetworkImageView categoryImage = (NetworkImageView) convertView
				.findViewById(R.id.picture);

		TextView tv = (TextView) convertView.findViewById(R.id.text);

		// getting movie data for the row
		Categories cat = categories.get(position);

		// thumbnail image
		categoryImage.setImageUrl(cat.getImageName(), imageLoader);

		return convertView;
	}

}
