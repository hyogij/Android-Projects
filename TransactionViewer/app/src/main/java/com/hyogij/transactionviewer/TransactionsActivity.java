package com.hyogij.transactionviewer;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ListView;
import android.widget.TextView;

import com.hyogij.transactionviewer.Adapters.TransactionsAdapter;
import com.hyogij.transactionviewer.Datas.Transaction;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.ArrayList;

/**
 * An activity representing a list of Transactions.
 */
public class TransactionsActivity extends AppCompatActivity {
    private static final String CLASS_NAME = TransactionsActivity.class
            .getCanonicalName();

    private ListView listView = null;
    private TextView total = null;

    private ArrayList<Transaction> transactions = null;
    private TransactionsAdapter transactionsAdapter = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_transactions);

        listView = (ListView) findViewById(R.id.list);
        total = (TextView) findViewById(R.id.total);

        Intent intent = getIntent();
        transactions = (ArrayList<Transaction>) intent
                .getSerializableExtra(Constants
                        .TAG_PRODUCTS);

        String sku = intent.getStringExtra(Constants.TAG_TRANSACTION);
        setTitle(getString(R.string.transactions_title) + sku);

        setTotalAmount();
        onRefreshList();
    }

    private void setTotalAmount() {
        NumberFormat formatter = new DecimalFormat(Constants.DECIMAL_FORMAT);

        double totalAmount = 0.0;
        for (int i = 0; i < transactions.size(); i++) {
            totalAmount += transactions.get(i).getGbpValue();
        }

        total.setText(getString(R.string.total) + Constants.GBP_CURRENCY + " " +
                "" + String
                .valueOf
                        (formatter.format(totalAmount)));
    }

    private void onRefreshList() {
        transactionsAdapter = new TransactionsAdapter(this, R.layout
                .transaction_item, transactions);
        listView.setAdapter(transactionsAdapter);
    }
}