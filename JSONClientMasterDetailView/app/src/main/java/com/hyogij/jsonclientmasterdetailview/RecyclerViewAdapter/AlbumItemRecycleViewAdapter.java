package com.hyogij.jsonclientmasterdetailview.RecyclerViewAdapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.hyogij.jsonclientmasterdetailview.JsonDatas.Album;
import com.hyogij.jsonclientmasterdetailview.R;

import java.util.ArrayList;
import java.util.Locale;

/**
 * Created by hyogij on 15. 12. 17..
 */
public class AlbumItemRecycleViewAdapter extends RecyclerView.Adapter<AlbumItemRecycleViewAdapter.ViewHolder> {
    private static final String CLASS_NAME = AlbumItemRecycleViewAdapter.class.getCanonicalName();

    private ArrayList<Album> items = null;
    private ArrayList<Album> list = null; // Original album list
    private Context context = null;

    /**
     * Whether or not the activity is in two-pane mode, i.e. running on a tablet
     * device.
     */
    private boolean twoPane;

    public AlbumItemRecycleViewAdapter(Context context, ArrayList<Album> items, boolean twoPane) {
        this.context = context;
        this.items = items;

        this.list = new ArrayList<Album>();
        this.list.addAll(items);
        this.twoPane = twoPane;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.album_list_content, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder viewHolder, int position) {
        final Album album = items.get(position);

        viewHolder.album = album;
        viewHolder.userId.setText(context.getString(R.string.userId) + album.getUserId());
        viewHolder.id.setText(context.getString(R.string.id) + album.getId());
        viewHolder.title.setText(context.getString(R.string.title) + album.getTitle());
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    // Filter Class
    public void filter(String charText) {
        charText = charText.toLowerCase(Locale.getDefault());
        items.clear();
        if (charText.length() == 0) {
            items.addAll(list);
        } else {
            for (Album album : list) {
                if (album.toString().toLowerCase(Locale.getDefault()).contains(charText)) {
                    items.add(album);
                }
            }
        }
        notifyDataSetChanged();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public final View mView;
        public TextView userId;
        public TextView id;
        public TextView title;

        public Album album;

        public ViewHolder(View view) {
            super(view);
            mView = view;
            id = (TextView) view.findViewById(R.id.id);
            userId = (TextView) view.findViewById(R.id.userId);
            title = (TextView) view.findViewById(R.id.title);
        }

        @Override
        public String toString() {
            return super.toString() + " '" + album.toString() + "'";
        }
    }
}