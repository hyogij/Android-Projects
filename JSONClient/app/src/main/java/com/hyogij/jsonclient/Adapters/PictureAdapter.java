package com.hyogij.jsonclient.Adapters;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.hyogij.jsonclient.ImageLoaderUtils.ImageLoader;
import com.hyogij.jsonclient.JsonDatas.Picture;
import com.hyogij.jsonclient.R;

import java.util.ArrayList;
import java.util.Locale;

public class PictureAdapter extends ArrayAdapter<Picture> {
    private static final String CLASS_NAME = PictureAdapter.class.getCanonicalName();

    private ArrayList<Picture> items = null;
    private ArrayList<Picture> pictureList = null; // Original picture list

    public ImageLoader imageLoader = null;
    private Context context = null;

    public PictureAdapter(Context context, int textViewResourceId, ArrayList<Picture> items) {
        super(context, textViewResourceId, items);
        this.context = context;
        this.items = items;

        this.pictureList = new ArrayList<Picture>();
        this.pictureList.addAll(items);
        imageLoader = new ImageLoader(context.getApplicationContext());
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        View v = convertView;
        if (convertView == null) {
            LayoutInflater vi = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            v = vi.inflate(R.layout.picture_item, null);
        }

        Log.d(CLASS_NAME, "getView " + items.size() + " / " + position);
        Picture picture = items.get(position);
        if (picture != null) {
            TextView albumId = (TextView) v.findViewById(R.id.albumId);
            TextView id = (TextView) v.findViewById(R.id.id);
            TextView title = (TextView) v.findViewById(R.id.title);
            ImageView image = (ImageView) v.findViewById(R.id.image);

            albumId.setText(context.getString(R.string.albumId) + picture.getAlbumId());
            id.setText(context.getString(R.string.id) + picture.getId());
            title.setText(context.getString(R.string.title) + picture.getTitle());
            imageLoader.DisplayImage(picture.getThumbnailUrl(), image);
        }
        return v;
    }

    // Filter Class
    public void filter(String charText) {
        charText = charText.toLowerCase(Locale.getDefault());
        items.clear();
        if (charText.length() == 0) {
            items.addAll(pictureList);
        } else {
            for (Picture picture : pictureList) {
                if (picture.toString().toLowerCase(Locale.getDefault()).contains(charText)) {
                    items.add(picture);
                }
            }
        }
        Log.d(CLASS_NAME, "filter " + items.size());
        notifyDataSetChanged();
    }
}
