package com.hyogij.transactionviewer;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.hyogij.transactionviewer.Adapters.ProductsAdapter;
import com.hyogij.transactionviewer.Datas.Products;
import com.hyogij.transactionviewer.Datas.Rate;
import com.hyogij.transactionviewer.Datas.Transaction;
import com.hyogij.transactionviewer.Graph.BellmanFord;
import com.hyogij.transactionviewer.Graph.WeightedDigraph;
import com.hyogij.transactionviewer.Helpers.JSONHelper;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;

/**
 * An activity representing a list of Products.
 */
public class ProductsActivity extends AppCompatActivity {
    private static String CLASS_NAME = ProductsActivity.class
            .getCanonicalName();

    private TreeMap<String, ArrayList<Transaction>> transactions = null;
    private ArrayList<Products> productList = null;
    private ArrayList<Rate> rates = null;

    private ProductsAdapter productsAdapter = null;
    private ListView listView = null;

    private HashMap<String, Integer> currencyMap = null;
    private WeightedDigraph graph = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_products);

        setTitle(getString(R.string.products));

        productList = new ArrayList<Products>();
        listView = (ListView) findViewById(R.id.list);

        readRatesJsonFile();
        makeWeightedDigraph();
        readTranjactionJsonFile();

        onRefreshList();
    }

    private void readTranjactionJsonFile() {
        String jsonDatas = JSONHelper.readJSONFile(this,
                Constants.TRANSACTIONS_JSON_FILE);
        transactions = JSONHelper.parseTransactionsJSONData(jsonDatas);

        for (Map.Entry<String, ArrayList<Transaction>> entry : transactions
                .entrySet()) {
            String key = entry.getKey();
            ArrayList<Transaction> value = entry.getValue();

            // Exchange currency to GBP
            value = exchangeCurrency(value);
            Products products = new Products(key, value);
            productList.add(products);
        }
    }

    private ArrayList<Transaction> exchangeCurrency(ArrayList<Transaction>
                                                            value) {
        // Convert all currencies to GBP to reduce converting computations
        HashMap<String, Double> weightMap = new HashMap<String, Double>();
        for (String key : currencyMap.keySet()) {
            double weight = BellmanFord.BellmanFordAlgorithm(graph, currencyMap.get(key),
                    currencyMap.get(Constants.GBP_CURRENCY));
            weightMap.put(key, weight);
        }

        // Convert all transaction currencies to GBP using calculated weight
        for (int i = 0; i < value.size(); i++) {
            Transaction transaction = value.get(i);
            double weight = weightMap.get(transaction.getCurrency());
            transaction.setGbpValue(transaction.getAmount() * weight);
        }
        return value;
    }

    private void readRatesJsonFile() {
        String jsonDatas = JSONHelper.readJSONFile(this,
                Constants.RATES_JSON_FILE);
        rates = JSONHelper.parseRatesJSONData(jsonDatas);
    }

    private void makeWeightedDigraph() {
        currencyMap = new HashMap<String, Integer>();
        int size = 0;
        for (int i = 0; i < rates.size(); i++) {
            Rate rate = rates.get(i);
            String from = rate.getFrom();
            if (!currencyMap.containsKey(from)) {
                currencyMap.put(from, size++);
            }
            String to = rate.getTo();
            if (!currencyMap.containsKey(to)) {
                currencyMap.put(to, size++);
            }
        }

        // Create a new weighted digraph
        graph = new WeightedDigraph(size, rates.size());

        // Add edge into the weighted digraph
        for (int i = 0; i < rates.size(); i++) {
            Rate rate = rates.get(i);
            graph.addEdge(currencyMap.get(rate.getFrom()), currencyMap.get
                    (rate.getTo()), rate.getRate());
        }
    }

    private void onRefreshList() {
        productsAdapter = new ProductsAdapter(this, R.layout.product_item,
                productList);
        listView.setAdapter(productsAdapter);
        listView.setOnItemClickListener(onClickListItem);
    }

    private AdapterView.OnItemClickListener onClickListItem = new AdapterView
            .OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
                                long arg3) {
            Products products = productList.get(arg2);

            Intent transactionsIntent = new Intent(ProductsActivity
                    .this, TransactionsActivity.class);
            transactionsIntent.putExtra(Constants.TAG_PRODUCTS, products
                    .getTransactions());
            transactionsIntent.putExtra(Constants.TAG_TRANSACTION, products
                    .getSku());

            startActivity(transactionsIntent);
        }
    };
}
