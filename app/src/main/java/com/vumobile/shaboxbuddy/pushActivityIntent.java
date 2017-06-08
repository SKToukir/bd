package com.vumobile.shaboxbuddy;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

public class pushActivityIntent extends Activity {



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_push_activity_intent);

        Intent getserviceidIntent = getIntent();
        String serviceid = getserviceidIntent.getStringExtra("serviceid");



        Log.d("pushActivity","This is push Activity");

        Intent pushActivityIntentpass=new Intent(pushActivityIntent.this,ShaboxBuddyMainActivity.class);
        pushActivityIntentpass.putExtra("serviceid",serviceid);
        startActivity(pushActivityIntentpass);




    }

}


