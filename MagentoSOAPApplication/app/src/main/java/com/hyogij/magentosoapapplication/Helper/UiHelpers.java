package com.hyogij.magentosoapapplication.Helper;

import android.app.ProgressDialog;
import android.content.Context;
import android.widget.Toast;

import com.hyogij.magentosoapapplication.R;

/**
 * An utility class for toast message and progress dialog.
 */
public class UiHelpers {
    public static void showToast(Context context, String message) {
        Toast.makeText(context, message,
                Toast.LENGTH_SHORT).show();
    }

    private static ProgressDialog progressDialog = null;

    public static void showProgresDialog(Context context) {
        // Showing progress dialog
        progressDialog = new ProgressDialog(context);
        progressDialog.setMessage(context.getString(R.string.wait));
        progressDialog.setCancelable(false);
        progressDialog.show();
    }

    public static void hideProgresDialog() {
        // Dismiss the progress dialog
        if (progressDialog.isShowing()) {
            progressDialog.dismiss();
        }
    }
}
