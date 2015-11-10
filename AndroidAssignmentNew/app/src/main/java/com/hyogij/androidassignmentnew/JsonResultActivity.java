package com.hyogij.androidassignmentnew;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

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
 * Request Json data through HttpURLConnection and print results
 * Date : 2015.11.10
 * Author : hyogij@gmail.com
 * Reference : http://hmkcode.com/android-parsing-json-data/
 */
public class JsonResultActivity extends AppCompatActivity {
    private static final String CLASS_NAME = JsonResultActivity.class.getCanonicalName();
    private static final String JSON_DATA_URL = "http://hmkcode.appspot.com/rest/controller/get.json";

    private TextView txtResponse = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_json_result);

        // Get reference to the views
        txtResponse = (TextView) findViewById(R.id.txtResponse);

        // Call AsynTask to perform network operation on separate thread
        new HttpAsyncTask().execute(JSON_DATA_URL);
    }

    public void onClose(View view) {
        finish();
    }

    private class HttpAsyncTask extends AsyncTask<String, Void, String> {
        @Override
        protected String doInBackground(String... urls) {
            return request(urls[0]);
        }

        // Displays the results of the AsyncTask
        @Override
        protected void onPostExecute(String result) {
            Toast.makeText(getBaseContext(), "Received!", Toast.LENGTH_LONG).show();
            JSONObject json = null;
            try {
                json = new JSONObject(result);
                txtResponse.setText(printJSONObject(json));
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

    public String printJSONObject(JSONObject json) {
        StringBuffer sb = new StringBuffer();

        JSONArray articles = null;
        try {
            articles = json.getJSONArray("articleList");

            for (int i = 0; i < articles.length(); i++) {
                sb.append("title : ");
                sb.append(articles.getJSONObject(i).getString("title"));
                sb.append("\n");
                sb.append("url : ");
                sb.append(articles.getJSONObject(i).getString("url"));
                sb.append("\n");
                sb.append("categories : ");
                sb.append(articles.getJSONObject(i).getString("categories"));
                sb.append("\n");
                sb.append("tags : ");
                sb.append(articles.getJSONObject(i).getString("tags"));
                sb.append("\n");
            }
        } catch (JSONException e) {
            Log.d(CLASS_NAME, e.getMessage());
        }
        return sb.toString();
    }
}
