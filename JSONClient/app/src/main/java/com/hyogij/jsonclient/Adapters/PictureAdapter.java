package com.hyogij.jsonclient.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.hyogij.jsonclient.loader.ImageLoader;
import com.hyogij.jsonclient.data.Picture;
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
        PictureViewHolder viewHolder = null;
        if (convertView == null) {
            LayoutInflater vi = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = vi.inflate(R.layout.picture_item, null);

            viewHolder = new PictureViewHolder();
            viewHolder.albumId = (TextView) convertView.findViewById(R.id.albumId);
            viewHolder.id = (TextView) convertView.findViewById(R.id.id);
            viewHolder.title = (TextView) convertView.findViewById(R.id.title);
            viewHolder.image = (ImageView) convertView.findViewById(R.id.image);

            convertView.setTag(viewHolder);
        } else {
            viewHolder = (PictureViewHolder) convertView.getTag();
        }

        Picture picture = items.get(position);
        viewHolder.albumId.setText(context.getString(R.string.albumId) + picture.getAlbumId());
        viewHolder.id.setText(context.getString(R.string.id) + picture.getId());
        viewHolder.title.setText(context.getString(R.string.title) + picture.getTitle());
        imageLoader.DisplayImage(picture.getThumbnailUrl(), viewHolder.image);

        return convertView;
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
        notifyDataSetChanged();
    }

    public class PictureViewHolder {
        public TextView albumId;
        public TextView id;
        public TextView title;
        public ImageView image;
    }
}
