package com.vumobile.shaboxbuddy;



import android.util.Log;
import android.widget.RelativeLayout;
import android.widget.RelativeLayout.LayoutParams;

public  class ImageResize{
	
		
    public LayoutParams getlayoutType(int DimWidth){
        Log.i("Tracker", "This is ImageResize");
    	
	    if(DimWidth>500){
	    	RelativeLayout.LayoutParams myParams = new RelativeLayout.LayoutParams(250, 250);
	    	myParams.setMargins(0, 15, 0, 10);
	    	return myParams;
			//headerImage.setLayoutParams(myParams);
	    }else{
	    	RelativeLayout.LayoutParams myParams = new RelativeLayout.LayoutParams(200, 200);
	    	myParams.setMargins(0, 15, 0, 10);
			//headerImage.setLayoutParams(myParams);
	    	return myParams;
	    }
	
    }
}
    


