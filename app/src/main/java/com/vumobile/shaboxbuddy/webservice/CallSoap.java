package com.vumobile.shaboxbuddy.webservice;

import org.ksoap2.SoapEnvelope;
//import org.ksoap2.serialization.PropertyInfo;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapPrimitive;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;
import org.xmlpull.v1.XmlPullParserException;

import android.util.Log;

import com.vumobile.shaboxbuddy.ShaboxBuddyMainActivity;

import java.io.IOException;

public class CallSoap
{
    public final String SOAP_ACTION = "http://tempuri.org/MSISDN";
    public final String SOAP_ACTION_FOR_USER = "http://tempuri.org/StatusChk";

    public  final String OPERATION_NAME = "MSISDN";
    public  final String OPERATION_NAME_FOR_USER = "StatusChk";

    public  final String WSDL_TARGET_NAMESPACE = "http://tempuri.org/";

    public  final String SOAP_ADDRESS = "http://wap.shabox.mobi/SBAppService/SBService.asmx";
    public  final String SOAP_ADDRESS_FOR_USER = "http://wap.shabox.mobi/CZStatus/";


    public CallSoap(){

    }

    //Detect MSISDN
    public String Call()
    {
        SoapObject request = new SoapObject(WSDL_TARGET_NAMESPACE,OPERATION_NAME);

        SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(
                SoapEnvelope.VER11);
        envelope.dotNet = true;
        envelope.setOutputSoapObject(request);

        HttpTransportSE httpTransport = new HttpTransportSE("http://wap.shabox.mobi/czapp/Service1.asmx");
        Object response = null;

        try {
            httpTransport.call(SOAP_ACTION, envelope);
            response = envelope.getResponse();
            Log.i("MSISDN SOAP","" + response);

        }
        catch (Exception exception){
            response=exception.toString();
            Log.i("MSISDN SOAP XCEPTION","" + response);
            response = "ERROR";
            //response="ERROR";
        }

        return response.toString();
    }

    //check user status
    public String Call(String a) {
        // TODO Auto-generated method stub
        SoapObject request = new SoapObject(WSDL_TARGET_NAMESPACE,OPERATION_NAME_FOR_USER);
        request.addProperty("msisdn",a);

        SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(
                SoapEnvelope.VER11);
        envelope.dotNet = true;

        envelope.setOutputSoapObject(request);
        HttpTransportSE httpTransport = new HttpTransportSE(SOAP_ADDRESS_FOR_USER);
        Object response=null;

        try {
            httpTransport.call(SOAP_ACTION_FOR_USER, envelope);
            response = envelope.getResponse();

        }
        catch (Exception exception){
            //response=exception.toString();
            response="ERROR";
        }

        return response.toString();
    }


