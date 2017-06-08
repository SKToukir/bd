package com.vumobile.shaboxbuddy.util;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.SystemClock;
import android.util.Log;

import com.vumobile.shaboxbuddy.GCMIntentService;

import static android.content.Context.ALARM_SERVICE;

/**
 * Created by toukirul on 15/2/2017.
 */

public class MyReceiver extends BroadcastReceiver
{

    private PendingIntent pendingIntent;

    @Override
    public void onReceive(Context context, Intent intent)
    {
		/*Intent service1 = new Intent(context, MyAlarmService.class);
	     context.startService(service1);*/
        Log.i("App", "called receiver method");
        try{
            //Utils.generateNotification(context);
            // Utils.sendNotification(context,"Amar","Sticker");
            if (Intent.ACTION_BOOT_COMPLETED.equals(intent.getAction())) {
                Intent serviceIntent = new Intent(context, GCMIntentService.class);
                context.startService(serviceIntent);
                Intent myIntent = new Intent(context, MyReceiver.class);
                pendingIntent = PendingIntent.getBroadcast(context, 0, myIntent, 0);

                AlarmManager alarmManager = (AlarmManager)context.getSystemService(ALARM_SERVICE);
                alarmManager.setRepeating(AlarmManager.ELAPSED_REALTIME_WAKEUP, SystemClock.elapsedRealtime(), 90 * 1000, pendingIntent);

            }
            context.startService(new Intent(context.getApplicationContext(), GCMIntentService.class));

        }catch(Exception e){
            e.printStackTrace();
        }
    }

}
