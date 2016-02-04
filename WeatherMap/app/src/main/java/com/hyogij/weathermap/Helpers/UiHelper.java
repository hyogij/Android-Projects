package com.hyogij.weathermap.helpers;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Toast;

import com.hyogij.weathermap.datas.Weather;
import com.hyogij.weathermap.R;

/**
 * A helper class for ProgressDialog and Toast message
 */
public class UiHelper {
    private static ProgressDialog progressDialog = null;

    // Showing progress dialog
    public static void showProgressDialog(Context context) {
        progressDialog = new ProgressDialog(context);
        progressDialog.setMessage(context.getString(R.string.wait));
        progressDialog.setCancelable(false);
        progressDialog.show();
    }

    // Dismiss the progress dialog
    public static void hideProgressDialog() {
        if (progressDialog.isShowing()) {
            progressDialog.dismiss();
        }
    }

    public static void showAlertDialog(Context context, Weather weather) {
        AlertDialog.Builder alert = new AlertDialog.Builder(context);
        alert.setTitle(weather.getTitle());

        WebView wv = new WebView(context);
        wv.loadData(weather.getDescription(), "text/html", "UTF-8");
        wv.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                view.loadUrl(url);
                return true;
            }
        });

        alert.setView(wv);
        alert.setNegativeButton(context.getString(R.string.close), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int id) {
                dialog.dismiss();
            }
        });
        alert.show();
    }

    public static void showToast(Context context, String message) {
        Toast.makeText(context, message,
                Toast.LENGTH_SHORT).show();
    }
}