    //Web Service For FORGOT PIN CODE, GET BUDDY CATEGORY, GET_BUDDY_SERVICE_TEXT etc
    public String Call_WS(String msisdn,String q,String pin)
    {
        Object response = null;

        if(q.equals("FORGOT_PIN_CODE")){

            SoapObject request = new SoapObject(WSDL_TARGET_NAMESPACE,"GetPinCode");
            request.addProperty("msisdn",msisdn);

            SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(
                    SoapEnvelope.VER11);

            envelope.dotNet = true;
            envelope.setOutputSoapObject(request);

            HttpTransportSE httpTransport = new HttpTransportSE(SOAP_ADDRESS);
            Log.i("FORGET_PIN","" + msisdn);

            try {
                httpTransport.call("http://tempuri.org/GetPinCode", envelope);
                response = envelope.getResponse();
                Log.i("FORGET_PIN_RESULT SOAP","" + response);
            }
            catch (Exception exception){
                //response=exception.toString();
                response="ERROR PINCODE FORGET PIN";
            }
        }else if(q.equals("pincode_check") ){

            SoapObject request = new SoapObject(WSDL_TARGET_NAMESPACE,"chkPin");
            request.addProperty("msisdn",msisdn);
            request.addProperty("pincode",pin);

            SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(
                    SoapEnvelope.VER11);

            envelope.dotNet = true;
            envelope.setOutputSoapObject(request);

            HttpTransportSE httpTransport = new HttpTransportSE(SOAP_ADDRESS_FOR_USER);
            Log.i("PIN_CODE_CHECK","" + msisdn);

            try {
                httpTransport.call("http://tempuri.org/chkPin", envelope);
                response = envelope.getResponse();
            }
            catch (Exception exception){
                response="ERROR PINCODE  CHECKING";
            }
        }else if(q.equals("GET_BUDDY_CATEGORY")){

            SoapObject request = new SoapObject(WSDL_TARGET_NAMESPACE,"GetBuddyCategory");
            request.addProperty("strAction",msisdn);

            SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(
                    SoapEnvelope.VER11);

            envelope.dotNet = true;
            envelope.setOutputSoapObject(request);

            HttpTransportSE httpTransport = new HttpTransportSE(SOAP_ADDRESS,60000);
            //Log.i("GET_ROBI_APP","" + msisdn);

            try {
                httpTransport.call("http://tempuri.org/GetBuddyCategory", envelope);
                response = envelope.getResponse();
                sendMSDNtoSOAP();
            }
            catch (Exception exception){
                response=exception.toString();
                //response="";
                Log.i("GET_ROBI_APP","" + msisdn);
            }

        }else if(q.equals("GET_BUDDY_SERVICE_TEXT")){

            SoapObject request = new SoapObject(WSDL_TARGET_NAMESPACE,"GetBuddyDailyContent");
            request.addProperty("strServiceId",msisdn);

            SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(
                    SoapEnvelope.VER11);

            envelope.dotNet = true;
            envelope.setOutputSoapObject(request);

            HttpTransportSE httpTransport = new HttpTransportSE(SOAP_ADDRESS,60000);
            //Log.i("GET_ROBI_APP","" + msisdn);

            try {
                httpTransport.call("http://tempuri.org/GetBuddyDailyContent", envelope);
                response = envelope.getResponse();
                sendMSDNtoSOAP();
            }
            catch (Exception exception){
                response=exception.toString();
                //response="ERROR PINCODE  CHECKING";
            }

        }else if(q.equals("GET_BUDDY_TOP_CHART_SERVICE_TEXT")){

            SoapObject request = new SoapObject(WSDL_TARGET_NAMESPACE,"GetTopChart");
            //request.addProperty("strServiceId",msisdn);

            SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(
                    SoapEnvelope.VER11);

            envelope.dotNet = true;
            envelope.setOutputSoapObject(request);

            HttpTransportSE httpTransport = new HttpTransportSE(SOAP_ADDRESS,60000);
            //Log.i("GET_ROBI_APP","" + msisdn);

            try {
                httpTransport.call("http://tempuri.org/GetTopChart", envelope);
                response = envelope.getResponse();
                sendMSDNtoSOAP();
            }
            catch (Exception exception){
                response=exception.toString();
                //response="ERROR TOP CHART  CHECKING";
            }

        }else if(q.equals("SUBMIT_MSISDN")){

            SoapObject request = new SoapObject(WSDL_TARGET_NAMESPACE,"onMobileSubmit");
            request.addProperty("strMobileNo",msisdn);

            SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);

            envelope.dotNet = true;
            envelope.setOutputSoapObject(request);

            HttpTransportSE httpTransport = new HttpTransportSE(SOAP_ADDRESS,60000);
            //Log.i("ON SUBMIT","" + msisdn);

            try {
                httpTransport.call("http://tempuri.org/onMobileSubmit", envelope);
                response = envelope.getResponse();
                sendMSDNtoSOAP();
            }
            catch (Exception exception){
                response=exception.toString();
                //response="ERROR PINCODE  CHECKING";
            }

        }else if(q.equals("SUBMIT_PINCODE")){

            SoapObject request = new SoapObject(WSDL_TARGET_NAMESPACE,"onPinCodeSubmit");
            request.addProperty("strMobileNo",msisdn);
            request.addProperty("strPinCode",pin);

            SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(
                    SoapEnvelope.VER11);

            envelope.dotNet = true;
            envelope.setOutputSoapObject(request);

            HttpTransportSE httpTransport = new HttpTransportSE(SOAP_ADDRESS,60000);
            Log.i("ON SUBMIT PIN SOAP","MNo " + msisdn + " Pincode " + pin);

            try {
                httpTransport.call("http://tempuri.org/onPinCodeSubmit", envelope);
                response = envelope.getResponse();
                sendMSDNtoSOAP();
            }
            catch (Exception exception){
                response=exception.toString();
                //response="ERROR PINCODE  CHECKING";
            }

        }else if(q.equals("CHECK_STATUS")){

            SoapObject request = new SoapObject(WSDL_TARGET_NAMESPACE,"CheckStatus");
            request.addProperty("strMobileNo",msisdn);

            SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);

            envelope.dotNet = true;
            envelope.setOutputSoapObject(request);

            HttpTransportSE httpTransport = new HttpTransportSE(SOAP_ADDRESS,60000);


