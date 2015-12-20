package com.hyogij.jsonclientmasterdetailview.Util;

import android.content.Context;
import android.widget.Toast;

/**
 * An utility class for sting and toast message.
 */
public class Utils {
    public static String getActvityTitle(String title, String header, String
            id) {
        StringBuilder actvityTitle = new StringBuilder(title);
        actvityTitle.append(" : ");
        actvityTitle.append(header);
        actvityTitle.append("(");
        actvityTitle.append(id);
        actvityTitle.append(")");
        return actvityTitle.toString();
    }

    public static void showToast(Context context, String message) {
        Toast.makeText(context, message,
                Toast.LENGTH_SHORT).show();
    }
}
