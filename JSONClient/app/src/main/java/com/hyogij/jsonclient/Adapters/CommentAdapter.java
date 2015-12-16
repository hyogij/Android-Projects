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
        View v = convertView;
        if (v == null) {
            LayoutInflater vi = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            v = vi.inflate(R.layout.comment_item, null);
        }
        Comment comment = items.get(position);
        if (comment != null) {
            TextView postId = (TextView) v.findViewById(R.id.postId);
            TextView id = (TextView) v.findViewById(R.id.id);
            TextView name = (TextView) v.findViewById(R.id.name);
            TextView email = (TextView) v.findViewById(R.id.email);
            TextView body = (TextView) v.findViewById(R.id.body);

            postId.setText(context.getString(R.string.postId) + comment.getPostId());
            id.setText(context.getString(R.string.id) + comment.getId());
            name.setText(context.getString(R.string.name) + comment.getName());
            email.setText(context.getString(R.string.email) + comment.getEmail());
            body.setText(context.getString(R.string.email) + comment.getBody());
        }
        return v;
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
}