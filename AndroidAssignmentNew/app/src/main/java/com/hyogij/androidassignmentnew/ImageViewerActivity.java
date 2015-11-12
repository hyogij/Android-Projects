package com.hyogij.androidassignmentnew;

import android.app.ActivityManager;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.util.LruCache;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

/*
 * Load an image form web and save it to the memory cache
 * Date : 2015.11.12
 * Author : hyogij@gmail.com
 * Reference : http://noransmile.tistory.com/4, http://goo.gl/nbC12Y
 */
public class ImageViewerActivity extends AppCompatActivity {
    private static final String CLASS_NAME = ImageViewerActivity.class.getCanonicalName();
    private static final String IMAGE_URL =
            "http://geckobrosradio.com/wp-content/uploads/2014/06/android-logo-png.png";
    private static final String IMAGE_NAME = "android-logo-png.png";

    private ImageView imageView = null;
    private LruCache<String, Bitmap> memoryCache = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image_viewer);

        // Get reference to the views
        imageView = (ImageView) findViewById(R.id.imageView);

        initMemoryCache();

        // Load Image from web
        new LoadImagefromUrl().execute(imageView, IMAGE_URL);
    }

    public void onClose(View view) {
        finish();
    }

    public void onRefresh(View view) {
        Toast.makeText(getBaseContext(), "onRefresh!", Toast.LENGTH_LONG).show();
        imageView.setImageResource(android.R.color.transparent);

        // If the image is cached, then load it from memory cache
        Bitmap bitmap = getBitmapFromMemCache(IMAGE_NAME);
        if (bitmap == null) {
            // Load Image from web
            new LoadImagefromUrl().execute(imageView, IMAGE_URL);
        } else {
            imageView.setImageBitmap(bitmap);
        }
    }

    private class LoadImagefromUrl extends AsyncTask<Object, Void, Bitmap> {
        ImageView ivPreview = null;

        @Override
        protected Bitmap doInBackground(Object... params) {
            this.ivPreview = (ImageView) params[0];
            String url = (String) params[1];
            return loadBitmap(url);
        }

        @Override
        protected void onPostExecute(Bitmap result) {
            Toast.makeText(getBaseContext(), "Read from Web!", Toast.LENGTH_LONG).show();

            super.onPostExecute(result);

            // Save the image to memory cache
            addBitmapToMemoryCache(IMAGE_NAME, result);
            ivPreview.setImageBitmap(result);
        }
    }

    public Bitmap loadBitmap(String url) {
        URL newurl = null;
        Bitmap bitmap = null;
        try {
            newurl = new URL(url);
            bitmap = BitmapFactory.decodeStream(newurl.openConnection().getInputStream());
        } catch (MalformedURLException e) {
            Log.d(CLASS_NAME, e.getMessage());
        } catch (IOException e) {
            Log.d(CLASS_NAME, e.getMessage());
        }
        return bitmap;
    }

    // Initialize memory cache
    private void initMemoryCache() {
        // Get memory class of this device, exceeding this amount will throw an
        // OutOfMemory exception.
        final int memClass = ((ActivityManager) getSystemService(
                Context.ACTIVITY_SERVICE)).getMemoryClass();

        // Use 1/8th of the available memory for this memory cache.
        final int cacheSize = 1024 * 1024 * memClass / 8;

        memoryCache = new LruCache<String, Bitmap>(cacheSize) {
            @Override
            protected int sizeOf(String key, Bitmap bitmap) {
                // The cache size will be measured in bytes rather than number of items.
                return bitmap.getByteCount();
            }
        };
    }

    // Save the image to memory cache
    public void addBitmapToMemoryCache(String key, Bitmap bitmap) {
        if (getBitmapFromMemCache(key) == null) {
            memoryCache.put(key, bitmap);
        }
    }

    // Load the image from memory cache
    public Bitmap getBitmapFromMemCache(String key) {
        return memoryCache.get(key);
    }
}