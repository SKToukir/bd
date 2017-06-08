package com.vumobile.network;

import java.lang.ref.SoftReference;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import android.graphics.Bitmap;
import android.util.Log;

public class MemoryCache {
	 private Map<String, SoftReference<Bitmap>> cache=Collections.synchronizedMap(new HashMap<String, SoftReference<Bitmap>>());
	  
	    public Bitmap get(String id){
            Log.e("Tracker", "This is  More Memory Cache");
	        if(!cache.containsKey(id))
	            return null;
	        SoftReference<Bitmap> ref=cache.get(id);
	        return ref.get();
	    }
	  
	    public void put(String id, Bitmap bitmap){
            Log.e("Tracker", "This is  More Memory Cache");
	        cache.put(id, new SoftReference<Bitmap>(bitmap));
	    }
	  
	    public void clear() {
	        cache.clear();
	    }
}
