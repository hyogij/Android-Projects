package com.hyogij.transactionviewer.Datas;

import android.os.Parcel;

import java.util.ArrayList;

/**
 * A class for Products data.
 */
public class Products {
    private String sku = null;
    private ArrayList<Transaction> transactions = null;

    public Products(String sku, ArrayList<Transaction> transactions) {
        this.sku = sku;
        this.transactions = transactions;
    }

    protected Products(Parcel in) {
        sku = in.readString();
        in.readTypedList(transactions, Transaction.CREATOR);
    }

    public String getSku() {
        return sku;
    }

    public ArrayList<Transaction> getTransactions() {
        return transactions;
    }

    public int getSize() {
        return transactions.size();
    }

    @Override
    public String toString() {
        return "Products{" +
                "sku='" + sku + '\'' +
                ", transactions=" + transactions.toString() +
                '}';
    }
}
