package com.hyogij.jsonclient.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.hyogij.jsonclient.JsonDatas.Comment;
import com.hyogij.jsonclient.R;

import java.util.ArrayList;
import java.util.Locale;

/**
 * Created by hyogij on 15. 12. 15..
 */
public class CommentAdapter extends ArrayAdapter<Comment> {
    private ArrayList<Comment> items = null;
    private ArrayList<Comment> commentList = null; // Original comment list
    private Context context = null;

    public CommentAdapter(Context context, int textViewResourceId, ArrayList<Comment> items) {
        super(context, textViewResourceId, items);
        this.context = context;
        this.items = items;

        this.commentList = new ArrayList<Comment>();
        this.commentList.addAll(items);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        CommentViewHolder viewHolder = null;
        if (convertView == null) {
            LayoutInflater vi = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = vi.inflate(R.layout.comment_item, null);

            viewHolder = new CommentViewHolder();
            viewHolder.postId = (TextView) convertView.findViewById(R.id.postId);
            viewHolder.id = (TextView) convertView.findViewById(R.id.id);
            viewHolder.name = (TextView) convertView.findViewById(R.id.name);
            viewHolder.email = (TextView) convertView.findViewById(R.id.email);
            viewHolder.body = (TextView) convertView.findViewById(R.id.body);

            convertView.setTag(viewHolder);
        } else {
            viewHolder = (CommentViewHolder) convertView.getTag();
        }

        Comment comment = items.get(position);
        viewHolder.postId.setText(context.getString(R.string.postId) + comment.getPostId());
        viewHolder.id.setText(context.getString(R.string.id) + comment.getId());
        viewHolder.name.setText(context.getString(R.string.name) + comment.getName());
        viewHolder.email.setText(context.getString(R.string.email) + comment.getEmail());
        viewHolder.body.setText(context.getString(R.string.email) + comment.getBody());

        return convertView;
    }

    // Filter Class
    public void filter(String charText) {
        charText = charText.toLowerCase(Locale.getDefault());
        items.clear();
        if (charText.length() == 0) {
            items.addAll(commentList);
        } else {
            for (Comment comment : commentList) {
                if (comment.toString().toLowerCase(Locale.getDefault()).contains(charText)) {
                    items.add(comment);
                }
            }
        }
        notifyDataSetChanged();
    }

    public class CommentViewHolder {
        public TextView postId;
        public TextView id;
        public TextView name;
        public TextView email;
        public TextView body;
    }
}