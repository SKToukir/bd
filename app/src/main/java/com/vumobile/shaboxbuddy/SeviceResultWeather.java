package com.vumobile.shaboxbuddy;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.toolbox.NetworkImageView;
import com.vumobile.network.ServerSideActivity;
import com.vumobile.shaboxbuddy.app.AppController;
import com.vumobile.shaboxbuddy.util.CacheImageDownloader;

public class SeviceResultWeather  extends Activity {
	NetworkImageView imageLebel;
	Context context;
	String imgUrl;
    String weatheralterlink;
	
	//For Creating Object
	SingleNodeXMLParser singleNodeparser;
	//For Creating ImageResize Object
	ImageResize mImageResize;
	public static int dimWidth,dimHeight;
	WebView weatherWebview;
	public static TextView textResult,txtHeader;
	ImageView weather_image;
	ImageView headerImage;
	String serviceTitle;
	public String Header_Image_url="http://wap.gpgamestore.com/CMS/UIHeader/CategoryImg/app_weather.png";
	public static String WEATHER = "";
	public CacheImageDownloader cacheImageDownloader;
	public com.android.volley.toolbox.ImageLoader imageLoader = AppController.getInstance().getImageLoader();
	
	public void onCreate(Bundle savedInstanceState) 
	{
        super.onCreate(savedInstanceState);
        setContentView(R.layout.service_text_weather_page);
        context=this;
        Log.i("Tracker", "This is ServiceResultWeather");
        this.setTitle("Shabox Buddy");
       
        /*Typeface font = Typeface.createFromAsset(getAssets(), "SolaimanLipi-8-Jan-2011.ttf");  
        textResult.setTypeface(font);*/
        weatherWebview= (WebView) findViewById(R.id.webView);
        txtHeader = (TextView) findViewById(R.id.textLebelHeader);
        headerImage = (ImageView)findViewById(R.id.header_image);
        cacheImageDownloader=new CacheImageDownloader();
		cacheImageDownloader.download(Header_Image_url, headerImage);

        weatherWebview.setVisibility(View.GONE);

        weatherWebview.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                return true;
            }
        });

		initUi();
        
        try{	
        		//Get Screen Size
		        DisplayMetrics dms = new DisplayMetrics();
			    getWindowManager().getDefaultDisplay().getMetrics(dms);	    
			    dimWidth = dms.widthPixels;
			    dimHeight = dms.heightPixels;
			    mImageResize = new ImageResize();

		        singleNodeparser = new SingleNodeXMLParser();
		        
		        
		        textResult = (TextView) findViewById(R.id.textView1);
		        txtHeader = (TextView) findViewById(R.id.textLebelHeader);
		        weather_image =(ImageView)findViewById(R.id.imageView_weather);
		        
				Intent inn = getIntent();

				int intValue = inn.getIntExtra("ServiceResultWeatherId", 0);
				int weather_id = intValue;
				Log.i("WEATHER_ID","" + weather_id);
		        
		        switch (weather_id) {
		            case 1:  WEATHER = "http://www.accuweather.com/en/bd/dhaka/28143/weather-forecast/28143"; //"http://widget.addgadgets.com/weather/v1/?q=Dhaka,Bangladesh&s=2&u=1";
		                     weatheralterlink="http://wap.shabox.mobi/cztest/Faisal/dhaka.html";
                        break;
		            case 2:  WEATHER= "http://widget.addgadgets.com/weather/v1/?q=Chittagong,Bangladesh&s=2&u=1";
                             weatheralterlink="http://wap.shabox.mobi/cztest/Faisal/chittogong.html";
		                     break;
		            case 3:  WEATHER= "http://widget.addgadgets.com/weather/v1/?q=Rajshahi,Bangladesh&s=2&u=1";
                        weatheralterlink="http://wap.shabox.mobi/cztest/Faisal/rajshahi.html";
		                     break;
		            case 4:  WEATHER= "http://widget.addgadgets.com/weather/v1/?q=Khulna,Bangladesh&s=2&u=1";
                             weatheralterlink="http://wap.shabox.mobi/cztest/Faisal/khulna.html";
		                     break;
		            case 5:  WEATHER= "http://widget.addgadgets.com/weather/v1/?q=Barisal,Bangladesh&s=2&u=1";
                        weatheralterlink="http://wap.shabox.mobi/cztest/Faisal/barishal.html";
		                     break;
		            case 6:  WEATHER= "http://widget.addgadgets.com/weather/v1/?q=Sylhet,Bangladesh&s=2&u=1";
                        weatheralterlink="http://wap.shabox.mobi/cztest/Faisal/sylhet.html";
		                     break;
		            case 7:  WEATHER= "http://widget.addgadgets.com/weather/v1/?q=Rangpur,Bangladesh&s=2&u=1";
                            weatheralterlink="http://wap.shabox.mobi/cztest/Faisal/rangpur.html";
		                     break;
		            default: WEATHER= "http://widget.addgadgets.com/weather/v1/?q=Dhaka,Bangladesh&s=2&u=1";
                        weatheralterlink="http://wap.shabox.mobi/cztest/Faisal/dhaka.html";
		                     break;
		        }

				new weatherAsyncTask().execute(WEATHER);

		        new putSuccessLog().execute("");

				serviceTitle = serviceTitle.substring(14);

			    txtHeader.setText(serviceTitle);
			    
        }catch(Exception  e){

			 Log.d("Exception Occour", e.toString());

		}
			
	}
	public void initUi() {
		imageLoader = AppController.getInstance().getImageLoader();
		imageLebel = (NetworkImageView) findViewById(R.id.textHeadLebel);
		imageLebel.setImageUrl("http://wap.shabox.mobi/CMS/UIHeader/D480x800/Shabox_Header.png".replaceAll(" ", "%20"),imageLoader);

		
	}
	public void onFaqButtonClicked(View arg0) {
		Intent start = new Intent(getApplicationContext(), ServiceResultFaq.class);
		startActivity(start);
	}
	
	public void onAboutButtonClicked(View arg0) {
		Intent start = new Intent(getApplicationContext(), ServiceResultHelp.class);
		startActivity(start);
	}
	
	public void onHomeButtonClicked(View arg0) {
        this.finish();
    }
	
	private Bitmap downloadUrl(String strUrl) throws IOException {
	     Bitmap bitmap=null;
	     InputStream iStream = null;
	     
	     try{
		      URL url = new URL(strUrl);  /** Creating an http connection to communcate with url */
		      HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();   /** Connecting to url */
		      urlConnection.connect(); /** Reading data from url */
		      iStream = urlConnection.getInputStream(); /** Creating a bitmap from the stream returned from the url */
		      bitmap = BitmapFactory.decodeStream(iStream);
	     }catch(Exception e){
	    	 Log.d("Exception while downloading url", e.toString());
	     }finally{
	    	 iStream.close();      
	     }
	     
	     return bitmap;
    }
	
	class weatherAsyncTask extends AsyncTask<String, String, Bitmap> {
		 
		    Bitmap bitmap = null;
		    
			@Override
			protected void onPreExecute() {
				super.onPreExecute();
				
			}

			@Override
			protected Bitmap doInBackground(String... aurl) {
				try{
				    bitmap = downloadUrl(aurl[0]);
				    
				    Log.i("Weather Download URl", aurl[0]);
				   }catch(Exception e){
				    Log.d("Background Task",e.toString());
				   }
				   
				   return bitmap;				 
			}
			
			protected void onProgressUpdate(String... progress) {
				 Log.d("ANDRO_ASYNC",progress[0]);	
			}

			@Override
			protected void onPostExecute(Bitmap resultImage) {
                if(bitmap!=null) {
                    weather_image.setImageBitmap(resultImage);
                }
                else {
                    weatherWebview.setVisibility(View.VISIBLE);
                    setwebview(weatheralterlink);
                }
			}

		}
	
	
		//For getHeaderimage
		class getHeaderimageAsyncTask extends AsyncTask<String, String, Bitmap> {
			 
		    Bitmap bitmap = null;
		    
			@Override
			protected void onPreExecute() {
				super.onPreExecute();
				
			}

			@Override
			protected Bitmap doInBackground(String... aurl)	{
				try{
					bitmap = downloadUrl(aurl[0]);
				    
				   }catch(Exception e){
				    Log.d("Background Task",e.toString());
				   }
				   
				   return bitmap;
				 
			}
			
			protected void onProgressUpdate(String... progress) {
				 Log.d("ANDRO_ASYNC",progress[0]);	
			}

			@Override
			protected void onPostExecute(Bitmap resultImage) {
				headerImage.setImageBitmap(resultImage);
			}

		}
		
		class putSuccessLog extends AsyncTask<String, String, String> {
			 
		    Bitmap bitmap = null;
		    
			@Override
			protected void onPreExecute() {
				super.onPreExecute();
				
			}

			@Override
			protected String doInBackground(String... aurl){
				try{
					//Start Putting success log
					DisplayMetrics dms = new DisplayMetrics();
				    getWindowManager().getDefaultDisplay().getMetrics(dms);
			    	
			    	ServerSideActivity ssa = new ServerSideActivity();
			    	RequiredUserInfo userinfo = new RequiredUserInfo();
			    	
			    	String HS_MANUFAC = userinfo.deviceMANUFACTURER(getApplication());
			    	String HS_MOD = userinfo.deviceModel(getApplication());
			    	String HS_DIM = dms.widthPixels + "x" + dms.heightPixels;
			    	String APN = "";
			    	String PORTAL_FULLnSHORT = "SB_GGL_AND_APP";
			    	String SERVICE_REQUEST = serviceTitle;
			    	String CMPAIN_KEY = "";
			    	String OS = "Android";
			    	
			    	try{
			    		ssa.httpServerPostSuccessLog("success_log", "SB_GGL_AND_APP",ShaboxBuddyMainActivity.ResultMno, SERVICE_REQUEST, HS_MANUFAC, HS_MOD, HS_DIM, APN, PORTAL_FULLnSHORT, CMPAIN_KEY, OS);
			    	}catch(Exception e){
			    		Log.i("SERVER_POST_ERROR","" + e.getMessage());
			    	}
			    	//End Putting success log
				    
				   }catch(Exception e){
				    Log.d("Background Task",e.toString());
				   }
				
				   return null;					 
			}
			
			protected void onProgressUpdate(String... progress){
				 Log.d("ANDRO_ASYNC",progress[0]);	
			}

			@Override
			protected void onPostExecute(String resultImage){
				
			}

		}
		
		@Override
		protected void onResume() {
			// TODO Auto-generated method stub
			super.onResume();
			overridePendingTransition(0, 0);
		}

    public void setwebview(String weatheralterlink){


        //grab the webview settings
        WebSettings websettings = weatherWebview.getSettings();

        //enable javascript
        websettings.setJavaScriptEnabled(true);

        //add a js interface to display the widgets
        // webview.addJavascriptInterface(new JavaScriptInterface(this), "Android");

        //load a webpage
        weatherWebview.loadUrl(weatheralterlink);
    }

}
