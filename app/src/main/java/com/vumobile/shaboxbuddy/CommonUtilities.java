package com.vumobile.shaboxbuddy;

import android.content.Context;
import android.content.Intent;
import android.util.Log;

public final class CommonUtilities {

    // give your server registration url here

    static final String SERVER_URL = "http://203.76.126.210/sbgcm_server/register.php";

    // Google project id
    public static final String SENDER_ID = "910685358180";


    // Shabox BD Project Number 910685358180
    // Shabox Api Key AIzaSyALWKuPCXoXSevEhHNlbfMDDxPGPpM0cV8
    /**
     * Tag used on log messages.
     */
    static final String TAG = "ShaboxBuddy GCM";

    public static final String DISPLAY_MESSAGE_ACTION ="com.vumobile.shaboxbuddy.DISPLAY_MESSAGE";

    public static final String EXTRA_MESSAGE = "message";

    /**
     * Notifies UI to display a message.
     * <p>
     * This method is defined in the common helper because it's used both by
     * the UI and the background service.
     *
     * @param context application's context.
     * @param message message to be displayed.
     */
    static void displayMessage(Context context, String message) {
        Intent intent = new Intent(DISPLAY_MESSAGE_ACTION);
        intent.putExtra(EXTRA_MESSAGE, message);
        Log.i("Tracker", "This is Common Utilites");
        context.sendBroadcast(intent);
    }
}
