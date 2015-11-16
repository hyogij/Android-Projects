package com.hyogij.berlinmap;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.util.Log;
import android.view.View;
import android.widget.ListView;

import com.hyogij.berlinmap.locationInfos.LocationInfo;
import com.hyogij.berlinmap.locationInfos.LocationInfosAdapter;
import com.hyogij.berlinmap.locationInfos.SQLiteHelper;

import java.util.ArrayList;

/**
 * Created by hyogij on 15. 11. 16..
 */
public class ListViewFragment extends ListFragment {
    private static final String CLASS_NAME = ListViewFragment.class.getCanonicalName();
    private static final String URL_PARAMETER = "URL_PARAMETER";

    private ArrayList<LocationInfo> locations = null;
    private SQLiteHelper sqliteHelper = null;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setLocationInfo();
        // Initialize and set the list adapter
        setListAdapter(new LocationInfosAdapter(getActivity(), locations));
    }

    public void setLocationInfo() {
        sqliteHelper = new SQLiteHelper(getActivity());

        // Get all location informations
        this.locations = sqliteHelper.getAllLocationInfos();
        Log.d(CLASS_NAME, locations.toString());
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        // Remove the dividers from the ListView of the ListFragment
        getListView().setDivider(null);
    }

    @Override
    public void onListItemClick(ListView l, View v, int position, long id) {
        // Retrieve theListView item
        LocationInfo locationInfo = (LocationInfo) locations.get(position);

        Intent intent = new Intent(getActivity(), WebViewActivity.class);
        intent.putExtra(URL_PARAMETER, locationInfo.getUrl());
        startActivity(intent);
    }
}