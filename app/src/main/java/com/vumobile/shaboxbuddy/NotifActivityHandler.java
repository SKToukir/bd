package com.vumobile.shaboxbuddy;

import android.app.Activity;
import android.app.DownloadManager;
import android.app.NotificationManager;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;



import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by IT-10 on 9/22/2015.
 */
public class NotifActivityHandler extends Activity {

    private NotifActivityHandler ctx;
    Context context;
    public String PushMessage = "";
    public String pushResponseUrl = "http://www.vumobile.biz/sticker_gcm_server/push_response.php";
    public static String resultMno = null;
    PHPRequest phpRequest = new PHPRequest();

    public static final String NOTIFICATION_ID = "NOTIFICATION_ID";

    String StickerTitle="";
    private final String PATH = "/data/data/pack.coderzheaven/";
   // public static String sample_url = GCMIntentService.sample_url;

    JSONObject jsonobject;
    JSONArray jsonarray;
    private static String url2 = "http://wap.shabox.mobi/GCMPanel/Amar_stickerInPush.aspx";

    // JSON Node names
    private static final String TAG_CONTACTS = "Table";


    public static String image_title = "";
    public static String sample_url = "";
    JSONArray contacts = null;
   public static String action;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.);
        action= (String)getIntent().getExtras().get("DO");

        try {
            NotifActivityHandler.this.finish();
        }catch (Exception e){
            e.getStackTrace();
        }




            //ContentDownloadActivity cws = new ContentDownloadActivity();
            resultMno= AppConstant.mno;

            Log.i("MSISDN ", "" + resultMno);



        ctx=this;
        try {

            //Log.i("LOG", "lauching action: " + action);

            if(action.equals("2")){


              Intent i = new Intent(NotifActivityHandler.this, ShaboxBuddyMainActivity.class);

              startActivity(i);

              SendLaunchPushRes();

                //DownloadFromUrl(imageUrl);

                if(isNetworkAvailable()){
                    new GetContacts().execute();
                    /** Getting a reference to Edit text containing url */

                    /** Creating a new non-ui thread task */
                    DownloadTask downloadTask = new DownloadTask();

                    /** Starting the task created above */
                    downloadTask.execute(sample_url);
                    //Toast.makeText(getBaseContext(), "action"+action , Toast.LENGTH_SHORT).show();

                }else{
                    Toast.makeText(getBaseContext(), "Network is not Available", Toast.LENGTH_SHORT).show();
                }

                clearNotification();

          }  else if(action.equals("3")){
                Toast.makeText(getApplicationContext(),"working", Toast.LENGTH_SHORT).show();
            }else{
                Toast.makeText(getApplicationContext(),"Something went wrong", Toast.LENGTH_SHORT).show();
          }
        } catch (Exception e) {
            e.printStackTrace();
        }



    }

    //end

public void clearNotification(){
    NotificationManager notificationManager = (NotificationManager)
            getSystemService(Context.
                    NOTIFICATION_SERVICE);

    notificationManager.cancelAll();
   // NotifActivityHandler.this.finish();
}

