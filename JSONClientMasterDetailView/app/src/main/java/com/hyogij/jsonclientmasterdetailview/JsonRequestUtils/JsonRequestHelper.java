package com.hyogij.jsonclientmasterdetailview.JsonRequestUtils;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Handler;
import android.util.Log;

import com.hyogij.jsonclientmasterdetailview.R;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * An helper class for JSON request.
 */
public class JsonRequestHelper {
    private static final String CLASS_NAME = JsonRequestHelper.class
            .getCanonicalName();

    private ProgressDialog progressDialog = null;
    private Handler handler = null;
    private Context context;
    private String jsonData = null;

    public JsonRequestHelper(Context context, Handler handler, String url) {
        this.context = context;
        this.handler = handler;

        // Call AsynTask to perform network operation on separate thread
        new HttpAsyncTask().execute(url);
    }

    public String getJsonData() {
        Log.d(CLASS_NAME, jsonData.toString());
        return jsonData;
    }

    // Async task class to get json by making HTTP call
    private class HttpAsyncTask extends AsyncTask<String, Void, String> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            // Showing progress dialog

            progressDialog = new ProgressDialog(context);
            progressDialog.setMessage(context.getString(R.string.wait));
            progressDialog.setCancelable(false);
            progressDialog.show();
        }

        @Override
        protected String doInBackground(String... urls) {
            return request(urls[0]);
        }

        @Override
        protected void onPostExecute(String result) {
            // Dismiss the progress dialog
            if (progressDialog.isShowing()) {
                progressDialog.dismiss();
            }

            jsonData = result;

            // Send message to caller to notify finishing task
            if (jsonData.length() != 0) {
                handler.sendEmptyMessage(0);    // Success
            } else {
                handler.sendEmptyMessage(1);    // Fail
            }
        }
    }

    private String request(String urlString) {
        StringBuffer chaine = new StringBuffer("");
        try {
            URL url = new URL(urlString);
            HttpURLConnection connection = (HttpURLConnection) url
                    .openConnection();
            connection.setRequestProperty("User-Agent", "");
            connection.setRequestMethod("GET");
            connection.setDoInput(true);
            connection.connect();

            InputStream inputStream = connection.getInputStream();
            BufferedReader rd = new BufferedReader(new InputStreamReader
                    (inputStream));
            String line = "";
            while ((line = rd.readLine()) != null) {
                chaine.append(line);
            }
        } catch (IOException e) {
            Log.d(CLASS_NAME, "IOException " + e.getMessage());
        } catch (Exception e) {
            Log.d(CLASS_NAME, "Exception " + e.getMessage());
        }

        return chaine.toString();
    }
}
