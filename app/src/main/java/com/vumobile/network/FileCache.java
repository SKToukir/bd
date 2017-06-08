package com.vumobile.network;

import java.io.File;

import android.content.Context;
import android.util.Log;

public class FileCache {

	  private File cacheDir;
	  
	    public FileCache(Context context){
	        //Find the dir to save cached images
	        if (android.os.Environment.getExternalStorageState().equals(android.os.Environment.MEDIA_MOUNTED)) {
                cacheDir = new File(android.os.Environment.getExternalStorageDirectory(), "TempImages");
                Log.e("Tracker", "This is  fielCache");
            }
	        else
	            cacheDir=context.getCacheDir();
	        if(!cacheDir.exists())
	            cacheDir.mkdirs();
	    }
	  
	    public File getFile(String url){
	        String filename=String.valueOf(url.hashCode());
	        File f = new File(cacheDir, filename);
            Log.e("Tracker","This is  File Cache");
	        return f;
	  
	    }
	  
	    public void clear(){
            Log.e("Tracker","This is  File Cache");
	        File[] files=cacheDir.listFiles();
	        if(files==null)
	            return;
	        for(File f:files)
	            f.delete();
	    }
	  
	}
