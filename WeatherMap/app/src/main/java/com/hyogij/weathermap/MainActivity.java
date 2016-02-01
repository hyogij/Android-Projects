package com.hyogij.weathermap;

import android.graphics.Color;
import android.location.Location;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.RelativeLayout;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.GoogleMap.OnMapClickListener;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.hyogij.weathermap.Helpers.JSONHelper;
import com.hyogij.weathermap.Helpers.LocationHelper;
import com.hyogij.weathermap.Helpers.UiHelper;
import com.hyogij.weathermap.Helpers.Utils;
import com.hyogij.weathermap.JSONDatas.Weather;
import com.hyogij.weathermap.Volley.VolleyHelper;

import org.json.JSONObject;

import java.util.HashMap;


/**
 * An activity representing map with weather informations
 */
public class MainActivity extends AppCompatActivity {
    private static final String CLASS_NAME = MainActivity.class.getCanonicalName();

    private static final int CAMERA_UPDATE_ZOOM_LEVEL = 15;
    private static final int ANIMATE_CAMERA_UPDATE_ZOOM_LEVEL = 13;

    private RelativeLayout relativeLayout = null;
    private GoogleMap map = null;
    private HashMap<String, Weather> markers = null;

    private LocationHelper locationHelper = null;
    private JsonObjectRequest jsonObjectRequest = null;
    private LatLng currentLatLng = null;
    private Weather currentWeather = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        relativeLayout = (RelativeLayout) findViewById(R.id.relativeLayout);
        map = ((MapFragment) getFragmentManager().findFragmentById(R.id.map))
                .getMap();

        markers = new HashMap<String, Weather>();

        // Initailize location helper class
        locationHelper = new LocationHelper(this, handler);

        setEventListener();
    }

    private void setEventListener() {
        map.setOnMapClickListener(new OnMapClickListener() {
            public void onMapClick(LatLng point) {
                currentLatLng = point;
                getWeatherInformation(currentLatLng);
            }
        });

        map.setOnMarkerClickListener(new GoogleMap.OnMarkerClickListener() {
            public boolean onMarkerClick(Marker marker) {
                Weather weather = markers.get(marker.getId());

                // Show the weather information using received html string
                UiHelper.showAlertDialog(getParent(), weather);
                return true;
            }
        });
    }

    private void getWeatherInformation(LatLng point) {
        String cityName = Utils.getCityName(MainActivity.this, point);

        if (cityName != null) {
            createJsonObjectRequest(Utils.getYQLRequestUrl(cityName), cityName);
            requestJSON();
        } else {
            // There is no corresponding to cityname
            UiHelper.showToast(this, getString(R.string.error_geocoding));
        }
    }

    private void createJsonObjectRequest(String url, final String cityName) {
        // Request a json object response from the provided URL
        jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        UiHelper.hideProgressDialog();

                        currentWeather = JSONHelper.parseJSONData(response, cityName);
                        if (currentWeather == null) {
                            // There is no weather information
                            StringBuilder stringBuilder
                                    = new StringBuilder(getString(R.string.error_weather));
                            stringBuilder.append(cityName);
                            UiHelper.showToast(MainActivity.this, stringBuilder.toString());
                        } else {
                            updateWeatherInformation();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                UiHelper.hideProgressDialog();
            }
        });
    }

    private void requestJSON() {
        UiHelper.showProgressDialog(MainActivity.this);
        VolleyHelper.getInstance(getApplicationContext()).addToRequestQueue(jsonObjectRequest);
    }

    // Handler to wait changing current location
    Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case 0:
                    changeCurrentLocation();
                    break;
                default:
                    break;
            }
        }
    };

    private void changeCurrentLocation() {
        Location location = locationHelper.getLocation();

        // Creating a LatLng object for the current location
        currentLatLng = new LatLng(location.getLatitude(), location.getLongitude());

        map.moveCamera(CameraUpdateFactory.newLatLngZoom(currentLatLng, CAMERA_UPDATE_ZOOM_LEVEL));
        map.animateCamera(CameraUpdateFactory.zoomTo(ANIMATE_CAMERA_UPDATE_ZOOM_LEVEL));

        // Request weather information via api query
        getWeatherInformation(currentLatLng);
    }

    private void updateWeatherInformation() {
        addMarker();
        setBackgroundColor();
        UiHelper.showAlertDialog(MainActivity.this, currentWeather);
    }

    private void addMarker() {
        MarkerOptions markerOptions = new MarkerOptions();
        markerOptions.position(currentLatLng);
        markerOptions.icon(BitmapDescriptorFactory.fromResource(R.drawable.weather_icon));
        Marker marker = map.addMarker(markerOptions);
        marker.showInfoWindow();
        markers.put(marker.getId(), currentWeather);
    }

    private void setBackgroundColor() {
        // Unit for temperature is Fahrenheit
        Log.d(CLASS_NAME, currentWeather.getTemp());
        String color = Utils.getColor(MainActivity.this, currentWeather.getTemp());
        relativeLayout.setBackgroundColor(Color.parseColor(color));
        relativeLayout.invalidate();
    }
}
