package com.hyogij.magentosoapapplication;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;

import com.hyogij.magentosoapapplication.adapters.ProductAdapter;
import com.hyogij.magentosoapapplication.datas.Product;
import com.hyogij.magentosoapapplication.helper.SOAPHelper;
import com.hyogij.magentosoapapplication.helper.UiHelpers;

import java.util.ArrayList;

// Reference : https://snow.dog/blog/android-and-magento-soap-api-part-ii/
public class MainActivity extends AppCompatActivity {
    private static final String CLASS_NAME = MainActivity.class.getCanonicalName();

    private ArrayList<Product> items = null;
    private ProductAdapter productAdapter = null;
    private GridView gridView = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        gridView = (GridView) findViewById(R.id.gridView);

        onProductList();
    }

    private void onProductList() {
        AsyncSOAPRequest task = new AsyncSOAPRequest();
        task.execute();
    }

    private class AsyncSOAPRequest extends AsyncTask<Void, Void, Void> {
        @Override
        protected void onPreExecute() {
            Log.d(CLASS_NAME, "onPreExecute");
            UiHelpers.showProgresDialog(MainActivity.this);
        }

        @Override
        protected Void doInBackground(Void... pParams) {
            Log.d(CLASS_NAME, "doInBackground");
            items = SOAPHelper.onProductList();
            return null;
        }

        @Override
        protected void onPostExecute(Void result) {
            Log.d(CLASS_NAME, "onPostExecute");
            UiHelpers.hideProgresDialog();
            if (items != null && items.size() != 0) {
                onRefreshList();
            } else {
                Log.d(CLASS_NAME, getString(R.string.empty));
                UiHelpers.showToast(MainActivity.this, getString(R.string.empty));
            }
        }
    }

    private void onRefreshList() {
        productAdapter = new ProductAdapter(this, R.layout.product_item,
                items);
        gridView.setAdapter(productAdapter);
        gridView.setOnItemClickListener(onClickListItem);
    }

    private AdapterView.OnItemClickListener onClickListItem = new AdapterView
            .OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
                                long arg3) {
            Product product = items.get(arg2);
            Log.d(CLASS_NAME, product.toString());
        }
    };
}
