package com.hyogij.androidassignmentnew;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import java.util.ArrayList;

/*
 * Example application to test json and and sqlite
 * Date : 2015.11.10
 * Author : hyogij@gmail.com
 * Reference : http://hmkcode.com/android-tutorial/
 */
public class MainActivity extends AppCompatActivity {
    private static final String CLASS_NAME = MainActivity.class.getCanonicalName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void onRequestJson(View view) {
        Intent intent = new Intent(this, JsonResultActivity.class);
        startActivity(intent);
    }

    public void onReadDatabase(View view) {
        Intent intent = new Intent(this, DatabaseResultActivity.class);
        startActivity(intent);
    }
}
