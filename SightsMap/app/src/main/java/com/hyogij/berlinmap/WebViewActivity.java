package com.hyogij.berlinmap;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.webkit.WebView;

/*
 * Description : Display web browser with given url
 * Date : 2015.11.16
 * Author : hyogij@gmail.com
 */
public class WebViewActivity extends AppCompatActivity {
    private static final String CLASS_NAME = WebViewActivity.class.getCanonicalName();
    private static final String URL_PARAMETER = "URL_PARAMETER";

    private WebView webview = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_web_view);

        webview = (WebView) findViewById(R.id.webView);

        String url = getIntent().getStringExtra(URL_PARAMETER);
        webview.loadUrl(url);
    }
}