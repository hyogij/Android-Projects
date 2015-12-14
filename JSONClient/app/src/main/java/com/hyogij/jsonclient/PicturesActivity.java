package com.hyogij.jsonclient;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.hyogij.jsonclient.ImageLoaderUtils.LazyAdapter;
import com.hyogij.jsonclient.JSonRequestUtils.ServiceHandler;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by hyogij on 2015. 12. 12..
 */
public class PicturesActivity extends Activity {
    private static final String CLASS_NAME = PicturesActivity.class
            .getCanonicalName();

    // URL to get users JSON
    private static String URL_PREFIX = "http://jsonplaceholder.typicode" +
            ".com/photos?albumId=";

    // JSON Node names
    private static final String TAG_ID = "id";
    private static final String TAG_ALBUMID = "albumId";
    private static final String TAG_TITLE = "title";
    private static final String TAG_URL = "url";
    private static final String TAG_THUMBNAILURL = "thumbnailUrl";

    private ProgressDialog progressDialog = null;
    private ArrayList<HashMap<String, String>> pictureList = null;

    ListView list;
    LazyAdapter adapter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.pictures_activity);

        list = (ListView) findViewById(R.id.list);

        Intent intent = getIntent();
        String albumId = intent.getStringExtra(TAG_ALBUMID);

        pictureList = new ArrayList<HashMap<String, String>>();
        // Calling async task to get json
        new GetUsers().execute(URL_PREFIX + albumId);
    }

    @Override
    public void onDestroy() {
        list.setAdapter(null);
        super.onDestroy();
    }

    // Async task class to get json by making HTTP call
    private class GetUsers extends AsyncTask<String, Void, Void> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            // Showing progress dialog
            progressDialog = new ProgressDialog(PicturesActivity.this);
            progressDialog.setMessage("Please wait...");
            progressDialog.setCancelable(false);
            progressDialog.show();
        }

        @Override
        protected Void doInBackground(String... urls) {
            // Creating service handler class instance
            ServiceHandler sh = new ServiceHandler();
            // Making a request to url and getting response
            String jsonStr = sh.makeServiceCall(urls[0], ServiceHandler.GET);

            if (jsonStr != null) {
                try {
                    JSONArray jarray = new JSONArray(jsonStr);

                    for (int i = 0; i < jarray.length(); i++) {
                        JSONObject jsonObject = jarray.getJSONObject(i);
                        String id = jsonObject.getString(TAG_ID);
                        String albumId = jsonObject.getString(TAG_ALBUMID);
                        String title = jsonObject.getString(TAG_TITLE);
                        String url = jsonObject.getString(TAG_URL);
                        String thumbnailUrl = jsonObject.getString
                                (TAG_THUMBNAILURL);

                        // tmp hashmap for single contact
                        HashMap<String, String> picture = new HashMap<String,
                                String>();

                        // adding each child node to HashMap key => value
                        picture.put(TAG_ID, id);
                        picture.put(TAG_ALBUMID, albumId);
                        picture.put(TAG_TITLE, title);
                        picture.put(TAG_URL, url);
                        picture.put(TAG_THUMBNAILURL, thumbnailUrl);

                        // adding contact to picture list
                        pictureList.add(picture);
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
            adapter = new LazyAdapter(PicturesActivity.this, pictureList);
            list.setAdapter(adapter);
            list.setOnItemClickListener(itemClickListener);
        }
    }

    private AdapterView.OnItemClickListener itemClickListener = new
            AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int
                        position,
                                        long l_position) {
                    HashMap<String, String> picture = pictureList.get(position);
                    Intent pictureViewIntent = new Intent(PicturesActivity
                            .this, PictureViewActivity.class);
                    pictureViewIntent.putExtra(TAG_URL, picture.get(TAG_URL));
                    startActivity(pictureViewIntent);
                }
            };
}