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
        PostViewHolder viewHolder = null;
        if (convertView == null) {
            LayoutInflater vi = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = vi.inflate(R.layout.post_item, null);

            viewHolder = new PostViewHolder();
            viewHolder.userId = (TextView) convertView.findViewById(R.id.userId);
            viewHolder.id = (TextView) convertView.findViewById(R.id.id);
            viewHolder.title = (TextView) convertView.findViewById(R.id.title);
            viewHolder.body = (TextView) convertView.findViewById(R.id.body);

            convertView.setTag(viewHolder);
        } else {
            viewHolder = (PostViewHolder) convertView.getTag();
        }

        Post post = items.get(position);
        viewHolder.userId.setText(context.getString(R.string.userId) + post.getUserId());
        viewHolder.id.setText(context.getString(R.string.id) + post.getId());
        viewHolder.title.setText(context.getString(R.string.title) + post.getTitle());
        viewHolder.body.setText(context.getString(R.string.body) + post.getBody());

        return convertView;
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

    public class PostViewHolder {
        public TextView userId;
        public TextView id;
        public TextView title;
        public TextView body;
    }
}