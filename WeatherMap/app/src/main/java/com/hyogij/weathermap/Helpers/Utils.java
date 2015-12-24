package com.hyogij.weathermap.Helpers;

import android.content.Context;
import android.location.Address;
import android.location.Geocoder;
import android.util.Log;

import com.google.android.gms.maps.model.LatLng;
import com.hyogij.weathermap.Constant.Constant;
import com.hyogij.weathermap.R;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

/**
 * An utility class for url, cityname and color
 */
public class Utils {
    private static final String CLASS_NAME = Utils.class.getCanonicalName();

    public static String getYQLRequestUrl(String cityName) {
        StringBuilder url = new StringBuilder(Constant.YAHOOAPIS);
        url.append(Constant.YAHOOAPIS_QUERY);
        url.append(cityName);
        url.append(Constant.YAHOOAPIS_OPTIONS);

        return url.toString();
    }

    public static String getCityName(Context context, LatLng latLng) {
        String cityName = null;

        // Transforming a (latitude, longitude) coordinate into a (partial) address
        Geocoder gcd = new Geocoder(context, Locale.ENGLISH);
        List<Address> addresses = null;
        try {
            addresses = gcd.getFromLocation(latLng.latitude, latLng.longitude, 1);
            if (addresses != null && addresses.size() > 0) {
                cityName = addresses.get(0).getLocality().toString();
            }
        } catch (IOException e) {
            Log.d(CLASS_NAME, "IOException " + e.getMessage());
        } catch (Exception e) {
            Log.d(CLASS_NAME, "Exception " + e.getMessage());
        }

        return cityName;
    }

    // Convert to fahrenheit to rgb color.
    // Refer to https://web.njit.edu/~walsh/celsius/full.html
    public static String getColor(Context context, String fahrenheit) {
        int index = (Integer.valueOf(fahrenheit) + 60) / 15;
        String[] colors = context.getResources().getStringArray(R.array.colors);
        return colors[index];
    }
}
