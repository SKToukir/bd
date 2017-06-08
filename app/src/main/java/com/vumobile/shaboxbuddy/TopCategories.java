package com.vumobile.shaboxbuddy;

import android.accounts.Account;
import android.accounts.AccountManager;
import android.app.Activity;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.webkit.WebView;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonArrayRequest;
import com.vumobile.imageadapter.TopImageAdapter;
import com.vumobile.shaboxbuddy.app.AppController;
import com.vumobile.shaboxbuddy.model.Categories;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

@SuppressWarnings("MissingPermission")
public class TopCategories extends Activity {
	private static final String TAG = TopCategories.class.getSimpleName();
	public static String service_Id;
	// Movies json url
	private static final String topCategoriesUrl = "http://203.76.126.210/shaboxbuddy/get_categories_new.php?callback=&cat=5";
	private ProgressDialog pDialog;
	private List<Categories> topCategoryList = new ArrayList<Categories>();
	private GridView topGridView;
	private TopImageAdapter topImageAdapter;
    public static String updateReasonString;
    public static boolean updateDialogOpenOrnotTopCategory;
    public static String updateDialogText;

	public String emailID;


	String[] serviceID;
	String[] serviceTitle;
	String[] imageName;
	String[] numberOfLike;
	public String UpdateString;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
        Log.i("Tracker", "This is TopCategories");
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.topcategories);


        topGridView = (GridView) findViewById(R.id.topcategoriesgridview);
		topImageAdapter = new TopImageAdapter(this, topCategoryList);
		topGridView.setAdapter(topImageAdapter);


        //--------------On item click listener for top view---------------

		topGridView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				// TODO Auto-generated method stub
                if (updateDialogOpenOrnotTopCategory==true){
                Update();
                }

                else {
                    service_Id = serviceID[position];
                    Log.i("serviceId",serviceID[position]);
                    ServiceQuery sq = new ServiceQuery(TopCategories.this,
                            getApplication());
				/*
				 * show the please wait dialog while msisdn still not detected
				 * but user click on the content
				 */
                    try{
						if (sq.getMSISDN().equalsIgnoreCase("START")) {
							sq.msisdnDetectionRunning(serviceID[position]);
							return;
						}
					}catch(NullPointerException e){
						e.printStackTrace();
					}

                    // If WIFI and open MSISDN entry dialog
                    if (sq.getMSISDN().equalsIgnoreCase("ERROR")) {
                        // Toast.makeText(getApplicationContext(),sq.getMSISDN()+serviceID[position]
                        // , Toast.LENGTH_SHORT).show();

                        sq.entryMSISDN(serviceID[position]);
                        return;
                    }

                    sq.getServiceText(serviceID[position]);
                //Log.i("serviceText",sq.getServiceText(serviceID[position]));
                }
            }
		});

		pDialog = new ProgressDialog(this);
		// Showing progress dialog before making http request
		pDialog.setMessage("Loading...");
		pDialog.show();
		pDialog.setCancelable(true);

		// Creating volley request obj
		JsonArrayRequest movieReq = new JsonArrayRequest(topCategoriesUrl,
				new Response.Listener<JSONArray>() {
					@Override
					public void onResponse(JSONArray response) {
						Log.d(TAG, response.toString());
						hidePDialog();
						serviceTitle = new String[response.length()];
						serviceID = new String[response.length()];
                        Log.d("serviceid top",serviceID.toString());
						numberOfLike = new String[response.length()];
						imageName = new String[response.length()];
						// Parsing json
						for (int i = 0; i < response.length(); i++) {
							try {

								JSONObject obj = response.getJSONObject(i);
								Categories topCat = new Categories();
								topCat.setServiceID(String.valueOf(obj
										.get("ServiceID")));
								topCat.setServiceTitle(obj
										.getString("ServiceTitle"));
								 topCat.setImageName("http://wap.shabox.mobi/CMS/UIHeader/CategoryImg/"+obj.getString("ImageName"));
                                 Log.i("ImageName",imageName.toString());
//								topCat.setImageName("http://203.76.126.210/catimage/"
//										+ obj.getString("ImageName"));

								topCat.setCountLike(String.valueOf(obj
										.get("countLike")));
								serviceTitle[i] = obj.getString("ServiceTitle");
								serviceID[i] = String.valueOf(obj
										.get("ServiceID"));
								imageName[i] = obj.getString("ImageName");
								numberOfLike[i] = String.valueOf(obj
										.get("countLike"));

								topCategoryList.add(topCat);

							} catch (JSONException e) {
								e.printStackTrace();
							}

						}

						// notifying list adapter about data changes
						// so that it renders the list view with updated data
						topImageAdapter.notifyDataSetChanged();
					}
				}, new Response.ErrorListener() {
					@Override
					public void onErrorResponse(VolleyError error) {
						VolleyLog.d(TAG, "Error: " + error.getMessage());
						hidePDialog();

					}
				});

		// Adding request to request queue
		AppController.getInstance().addToRequestQueue(movieReq);
	}

	@Override
	public void onDestroy() {
		super.onDestroy();
		hidePDialog();
	}

	private void hidePDialog() {
		if (pDialog != null) {
			pDialog.dismiss();
			pDialog = null;
		}
	}


    //-----------------Checke the update-----------------
    /* Check Update Version Methood */
	public void Update() {


		final Dialog updateDialog = new Dialog(this, android.R.style.Theme_Translucent_NoTitleBar);

		updateDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);

		updateDialog.setContentView(R.layout.update_dialog_activity);
		TextView update_text = (TextView) updateDialog
				.findViewById(R.id.update_text);
		update_text.setText(updateReasonString);
		Button update_app = (Button) updateDialog.findViewById(R.id.update_app);
		ImageView img = (ImageView) updateDialog.findViewById(R.id.imageView1);
		Log.i("Tracker"," The Update dialog is called");

		update_app.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub

				updateDialog.dismiss();
				TopCategories.updateDialogOpenOrnotTopCategory=false;

				/**
				 * if this button is clicked, close current activity
				 */
				final String appPackageName = getPackageName();
				try {
					startActivity(new Intent(Intent.ACTION_VIEW, Uri
							.parse("market://details?id=" + appPackageName)));
				} catch (android.content.ActivityNotFoundException anfe) {
					startActivity(new Intent(
							Intent.ACTION_VIEW,
							Uri.parse("http://play.google.com/store/apps/details?id="
									+ appPackageName)));
				}
				// Post the Update Log
				try {

					emailID=getMailId();
					String updateLogString="http://203.76.126.210/shaboxbuddy/All_AppUpdateLog.php?Email="+emailID+
							"&MNO="+AppConstant.mno+"&AppName=SB&AppVersion="+UpdateString.toString().trim();
					WebView updateWebView=new WebView(TopCategories.this);
					updateWebView.loadUrl(updateLogString);
				} catch (Exception e) {
					e.printStackTrace();
				}


			}
		});

		img.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				updateDialog.dismiss();
			}
		});

		updateDialog.show();

	}


	//Get the mail id configured with Google pLay store
	public String getMailId() {
		String strGmail = null;
		try {
			Account[] accounts = AccountManager.get(this).getAccounts();
			Log.e("PIKLOG", "Size: " + accounts.length);
			for (Account account : accounts) {

				String possibleEmail = account.name;
				String type = account.type;

				if (type.equals("com.google")) {

					strGmail = possibleEmail;
					Log.e("PIKLOG", "Emails: " + strGmail);
					break;
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		return strGmail;
	}






}
