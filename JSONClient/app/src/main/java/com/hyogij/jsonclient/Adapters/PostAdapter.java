package com.hyogij.jsonclient.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.hyogij.jsonclient.JsonDatas.Post;
import com.hyogij.jsonclient.R;

import java.util.ArrayList;
import java.util.Locale;

/**
 * Created by hyogij on 15. 12. 15..
 */
public class PostAdapter extends ArrayAdapter<Post> {
    private ArrayList<Post> items = null;
    private ArrayList<Post> postList = null; // Original post list
    private Context context = null;

    public PostAdapter(Context context, int textViewResourceId, ArrayList<Post> items) {
        super(context, textViewResourceId, items);
        this.context = context;
        this.items = items;

        this.postList = new ArrayList<Post>();
        this.postList.addAll(items);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View v = convertView;
        if (v == null) {
            LayoutInflater vi = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            v = vi.inflate(R.layout.post_item, null);
        }
        Post post = items.get(position);
        if (post != null) {
            TextView userId = (TextView) v.findViewById(R.id.userId);
            TextView id = (TextView) v.findViewById(R.id.id);
            TextView title = (TextView) v.findViewById(R.id.title);
            TextView body = (TextView) v.findViewById(R.id.body);

            userId.setText(context.getString(R.string.userId) + post.getUserId());
            id.setText(context.getString(R.string.id) + post.getId());
            title.setText(context.getString(R.string.title) + post.getTitle());
            title.setText(context.getString(R.string.body) + post.getBody());
        }
        return v;
    }

    // Filter Class
    public void filter(String charText) {
        charText = charText.toLowerCase(Locale.getDefault());
        items.clear();
        if (charText.length() == 0) {
            items.addAll(postList);
        } else {
            for (Post post : postList) {
                if (post.toString().toLowerCase(Locale.getDefault()).contains(charText)) {
                    items.add(post);
                }
            }
        }
        notifyDataSetChanged();
    }
}