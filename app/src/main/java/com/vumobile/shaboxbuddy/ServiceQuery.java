package com.vumobile.shaboxbuddy;

import android.app.Application;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.vumobile.network.NetworkChecker;
import com.vumobile.shaboxbuddy.webservice.CallerAll;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class ServiceQuery{

    public static int sofolValue=0;

    static Context mContext;
    Application mApp;
    String serviceText = "RUNNING";
    private Handler handler=new Handler();

    public static boolean stopLoadingState = false;
    public static Utilities utils;

    public static String CheckUserStatus = null;
    public static String SubscriberStatus = null;

    //Start For Like Button
    public NetworkChecker internetConnection = new NetworkChecker();
    public static int dimWidth;
    public static int dimHeight;
    public static String mmodel;
    public static String u = null;
    public static String bbrand = null;
    public static String ipAddress = null;
    public static String mmno = null;
    public static String IMEI = null;
    String robiServiceType="daily";
    //End For Like Button
    boolean nibondhonBatil=false;
    boolean robi=false;
    public static String serviceTextID = null;
    public static String banner_imgUrl = null;
    public static String banner_ReUrl = null;

    public static String result = null;
    public static ProgressDialog progDialog, progressMnoDetecting,progPlainText;
    public static Dialog dialogEnterMSISDN, dialogPincode, dialogConfirmatin,dialogReConfirmatin;


    public static Dialog   robiServicePackOption,robiDialogConfirmatinDaily,robiReShuruDialogDaily, robiDialogReConfirmatinDaily,robiDialogConfirmatinWeekly,robiWapDialog,robiShuruDialogWeekly,robiShuruDialogDaily,robiNibondhonBatilMsg;

    ServiceQuery(Context c, Application apps) {
        Log.i("Tracker", "This is ServiceQuery");
        mContext = c;
        mApp = apps;

        progDialog = new ProgressDialog(mContext);
        progDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progDialog.setCancelable(true);
        progDialog.setMessage("Loading...");
        //progDialog.setMessage("Loading...");
    }

//Open music player from UI List

    public String getMSISDN(){
        //ShaboxBuddyMainActivity.ResultMno = "01611527806";
        return ShaboxBuddyMainActivity.ResultMno;
        //return "ERROR";
    }

    public String getCategory(String contentID){
        if(contentID.equals("5")){
            return ShaboxBuddyMainActivity.resultTopCategory;
        }else
            return ShaboxBuddyMainActivity.resultMoreCategory;
    }

    public String getCategoryMore(String contentID){
        return ShaboxBuddyMainActivity.resultMoreCategory;
    }

    public static void  getHelpPage(){
        Intent mIntent = new Intent(mContext,ServiceResultHelp.class);
        mContext.startActivity(mIntent);
    }

    public  String getServiceText(String id)
    {
        serviceTextID = id;


        //SubscriberStatus = "Y";
        try{
            if(serviceTextID.equals("27") && SubscriberStatus.equals("Y")){
                new getValueAsyncTask().execute("GET_BUDDY_TOP_CHART_SERVICE_TEXT",serviceTextID,"");
                return null;
            }if(serviceTextID.equals("7") && SubscriberStatus.equals("Y")){
                new getValueAsyncTask().execute("GET_BUDDY_SERVICE_TEXT",serviceTextID,"");
                return null;
            }else if(SubscriberStatus.equals("Y")){
                //new getValueAsyncTask().execute("GET_BUDDY_SERVICE_TEXT",serviceTextID,"");
                new getPlainServiceAsyncTask().execute(serviceTextID);
                return null;
            }
        }catch(Exception e){
            Log.i("START_VIEW","" + e.getMessage());
        }

        //Toast.makeText(mContext,"SERVICE ID W " + serviceTextWeatherID, Toast.LENGTH_SHORT).show();



        Utilities verifyMSISDN = new Utilities();
        ShaboxBuddyMainActivity.MSISDN = verifyMSISDN.msisdnVerification(ShaboxBuddyMainActivity.ResultMno);
        new getValueAsyncTask().execute("CHECK_STATUS", ShaboxBuddyMainActivity.MSISDN, serviceTextID);

        return "";
    }

    public String getServiceText(){
        //mApp.viewPanel.setVisibility(View.VISIBLE);
        return serviceText;
    }

    public String getMsisdnSession(){
        //mApp.viewPanel.setVisibility(View.VISIBLE);
        return ShaboxBuddyMainActivity.ResultMno;
    }

    public String msisdnDetectionRunning(String id)
    {
        progressMnoDetecting = new ProgressDialog(mContext);
        progressMnoDetecting.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progressMnoDetecting.setCancelable(true);
        progressMnoDetecting.setMessage("Please wait....");
        progressMnoDetecting.show();

        serviceTextID = id;


        handler.postDelayed(timedTask, 100);

        return null;
    }

    public String entryMSISDN(String id){
        serviceTextID = id;
        //mApp.viewPanel.setVisibility(View.VISIBLE);
        dialogEnterMSISDN = new Dialog(mContext,R.style.MyDialog);
        dialogEnterMSISDN.setContentView(R.layout.msisdn_form);
        dialogEnterMSISDN.setCanceledOnTouchOutside(true);
        Button btnSubmit = (Button) dialogEnterMSISDN.findViewById(R.id.btnOk);
        final EditText txtMNO = (EditText) dialogEnterMSISDN.findViewById(R.id.txtMno);
        AppConstant.mno_wifi=txtMNO.getText().toString().trim();
        btnSubmit.setOnClickListener(new OnClickListener() {
            public void onClick(View v)
            {
                if(txtMNO.getText().toString().length()<11)
                {
                    Toast pinToast=Toast.makeText(mContext,"Please Enter a Valid Mobile No.", Toast.LENGTH_SHORT);
                    pinToast.setGravity(Gravity.CENTER,0,0);
                    pinToast.show();
                }
                else{
                    ShaboxBuddyMainActivity.MSISDN = "" + txtMNO.getText();
                    if(ShaboxBuddyMainActivity.MSISDN.startsWith("88018")||ShaboxBuddyMainActivity.MSISDN.startsWith("018"))
                    {
                        robiWapDialog=new Dialog(mContext,R.style.MyDialog);
                        robiWapDialog.setContentView(R.layout.robiwapdialog);
                        Log.i("tracker","robiwapdialog layout called");
                        robiWapDialog.setCanceledOnTouchOutside(true);
                        dialogEnterMSISDN.dismiss();
                        robiWapDialog.show();
                        Button robiHomeBtn=(Button)robiWapDialog.findViewById(R.id.robiWapHomeButton);
                        robiHomeBtn.setOnClickListener(new OnClickListener() {

                            @Override
                            public void onClick(View v) {

                                robiWapDialog.dismiss();
                            }
                        });
                    }
                    else{
                        new getValueAsyncTask().execute(ShaboxBuddyMainActivity.tag_submit_msisdn, ShaboxBuddyMainActivity.MSISDN, "");
                    }
                }
            }
        });

        dialogEnterMSISDN.show();

        return null;
    }

    /// start enterence popup
    /*public void startChecking() {
       new getValueAsyncTask1().execute( "CHECK_STATUS" , ShaboxBuddyMainActivity.MSISDN, serviceTextID);
    }

    public class getValueAsyncTask1 extends AsyncTask<String, Integer, String>
    {


        @Override
        protected void onPreExecute(){
            super.onPreExecute();

            progDialog = new ProgressDialog(mContext);
            progDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
            progDialog.setCancelable(false);
            progDialog.setMessage("Loading...");
            progDialog.show();
        }

        @Override
        protected String doInBackground(String... murl)

        {
            Log.d("service url",murl.toString());

            String contentID = murl[0];
            String currentProcess = null;

            try{
                currentProcess = murl[0];
                Thread.sleep(1000);
                CallerAll ws = new CallerAll();
                result = "RUNNING";
                ws.serviceID = murl[1]; 	// MSISDN or Service Text Id
                ws.criteria = murl[0]; 	// Process name
                ws.pin = murl[2]; 		// no need to pass pin code
                ws.join();
                ws.start();

                while(result=="RUNNING")
                {
                    try{
                        Thread.sleep(100);
                    }catch(Exception ex){
                    }
                }

                //Log.i("Charging Status","" + result);
            }catch(Exception e){
                Log.d("Background Task", e.toString());
            }

            return murl[0];
        }

        @Override
        protected void onPostExecute(String processResult){
            progDialog.dismiss();
            serviceText = result;
            Log.d("TEXT", result);
            //=====================Check the Operator for set the Operator wise content============
            TelephonyManager tm = (TelephonyManager)mContext.getSystemService(Context.TELEPHONY_SERVICE);
            String number = tm.getNetworkOperatorName().toLowerCase();
            Log.e("sim",number);

            if(result.equalsIgnoreCase("Invalid PinCode")||result.equalsIgnoreCase("Please input Mobile No.")||result.equalsIgnoreCase("Enter a valid Mobile No."))
            {
                Toast pinToast=Toast.makeText(mContext, result, Toast.LENGTH_SHORT);
                pinToast.setGravity(Gravity.CENTER,0,0);
                pinToast.show();

            }
            try {
                if (processResult.equals(ShaboxBuddyMainActivity.tag_submit_msisdn) && result.equals("1")) {
                    dialogEnterMSISDN.dismiss();

                    dialogPincode = new Dialog(mContext, R.style.MyDialog);
                    dialogPincode.setContentView(R.layout.pincode_form);
                    dialogPincode.setCanceledOnTouchOutside(false);
                    Button btnSubmit = (Button) dialogPincode.findViewById(R.id.btnOk);
                    TextView txtClickHere = (TextView) dialogPincode.findViewById(R.id.btn_click_here);
                    final EditText txtPincode = (EditText) dialogPincode.findViewById(R.id.txtPinCode);

                    btnSubmit.setOnClickListener(new OnClickListener() {
                        public void onClick(View v) {
                            dialogEnterMSISDN.dismiss();
                            txtPincode.getText();
                            if (txtPincode.getText().toString().equalsIgnoreCase("")) {
                                Toast pinToast = Toast.makeText(mContext, "Please give PIN Code", Toast.LENGTH_SHORT);
                                pinToast.setGravity(Gravity.CENTER, 0, 0);
                                pinToast.show();
                            } else {
                                Utilities verifyMSISDN = new Utilities();
                                ShaboxBuddyMainActivity.MSISDN = verifyMSISDN.msisdnVerification(ShaboxBuddyMainActivity.MSISDN);
                                new getValueAsyncTask1().execute("SUBMIT_PINCODE", ShaboxBuddyMainActivity.MSISDN, "" + txtPincode.getText());
                            }
                        }
                    });

                    txtClickHere.setOnClickListener(new OnClickListener() {
                        public void onClick(View v) {
                            try {
                                dialogEnterMSISDN.dismiss();
                                Utilities verifyMSISDN = new Utilities();
                                ShaboxBuddyMainActivity.MSISDN = verifyMSISDN.msisdnVerification(ShaboxBuddyMainActivity.MSISDN);
                                new getValueAsyncTask1().execute("FORGOT_PIN_CODE", ShaboxBuddyMainActivity.MSISDN, "" + txtPincode.getText().toString());
                            } catch (Exception e) {
                                //Log.i("Exception Forget Pin code","" + e.getMessage());
                            }

                        }
                    });

                    dialogPincode.show();

                } else if (processResult.equals(ShaboxBuddyMainActivity.tag_submit_pin) && result.equals("1")) {
                    //dialogEnterMSISDN.dismiss();
                    dialogPincode.dismiss();
                    //Toast.makeText(mContext,"Successfully completed", Toast.LENGTH_SHORT).show();
                    Utilities verifyMSISDN = new Utilities();
                    ShaboxBuddyMainActivity.MSISDN = verifyMSISDN.msisdnVerification(ShaboxBuddyMainActivity.MSISDN);
                    ShaboxBuddyMainActivity.ResultMno = ShaboxBuddyMainActivity.MSISDN;
                    new getValueAsyncTask1().execute("CHECK_STATUS", ShaboxBuddyMainActivity.MSISDN, serviceTextID);

                } else if (processResult.equals("CHECK_STATUS") && result.equals("0")) {
                    if (ShaboxBuddyMainActivity.ResultMno.startsWith("88018") || ShaboxBuddyMainActivity.ResultMno.startsWith("018")) {

                        robiDialogConfirmatinDaily = new Dialog(mContext, R.style.MyDialog);
                        robiDialogConfirmatinDaily.setContentView(R.layout.robiconfirmationdialogdaily);
                        robiDialogConfirmatinDaily.setCanceledOnTouchOutside(false);
                        ImageButton nibondhonButton = (ImageButton) robiDialogConfirmatinDaily.findViewById(R.id.robinibondhonbtn);
                        ImageButton batilButton = (ImageButton) robiDialogConfirmatinDaily.findViewById(R.id.robibatilbutton);
                        ImageButton close_btn = (ImageButton) robiDialogConfirmatinDaily.findViewById(R.id.close_btn);

                        nibondhonButton.setOnClickListener(new OnClickListener() {

                            @Override
                            public void onClick(View v) {

                                nibondhonBatil = false;
                                Utilities verifyMSISDN = new Utilities();
                                ShaboxBuddyMainActivity.MSISDN = verifyMSISDN.msisdnVerification(ShaboxBuddyMainActivity.MSISDN);
                                new getValueAsyncTask1().execute("GET_SUBSCRIBER_STATUS_ROBI_DAILY", ShaboxBuddyMainActivity.MSISDN, serviceTextID);
                                robi = true;
                                robiServiceType = "daily";
                                //sendMSDNtoSOAP();
                                robiDialogConfirmatinDaily.dismiss();
                                //robiShuruDialog.show();
                            }
                        });


                        batilButton.setOnClickListener(new OnClickListener() {

                            @Override
                            public void onClick(View v) {

                                robiDialogConfirmatinDaily.dismiss();
                            }
                        });

                        close_btn.setOnClickListener(new OnClickListener() {

                            @Override
                            public void onClick(View v) {

                                robiDialogConfirmatinDaily.dismiss();
                            }
                        });

                        robiDialogConfirmatinDaily.show();

                    } else if (ShaboxBuddyMainActivity.ResultMno.startsWith("88017") || ShaboxBuddyMainActivity.ResultMno.startsWith("017")) {
                        try {

                            dialogConfirmatin = new Dialog(mContext, R.style.MyDialog);
                            dialogConfirmatin.setContentView(R.layout.gp_confirmation);
                            dialogConfirmatin.setCanceledOnTouchOutside(false);
                            ImageButton btnRegister = (ImageButton) dialogConfirmatin.findViewById(R.id.btnRegister);
                            ImageButton close_btn = (ImageButton) dialogConfirmatin.findViewById(R.id.close_btn);

                            btnRegister.setOnClickListener(new OnClickListener() {
                                public void onClick(View v) {

                                    dialogReConfirmatin = new Dialog(mContext, R.style.MyDialog);
                                    dialogReConfirmatin.setContentView(R.layout.gp_reconfirmation);
                                    dialogReConfirmatin.setCanceledOnTouchOutside(false);
                                    final ImageButton reconfirm_btnRegister = (ImageButton) dialogReConfirmatin.findViewById(R.id.reconfirm_btnRegister);
                                    final ImageButton reconfirm_close_btn = (ImageButton) dialogReConfirmatin.findViewById(R.id.reconfirm_close_btn);

                                    dialogReConfirmatin.show();
                                    dialogConfirmatin.dismiss();

                                    reconfirm_btnRegister.setOnClickListener(new OnClickListener() {
                                        @Override
                                        public void onClick(View v) {
                                            // TODO Auto-generated method stub
                                            Utilities verifyMSISDN = new Utilities();
                                            ShaboxBuddyMainActivity.MSISDN = verifyMSISDN.msisdnVerification(ShaboxBuddyMainActivity.MSISDN);
                                            new getValueAsyncTask1().execute("GET_SUBSCRIBER_STATUS", ShaboxBuddyMainActivity.MSISDN, serviceTextID);

                                            dialogReConfirmatin.dismiss();
                                        }
                                    });

                                    reconfirm_close_btn.setOnClickListener(new OnClickListener() {
                                        @Override
                                        public void onClick(View v) {
                                            // TODO Auto-generated method stub
                                            dialogReConfirmatin.dismiss();
                                        }
                                    });

                                }
                            });

                            close_btn.setOnClickListener(new OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    // TODO Auto-generated method stub
                                    dialogConfirmatin.dismiss();
                                }
                            });

                            dialogConfirmatin.show();

                        } catch (Exception e) {
                            Log.i("Dilog Exception", "" + e.getMessage());
                        }
                    } else {
                        try {

                            dialogConfirmatin = new Dialog(mContext, R.style.MyDialog);
                            dialogConfirmatin.setContentView(R.layout.confirmation);
                            dialogConfirmatin.setCanceledOnTouchOutside(false);
                            ImageButton btnRegister = (ImageButton) dialogConfirmatin.findViewById(R.id.btnRegister);
                            ImageButton close_btn = (ImageButton) dialogConfirmatin.findViewById(R.id.close_btn);

                            close_btn.setOnClickListener(new OnClickListener() {
                                public void onClick(View v) {
                                    dialogConfirmatin.dismiss();
                                }
                            });

                            btnRegister.setOnClickListener(new OnClickListener() {
                                public void onClick(View v) {
                                    dialogReConfirmatin = new Dialog(mContext, R.style.MyDialog);
                                    dialogReConfirmatin.setContentView(R.layout.re_confirmation);
                                    dialogReConfirmatin.setCanceledOnTouchOutside(false);
                                    final ImageButton reconfirm_btnRegister = (ImageButton) dialogReConfirmatin.findViewById(R.id.reconfirm_btnRegister);
                                    final ImageButton reconfirm_close_btn = (ImageButton) dialogReConfirmatin.findViewById(R.id.reconfirm_close_btn);
                                    dialogReConfirmatin.show();
                                    dialogConfirmatin.dismiss();
                                    reconfirm_btnRegister.setOnClickListener(new OnClickListener() {
                                        @Override
                                        public void onClick(View v) {
                                            // TODO Auto-generated method stub
                                            Utilities verifyMSISDN = new Utilities();
                                            *//*ShaboxBuddyMainActivity.MSISDN = verifyMSISDN.msisdnVerification(ShaboxBuddyMainActivity.MSISDN);*//*
                                            new getValueAsyncTask1().execute("GET_SUBSCRIBER_STATUS", ShaboxBuddyMainActivity.MSISDN, serviceTextID);
                                            // sendMSDNtoSOAP();
                                            dialogReConfirmatin.dismiss();
                                        }
                                    });

                                    reconfirm_close_btn.setOnClickListener(new OnClickListener() {

                                        @Override
                                        public void onClick(View v) {
                                            // TODO Auto-generated method stub
                                            dialogReConfirmatin.dismiss();
                                        }
                                    });


                                }
                            });

                            dialogConfirmatin.show();

                        } catch (Exception e) {
                            Log.i("Dilog Exception", "" + e.getMessage());
                        }
                    }

                } else if (processResult.equals("CHECK_STATUS") && result.equals("1")) {

                    CheckUserStatus = "1";
                    Utilities verifyMSISDN = new Utilities();
                    ShaboxBuddyMainActivity.MSISDN = verifyMSISDN.msisdnVerification(ShaboxBuddyMainActivity.MSISDN);

                    SubscriberStatus = "Y";

                    if (robi == true) {

                        if (robiServiceType.equalsIgnoreCase("daily")) {

                            robiShuruDialogDaily = new Dialog(mContext, R.style.MyDialog);
                            robiShuruDialogDaily.setContentView(R.layout.robishurudialogdaily);
                            robiShuruDialogDaily.setCanceledOnTouchOutside(false);
                            ImageButton robiShuruBtnDaily = (ImageButton) robiShuruDialogDaily.findViewById(R.id.robiShuruButton);
                            ImageButton robiNibondhonBatilBtn = (ImageButton) robiShuruDialogDaily.findViewById(R.id.robiNibondhonBatilButton);
                            ImageButton robiClose_btn = (ImageButton) robiShuruDialogDaily.findViewById(R.id.re_close_btn);
                            robiShuruBtnDaily.setOnClickListener(new OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    robiShuruDialogDaily.dismiss();
                                    new getPlainServiceAsyncTask().execute(serviceTextID);
                                }
                            });

                            robiNibondhonBatilBtn.setOnClickListener(new OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    robiShuruDialogDaily.dismiss();
                                    nibondhonBatil = true;
                                    SubscriberStatus = "N";
//									Intent start = new Intent(mContext,
//											SplashActivity.class);
//									start.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
//									mContext.startActivity(start);
                                    new getValueAsyncTask1().execute("UN_SUBSCRIBE_ROBI_DAILY", ShaboxBuddyMainActivity.MSISDN, serviceTextID);
                                }
                            });

                            robiClose_btn.setOnClickListener(new OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    robiShuruDialogDaily.dismiss();
                                }
                            });

                            robiShuruDialogDaily.show();

                        }
                    } else {
                        new getPlainServiceAsyncTask().execute(serviceTextID);
                    }


                }

            }catch(Exception e){
                Toast.makeText(mContext,result + " For " + processResult + "  " + e.getMessage(), Toast.LENGTH_SHORT).show();
                Log.i("XCEPTION ON POST","" + result + " For " + processResult + "  " + e.getMessage());
            }


            robiNibondhonBatilMsg =new Dialog(mContext,R.style.MyDialog);
            robiNibondhonBatilMsg.setContentView(R.layout.robinibondhonbatilmsg);
            robiNibondhonBatilMsg.setCanceledOnTouchOutside(false);
            Button robiBatilHomeButton=(Button) robiNibondhonBatilMsg.findViewById(R.id.robinibondhonbatilhome);
            if(nibondhonBatil==true)
            {
                robiNibondhonBatilMsg.show();
                robiBatilHomeButton.setOnClickListener(new OnClickListener() {

                    @Override
                    public void onClick(View v) {

                        robiNibondhonBatilMsg.dismiss();
                    }
                });
            }
            Log.i("Balance Status", result);
        }


    }
*/




    //// start item click Asyn task class//
    public class getValueAsyncTask extends AsyncTask<String, Integer, String>
    {


        @Override
        protected void onPreExecute(){
            super.onPreExecute();

            progDialog = new ProgressDialog(mContext);
            progDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
            progDialog.setCancelable(true);
            progDialog.setMessage("Loading...");
            progDialog.show();
        }

        @Override
        protected String doInBackground(String... url)

        {
            Log.d("service url",url.toString());

                    String contentID = url[0];
            String currentProcess = null;

            try{
                currentProcess = url[0];
                Thread.sleep(1000);
                CallerAll ws = new CallerAll();
                result = "RUNNING";
                ws.serviceID = url[1]; 	// MSISDN or Service Text Id
                ws.criteria = url[0]; 	// Process name
                ws.pin = url[2]; 		// no need to pass pin code
                ws.join();
                ws.start();

                while(result=="RUNNING")
                {
                    try{
                        Thread.sleep(100);
                    }catch(Exception ex){
                    }
                }

                //Log.i("Charging Status","" + result);
            }catch(Exception e){
                Log.d("Background Task", e.toString());
            }

            return url[0];
        }

        @Override
        protected void onPostExecute(String processResult){
            progDialog.dismiss();
            serviceText = result;
            Log.d("TEXT", result);
            //=====================Check the Operator for set the Operator wise content============
            TelephonyManager tm = (TelephonyManager)mContext.getSystemService(Context.TELEPHONY_SERVICE);
            String number = tm.getNetworkOperatorName().toLowerCase();
            Log.e("sim",number);

            if(result.equalsIgnoreCase("Invalid PinCode")||result.equalsIgnoreCase("Please input Mobile No.")||result.equalsIgnoreCase("Enter a valid Mobile No."))
            {
                Toast pinToast=Toast.makeText(mContext, result, Toast.LENGTH_SHORT);
                pinToast.setGravity(Gravity.CENTER,0,0);
                pinToast.show();

            }
            try
            {
                if(processResult.equals(ShaboxBuddyMainActivity.tag_submit_msisdn) && result.equals("1"))
                {
                    dialogEnterMSISDN.dismiss();

                    dialogPincode = new Dialog(mContext,R.style.MyDialog);
                    dialogPincode.setContentView(R.layout.pincode_form);
                    dialogPincode.setCanceledOnTouchOutside(false);
                    Button btnSubmit = (Button) dialogPincode.findViewById(R.id.btnOk);
                    TextView txtClickHere = (TextView) dialogPincode.findViewById(R.id.btn_click_here);
                    final EditText txtPincode = (EditText) dialogPincode.findViewById(R.id.txtPinCode);

                    btnSubmit.setOnClickListener(new OnClickListener() {
                        public void onClick(View v)
                        {
                            dialogEnterMSISDN.dismiss();
                            txtPincode.getText();
                            if(txtPincode.getText().toString().equalsIgnoreCase(""))
                            {
                                Toast pinToast=Toast.makeText(mContext, "Please give PIN Code", Toast.LENGTH_SHORT);
                                pinToast.setGravity(Gravity.CENTER,0,0);
                                pinToast.show();
                            }
                            else
                            {
                                Utilities verifyMSISDN = new Utilities();
                                ShaboxBuddyMainActivity.MSISDN = verifyMSISDN.msisdnVerification(ShaboxBuddyMainActivity.MSISDN);
                                new getValueAsyncTask().execute("SUBMIT_PINCODE",ShaboxBuddyMainActivity.MSISDN,"" + txtPincode.getText());
                            }
                        }
                    });

                    txtClickHere.setOnClickListener(new OnClickListener() {
                        public void onClick(View v)
                        {
                            try{
                                dialogEnterMSISDN.dismiss();
                                Utilities verifyMSISDN = new Utilities();
                                ShaboxBuddyMainActivity.MSISDN = verifyMSISDN.msisdnVerification(ShaboxBuddyMainActivity.MSISDN);
                                new getValueAsyncTask().execute("FORGOT_PIN_CODE",ShaboxBuddyMainActivity.MSISDN,"" + txtPincode.getText().toString());
                            }catch(Exception e){
                                //Log.i("Exception Forget Pin code","" + e.getMessage());
                            }

                        }
                    });

                    dialogPincode.show();

                }else if(processResult.equals(ShaboxBuddyMainActivity.tag_submit_pin) && result.equals("1"))
                {
                    //dialogEnterMSISDN.dismiss();
                    dialogPincode.dismiss();
                    //Toast.makeText(mContext,"Successfully completed", Toast.LENGTH_SHORT).show();
                    Utilities verifyMSISDN = new Utilities();
                    ShaboxBuddyMainActivity.MSISDN = verifyMSISDN.msisdnVerification(ShaboxBuddyMainActivity.MSISDN);
                    ShaboxBuddyMainActivity.ResultMno = ShaboxBuddyMainActivity.MSISDN;
                    new getValueAsyncTask().execute("CHECK_STATUS",ShaboxBuddyMainActivity.MSISDN,serviceTextID);

                }else if(processResult.equals("CHECK_STATUS") && result.equals("0"))
                {
                    if(ShaboxBuddyMainActivity.ResultMno.startsWith("88018")||ShaboxBuddyMainActivity.ResultMno.startsWith("018"))
                    {

//                        robiServicePackOption=new Dialog(mContext,R.style.MyDialog);
//                        robiServicePackOption.setContentView(R.layout.robiservicepackageoption);
//                        robiServicePackOption.setCanceledOnTouchOutside(false);
//                        Button robinibondhonButtonDaily=(Button)robiServicePackOption.findViewById(R.id.robinibondhonbuttondaily);
//                        robinibondhonButtonDaily.setOnClickListener(new OnClickListener()
//                        {
//                            @Override
//                            public void onClick(View v) {
//
//                                robiServicePackOption.dismiss();

                                robiDialogConfirmatinDaily = new Dialog(mContext,R.style.MyDialog);
                                robiDialogConfirmatinDaily.setContentView(R.layout.robiconfirmationdialogdaily);
                                robiDialogConfirmatinDaily.setCanceledOnTouchOutside(false);
                                ImageButton nibondhonButton=(ImageButton)robiDialogConfirmatinDaily.findViewById(R.id.robinibondhonbtn);
                                ImageButton batilButton=(ImageButton)robiDialogConfirmatinDaily.findViewById(R.id.robibatilbutton);
                                ImageButton close_btn=(ImageButton)robiDialogConfirmatinDaily.findViewById(R.id.close_btn);

                             nibondhonButton.setOnClickListener(new OnClickListener() {

                                    @Override
                                    public void onClick(View v) {

                                        nibondhonBatil=false;
                                        Utilities verifyMSISDN = new Utilities();
                                        ShaboxBuddyMainActivity.MSISDN = verifyMSISDN.msisdnVerification(ShaboxBuddyMainActivity.MSISDN);
                                        new getValueAsyncTask().execute("GET_SUBSCRIBER_STATUS_ROBI_DAILY",ShaboxBuddyMainActivity.MSISDN,serviceTextID);
                                        robi=true;
                                        robiServiceType="daily";
                                        //sendMSDNtoSOAP();
                                        robiDialogConfirmatinDaily.dismiss();
                                        //robiShuruDialog.show();
                                    }
                                });


                                batilButton.setOnClickListener(new OnClickListener() {

                                    @Override
                                    public void onClick(View v) {

                                        robiDialogConfirmatinDaily.dismiss();
                                    }
                                });

                        close_btn.setOnClickListener(new OnClickListener() {

                            @Override
                            public void onClick(View v) {

                                robiDialogConfirmatinDaily.dismiss();
                            }
                        });

                                robiDialogConfirmatinDaily.show();

//                            }
//                        });
//
//
//                        Button robinibondhonButtonWeekly=(Button)robiServicePackOption.findViewById(R.id.robinibondhonbuttonweekly);
//                        robinibondhonButtonWeekly.setOnClickListener(new OnClickListener() {
//
//                            @Override
//                            public void onClick(View v) {
//
//
//                                robiServicePackOption.dismiss();
//                                robiDialogConfirmatinWeekly = new Dialog(mContext,R.style.MyDialog);
//                                robiDialogConfirmatinWeekly.setContentView(R.layout.robiconfirmationdialogweekly);
//                                robiDialogConfirmatinWeekly.setCanceledOnTouchOutside(false);
//                                Button nibondhonButton=(Button)robiDialogConfirmatinWeekly.findViewById(R.id.robinibondhonbutton);
//                                Button batilButton=(Button)robiDialogConfirmatinWeekly.findViewById(R.id.robibatilbutton);
//                                nibondhonButton.setOnClickListener(new OnClickListener() {
//
//                                    @Override
//                                    public void onClick(View v) {
//
//
//                                        nibondhonBatil=false;
//                                        Utilities verifyMSISDN = new Utilities();
//                                        ShaboxBuddyMainActivity.MSISDN = verifyMSISDN.msisdnVerification(ShaboxBuddyMainActivity.MSISDN);
//                                        new getValueAsyncTask().execute("GET_SUBSCRIBER_STATUS_ROBI_WEEKLY",ShaboxBuddyMainActivity.MSISDN,serviceTextID);
//                                        robi=true;
//                                        robiServiceType="weekly";
//                                        robiDialogConfirmatinWeekly.dismiss();
//                                        //robiShuruDialog.show();
//
//                                    }
//                                });
//                                batilButton.setOnClickListener(new OnClickListener() {
//
//                                    @Override
//                                    public void onClick(View v) {
//
//                                        robiDialogConfirmatinWeekly.dismiss();
//                                    }
//                                });
//
//                                robiDialogConfirmatinWeekly.show();
//
//                            }
//                        });
//
//                        Button robibatilButtonOption=(Button)robiServicePackOption.findViewById(R.id.robibatilbuttonoption);
//                        robibatilButtonOption.setOnClickListener(new OnClickListener() {
//
//                            @Override
//                            public void onClick(View v) {
//
//                                robiServicePackOption.dismiss();
//                            }
//                        });
//                        robiServicePackOption.show();


                    }
                    else if(ShaboxBuddyMainActivity.ResultMno.startsWith("88017")||ShaboxBuddyMainActivity.ResultMno.startsWith("017"))
                    {
                        try{

                            dialogConfirmatin = new Dialog(mContext,R.style.MyDialog);
                            dialogConfirmatin.setContentView(R.layout.gp_confirmation);
                            dialogConfirmatin.setCanceledOnTouchOutside(false);
                            ImageButton btnRegister = (ImageButton) dialogConfirmatin.findViewById(R.id.btnRegister);
                            ImageButton close_btn = (ImageButton) dialogConfirmatin.findViewById(R.id.close_btn);

                            //TextView txtClickHere = (TextView) dialog.findViewById(R.id.);
                            //final EditText txtPincode = (EditText) dialog.findViewById(R.id.txtPinCode);
                            btnRegister.setOnClickListener(new OnClickListener() {
                                public void onClick(View v) {

                                    dialogReConfirmatin = new Dialog(mContext, R.style.MyDialog);
                                    dialogReConfirmatin.setContentView(R.layout.gp_reconfirmation);
                                    dialogReConfirmatin.setCanceledOnTouchOutside(false);
                                    final ImageButton reconfirm_btnRegister = (ImageButton) dialogReConfirmatin.findViewById(R.id.reconfirm_btnRegister);
                                    final ImageButton reconfirm_close_btn = (ImageButton) dialogReConfirmatin.findViewById(R.id.reconfirm_close_btn);

                                    dialogReConfirmatin.show();
                                    dialogConfirmatin.dismiss();

                                    reconfirm_btnRegister.setOnClickListener(new OnClickListener() {
                                        @Override
                                        public void onClick(View v) {
                                            // TODO Auto-generated method stub
                                            Utilities verifyMSISDN = new Utilities();
                                            ShaboxBuddyMainActivity.MSISDN = verifyMSISDN.msisdnVerification(ShaboxBuddyMainActivity.MSISDN);
                                            new getValueAsyncTask().execute("GET_SUBSCRIBER_STATUS", ShaboxBuddyMainActivity.MSISDN, serviceTextID);
                                           /* CallSoap callSoap=new CallSoap();
                                            callSoap.sendMSDNtoSOAP();*/
                                            dialogReConfirmatin.dismiss();
                                        }
                                    });

                                    reconfirm_close_btn.setOnClickListener(new OnClickListener() {
                                        @Override
                                        public void onClick(View v) {
                                            // TODO Auto-generated method stub
                                            dialogReConfirmatin.dismiss();
                                        }
                                    });

                                }
                            });

                            close_btn.setOnClickListener(new OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    // TODO Auto-generated method stub
                                    dialogConfirmatin.dismiss();
                                }
                            });

                         /*   dialogConfirmatin.setOnCancelListener(new DialogInterface.OnCancelListener() {
                                @Override
                                public void onCancel(DialogInterface dialog) {
                                    //mContext.dialog1.dismiss();
                                }
                            });*/

                            dialogConfirmatin.show();

                        }catch(Exception e){
                            Log.i("Dilog Exception","" + e.getMessage());
                        }
                    }
                    else
                    {
                        try{

                            dialogConfirmatin = new Dialog(mContext,R.style.MyDialog);
                            dialogConfirmatin.setContentView(R.layout.confirmation);
                            dialogConfirmatin.setCanceledOnTouchOutside(false);
                            ImageButton btnRegister = (ImageButton) dialogConfirmatin.findViewById(R.id.btnRegister);
                            ImageButton close_btn = (ImageButton) dialogConfirmatin.findViewById(R.id.close_btn);

                            close_btn.setOnClickListener(new OnClickListener() {
                                public void onClick(View v)
                                {
                                    dialogConfirmatin.dismiss();
                                }
                            });

                            //TextView txtClickHere = (TextView) dialog.findViewById(R.id.);
                            //final EditText txtPincode = (EditText) dialog.findViewById(R.id.txtPinCode);
                            btnRegister.setOnClickListener(new OnClickListener() {
                                public void onClick(View v)
                                {
                                    dialogReConfirmatin = new Dialog(mContext,R.style.MyDialog);
                                    dialogReConfirmatin.setContentView(R.layout.re_confirmation);
                                    dialogReConfirmatin.setCanceledOnTouchOutside(false);
                                    final ImageButton reconfirm_btnRegister = (ImageButton) dialogReConfirmatin.findViewById(R.id.reconfirm_btnRegister);
                                    final ImageButton reconfirm_close_btn = (ImageButton) dialogReConfirmatin.findViewById(R.id.reconfirm_close_btn);
                                    dialogReConfirmatin.show();
                                    dialogConfirmatin.dismiss();
                                    reconfirm_btnRegister.setOnClickListener(new OnClickListener() {
                                        @Override
                                        public void onClick(View v) {
                                            // TODO Auto-generated method stub
                                            Utilities verifyMSISDN = new Utilities();
                                            ShaboxBuddyMainActivity.MSISDN = verifyMSISDN.msisdnVerification(ShaboxBuddyMainActivity.MSISDN);
                                            new getValueAsyncTask().execute("GET_SUBSCRIBER_STATUS", ShaboxBuddyMainActivity.MSISDN, serviceTextID);
                                            // sendMSDNtoSOAP();
                                            dialogReConfirmatin.dismiss();
                                        }
                                    });

                                    reconfirm_close_btn.setOnClickListener(new OnClickListener() {

                                        @Override
                                        public void onClick(View v) {
                                            // TODO Auto-generated method stub
                                            dialogReConfirmatin.dismiss();
                                        }
                                    });


                                }
                            });
                            /*dialogConfirmatin*//*.setOnCancelListener(new DialogInterface.OnCancelListener() {
                                @Override
                                public void onCancel(DialogInterface dialog) {
                                    //mContext.dialog1.dismiss();
                                }
                            });*/

                            dialogConfirmatin.show();

                        }catch(Exception e){
                            Log.i("Dilog Exception","" + e.getMessage());
                        }
                    }

                }
                else if(processResult.equals("CHECK_STATUS") && result.equals("1"))
                {
                    //dialog.dismiss();


                    CheckUserStatus = "1";
                    Utilities verifyMSISDN = new Utilities();
                    ShaboxBuddyMainActivity.MSISDN = verifyMSISDN.msisdnVerification(ShaboxBuddyMainActivity.MSISDN);

                    SubscriberStatus = "Y";
                    if(serviceTextID.equals("27")){
                        new getValueAsyncTask().execute("GET_BUDDY_TOP_CHART_SERVICE_TEXT",serviceTextID,"");

                    }else if(serviceTextID.equals("7")){

                        Intent intent = new Intent(mContext,WeatherListActivity.class);
                        Bundle cShufflePlaylist = new Bundle();
//			    	cShufflePlaylist.putString("ServiceResult", result);
//			    	cShufflePlaylist.putString("ServiceResultWeatherId", serviceTextWeatherID);
//			    	
//
//		    	    intent.putExtras(cShufflePlaylist);
                        mContext.startActivity(intent);

                    }else{
                        if(robi==true){

                            if(robiServiceType.equalsIgnoreCase("weekly"))
                            {

                                sofolValue=1;
                                robiShuruDialogWeekly=new Dialog(mContext,R.style.MyDialog);
                                robiShuruDialogWeekly.setContentView(R.layout.robishurudialogweekly);
                                robiShuruDialogWeekly.setCanceledOnTouchOutside(false);
                                Button robiShuruBtn=(Button)robiShuruDialogWeekly.findViewById(R.id.robiShuruButton);
                                Button robiNibondhonBatilBtn=(Button)robiShuruDialogWeekly.findViewById(R.id.robiNibondhonBatilButton);
                                robiShuruBtn.setOnClickListener(new OnClickListener() {

                                    @Override
                                    public void onClick(View v) {

                                        robiShuruDialogWeekly.dismiss();
                                        new  getPlainServiceAsyncTask().execute(serviceTextID);



                                    }
                                });

                                robiNibondhonBatilBtn.setOnClickListener(new OnClickListener() {

                                    @Override
                                    public void onClick(View v) {

                                        robiShuruDialogWeekly.dismiss();
                                        nibondhonBatil=true;
                                        SubscriberStatus="N";
//							Intent start = new Intent(mContext,
//									SplashActivity.class);
//							start.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
//							mContext.startActivity(start);

                                        new getValueAsyncTask().execute("UN_SUBSCRIBE_ROBI_WEEKLY", ShaboxBuddyMainActivity.MSISDN, serviceTextID);


                                    }
                                });

                                robiShuruDialogWeekly.show();

                            }
                            else if(robiServiceType.equalsIgnoreCase("daily"))
                            {

                                robiShuruDialogDaily=new Dialog(mContext,R.style.MyDialog);
                                robiShuruDialogDaily.setContentView(R.layout.robishurudialogdaily);
                                robiShuruDialogDaily.setCanceledOnTouchOutside(false);
                                ImageButton robiShuruBtnDaily=(ImageButton)robiShuruDialogDaily.findViewById(R.id.robiShuruButton);
                                ImageButton robiNibondhonBatilBtn=(ImageButton)robiShuruDialogDaily.findViewById(R.id.robiNibondhonBatilButton);
                                ImageButton robiClose_btn=(ImageButton)robiShuruDialogDaily.findViewById(R.id.re_close_btn);
                                robiShuruBtnDaily.setOnClickListener(new OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        robiShuruDialogDaily.dismiss();
                                        new getPlainServiceAsyncTask().execute(serviceTextID);
                                    }
                                });

                                robiNibondhonBatilBtn.setOnClickListener(new OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        robiShuruDialogDaily.dismiss();
                                        nibondhonBatil=true;
                                        SubscriberStatus="N";
//									Intent start = new Intent(mContext,
//											SplashActivity.class);
//									start.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
//									mContext.startActivity(start);
                                        new getValueAsyncTask().execute("UN_SUBSCRIBE_ROBI_DAILY", ShaboxBuddyMainActivity.MSISDN, serviceTextID);
                                        }
                                });

                                robiClose_btn.setOnClickListener(new OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        robiShuruDialogDaily.dismiss();
                                    }
                                });

                                robiShuruDialogDaily.show();

                            }


                        }
                        //new getValueAsyncTask().execute("GET_BUDDY_SERVICE_TEXT",serviceTextID,"");

                        else {new  getPlainServiceAsyncTask().execute(serviceTextID);}
                    }


                }else if(processResult.equals("GET_SUBSCRIBER_STATUS")||processResult.equals("GET_SUBSCRIBER_STATUS_ROBI_WEEKLY")||processResult.equals("GET_SUBSCRIBER_STATUS_ROBI_DAILY") && result.equals("Y"))
                {
                    SubscriberStatus = "Y";
                    if(serviceTextID.equals("27")){
                        new getValueAsyncTask().execute("GET_BUDDY_TOP_CHART_SERVICE_TEXT",serviceTextID,"");
                    }else
                        new getValueAsyncTask().execute("GET_BUDDY_SERVICE_TEXT",serviceTextID,"");

                }else if(processResult.equals("GET_SUBSCRIBER_STATUS") && result.equals("N")){
                    //Toast.makeText(mContext," " + result, Toast.LENGTH_SHORT).show();
                    Toast.makeText(mContext,"Service cannot perform due to insufficient balance", Toast.LENGTH_SHORT).show();

                }else if(processResult.equals("GET_BUDDY_SERVICE_TEXT"))
                {
                    if(robi==true)
                    {
                        if(robiServiceType.equalsIgnoreCase("weekly"))
                        {
                            sofolValue=1;
                            robiShuruDialogWeekly=new Dialog(mContext,R.style.MyDialog);
                            robiShuruDialogWeekly.setContentView(R.layout.robishurudialogweekly);
                            robiShuruDialogWeekly.setCanceledOnTouchOutside(false);
                            Button robiShuruBtn=(Button)robiShuruDialogWeekly.findViewById(R.id.robiShuruButton);
                            Button robiNibondhonBatilBtn=(Button)robiShuruDialogWeekly.findViewById(R.id.robiNibondhonBatilButton);
                            robiShuruBtn.setOnClickListener(new OnClickListener() {

                                @Override
                                public void onClick(View v) {

                                    robiShuruDialogWeekly.dismiss();
                                    if(serviceTextID.equals("7")){

                                        Intent intent = new Intent(mContext,WeatherListActivity.class);

//						    	Bundle cShufflePlaylist = new Bundle();
//						    	cShufflePlaylist.putString("ServiceResult", result);
//						    	cShufflePlaylist.putString("ServiceResultWeatherId", serviceTextWeatherID);
//					    	    intent.putExtras(cShufflePlaylist);
                                        mContext.startActivity(intent);

                                    }else{

                                        // Using http post
                                        new getPlainServiceAsyncTask().execute(serviceTextID);
                                    }


                                }
                            });
                            robiNibondhonBatilBtn.setOnClickListener(new OnClickListener() {

                                @Override
                                public void onClick(View v) {

                                    robiShuruDialogWeekly.dismiss();
                                    SubscriberStatus="N";
                                    nibondhonBatil=true;
                                    new getValueAsyncTask().execute("UN_SUBSCRIBE_ROBI_WEEKLY", ShaboxBuddyMainActivity.MSISDN, serviceTextID);
                                }
                            });
                            robiShuruDialogWeekly.show();
                        }
                        else if(robiServiceType.equalsIgnoreCase("daily"));
                        {
                            robiShuruDialogDaily=new Dialog(mContext,R.style.MyDialog);
                            robiShuruDialogDaily.setContentView(R.layout.robishurudialogdaily);
                            robiShuruDialogDaily.setCanceledOnTouchOutside(false);
                            ImageButton robiShuruBtnDaily=(ImageButton)robiShuruDialogDaily.findViewById(R.id.robiShuruButton);
                            ImageButton robiNibondhonBatilBtn=(ImageButton)robiShuruDialogDaily.findViewById(R.id.robiNibondhonBatilButton);
                            ImageButton robiClose_btn=(ImageButton)robiShuruDialogDaily.findViewById(R.id.re_close_btn);
                            robiShuruBtnDaily.setOnClickListener(new OnClickListener() {

                                @Override
                                public void onClick(View v) {
                                    robiShuruDialogDaily.dismiss();
                                    if(serviceTextID.equals("7")){

                                        Intent intent = new Intent(mContext,WeatherListActivity.class);
//						    	Bundle cShufflePlaylist = new Bundle();
//						    	cShufflePlaylist.putString("ServiceResult", result);
//						    	cShufflePlaylist.putString("ServiceResultWeatherId", serviceTextWeatherID);
//					    	    intent.putExtras(cShufflePlaylist);
                                        mContext.startActivity(intent);

                                    }else{

                                        // Using http post
                                        new getPlainServiceAsyncTask().execute(serviceTextID);
                                    }
                                }
                            });

                            robiNibondhonBatilBtn.setOnClickListener(new OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    robiShuruDialogDaily.dismiss();
                                    SubscriberStatus="N";
                                    nibondhonBatil=true;
                                    new getValueAsyncTask().execute("UN_SUBSCRIBE_ROBI_DAILY", ShaboxBuddyMainActivity.MSISDN, serviceTextID);
                                }
                            });

                            robiClose_btn.setOnClickListener(new OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    robiShuruDialogDaily.dismiss();
                                }
                            });

                            robiShuruDialogDaily.show();
                        }

                    }


                    else{
                        if(serviceTextID.equals("7")){

                            Intent intent = new Intent(mContext,WeatherListActivity.class);
//				    	Bundle cShufflePlaylist = new Bundle();
//				    	cShufflePlaylist.putString("ServiceResult", result);
//				    	cShufflePlaylist.putString("ServiceResultWeatherId", serviceTextWeatherID);
//			    	    intent.putExtras(cShufflePlaylist);
                            mContext.startActivity(intent);

                        }else{

                            // Using http post
                            new getPlainServiceAsyncTask().execute(serviceTextID);
                        }
                    }

                }else if(processResult.equals("GET_BUDDY_TOP_CHART_SERVICE_TEXT"))
                {
                    if(robi==true)
                    {
                        if(robiServiceType.equalsIgnoreCase("weekly"))
                        {
                            robiShuruDialogWeekly=new Dialog(mContext,R.style.MyDialog);
                            robiShuruDialogWeekly.setContentView(R.layout.robishurudialogweekly);
                            robiShuruDialogWeekly.setCanceledOnTouchOutside(false);
                            Button robiShuruBtn=(Button)robiShuruDialogWeekly.findViewById(R.id.robiShuruButton);
                            Button robiNibondhonBatilBtn=(Button)robiShuruDialogWeekly.findViewById(R.id.robiNibondhonBatilButton);
                            robiShuruBtn.setOnClickListener(new OnClickListener() {

                                @Override
                                public void onClick(View v) {

                                    robiShuruDialogWeekly.dismiss();
                                    Intent intent = new Intent(mContext,ServiceResultTopChart.class);
                                    Bundle bandle = new Bundle();
                                    bandle.putString("ServiceResultTopChart", result);
                                    intent.putExtras(bandle);

                                    mContext.startActivity(intent);


                                }
                            });
                            robiNibondhonBatilBtn.setOnClickListener(new OnClickListener() {

                                @Override
                                public void onClick(View v) {

                                    robiShuruDialogWeekly.dismiss();
                                    SubscriberStatus="N";
                                    nibondhonBatil=true;
                                    new getValueAsyncTask().execute("UN_SUBSCRIBE_ROBI_WEEKLY", ShaboxBuddyMainActivity.MSISDN, serviceTextID);
                                }
                            });
                            robiShuruDialogWeekly.show();
                        }
                        else if (robiServiceType.equalsIgnoreCase("daily"))
                        {
                            robiShuruDialogDaily.setContentView(R.layout.robishurudialogdaily);
                            robiShuruDialogDaily.setCanceledOnTouchOutside(false);
                            ImageButton robiShuruBtnDaily=(ImageButton)robiShuruDialogDaily.findViewById(R.id.robiShuruButton);
                            ImageButton robiNibondhonBatilBtn=(ImageButton)robiShuruDialogDaily.findViewById(R.id.robiNibondhonBatilButton);
                            ImageButton robiClose_btn=(ImageButton)robiShuruDialogDaily.findViewById(R.id.re_close_btn);
                            robiShuruBtnDaily.setOnClickListener(new OnClickListener() {

                                @Override
                                public void onClick(View v) {

                                    robiShuruDialogDaily.dismiss();
                                    Intent intent = new Intent(mContext,ServiceResultTopChart.class);
                                    Bundle bandle = new Bundle();
                                    bandle.putString("ServiceResultTopChart", result);
                                    intent.putExtras(bandle);
                                    mContext.startActivity(intent);
                                }
                            });

                            robiNibondhonBatilBtn.setOnClickListener(new OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    robiShuruDialogDaily.dismiss();
                                    SubscriberStatus="N";
                                    nibondhonBatil=true;
                                    new getValueAsyncTask().execute("UN_SUBSCRIBE_ROBI_DAILY", ShaboxBuddyMainActivity.MSISDN, serviceTextID);
                                }
                            });

                            robiClose_btn.setOnClickListener(new OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    robiShuruDialogDaily.dismiss();
                                }
                            });
                            robiShuruDialogDaily.show();
                        }
                    }
                    else{
                        Intent intent = new Intent(mContext,ServiceResultTopChart.class);
                        Bundle bandle = new Bundle();
                        bandle.putString("ServiceResultTopChart", result);
                        intent.putExtras(bandle);
                        mContext.startActivity(intent);
                    }

                }else{
                    //Toast.makeText(mContext," " + result, Toast.LENGTH_SHORT).show();
                }


            }catch(Exception e){
                Toast.makeText(mContext,result + " For " + processResult + "  " + e.getMessage(), Toast.LENGTH_SHORT).show();
                Log.i("XCEPTION ON POST","" + result + " For " + processResult + "  " + e.getMessage());
            }


            robiNibondhonBatilMsg =new Dialog(mContext,R.style.MyDialog);
            robiNibondhonBatilMsg.setContentView(R.layout.robinibondhonbatilmsg);
            robiNibondhonBatilMsg.setCanceledOnTouchOutside(false);
            Button robiBatilHomeButton=(Button) robiNibondhonBatilMsg.findViewById(R.id.robinibondhonbatilhome);
            if(nibondhonBatil==true)
            {
                robiNibondhonBatilMsg.show();
                robiBatilHomeButton.setOnClickListener(new OnClickListener() {

                    @Override
                    public void onClick(View v) {

                        robiNibondhonBatilMsg.dismiss();
                    }
                });
            }
            Log.i("Balance Status", result);
        }


    }

     class getPlainServiceAsyncTask extends AsyncTask<String, String, String> {

        Bitmap bitmap = null;
        String haha;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progPlainText = new ProgressDialog(mContext);
            progPlainText.setProgressStyle(ProgressDialog.STYLE_SPINNER);
            progPlainText.setCancelable(true);
            progPlainText.setMessage("Loading....");
            progPlainText.show();

        }

        @Override
        protected String doInBackground(String... aurl)
        {
            StringBuilder total = new StringBuilder();

            try {

                HttpClient httpclient = new DefaultHttpClient();
                HttpPost httppost = new HttpPost("http://203.76.126.210/shaboxbuddy/index.php");
                // Add your data

                List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>(2);
                nameValuePairs.add(new BasicNameValuePair("ServiceId", "" + aurl[0]));
                haha=aurl[0];
                httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs));

                // Execute HTTP Post Request
                HttpResponse response = httpclient.execute(httppost);

                BufferedReader r = new BufferedReader(new InputStreamReader(response.getEntity().getContent()));

                String line = null;

                while ((line = r.readLine()) != null) {
                    total.append(line);
                }

                //return total.toString();

                if(total.toString().trim().equals("1")){
                    //Log.i("You cross your streaming limit.","Message");
                }

                Log.i("RESPONSE"," " + total.toString().trim());


            } catch (Exception e) {
                Log.i("Error TEXT LOAD","" + e.getMessage());
                return "Error in loading. Please try again";

            }

            return total.toString().trim();

        }
        protected void onProgressUpdate(String... progress) {
            Log.d("ANDRO_ASYNC",progress[0]);
        }
        @Override
        protected void onPostExecute(String result)
        {
            //textResult.setText(""+ result);
            progPlainText.dismiss();
            progDialog.dismiss();


            try
            {

                if(progDialog.isShowing()){
                    progDialog.dismiss();
                }

                if(dialogEnterMSISDN.isShowing()){
                    dialogEnterMSISDN.dismiss();
                }

            }catch(Exception e){

            }

            if(serviceTextID.equals("7")){

                Intent intent = new Intent(mContext,WeatherListActivity.class);
//		    	Bundle cShufflePlaylist = new Bundle();
//		    	cShufflePlaylist.putString("ServiceResult", result);
//		    	cShufflePlaylist.putString("ServiceResultWeatherId", serviceTextWeatherID);
//	    	    intent.putExtras(cShufflePlaylist);
                mContext.startActivity(intent);
                return;
            }else if(serviceTextID.equals("44")){
                Intent intent = new Intent(mContext,FifaSeviceResult.class);
                Bundle valuesBundle = new Bundle();
                valuesBundle.putString("ServiceResult", result);
                intent.putExtras(valuesBundle);
                mContext.startActivity(intent);
            }else {
                Intent intent = new Intent(mContext,SeviceResult.class);
                Bundle valuesBundle = new Bundle();
                valuesBundle.putString("ServiceResult", result);
                intent.putExtras(valuesBundle);
                mContext.startActivity(intent);
            }

            Log.i("ggggggggggggggggggggg"," " + result.toString());

        }


    }

    public Runnable timedTask = new Runnable(){
        @Override
        public void run(){

            try{

                if(!ShaboxBuddyMainActivity.ResultMno.equals("START")){

                    progressMnoDetecting.dismiss();
                    handler.removeCallbacks(timedTask);

                    if(ShaboxBuddyMainActivity.ResultMno.equals("ERROR")){
                        entryMSISDN(serviceTextID);
                    }


                    else{
                        getServiceText(serviceTextID);
                    }

                }else{

                    handler.postDelayed(timedTask, 100);
                }


            }catch(Exception e){
                Log.d("Background Task", e.toString());
            }


        }

    };


}

