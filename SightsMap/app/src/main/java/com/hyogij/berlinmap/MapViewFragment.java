package com.hyogij.berlinmap;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.MapsInitializer;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.hyogij.berlinmap.locationInfos.LocationInfo;
import com.hyogij.berlinmap.locationInfos.SQLiteHelper;

import java.util.ArrayList;

/*
 * Description : Display the map with markers
 * Date : 2015.11.16
 * Author : hyogij@gmail.com
 */
public class MapViewFragment extends Fragment implements GoogleMap.OnInfoWindowClickListener {
    private static final String CLASS_NAME = MapViewFragment.class.getCanonicalName();
    private static final String URL_PARAMETER = "URL_PARAMETER";

    private MapView mapView = null;
    private GoogleMap googleMap = null;
    private SQLiteHelper sqliteHelper = null;

    private ArrayList<LocationInfo> locations = null;

    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.activity_map_view, null, false);

        mapView = (MapView) v.findViewById(R.id.mapView);
        mapView.onCreate(savedInstanceState);
        mapView.onResume();
        try {
            MapsInitializer.initialize(getActivity().getApplicationContext());
        } catch (Exception e) {
            Log.d(CLASS_NAME, e.getMessage());
        }

        googleMap = mapView.getMap();
        googleMap.setOnInfoWindowClickListener(this);

        setLocationInfo();
        displayMap();

        return v;
    }

    public void setLocationInfo() {
        sqliteHelper = new SQLiteHelper(getActivity());

        // Read all location informations
        this.locations = sqliteHelper.getAllLocationInfos();
        Log.d(CLASS_NAME, locations.toString());
    }

    // Make markers with all of location informations and display
    private void displayMap() {
        for (int i = 0; i < locations.size(); i++) {
            LocationInfo locationInfo = locations.get(i);

            MarkerOptions markerOptions = new MarkerOptions();
            markerOptions.position(new LatLng(locationInfo.getLatitude(),
                    locationInfo.getLongitude()));
            markerOptions.title(locationInfo.getName());
            markerOptions.snippet(locationInfo.getDescription());

            googleMap.addMarker(markerOptions);

            if (i == 0) {
                googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(
                        new LatLng(locationInfo.getLatitude(),
                                locationInfo.getLongitude()), 12));
            }
        }

        googleMap.animateCamera(CameraUpdateFactory.zoomTo(12), 2000, null);
    }

    @Override
    public void onInfoWindowClick(Marker marker) {
        // Open the web page that contains given sight information
        Intent intent = new Intent(getActivity(), WebViewActivity.class);
        intent.putExtra(URL_PARAMETER, sqliteHelper.getLocationInfo(marker.getTitle()).getUrl());
        startActivity(intent);
    }
}