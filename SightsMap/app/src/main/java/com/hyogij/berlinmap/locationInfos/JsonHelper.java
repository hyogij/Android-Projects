package com.hyogij.berlinmap.locationInfos;

import android.content.Context;
import android.os.AsyncTask;
import android.os.Handler;
import android.util.Log;

import com.hyogij.berlinmap.MainActivity;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

/*
 * Description : JsonHelper class for handling json data
 * Date : 2015.11.16
 * Author : hyogij@gmail.com
 */
public class JsonHelper {
    private static final String CLASS_NAME = MainActivity.class.getCanonicalName();
    // TODO(hyogij): Modify below url that works well
    private static final String JSON_DATA_URL = "";

    private SQLiteHelper sqlHelper = null;
    private Handler handler = null;

    public JsonHelper(Context context, Handler handler) {
        sqlHelper = new SQLiteHelper(context);
        this.handler = handler;

        // Call AsynTask to perform network operation on separate thread
        new HttpAsyncTask().execute(JSON_DATA_URL);
    }

    private class HttpAsyncTask extends AsyncTask<String, Void, String> {
        @Override
        protected String doInBackground(String... urls) {
            return request(urls[0]);
        }

        @Override
        protected void onPostExecute(String result) {
            JSONObject json = null;
            try {
                // Send message to caller to notify finishing task
                handler.sendEmptyMessage(0);

                json = new JSONObject(result);

                // Parse the results of the AsyncTask
                parseJSONData(json);
            } catch (JSONException e) {
                Log.d(CLASS_NAME, e.getMessage());
            }
        }
    }

    private String request(String urlString) {
        StringBuffer chaine = new StringBuffer("");
        try {
            URL url = new URL(urlString);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestProperty("User-Agent", "");
            connection.setRequestMethod("GET");
            connection.setDoInput(true);
            connection.connect();

            InputStream inputStream = connection.getInputStream();
            BufferedReader rd = new BufferedReader(new InputStreamReader(inputStream));
            String line = "";
            while ((line = rd.readLine()) != null) {
                chaine.append(line);
            }
        } catch (IOException e) {
            Log.d(CLASS_NAME, e.getMessage());
        } catch (Exception e) {
            Log.d(CLASS_NAME, e.getMessage());
        }

        return chaine.toString();
    }

    public void parseJSONData(JSONObject json) {
        JSONArray items = null;
        try {
            items = json.getJSONArray("items");
            for (int i = 0; i < items.length(); i++) {
                LocationInfo locationInfo = new LocationInfo(
                        items.getJSONObject(i).getDouble("latitude"),
                        items.getJSONObject(i).getDouble("longitude"),
                        items.getJSONObject(i).getString("name"),
                        items.getJSONObject(i).getString("description"),
                        items.getJSONObject(i).getString("url"));

                // Add location information to the database
                sqlHelper.addLocationInfo(locationInfo);
            }
        } catch (JSONException e) {
            Log.d(CLASS_NAME, e.getMessage());
        }
        return;
    }
}