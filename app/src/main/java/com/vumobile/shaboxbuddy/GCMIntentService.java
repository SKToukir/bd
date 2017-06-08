package com.vumobile.shaboxbuddy;

import android.app.AlarmManager;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.app.TaskStackBuilder;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.os.SystemClock;
import android.support.annotation.Nullable;
import android.support.v4.app.NotificationCompat;
import android.util.Log;
import android.widget.RemoteViews;

import com.vumobile.shaboxbuddy.util.UserInfoClass;
import com.vumobile.shaboxbuddy.webservice.SplashCaller;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

@SuppressWarnings("ALL")
public class GCMIntentService extends Service/*GCMBaseIntentService*/ {
    public static String resultMno = null;
    private static final String NOTIFICATION_DELETED_ACTION = "NOTIFICATION_DELETED";
    public static String resultMnos;
	private static final String TAG = "GCMIntentService";
    public NotificationManager mNotificationManager;
    public static boolean isNotificationAccessEnabled = false;
    public String message1="";
    public String image_title = "";
    public String ImageURL = "";
    public String serviceid = "";
    JSONArray contacts = null;
    PHPRequest phpRequest=new PHPRequest();
    private static final String TAG_CONTACTS = "Table";
    ShaboxBuddyMainActivity shaboxBuddyMainActivity=new ShaboxBuddyMainActivity();
    private static String url2 = "http://wap.shabox.mobi/GCMPanel/BuddyPush.aspx?email=";//http://wap.shabox.mobi/GCMPanel/Amar_stickerInPush.aspx";
    public String pushResponseUrl = "http://www.vumobile.biz/sbgcm_server/push_response.php";

//    @Override
//    public void onMessageReceived(RemoteMessage remoteMessage) {
//        //super.onMessageReceived(remoteMessage);
//        Log.e("notification onMessage", remoteMessage.getNotification().getBody());
//
//        sendNotification(remoteMessage.getNotification().getBody());
//
//    }


    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {


        Log.i(TAG, "Service onStartCommand");
        if (intent == null) {
            cleanupAndStopServiceRightAway();
            return START_NOT_STICKY;
        }
        //Creating new thread for my service
        //Always write your long running tasks in a separate thread, to avoid ANR
        new Thread(new Runnable() {
            @Override
            public void run() {

                //Your logic that service will perform will be placed here
                //In this example we are just looping and waits for 1000 milliseconds in each loop.


                try {

                    Thread.sleep(6000);

                    try {
                        new GetContacts().execute();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                } catch (Exception e) {

                }

                //i = i+1;


            }
        }).start();

        return START_STICKY;
    }

    private void cleanupAndStopServiceRightAway() {
        // Add your code here to cleanup the service

        // Add your code to perform any recovery required
        // for recovering from your previous crash

        // Request to stop the service right away at the end
        stopSelf();
    }

//    private void sendNotification(String messageBody) {
//        Intent intent = new Intent(this, ShaboxBuddyMainActivity.class);
//        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
//        PendingIntent pendingIntent = PendingIntent.getActivity(GCMIntentService.this, 0, intent, PendingIntent.FLAG_ONE_SHOT);
//
//        //Uri defaultSoundUri= RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
//        NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(this)
//                .setSmallIcon(R.drawable.icon)
//                .setContentTitle("Buddy")
//                .setContentText(messageBody)
//                .setAutoCancel(true)
//                .setContentIntent(pendingIntent);
//
//        NotificationManager notificationManager =
//                (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
//
//        notificationManager.notify(0 , notificationBuilder.build());
//
//    }






//    public GCMIntentService() {
//        super(SENDER_ID);
//    }
//
//    /**
//     * Method called on device registered
//     **/
//    @Override
//    protected void onRegistered(Context context, String registrationId) {
//        Log.i(TAG, "Device registered: regId = " + registrationId);
//        //displayMessage(context, "Your device registred with GCM");
//        Log.d("NAME", ShaboxBuddyMainActivity.name);
//      //  Toast.makeText(context, "Test", Toast.LENGTH_LONG).show();
//        ServerUtilities.register(context, ShaboxBuddyMainActivity.name, ShaboxBuddyMainActivity.model, ShaboxBuddyMainActivity.email, registrationId);
//    }
//
//    /**
//     * Method called on device un registred
//     * */
//    @Override
//    protected void onUnregistered(Context context, String registrationId) {
//        Log.i(TAG, "Device unregistered");
//        displayMessage(context, getString(R.string.gcm_unregistered));
//        ServerUtilities.unregister(context, registrationId);
//    }
//
//    /**
//     * Method called on Receiving a new message
//     * */
//    @Override
//    protected void onMessage(Context context, Intent intent) {
//        Log.i(TAG, "Received message");
//        String message = intent.getExtras().getString("price");
//       AppConstant.pushMessage=message;
//        new SendLaunchPushResponse().execute();
//        new GetContacts().execute();
//        //displayMessage(context, message);
//        //generateNotification(context,message);
//        /*Intent intent2=new Intent(context,PushActivity.class);
//        intent2.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//        startActivity(intent2);*/
//
//        Log.i("Tracker", "This is GCMINtentSerVice");
//        // notifies user
////        generateNotification(context, message);
//    }
//
//    /**
//     * Method called on receiving a deleted message
//     * */
//    @Override
//    protected void onDeletedMessages(Context context, int total) {
//        Log.i(TAG, "Received deleted messages notification");
//        String message = getString(R.string.gcm_deleted, total);
//        displayMessage(context, message);
//        // notifies user
////        generateNotification(context, message);
//    }
//
//    /**
//     * Method called on Error
//     * */
//    @Override
//    public void onError(Context context, String errorId) {
//        Log.i(TAG, "Received error: " + errorId);
//        displayMessage(context, getString(R.string.gcm_error, errorId));
//    }
//
//    @Override
//    protected boolean onRecoverableError(Context context, String errorId) {
//        // log message
//        Log.i(TAG, "Received recoverable error: " + errorId);
//        displayMessage(context, getString(R.string.gcm_recoverable_error,errorId));
//        return super.onRecoverableError(context, errorId);
//    }

    /**
     * Issues a notification to inform the user that server has sent a message.
     */
   /* @SuppressWarnings("deprecation")
	private static void generateNotification(Context context, String message) {
        int icon = R.drawable.icon;
        long when = System.currentTimeMillis();
        NotificationManager notificationManager = (NotificationManager)
                context.getSystemService(Context.NOTIFICATION_SERVICE);
        Notification notification = new Notification(icon, message, when);
        
        String title = context.getString(R.string.app_name);
        
        Intent notificationIntent = new Intent(context, ShaboxBuddyMainActivity.class);
        // set intent so it does not start a new activity
        notificationIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
        PendingIntent intent = PendingIntent.getActivity(context, 0, notificationIntent, 0);
        notification.setLatestEventInfo(context, title, message, intent);
        notification.flags |= Notification.FLAG_AUTO_CANCEL;
        
        // Play default notification sound
        notification.defaults |= Notification.DEFAULT_SOUND;
        
        //notification.sound = Uri.parse("android.resource://" + context.getPackageName() + "your_sound_file_name.mp3");
        
        // Vibrate if vibrate is enabled
        notification.defaults |= Notification.DEFAULT_VIBRATE;
        notificationManager.notify(0, notification);      

    }*/



    private void setCustomViewNotification(Context context, String sms,String title, String Image) {

        Bitmap remote_picture = null;

        try {
            //Log.d("Response Tariqul sample_url", ImageURL.toString());
            remote_picture = BitmapFactory.decodeStream((InputStream) new URL(Image).getContent());
        } catch (IOException e) {
            e.printStackTrace();
        }
        Calendar c = Calendar.getInstance();
        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm a");
        String strDate = sdf.format(c.getTime());

        mNotificationManager = (NotificationManager)
                context.getSystemService(Context.NOTIFICATION_SERVICE);
        // Creates an explicit intent for an ResultActivity to receive.
        Intent resultIntent = new Intent(context, ShaboxBuddyMainActivity.class);

        resultIntent.putExtra("serviceid",serviceid);
        resultIntent.putExtra("DoDownload", "4");
        resultIntent.putExtra("doAction","2");
     /*   DownloadTask downloadTask = new DownloadTask();

        *//** Starting the task created above *//*
        downloadTask.execute(Image);*/
        // This ensures that the back button follows the recommended convention for the back key.
        TaskStackBuilder stackBuilder = TaskStackBuilder.create(context);

        // Adds the back stack for the Intent (but not the Intent itself)
        stackBuilder.addParentStack(ShaboxBuddyMainActivity.class);

        // Adds the Intent that starts the Activity to the top of the stack.
        stackBuilder.addNextIntent(resultIntent);
        PendingIntent resultPendingIntent = stackBuilder.getPendingIntent(0, PendingIntent.FLAG_UPDATE_CURRENT);

        // Create remote view and set bigContentView.
        RemoteViews expandedView = new RemoteViews(this.getPackageName(), R.layout.push_activity);

        Intent volume = new Intent(context, ShaboxBuddyMainActivity.class);//NotifActivityHandler
        volume.putExtra("serviceid",serviceid);


        volume.putExtra("DO", "2");
        PendingIntent pVolume = PendingIntent.getActivity(context, 1, volume, 0);
        expandedView.setOnClickPendingIntent(R.id.MainlayoutCustom, pVolume);

        expandedView.setTextViewText(R.id.text_view, sms);
        //expandedView.setTextViewText(R.id.notificationTime, strDate);

        try {
            expandedView.setImageViewBitmap(R.id.imageViewTest, remote_picture );
        }catch (Exception e){

            e.printStackTrace();
        }
        Intent intent = new Intent(NOTIFICATION_DELETED_ACTION);
        PendingIntent pendintIntent = PendingIntent.getBroadcast(context, 0, intent, 0);

        Notification notification = new NotificationCompat.Builder(context)
                .setSmallIcon(getNotificationIcon())
                .setLargeIcon(remote_picture)
                .setAutoCancel(true)
                .setContentIntent(resultPendingIntent)
                .setContentTitle(title)
                          //.setDeleteIntent(pendintIntent)
                .build();

        notification.bigContentView = expandedView;
        notification.defaults |= Notification.DEFAULT_SOUND;
        mNotificationManager.notify(0, notification);

    }

    private int getNotificationIcon() {
        boolean whiteIcon = (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP);
        return whiteIcon ? R.drawable.buddy_notification : R.drawable.buddy_notification;
    }

//    @Override
//    public IBinder onBind(Intent mIntent) {
//        IBinder mIBinder = super.onBind(mIntent);
//        isNotificationAccessEnabled = true;
//        return mIBinder;
//    }


    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public boolean onUnbind(Intent mIntent) {
        boolean mOnUnbind = super.onUnbind(mIntent);
        isNotificationAccessEnabled = false;
        return mOnUnbind;
    }

    private class GetContacts extends AsyncTask<Void, Void, Void> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

        }