/*    public class NotificationDismissedReceiver extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            int notificationId = intent.getExtras().getInt("com.my.app.notificationId");
            Toast.makeText(getApplicationContext(), "Successful"+""+ notificationId, Toast.LENGTH_SHORT).show();

        }
    }*/

    private boolean isNetworkAvailable(){
        boolean available = false;
        /** Getting the system's connectivity service */
        ConnectivityManager connMgr = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);

        /** Getting active network interface  to get the network's status */
        NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();

        if(networkInfo !=null && networkInfo.isAvailable())
            available = true;

        /** Returning the status of the network */
        return available;
    }

    private Bitmap downloadUrl(String strUrl) throws IOException{
        Bitmap bitmap=null;
        InputStream iStream = null;
        try{
            URL url = new URL(strUrl);
            /** Creating an http connection to communcate with url */
            HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();

            /** Connecting to url */
            urlConnection.connect();

            /** Reading data from url */
            iStream = urlConnection.getInputStream();

            /** Creating a bitmap from the stream returned from the url */
            bitmap = BitmapFactory.decodeStream(iStream);

        }catch(Exception e){
            Log.d("Exception while downloading url", e.toString());
        }finally{
            iStream.close();
        }
        return bitmap;
    }

    private class DownloadTask extends AsyncTask<String, Integer, Bitmap>{
        Bitmap bitmap = null;
        @Override
        protected Bitmap doInBackground(String... url) {
            try{
                bitmap = downloadUrl(url[0]);
            }catch(Exception e){
                Log.d("Background Task",e.toString());
            }
            return bitmap;
        }

        @Override
        protected void onPostExecute(Bitmap result) {
            /** Getting a reference to ImageView to display the
             * downloaded image
             */
         /*   ImageView iView = (ImageView) findViewById(R.id.iv_image);

            *//** Displaying the downloaded image *//*
            iView.setImageBitmap(result);*/

            /** Showing a message, on completion of download process */

            //StickerTabActivity.dm = (DownloadManager) getSystemService(DOWNLOAD_SERVICE);
            DownloadManager.Request request = new DownloadManager.Request(Uri.parse(sample_url));
            request.setDestinationInExternalPublicDir("/Amar_Sticker/",
                    StickerTitle + ".png");
           // StickerTabActivity.enqueueid = StickerTabActivity.dm
                    //.enqueue(request);//faisal

            Toast.makeText(getBaseContext(), "Sticker downloaded successfully", Toast.LENGTH_SHORT).show();

            action="-1";
        }
    }


    public  void SendLaunchPushRes(){
        new SendLaunchPushResponse().execute();
    }

    private class SendLaunchPushResponse extends AsyncTask<String, String, String> {


        RequiredUserInfo userinfo = new RequiredUserInfo();
        String HS_MANUFAC_ = userinfo.deviceMANUFACTURER(NotifActivityHandler.this);
        String HS_MOD_ = userinfo.deviceModel(NotifActivityHandler.this);
        String user_email=userinfo.userEmail(NotifActivityHandler.this);
        /*model = Build.MODEL;
		manufacture = Build.MANUFACTURER;
		brand = Build.BRAND; // like SEMC
		dimWidth = dms.widthPixels;
		dimHeight = dms.heightPixels;*/

        @Override
        protected void onPreExecute() {
            // TODO Auto-generated method stub
            super.onPreExecute();


            if(resultMno.equalsIgnoreCase("ERROR"))
            {
                resultMno="wifi";
            }

        }

        @Override
        protected String doInBackground(String... args) {
            // TODO Auto-generated method stub

            List<NameValuePair> params = new ArrayList<NameValuePair>();
            params.add(new BasicNameValuePair("email",user_email));
            params.add(new BasicNameValuePair("action", "launch"));
            params.add(new BasicNameValuePair("handset_name", HS_MANUFAC_));
            params.add(new BasicNameValuePair("handset_model", HS_MOD_));
            params.add(new BasicNameValuePair("msisdn",resultMno));

            // getting JSON Object
            // Note that create product url accepts POST method
            phpRequest.makeHttpRequest(pushResponseUrl, "POST", params);
            Log.d("Tariqul", pushResponseUrl+"params: "+ params);
            return null;
        }
        @Override
        protected void onPostExecute(String result) {
            // TODO Auto-generated method stub
            super.onPostExecute(result);
        }
    }
// Download image from web in-up-push
private class GetContacts extends AsyncTask<Void, Void, Void> {

    @Override
    protected void onPreExecute() {
        super.onPreExecute();

    }

    @Override
    protected Void doInBackground(Void... arg0) {
        // Creating service handler class instance
        ServiceHandler sh = new ServiceHandler();

        // Making a request to url and getting response
        String jsonStr = sh.makeServiceCall(url2, ServiceHandler.GET);

        Log.d("Response: ", "> " + jsonStr);

        if (jsonStr != null) {
            try {
                JSONObject jsonObj = new JSONObject(jsonStr);
                Log.d("Response object: ", "> " + jsonObj);
                // Getting JSON Array node
                contacts = jsonObj.getJSONArray(TAG_CONTACTS);
                Log.d("Response array: ", "> " + contacts);
                // looping through All Contacts

                JSONObject c = contacts.getJSONObject(0);
                Log.d("Response next object: ", "> " + c);
                sample_url = c.getString("image_url");
                Log.d("Response data ", "> " + sample_url);
                StickerTitle = c.getString("image_title");
                Log.d("Response data ", "> " + image_title);


            } catch (JSONException e) {
                e.printStackTrace();
            }
        } else {
            Log.e("ServiceHandler", "Couldn't get any data from the url");
        }

        return null;
    }

    @Override
    protected void onPostExecute(Void result) {
        super.onPostExecute(result);


    }

}

}