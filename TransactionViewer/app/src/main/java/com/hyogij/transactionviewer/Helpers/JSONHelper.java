package com.hyogij.transactionviewer.helpers;

import android.content.Context;
import android.util.Log;

import com.hyogij.transactionviewer.datas.Rate;
import com.hyogij.transactionviewer.datas.Transaction;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.TreeMap;

/**
 * A class to read JSON file and to parse JSONArray
 */
public class JSONHelper {
    private static final String CLASS_NAME = JSONHelper.class
            .getCanonicalName();

    public static String readJSONFile(Context context, String fileName) {
        // Reading json file from assets folder
        StringBuffer sb = new StringBuffer();
        BufferedReader br = null;
        try {
            br = new BufferedReader(new InputStreamReader(context.getAssets()
                    .open(fileName)));
            String temp = null;
            while ((temp = br.readLine()) != null) {
                sb.append(temp);
            }
        } catch (IOException e) {
            Log.d(CLASS_NAME, e.getMessage());
        } finally {
            try {
                if (br != null) {
                    br.close(); // stop reading
                }
            } catch (IOException e) {
                Log.d(CLASS_NAME, e.getMessage());
            }
        }

        return sb.toString();
    }

    public static TreeMap<String, ArrayList<Transaction>>
    parseTransactionsJSONData
            (String jsonDatas) {
        TreeMap<String, ArrayList<Transaction>> transactions = new
                TreeMap<String,
                        ArrayList<Transaction>>();

        // Try to parse JSON
        try {
            // Creating JSONArray from String
            JSONArray jsonArray = new JSONArray(jsonDatas);

            // JSONArray has four JSONObject
            for (int i = 0; i < jsonArray.length(); i++) {

                // Creating JSONObject from JSONArray
                JSONObject jsonObj = jsonArray.getJSONObject(i);

                // Getting data from individual JSONObject
                String amount = jsonObj.getString("amount");
                String sku = jsonObj.getString("sku");
                String currency = jsonObj.getString("currency");

                Transaction transaction = new Transaction(Double.parseDouble
                        (amount), sku, currency);
                addToList(transactions, transaction);
            }

        } catch (JSONException e) {
            Log.d(CLASS_NAME, e.getMessage());
        }
        return transactions;
    }

    public static synchronized void addToList(TreeMap<String,
            ArrayList<Transaction>> items, Transaction myItem) {
        String mapKey = myItem.getSku();
        ArrayList<Transaction> itemsList = items.get(mapKey);

        // if list does not exist create it
        if (itemsList == null) {
            itemsList = new ArrayList<Transaction>();
            itemsList.add(myItem);
            items.put(mapKey, itemsList);
        } else {
            // add if item is not already in list
            if (!itemsList.contains(myItem)) {
                itemsList.add(myItem);
            }
        }
    }

    public static ArrayList<Rate> parseRatesJSONData
            (String jsonDatas) {
        ArrayList<Rate> rates = new ArrayList<Rate>();

        // Try to parse JSON
        try {
            // Creating JSONArray from String
            JSONArray jsonArray = new JSONArray(jsonDatas);

            // JSONArray has four JSONObject
            for (int i = 0; i < jsonArray.length(); i++) {

                // Creating JSONObject from JSONArray
                JSONObject jsonObj = jsonArray.getJSONObject(i);

                // Getting data from individual JSONObject
                String from = jsonObj.getString("from");
                String weight = jsonObj.getString("rate");
                String to = jsonObj.getString("to");

                Rate rate = new Rate(from, Double.parseDouble
                        (weight), to);
                rates.add(rate);
            }

        } catch (JSONException e) {
            Log.d(CLASS_NAME, e.getMessage());
        }
        return rates;
    }
}
