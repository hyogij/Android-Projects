package com.hyogij.weathermap.Constant;

/**
 * A class for constant variables
 */
public class Constant {
    // https://developer.yahoo.com/weather/
    public static final String YAHOOAPIS = "https://query.yahooapis.com/v1/public/yql?";
    public static final String YAHOOAPIS_QUERY = "q=select%20*%20from%20weather.forecast%20where%20"
            + "woeid%20in%20(select%20woeid%20from%20geo.places(1)" +
            "%20where%20text%3D%22";
    public static final String YAHOOAPIS_OPTIONS = "ak%22)&format=json&env=store%3A%2F%2F" +
            "datatables.org%2Falltableswithkeys";
}
