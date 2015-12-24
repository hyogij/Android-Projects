package com.hyogij.weathermap.Helpers;

import android.util.Log;

import com.hyogij.weathermap.JSONDatas.Forecast;
import com.hyogij.weathermap.JSONDatas.Weather;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * A class to parse received JSONObject to Yahoo weather format
 */
public class JSONHelper {
    private static final String CLASS_NAME = JSONHelper.class.getCanonicalName();

    public static Weather parseJSONData(JSONObject json, String cityName) {
        Weather weather = null;
        JSONObject item = null;
        try {
            item = json.getJSONObject("query").getJSONObject("results").getJSONObject
                    ("channel").getJSONObject
                    ("item");

            JSONArray forecasts = item.getJSONArray("forecast");
            ArrayList<Forecast> forecastList = new ArrayList<Forecast>();
            for (int i = 0; i < forecasts.length(); i++) {
                Forecast forecast = new Forecast(
                        forecasts.getJSONObject(i).getString("code"),
                        forecasts.getJSONObject(i).getString("date"),
                        forecasts.getJSONObject(i).getString("day"),
                        forecasts.getJSONObject(i).getString("high"),
                        forecasts.getJSONObject(i).getString("low"),
                        forecasts.getJSONObject(i).getString("text"));
                forecastList.add(forecast);
            }

            weather = new Weather(cityName,
                    item.getString("title"),
                    item.getString("link"),
                    item.getString("description"),
                    item.getString("pubDate"),
                    item.getJSONObject("condition").getString("temp"),
                    forecastList);

            Log.d(CLASS_NAME, weather.toString());
        } catch (JSONException e) {
            Log.d(CLASS_NAME, e.getMessage());
        } catch (Exception e) {
            Log.d(CLASS_NAME, e.getMessage());
        }

        return weather;
    }
}
