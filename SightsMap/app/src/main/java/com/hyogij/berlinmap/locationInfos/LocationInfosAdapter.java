package com.hyogij.berlinmap.locationInfos;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.hyogij.berlinmap.R;

import java.util.ArrayList;

/*
 * Description : LocationInfosAdapter class to display location information
 * Date : 2015.11.16
 * Author : hyogij@gmail.com
 */
public class LocationInfosAdapter extends ArrayAdapter<LocationInfo> {
    private ArrayList<LocationInfo> items = null;

    public LocationInfosAdapter(Context context, ArrayList<LocationInfo> objects) {
        super(context, android.R.layout.simple_list_item_1, objects);
        this.items = objects;
    }

    private class ViewHolder {
        TextView txtLatitude;
        TextView txtLongitude;
        TextView txtName;
        TextView txtDescription;
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder = null;
        if (convertView == null) {
            LayoutInflater inflater = LayoutInflater.from(getContext());
            convertView = inflater.inflate(R.layout.fragment_item_list, parent, false);

            viewHolder = new ViewHolder();
            viewHolder.txtName = (TextView) convertView.findViewById(R.id.txtName);
            viewHolder.txtLatitude = (TextView) convertView.findViewById(R.id.txtLatitude);
            viewHolder.txtLongitude = (TextView) convertView.findViewById(R.id.txtLongitude);
            viewHolder.txtDescription = (TextView) convertView.findViewById(R.id.txtDescription);

            convertView.setTag(viewHolder);
        } else {
            // Recycle the already inflated view
            viewHolder = (ViewHolder) convertView.getTag();
        }

        // Update the item view
        LocationInfo locationInfo = getItem(position);
        viewHolder.txtName.setText(locationInfo.getName());
        viewHolder.txtLatitude.setText(String.valueOf(locationInfo.getLatitude()));
        viewHolder.txtLongitude.setText(String.valueOf(locationInfo.getLongitude()));
        viewHolder.txtDescription.setText(locationInfo.getDescription());

        return convertView;
    }
}