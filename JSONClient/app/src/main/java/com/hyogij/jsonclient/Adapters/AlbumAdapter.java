package com.hyogij.jsonclient.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.hyogij.jsonclient.data.Album;
import com.hyogij.jsonclient.R;

import java.util.ArrayList;
import java.util.Locale;

/**
 * Created by hyogij on 15. 12. 15..
 */
public class AlbumAdapter extends ArrayAdapter<Album> {
    private ArrayList<Album> items = null;
    private ArrayList<Album> albumList = null; // Original album list
    private Context context = null;

    public AlbumAdapter(Context context, int textViewResourceId, ArrayList<Album> items) {
        super(context, textViewResourceId, items);
        this.context = context;
        this.items = items;

        this.albumList = new ArrayList<Album>();
        this.albumList.addAll(items);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        AlbumViewHolder viewHolder = null;
        if (convertView == null) {
            LayoutInflater vi = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = vi.inflate(R.layout.album_item, null);

            viewHolder = new AlbumViewHolder();
            viewHolder.userId = (TextView) convertView.findViewById(R.id.userId);
            viewHolder.id = (TextView) convertView.findViewById(R.id.id);
            viewHolder.title = (TextView) convertView.findViewById(R.id.title);

            convertView.setTag(viewHolder);
        } else {
            viewHolder = (AlbumViewHolder) convertView.getTag();
        }

        Album album = items.get(position);
        viewHolder.userId.setText(context.getString(R.string.userId) + album.getUserId());
        viewHolder.id.setText(context.getString(R.string.id) + album.getId());
        viewHolder.title.setText(context.getString(R.string.title) + album.getTitle());

        return convertView;
    }

    // Filter Class
    public void filter(String charText) {
        charText = charText.toLowerCase(Locale.getDefault());
        items.clear();
        if (charText.length() == 0) {
            items.addAll(albumList);
        } else {
            for (Album album : albumList) {
                if (album.toString().toLowerCase(Locale.getDefault()).contains(charText)) {
                    items.add(album);
                }
            }
        }
        notifyDataSetChanged();
    }

    public class AlbumViewHolder {
        public TextView userId;
        public TextView id;
        public TextView title;
    }
}