package com.hyogij.jsonclient;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.widget.ImageView;

import com.hyogij.jsonclient.ImageLoaderUtils.ImageLoader;

/**
 * Created by hyogij on 2015. 12. 13..
 */
public class PictureViewActivity extends Activity {
    private static final String CLASS_NAME = PictureViewActivity.class
            .getCanonicalName();

    public ImageLoader imageLoader;
    private static final String TAG_URL = "url";
    private String url = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.picture_view_activity);

        Intent intent = getIntent();
        url = intent.getStringExtra(TAG_URL);

        imageLoader = new ImageLoader(getApplicationContext());
        new ProcessFacebookTask().execute(null, null, null);
    }

    private class ProcessFacebookTask extends AsyncTask<Void, Void, Void> {
        @Override
        protected Void doInBackground(Void... params) {
            try {
                final ImageView image = (ImageView) findViewById(R.id.image);
                runOnUiThread(new Runnable() {
                    public void run() {
                        imageLoader.DisplayImage(url, image);
                    }
                });
            } catch (Exception e) {
                e.printStackTrace();
            }
            return null;
        }
    }
}
