package com.hyogij.berlinmap;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;

import com.hyogij.berlinmap.locationInfos.JsonHelper;

/*
 * Description : Main Activity for this application
 * Date : 2015.11.16
 * Author : hyogij@gmail.com
 */
public class MainActivity extends FragmentActivity {
    private static final String CLASS_NAME = MainActivity.class.getCanonicalName();

    private JsonHelper jsonHelper = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Receive json data
        requestJSON();
    }

    private void requestJSON() {
        jsonHelper = new JsonHelper(this, handler);
    }

    // Handler to wait receiving json data
    Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case 0:
                    ViewPager viewPager = (ViewPager) findViewById(R.id.pager);

                    // Set the ViewPagerAdapter into ViewPager
                    viewPager.setAdapter(new ViewPagerAdapter(getSupportFragmentManager()));
                    break;
                default:
                    break;
            }
        }
    };
}