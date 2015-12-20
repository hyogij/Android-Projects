package com.hyogij.jsonclientmasterdetailview;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.widget.ImageView;

import com.hyogij.jsonclientmasterdetailview.Const.Constants;
import com.hyogij.jsonclientmasterdetailview.ImageLoader.ImageLoader;
import com.hyogij.jsonclientmasterdetailview.Util.Utils;

/**
 * An activity representing a single picture detail screen.
 */
public class PictureViewActivity extends AppCompatActivity {
    private static final String CLASS_NAME = PictureViewActivity.class
            .getCanonicalName();

    public ImageLoader imageLoader = null;
    private String url = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_picture_view);

        Intent intent = getIntent();
        url = intent.getStringExtra(Constants.TAG_URL);

        String id = intent.getStringExtra(Constants.TAG_ID);
        // Change an activity name
        setTitle(Utils.getActvityTitle(getString(R.string
                        .picture_view_activity),
                Constants
                        .TAG_ID, id));

        imageLoader = new ImageLoader(getApplicationContext());
        new ImageLoaderTask().execute(null, null, null);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == android.R.id.home) {
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private class ImageLoaderTask extends AsyncTask<Void, Void, Void> {
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
