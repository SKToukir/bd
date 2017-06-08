package com.vumobile.shaboxbuddy.model;

import android.util.Log;

public class AdplayTextListModel {

	private String link;
	private String imgsrc;
	private String width;
	private String vdlink;
	private String title;
	private String navurl;
	
	public String getLink() {
		return link;
	}
	public void setLink(String link) {
		this.link = link;
	}
	public String getImgsrc() {
		return imgsrc;
	}
	public void setImgsrc(String imgsrc) {
		this.imgsrc = imgsrc;
	}
	public String getWidth() {
		return width;
	}
	public void setWidth(String width) {
		this.width = width;
	}
	public String getVdlink() {
		return vdlink;
	}
	public void setVdlink(String vdlink) {
		this.vdlink = vdlink;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getNavurl() {
		return navurl;
	}
	public void setNavurl(String navurl) {
		this.navurl = navurl;
	}
	@Override
	public String toString() {
        Log.e("Tracker", "This is AddPlayListTextModel");
		return "AdplayVideoListModel [link=" + link + ", imgsrc=" + imgsrc
				+ ", width=" + width + ", vdlink=" + vdlink + ", title="
				+ title + ", navurl=" + navurl + "]";
	}
	

}
