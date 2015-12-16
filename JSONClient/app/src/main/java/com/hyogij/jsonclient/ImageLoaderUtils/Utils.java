package com.hyogij.jsonclient.ImageLoaderUtils;

import android.util.Log;

import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class Utils {
    private static final String CLASS_NAME = Utils.class.getCanonicalName();

    public static String getOriginalUrl(String url) {
        String location = null;
        try {
            URL imageUrl = new URL(url);
            HttpURLConnection conn = (HttpURLConnection) imageUrl
                    .openConnection();
            conn.setConnectTimeout(30000);
            conn.setReadTimeout(30000);
            conn.setInstanceFollowRedirects(true);
            InputStream is = conn.getInputStream();
            location = conn.getHeaderField("Location");
        } catch (Exception e) {
            e.printStackTrace();
        }

        return location;
    }

    public static void CopyStream(InputStream is, OutputStream os) {
        final int buffer_size = 1024;
        try {
            byte[] bytes = new byte[buffer_size];
            for (; ; ) {
                int count = is.read(bytes, 0, buffer_size);
                if (count == -1)
                    break;
                os.write(bytes, 0, count);
            }
        } catch (Exception ex) {
            Log.d(CLASS_NAME, "Exception " + ex.toString());
        }
    }
}
