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

public class AlbumsActivity extends ListActivity {
    private static final String CLASS_NAME = AlbumsActivity.class
            .getCanonicalName();

    // URL to get users JSON
    private static String URL_PREFIX = "http://jsonplaceholder.typicode" +
            ".com/albums?userId=";

    // JSON Node names
    private static final String TAG_ID = "id";
    private static final String TAG_USERID = "userId";
    private static final String TAG_TITLE = "title";
    private static final String TAG_ALBUMID = "albumId";

    private ProgressDialog progressDialog = null;
    private ArrayList<HashMap<String, String>> albumList = null;
    private ListView listView = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.albums_activity);

        listView = getListView();

        Intent intent = getIntent();
        String userId = intent.getStringExtra(TAG_USERID);

        albumList = new ArrayList<HashMap<String, String>>();
        // Calling async task to get json
        new GetUsers().execute(URL_PREFIX + userId);
    }

    // Async task class to get json by making HTTP call
    private class GetUsers extends AsyncTask<String, Void, Void> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            // Showing progress dialog
            progressDialog = new ProgressDialog(AlbumsActivity.this);
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
                        String userid = jsonObject.getString(TAG_USERID);
                        String title = jsonObject.getString(TAG_TITLE);

                        // tmp hashmap for single contact
                        HashMap<String, String> album = new HashMap<String,
                                String>();

                        // adding each child node to HashMap key => value
                        album.put(TAG_ID, id);
                        album.put(TAG_USERID, userid);
                        album.put(TAG_TITLE, title);

                        // adding contact to album list
                        albumList.add(album);
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
                    AlbumsActivity.this, albumList,
                    R.layout.album_item, new String[]{TAG_ID, TAG_TITLE}, new
                    int[]{R
                    .id.id, R
                    .id.title});

            setListAdapter(adapter);
        }
    }

    @Override
    protected void onListItemClick(ListView l, View v, int position, long id) {
        HashMap<String, String> user = (HashMap<String, String>)
                getListAdapter().getItem(position);
        Intent albumIntent = new Intent(AlbumsActivity
                .this, PicturesActivity.class);
        albumIntent.putExtra(TAG_ALBUMID, user.get(TAG_ID));
        startActivity(albumIntent);
    }
}