        @SuppressWarnings("WrongThread")
        @Override
        protected Void doInBackground(Void... arg0) {
            // Creating service handler class instance
            ServiceHandler sh = new ServiceHandler();
            UserInfoClass user_info = new UserInfoClass();

            String pushUrl = url2+user_info.deviceID(GCMIntentService.this);

            Log.d(TAG, "pushurl: "+pushUrl);

            // Making a request to url and getting response
            String jsonStr = sh.makeServiceCall(
                    pushUrl, ServiceHandler.GET);

            Log.d("Response:", "> " + jsonStr);

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
                    String sample_url = c.getString("Url");
                    Log.d("Response sample_url ", "> " + sample_url);
                    image_title = c.getString("Title");
                    Log.d("Response image_title ", "> " + image_title);
                    serviceid=c.getString("Service_Id");
                    message1 = c.getString("message");
                    Log.d(TAG, "message: " + message1);


                    setCustomViewNotification(GCMIntentService.this, message1,image_title,sample_url);



                    new SendLaunchPushResponse(getApplicationContext(), "received").execute();
                    URL url = null;


                    try {

                        url = new URL(sample_url);



                    } catch (MalformedURLException e) {
                        System.out.println("The URL is not valid.");
                        System.out.println(e.getMessage());
                    }

                    if (url != null) {
                        ImageURL=url.toString();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }

                // setCustomViewNotification(context, message1);
            } else {
                Log.e("ServiceHandler", "Couldn't get any data from the url");
            }

