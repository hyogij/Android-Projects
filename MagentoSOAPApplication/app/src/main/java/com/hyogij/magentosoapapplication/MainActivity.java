package com.hyogij.magentosoapapplication;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.hyogij.magentosoapapplication.SOAP.SOAPHelper;

// Reference : https://snow.dog/blog/android-and-magento-soap-api-part-ii/
public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void onRequest(View view) {
        SOAPHelper.login();
    }
}