            try {
                httpTransport.call("http://tempuri.org/CheckStatus", envelope);
                response = envelope.getResponse();
                Log.i("CS SOAP RESULT for" + msisdn,"" + response);
                sendMSDNtoSOAP();
            }
            catch (Exception exception){
                response = exception.getMessage();
                //response="CS ERROR" + msisdn + " " + response;
            }

        }
        else if(q.equals("UN_SUBSCRIBE_ROBI_WEEKLY")){

            SoapObject request = new SoapObject(WSDL_TARGET_NAMESPACE,"RobiDeactivate");
            request.addProperty("msisdn",msisdn);
            request.addProperty("charginType","SBW");

            SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);

            envelope.dotNet = true;
            envelope.setOutputSoapObject(request);

            HttpTransportSE httpTransport = new HttpTransportSE(SOAP_ADDRESS,60000);


            try {
                httpTransport.call("http://tempuri.org/RobiDeactivate", envelope);
                response = envelope.getResponse();
                Log.i("CS SOAP RESULT for" + msisdn,"" + response);
                sendMSDNtoSOAP();
            }
            catch (Exception exception){
                response = exception.getMessage();
                //response="CS ERROR" + msisdn + " " + response;
            }

        }

        else if(q.equals("UN_SUBSCRIBE_ROBI_DAILY")){

            SoapObject request = new SoapObject(WSDL_TARGET_NAMESPACE,"RobiDeactivate");
            request.addProperty("msisdn",msisdn);
            request.addProperty("charginType","SBD");

            SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);

            envelope.dotNet = true;
            envelope.setOutputSoapObject(request);

            HttpTransportSE httpTransport = new HttpTransportSE(SOAP_ADDRESS,60000);


            try {
                httpTransport.call("http://tempuri.org/RobiDeactivate", envelope);
                response = envelope.getResponse();
                Log.i("CS SOAP RESULT for" + msisdn,"" + response);
                sendMSDNtoSOAP();
            }
            catch (Exception exception){
                response = exception.getMessage();
                //response="CS ERROR" + msisdn + " " + response;
            }

        }


        else if(q.equals("GET_SUBSCRIBER_STATUS")){



            SoapObject request = new SoapObject(WSDL_TARGET_NAMESPACE,"SubscriberStatus");
            request.addProperty("strMobileNo",msisdn);
            request.addProperty("sid",pin);
            request.addProperty("source","SB_GGL_AND_APP");
            request.addProperty("subType","SB");
            SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);

            envelope.dotNet = true;
            envelope.setOutputSoapObject(request);

            HttpTransportSE httpTransport = new HttpTransportSE(SOAP_ADDRESS);


            try {
                httpTransport.call("http://tempuri.org/SubscriberStatus", envelope);
                response = envelope.getResponse();
                Log.i("SOAP SUBSCRIBER RESULT","" + response);
                sendMSDNtoSOAP();
            }
            catch (Exception exception){
                response = exception.getMessage();
                //response="ERROR SUBSCRIBER for" + msisdn + " " + response;
            }
        }
        else if(q.equals("GET_SUBSCRIBER_STATUS_ROBI_DAILY")){

            SoapObject request = new SoapObject(WSDL_TARGET_NAMESPACE,"SubscriberStatus");
            request.addProperty("strMobileNo",msisdn);
            request.addProperty("sid",pin);
            request.addProperty("source","SB_GGL_AND_APP");
            request.addProperty("subType","SBD");

            SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);

            envelope.dotNet = true;
            envelope.setOutputSoapObject(request);

            HttpTransportSE httpTransport = new HttpTransportSE(SOAP_ADDRESS, 60000);


            try {
                httpTransport.call("http://tempuri.org/SubscriberStatus", envelope);
                response = envelope.getResponse();
                Log.i("SOAP SUBSCRIBER RESULT","" + response);
                sendMSDNtoSOAP();
            }
            catch (Exception exception){
                response = exception.getMessage();
                //response="ERROR SUBSCRIBER for" + msisdn + " " + response;
            }


        }
        else if(q.equals("GET_SUBSCRIBER_STATUS_ROBI_WEEKLY")){

            SoapObject request = new SoapObject(WSDL_TARGET_NAMESPACE,"SubscriberStatus");
            request.addProperty("strMobileNo",msisdn);
            request.addProperty("sid",pin);
            request.addProperty("source","SB_GGL_AND_APP");
            request.addProperty("subType","SBW");
            SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);

            envelope.dotNet = true;
            envelope.setOutputSoapObject(request);

            HttpTransportSE httpTransport = new HttpTransportSE(SOAP_ADDRESS, 60000);


            try {
                httpTransport.call("http://tempuri.org/SubscriberStatus", envelope);
                response = envelope.getResponse();
                Log.i("SOAP SUBSCRIBER RESULT","" + response);
                sendMSDNtoSOAP();
            }
            catch (Exception exception){
                response = exception.getMessage();
                //response="ERROR SUBSCRIBER for" + msisdn + " " + response;
            }


        }

        return response.toString();

    }
   /* public void sendMSDNtoSOAP(){
        String WSDL_TARGET_NAMESPACE = "http://tempuri.org/";

        String SOAP_ADDRESS = "http://wap.shabox.mobi/AppsSubscriptionSource/Service.asmx";//"http://wap.shabox.mobi/SBAppService/SBService.asmx";
        String SOAP_ACTION="http://tempuri.org/InsertAppsSource_Buddy";
        Log.d("Tracker","CallSoap GET_SUBSCRIBER_STATUS");
        SoapObject request = new SoapObject(WSDL_TARGET_NAMESPACE,"InsertAppsSource_Buddy");
        //request.addProperty("MobileNumber","8801788800205");
        request.addProperty("strMobileNo","8801788800205");


        SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);

        envelope.dotNet = true;
        envelope.setOutputSoapObject(request);

        HttpTransportSE httpTransport = new HttpTransportSE(SOAP_ADDRESS);


        try {
            httpTransport.call(SOAP_ACTION, envelope);
            SoapPrimitive response= (SoapPrimitive) envelope.getResponse();
            Log.d("response",response.toString());
            //http://tempuri.org/SubscriberStatus
            //response = envelope.getResponse();
            Log.i("SOAP SUBSCRIBER RESULT","" + response);
            Log.d("Tracker","Call Soap Subscripber result");
        } catch (IOException e) {
            e.printStackTrace();

        } catch (XmlPullParserException e) {
            e.printStackTrace();
        }


    }*/

    //============================Code for update the app source====================

    public void sendMSDNtoSOAP(){
        Object response=null;
        String WSDL_TARGET_NAMESPACE = "http://tempuri.org/";

        String SOAP_ADDRESS = "http://wap.shabox.mobi/AppsSubscriptionSource/Service.asmx";//"http://wap.shabox.mobi/SBAppService/SBService.asmx";
        String SOAP_ACTION="http://tempuri.org/InsertAppsSource_Buddy";//"http://tempuri.org/SubscriberStatus";
        Log.d("Tracker","CallSoap GET_SUBSCRIBER_STATUS");
        SoapObject request = new SoapObject(WSDL_TARGET_NAMESPACE,"InsertAppsSource_Buddy");//"SubscriberStatus");
        request.addProperty("MobileNumber", ShaboxBuddyMainActivity.ResultMno);

        SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);

        envelope.dotNet = true;
        envelope.setOutputSoapObject(request);

        HttpTransportSE httpTransport = new HttpTransportSE(SOAP_ADDRESS);


        try {
            Log.i("SOAP SUBSCRIBER RESULT","" + response);
            httpTransport.call(SOAP_ACTION, envelope);
           response=  envelope.getResponse();
            Log.d("response from send msdn to soap",response.toString());
            //http://tempuri.org/SubscriberStatus
            //response = envelope.getResponse();
            Log.i("SOAP SUBSCRIBER RESULT","" + response);
            Log.d("Tracker","Call Soap Subscripber result");
        } catch (IOException e) {
            e.printStackTrace();

        } catch (XmlPullParserException e) {
            e.printStackTrace();
        }
       /* SoapObject request = new SoapObject(WSDL_TARGET_NAMESPACE,"SubscriberStatus");
        request.addProperty("strMobileNo","01722269258");
        //request.addProperty("sid","46");
        request.addProperty("source","SB_GGL_AND_APP");
        request.addProperty("subType","SB");
        SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);

        envelope.dotNet = true;
        envelope.setOutputSoapObject(request);

        HttpTransportSE httpTransport = new HttpTransportSE(SOAP_ADDRESS);



        try {
            httpTransport.call("http://tempuri.org/SubscriberStatus", envelope);
            response = envelope.getResponse();
            Log.i("SOAP SUBSCRIBER RESULT","" + response);
        }
        catch (Exception exception){
            response = exception.getMessage();
            //response="ERROR SUBSCRIBER for" + msisdn + " " + response;
        }*/


    }



}
