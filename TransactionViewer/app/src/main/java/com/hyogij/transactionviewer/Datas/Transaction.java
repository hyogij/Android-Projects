package com.hyogij.transactionviewer.Datas;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * A class for Transaction data.
 */
public class Transaction implements Parcelable {
    private double amount = 0.0;
    private String sku = null;
    private String currency = null;
    private double gbpValue = 0.0;

    public Transaction(double amount, String sku, String currency) {
        this.amount = amount;
        this.sku = sku;
        this.currency = currency;
    }

    protected Transaction(Parcel in) {
        amount = in.readDouble();
        sku = in.readString();
        currency = in.readString();
        gbpValue = in.readDouble();
    }

    public static final Creator<Transaction> CREATOR = new
            Creator<Transaction>() {
                @Override
                public Transaction createFromParcel(Parcel in) {
                    return new Transaction(in);
                }

                @Override
                public Transaction[] newArray(int size) {
                    return new Transaction[size];
                }
            };

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public String getSku() {
        return sku;
    }

    public void setSku(String sku) {
        this.sku = sku;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public double getGbpValue() {
        return gbpValue;
    }

    public void setGbpValue(double gbpValue) {
        this.gbpValue = gbpValue;
    }

    @Override
    public String toString() {
        return "Transaction{" +
                "amount=" + amount +
                ", sku='" + sku + '\'' +
                ", currency='" + currency + '\'' +
                ", gbpCurrency='" + gbpValue + '\'' +
                '}';
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeDouble(amount);
        dest.writeString(sku);
        dest.writeString(currency);
        dest.writeDouble(gbpValue);
    }
}
