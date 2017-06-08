package com.vumobile.shaboxbuddy.util;

import android.accounts.Account;
import android.accounts.AccountManager;
import android.content.Context;
import android.os.Build;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.util.Patterns;

import java.util.regex.Pattern;

/**
 * Created by toukirul on 15/2/2017.
 */

public class UserInfoClass {
    //private Context mContext;

    public String userEmail(Context context){

        String possibleEmail = null;

        Pattern emailPattern = Patterns.EMAIL_ADDRESS; // API level 8+
        Account[] accounts = AccountManager.get(context).getAccountsByType("com.google");

        for (Account account : accounts) {
            if (emailPattern.matcher(account.name).matches()) {
                possibleEmail = account.name;
                Log.i("MY_EMAIL_count","" + possibleEmail);
                break;
            }
        }

        return possibleEmail;
    }

    public String deviceName(Context context){

        return Build.BRAND;
    }

    public String deviceModel(Context context){

        return Build.MODEL;
    }

    public String deviceMANUFACTURER(Context context){

        return Build.MANUFACTURER;
    }

    public String deviceID(Context context){
        TelephonyManager telephonyManager = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
        Log.i("DEVICE_MODEL_IMEI","" + telephonyManager.getDeviceId());

        return telephonyManager.getDeviceId();
    }
}
