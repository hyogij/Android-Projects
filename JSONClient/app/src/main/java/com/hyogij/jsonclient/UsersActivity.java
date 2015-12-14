package com.hyogij.jsonclient;

import android.app.ListActivity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import com.hyogij.jsonclient.JSonRequestUtils.ServiceHandler;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

public class UsersActivity extends ListActivity {
    private static final String CLASS_NAME = UsersActivity.class
            .getCanonicalName();

    // URL to get users JSON
    private static String url = "http://jsonplaceholder.typicode.com/users";

    // JSON Node names
    private static final String TAG_ID = "id";
    private static final String TAG_NAME = "name";
    private static final String TAG_USERNAME = "username";
    private static final String TAG_EMAIL = "email";
    private static final String TAG_ADDRESS = "address";
    private static final String TAG_PHONE = "phone";
    private static final String TAG_WEBSITE = "website";
    private static final String TAG_COMPANY = "company";
    private static final String TAG_USERID = "userId";

    private ProgressDialog progressDialog = null;
    private ArrayList<HashMap<String, String>> userList = null;
    private ListView listView = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.users_activity);

        listView = getListView();

        userList = new ArrayList<HashMap<String, String>>();
        // Calling async task to get json
        new GetUsers().execute();
    }

    // Async task class to get json by making HTTP call
    private class GetUsers extends AsyncTask<Void, Void, Void> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            // Showing progress dialog
            progressDialog = new ProgressDialog(UsersActivity.this);
            progressDialog.setMessage("Please wait...");
            progressDialog.setCancelable(false);
            progressDialog.show();
        }

        @Override
        protected Void doInBackground(Void... arg0) {
            // Creating service handler class instance
            ServiceHandler sh = new ServiceHandler();
            // Making a request to url and getting response
            String jsonStr = sh.makeServiceCall(url, ServiceHandler.GET);

            if (jsonStr != null) {
                try {
                    JSONArray jarray = new JSONArray(jsonStr);
                    for (int i = 0; i < jarray.length(); i++) {
                        JSONObject jsonObject = jarray.getJSONObject(i);
                        String id = jsonObject.getString(TAG_ID);
                        String name = jsonObject.getString(TAG_NAME);
                        String username = jsonObject.getString
                                (TAG_USERNAME);
                        String email = jsonObject.getString(TAG_EMAIL);
                        String address = jsonObject.getString
                                (TAG_ADDRESS);
                        String phone = jsonObject.getString(TAG_PHONE);
                        String website = jsonObject.getString
                                (TAG_WEBSITE);
                        String company = jsonObject.getString
                                (TAG_COMPANY);

                        HashMap<String, String> user = new HashMap<String,
                                String>();

                        // Adding each child node to HashMap key => value
                        user.put(TAG_ID, id);
                        user.put(TAG_NAME, name);
                        user.put(TAG_USERNAME, username);
                        user.put(TAG_EMAIL, email);
                        user.put(TAG_ADDRESS, address);
                        user.put(TAG_PHONE, phone);
                        user.put(TAG_WEBSITE, website);
                        user.put(TAG_COMPANY, company);

                        // Adding contact to user list
                        userList.add(user);
                    }

                } catch (JSONException e) {
                    Log.d(CLASS_NAME, e.getMessage());
                }
            } else {
                Log.d(CLASS_NAME, "Couldn't get any data from the url");
            }

            return null;
        }

        @Override
        protected void onPostExecute(Void result) {
            super.onPostExecute(result);
            // Dismiss the progress dialog
            if (progressDialog.isShowing())
                progressDialog.dismiss();

            // Updating parsed JSON data into ListView
            ListAdapter adapter = new SimpleAdapter(
                    UsersActivity.this, userList,
                    R.layout.user_item, new String[]{TAG_ID, TAG_NAME,
                    TAG_EMAIL}, new int[]{R.id.id,
                    R.id.name, R.id.email});

            setListAdapter(adapter);
        }
    }

    @Override
    protected void onListItemClick(ListView l, View v, int position, long id) {
        HashMap<String, String> user = (HashMap<String, String>)
                getListAdapter().getItem(position);
        Intent albumIntent = new Intent(UsersActivity
                .this, AlbumsActivity.class);
        albumIntent.putExtra(TAG_USERID, user.get(TAG_ID));
        startActivity(albumIntent);
    }
}