            return null;
        }

        @Override
        protected void onPostExecute(Void result) {
            super.onPostExecute(result);
            Log.d("Response onPostExecute ", "> " + "onPostExecute");
        }

    }

    @SuppressWarnings("Convert2Diamond")
    public class SendLaunchPushResponse extends AsyncTask<String, String, String> {
        RequiredUserInfo userinfo = new RequiredUserInfo();
        String HS_MANUFAC_ = userinfo.deviceMANUFACTURER(GCMIntentService.this);
        String HS_MOD_ = userinfo.deviceModel(GCMIntentService.this);
        String user_email=userinfo.userEmail(GCMIntentService.this);

        Context context;
        String action;

        public SendLaunchPushResponse(Context context1,String action1){
            context=context1;
            action=action1;
        }

        @Override
        protected void onPreExecute() {
            // TODO Auto-generated method stub
            super.onPreExecute();

        }

        @Override
        protected String doInBackground(String... args) {
            // TODO Auto-generated method stub

            try {
                Thread.sleep(1000);
                GCMIntentService.resultMno = "START";
                SplashCaller c = new SplashCaller();
                // c.ad= c.ad;
                c.join();
                c.start();

                while (GCMIntentService.resultMno == "START") {
                    try {
                        Thread.sleep(100);
                    } catch (Exception ex) {
                    }
                }

                AppConstant.mno = resultMno;
                Log.i("success_logs", "" + AppConstant.mno);
            } catch (Exception ex) {
                Log.i("EXCEPTION EXIST USER", "" + ex.toString());
                // rslt= "Exception";

            }

            if (AppConstant.mno.equals("ERROR")) {
                resultMno = "wifi";
            } else {
                resultMno = AppConstant.mno;
            }

            List<NameValuePair> params = new ArrayList<NameValuePair>();
            params.add(new BasicNameValuePair("email",user_email));
            params.add(new BasicNameValuePair("action", action));
            params.add(new BasicNameValuePair("handset_name", HS_MANUFAC_));
            params.add(new BasicNameValuePair("handset_model", HS_MOD_));
            params.add(new BasicNameValuePair("msisdn",resultMno));

            // getting JSON Object
            // Note that create product url accepts POST method
            phpRequest.makeHttpRequest(pushResponseUrl, "POST", params);
            return null;
        }
        @Override
        protected void onPostExecute(String result) {
            // TODO Auto-generated method stub
            super.onPostExecute(result);

        }

    }
    @Override
    public void onTaskRemoved(Intent rootIntent) {
        // TODO Auto-generated method stub
        Intent restartService = new Intent(getApplicationContext(),
                this.getClass());
        restartService.setPackage(getPackageName());
        PendingIntent restartServicePI = PendingIntent.getService(
                getApplicationContext(), 1, restartService,
                PendingIntent.FLAG_ONE_SHOT);
        AlarmManager alarmService = (AlarmManager)getApplicationContext().getSystemService(Context.ALARM_SERVICE);
        alarmService.set(AlarmManager.ELAPSED_REALTIME, SystemClock.elapsedRealtime() +1000, restartServicePI);

    }


    private void ensureServiceStaysRunning() {
        // KitKat appears to have (in some cases) forgotten how to honor START_STICKY
        // and if the service is killed, it doesn't restart.  On an emulator & AOSP device, it restarts...
        // on my CM device, it does not - WTF?  So, we'll make sure it gets back
        // up and running in a minimum of 20 minutes.  We reset our timer on a handler every
        // 2 minutes...but since the handler runs on uptime vs. the alarm which is on realtime,
        // it is entirely possible that the alarm doesn't get reset.  So - we make it a noop,
        // but this will still count against the app as a wakelock when it triggers.  Oh well,
        // it should never cause a device wakeup.  We're also at SDK 19 preferred, so the alarm
        // mgr set algorithm is better on memory consumption which is good.
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT)
        {
            // A restart intent - this never changes...
            final int restartAlarmInterval = 20*1000;
            final int resetAlarmTimer = 2*1000;
            final Intent restartIntent = new Intent(this, GCMIntentService.class);
            restartIntent.putExtra("ALARM_RESTART_SERVICE_DIED", true);
            final AlarmManager alarmMgr = (AlarmManager)getSystemService(Context.ALARM_SERVICE);
            Handler restartServiceHandler = new Handler()
            {
                @Override
                public void handleMessage(Message msg) {
                    // Create a pending intent
                    PendingIntent pintent = PendingIntent.getService(getApplicationContext(), 0, restartIntent, 0);
                    alarmMgr.set(AlarmManager.ELAPSED_REALTIME, SystemClock.elapsedRealtime() + restartAlarmInterval, pintent);
                    sendEmptyMessageDelayed(0, resetAlarmTimer);
                }
            };
            restartServiceHandler.sendEmptyMessageDelayed(0, 0);
        }
    }



}
