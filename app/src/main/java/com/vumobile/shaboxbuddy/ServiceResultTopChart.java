package com.vumobile.shaboxbuddy;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.toolbox.NetworkImageView;
import com.flurry.android.FlurryAgent;
import com.vumobile.network.ServerSideActivity;
import com.vumobile.shaboxbuddy.app.AppController;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

//@Anis
public class ServiceResultTopChart extends Activity{
	NetworkImageView imageLebel;
	Context context;
	String imgUrl;
	public com.android.volley.toolbox.ImageLoader imageLoader = AppController.getInstance().getImageLoader();
	//For Creating ImageResize Object
	ImageResize mImageResize;
		
	TextView BollywoodMovesList;
	TextView HollwwoodMovesList;
	TextView BollywoodMusicList;
	TextView HollywoodMusicList;
	public SingleNodeXMLParser singleNodeparser;
	ImageView headerImage;
	public static int dimWidth,dimHeight;
	String serviceTitle;
	
	public void onCreate(Bundle savedInstanceState) 
	{
        Log.i("Tracker", "This is ServiceResulttopchart");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.topchart);
        this.setTitle("Shabox Buddy");
        headerImage = (ImageView)findViewById(R.id.imageView1);
        
        context = this;
        imageLoader = AppController.getInstance().getImageLoader();

		initUi();
        
