package com.hyogij.jsonclient.ImageLoaderUtils;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.hyogij.jsonclient.R;

import java.util.ArrayList;
import java.util.HashMap;

public class LazyAdapter extends BaseAdapter {

    private Activity activity;

    private static final String TAG_TITLE = "title";
    private static final String TAG_THUMBNAILURL = "thumbnailUrl";

    private ArrayList<HashMap<String, String>> pictureList = null;
    private static LayoutInflater inflater = null;
    public ImageLoader imageLoader;

    public LazyAdapter(Activity a, ArrayList<HashMap<String, String>>
            pictureList) {
        activity = a;
        this.pictureList = pictureList;
        inflater = (LayoutInflater) activity.getSystemService(Context
                .LAYOUT_INFLATER_SERVICE);
        imageLoader = new ImageLoader(activity.getApplicationContext());
    }

    public int getCount() {
        return pictureList.size();
    }

    public Object getItem(int position) {
        return position;
    }

    public long getItemId(int position) {
        return position;
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        View vi = convertView;
        if (convertView == null)
            vi = inflater.inflate(R.layout.picture_item, null);

        TextView text = (TextView) vi.findViewById(R.id.title);
        ImageView image = (ImageView) vi.findViewById(R.id.image);
        text.setText("Title : " + pictureList.get(position).get(TAG_TITLE));
        imageLoader.DisplayImage(pictureList.get(position).get
                (TAG_THUMBNAILURL), image);
        return vi;
    }
}
