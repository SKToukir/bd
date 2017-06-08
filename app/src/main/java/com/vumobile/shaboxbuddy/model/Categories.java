package com.vumobile.shaboxbuddy.model;

import android.util.Log;

public class Categories {
	
	private String serviceID;
	private String serviceTitle;
	private String imageName;
	private String countLike;
	
	
	
	public Categories() {
		
	}
	public Categories(String serviceID, String serviceTitle, String imageName,
			String countLike) {
		super();
		this.serviceID = serviceID;
		this.serviceTitle = serviceTitle;
		this.imageName = imageName;
		this.countLike = countLike;
        Log.e("Tracker", "This CategoryModel");
	}
	public String getServiceID() {
		return serviceID;
	}
	public void setServiceID(String serviceID) {
		this.serviceID = serviceID;
	}
	public String getServiceTitle() {
		return serviceTitle;
	}
	public void setServiceTitle(String serviceTitle) {
		this.serviceTitle = serviceTitle;
	}
	public String getImageName() {
		return imageName;
	}
	public void setImageName(String imageName) {
		this.imageName = imageName;
	}
	public String getCountLike() {
		return countLike;
	}
	public void setCountLike(String countLike) {
		this.countLike = countLike;
	}

}