     try{
        //Get Screen Size
        DisplayMetrics dms = new DisplayMetrics();
	    getWindowManager().getDefaultDisplay().getMetrics(dms);	    
	    dimWidth = dms.widthPixels;
	    dimHeight = dms.heightPixels;
	    mImageResize = new ImageResize();
	    //Get Layout valye from ImageResizer Class 
	    headerImage.setLayoutParams(mImageResize .getlayoutType(dimWidth));
        
        //Ceating for single node xml parsing
        singleNodeparser = new SingleNodeXMLParser();
        
        BollywoodMovesList = (TextView)findViewById(R.id.txtMoveBollylist);
        HollwwoodMovesList = (TextView)findViewById(R.id.txtMoveHollylist);
        BollywoodMusicList = (TextView)findViewById(R.id.txtBollyMusiclist);
        HollywoodMusicList = (TextView)findViewById(R.id.txtHollyMusiclist);
        
        
        Intent intent = getIntent();
		String ServiceResultTopChart = intent.getStringExtra("ServiceResultTopChart");
		Log.i("ServiceResultTopChart", ServiceResultTopChart);
		
	
		String BollyMovelist = singleNodeparser.parsing(ServiceResultTopChart, "<BollywoodMovieContent>", "</BollywoodMovieContent>",23);
		BollywoodMovesList.setText(BollyMovelist.replace("&lt;br/&gt;",""));
		
	
		String HollywoodMoves = singleNodeparser.parsing(ServiceResultTopChart, "<HollywoodMovieContent>", "</HollywoodMovieContent>",23);
		HollwwoodMovesList.setText(HollywoodMoves.replace("&lt;br/&gt;",""));
		/* End For Top Chart Movies*/
		
		
		/* Start For Top Chart Movies*/		
		//For Bollywood Music List Show		
	
		String BollywoodMusic = singleNodeparser.parsing(ServiceResultTopChart, "<BollywoodMusicContent>", "</BollywoodMusicContent>",23);
		BollywoodMusicList.setText(BollywoodMusic.replace("&lt;br/&gt;",""));
		
		
		//For Hollywood Music List Show	
		
		String HollywoodMusic = singleNodeparser.parsing(ServiceResultTopChart, "<HollywoodMusicContent>", "</HollywoodMusicContent>",23);
		HollywoodMusicList.setText(HollywoodMusic.replace("&lt;br/&gt;",""));			/* End For Top Chart Music*/		
		
		//For HeaderImage
		/*String headerimage = singleNodeparser.parsing(ServiceResultTopChart, "<AppImageName>", "</AppImageName>", 14);
		Log.i("Entertainment Result ","" + headerimage);*/
		//Get Header Image URL
		String HeaderimageURL="http://wap.gpgamestore.com/CMS/UIHeader/CategoryImg/app_Top_Chart.png";
		//String HeaderimageURL="http://wap.gpgamestore.com/CMS/UIHeader/CategoryImg/" + headerimage;       
		new getHeaderimageAsyncTask().execute(HeaderimageURL);
		
		//Put success log
        new putSuccessLog().execute("");
        
		
 	 }catch(Exception e){
 		Log.d("Exception Occour", e.toString());
 		}
		
  }
	
	
	public void initUi() {
		
		imageLebel = (NetworkImageView) findViewById(R.id.textHeadLebel);
		imageLebel.setImageUrl("http://wap.shabox.mobi/CMS/UIHeader/D480x800/Shabox_Header.png".replaceAll(" ", "%20"),imageLoader);
		
	
	}
		public void onHomeButtonClicked(View arg0) {
	        this.finish();
		}
		public void onFaqButtonClicked(View arg0){
			Intent start = new Intent(getApplicationContext(), ServiceResultFaq.class);
			startActivity(start);
		}
		public void onAboutButtonClicked(View arg0){
			Intent start = new Intent(getApplicationContext(), ServiceResultHelp.class);
			startActivity(start);
			}
	
		//Methods for Creating Bitmap Image
		private Bitmap downloadUrl(String strUrl) throws IOException
	    {
		     Bitmap bitmap=null;
		     InputStream iStream = null;
		     
		     try{
			      URL url = new URL(strUrl);  /**//** Creating an http connection to communcate with url *//*
*/			      HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();  /* *//** Connecting to url *//*
*/			      urlConnection.connect();/* *//** Reading data from url *//*
*/			      iStream = urlConnection.getInputStream(); /**//** Creating a bitmap from the stream returned from the url *//*
*/			      bitmap = BitmapFactory.decodeStream(iStream);
		     }catch(Exception e){
		    	 Log.d("Exception while downloading url", e.toString());
		     }finally{
		    	 iStream.close();      
		     }
		     
		     return bitmap;
	    }
	
		//For getHeaderimage
		class getHeaderimageAsyncTask extends AsyncTask<String, String, Bitmap> {
				 
			    Bitmap bitmap = null;
			    
				@Override
				protected void onPreExecute() {
					super.onPreExecute();
					
				}

				@Override
				protected Bitmap doInBackground(String... aurl){
					try{
						bitmap = downloadUrl(aurl[0]);
					    
					   }catch(Exception e){
					    Log.d("Background Task",e.toString());
					   }
					
					   return bitmap;					 
				}
				
				protected void onProgressUpdate(String... progress){
					 Log.d("ANDRO_ASYNC",progress[0]);	
				}

				@Override
				protected void onPostExecute(Bitmap resultImage){
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
			    	String SERVICE_REQUEST = "	Top Chart";
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
		public void Share(View v){

	        Intent share = new Intent(android.content.Intent.ACTION_SEND);
	        share.setType("text/plain");
	        share.addFlags(Intent.FLAG_ACTIVITY_CLEAR_WHEN_TASK_RESET);

	        // Add data to the intent, the receiving app will decide
	        // what to do with it.	     
	     
	        share.putExtra(Intent.EXTRA_SUBJECT, "ShaboxBuddy Apps");
	        share.putExtra(
	        Intent.EXTRA_TEXT,"Hey! Read this in Buddy!  https://play.google.com/store/apps/details?id=com.vumobile.shaboxbuddy&hl=en");

	        startActivity(Intent.createChooser(share, "Share link!"));
	    
		}
		@Override
		protected void onStart()
		{
			super.onStart();
			FlurryAgent.onStartSession(this, AppConstant.FLURRY_API_KEY);
		}
		 
		@Override
		protected void onStop()
		{
			super.onStop();		
			FlurryAgent.onEndSession(this);
		}
		@Override
		protected void onResume() {
			// TODO Auto-generated method stub
			super.onResume();
			overridePendingTransition(0, 0);
		}

}
