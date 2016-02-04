package com.hyogij.transactionviewer.datas;

/**
 * A class for Rate data.
 */
public class Rate {
    private String from = null;
    private double rate = 0.0;
    private String to = null;

    public Rate(String from, double rate, String to) {
        this.from = from;
        this.rate = rate;
        this.to = to;
    }

    public String getFrom() {
        return from;
    }

    public void setFrom(String from) {
        this.from = from;
    }

    public double getRate() {
        return rate;
    }

    public void setRate(double rate) {
        this.rate = rate;
    }

    public String getTo() {
        return to;
    }

    public void setTo(String to) {
        this.to = to;
    }

    @Override
    public String toString() {
        return "Rate{" +
                "from='" + from + '\'' +
                ", rate=" + rate +
                ", to='" + to + '\'' +
                '}';
    }
}
