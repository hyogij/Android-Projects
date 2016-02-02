package com.hyogij.magentosoapapplication;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.hyogij.magentosoapapplication.Adapters.CustomerAdapter;
import com.hyogij.magentosoapapplication.Datas.Customer;
import com.hyogij.magentosoapapplication.Helper.SOAPHelper;

import java.util.ArrayList;

// Reference : https://snow.dog/blog/android-and-magento-soap-api-part-ii/
public class MainActivity extends AppCompatActivity {
    private static final String CLASS_NAME = MainActivity.class.getCanonicalName();

    private ArrayList<Customer> items = null;
    private CustomerAdapter customerAdapter = null;
    private ListView listView = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        listView = (ListView) findViewById(R.id.list);
    }

    public void onCustomerList(View view) {
        AsyncSOAPRequest task = new AsyncSOAPRequest();
        task.execute(1);
    }

    public void onStoreList(View view) {
        AsyncSOAPRequest task = new AsyncSOAPRequest();
        task.execute(2);
    }

    private class AsyncSOAPRequest extends AsyncTask<Integer, Void, Void> {
        @Override
        protected void onPreExecute() {
            Log.d(CLASS_NAME, "onPreExecute");
        }

        @Override
        protected Void doInBackground(Integer... pParams) {
            Integer type = pParams[0];
            Log.d(CLASS_NAME, "doInBackground");
            if (type == 1) {
                items = SOAPHelper.onCustomerCustomerList();
            } else {
                items = SOAPHelper.onStoreList();
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void result) {
            Log.d(CLASS_NAME, "onPostExecute");
            if (items != null && items.size() != 0) {
                onRefreshList();
            } else {
                Log.d(CLASS_NAME, "Item is Empty!");
                Toast.makeText(getApplicationContext(), "Item is Empty!", Toast.LENGTH_LONG).show();
            }
        }
    }

    private void onRefreshList() {
        customerAdapter = new CustomerAdapter(this, R.layout.customer_item,
                items);
        listView.setAdapter(customerAdapter);
        listView.setOnItemClickListener(onClickListItem);
    }

    private AdapterView.OnItemClickListener onClickListItem = new AdapterView
            .OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
                                long arg3) {
            Customer customer = items.get(arg2);
            Log.d(CLASS_NAME, customer.toString());

            /*
            Intent transactionsIntent = new Intent(ProductsActivity
                    .this, TransactionsActivity.class);
            transactionsIntent.putExtra(Constants.TAG_TRANSCATIONS, products
                    .getTransactions());
            transactionsIntent.putExtra(Constants.TAG_SKU, products
                    .getSku());

            startActivity(transactionsIntent);
             */
        }
    };
}
