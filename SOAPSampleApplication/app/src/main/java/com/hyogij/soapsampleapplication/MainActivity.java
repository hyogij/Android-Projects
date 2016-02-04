package com.hyogij.soapsampleapplication;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.hyogij.soapsampleapplication.helper.SOAPHelper;

import org.ksoap2.serialization.SoapPrimitive;

/**
 * An activity calculating celsius to fahrenheit.
 * References : https://trinitytuts.com/load-data-from-soap-web-service-in-android-application/,
 * http://code.tutsplus.com/tutorials/consuming-web-services-with-ksoap--mobile-21242
 */
public class MainActivity extends AppCompatActivity {
    private static final String CLASS_NAME = MainActivity.class.getCanonicalName();

    private EditText etCelsius = null;
    private String celsius = null;
    private SoapPrimitive soapPrimitive = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        etCelsius = (EditText) findViewById(R.id.etCelsius);
    }

    public void onCalculate(View view) {
        celsius = etCelsius.getText().toString();
        AsyncCallWS task = new AsyncCallWS();
        task.execute();
    }

    private class AsyncCallWS extends AsyncTask<Void, Void, Void> {
        @Override
        protected void onPreExecute() {
            Log.d(CLASS_NAME, "onPreExecute");
        }

        @Override
        protected Void doInBackground(Void... params) {
            Log.d(CLASS_NAME, "doInBackground");
//            SOAPHelper.soapRequest(celsius);
            SOAPHelper.getFahrenheit(celsius);
            return null;
        }

        @Override
        protected void onPostExecute(Void result) {
            Log.d(CLASS_NAME, "onPostExecute");
            soapPrimitive = SOAPHelper.getSoapPrimitive();
            Toast.makeText(MainActivity.this, "Response : " + soapPrimitive.toString(), Toast
                    .LENGTH_SHORT).show();
        }
    }
}
