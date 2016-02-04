package com.hyogij.jsonclientmasterdetailview;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MenuItem;
import android.widget.ImageView;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageRequest;
import com.hyogij.jsonclientmasterdetailview.util.Utils;
import com.hyogij.jsonclientmasterdetailview.volley.VolleyHelper;

/**
 * An activity representing a single picture detail screen.
 */
public class PictureViewActivity extends AppCompatActivity {
    private static final String CLASS_NAME = PictureViewActivity.class
            .getCanonicalName();

    private ImageView imageView = null;
    private String url = null;
    private ImageRequest imageRequest = null;

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

        imageView = (ImageView) findViewById(R.id.image);

        createImageRequest();
        requestImage();
    }

    private void requestImage() {
        Utils.showProgresDialog(this);
        VolleyHelper.getInstance(getApplicationContext()).addToRequestQueue(imageRequest);
    }

    private void createImageRequest() {
        // Retrieves an image specified by the URL, displays it in the UI.
        imageRequest = new ImageRequest(url,
                new Response.Listener<Bitmap>() {
                    @Override
                    public void onResponse(Bitmap bitmap) {
                        Utils.hideProgresDialog();

                        imageView.setImageBitmap(bitmap);
                    }
                }, 0, 0, ImageView.ScaleType.CENTER_CROP, null,
                new Response.ErrorListener() {
                    public void onErrorResponse(VolleyError error) {
                        Utils.hideProgresDialog();

                        Log.d(CLASS_NAME, error.getMessage());
                    }
                });
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
}
