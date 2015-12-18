package com.hyogij.jsonclientmasterdetailview.RecyclerViewAdapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.hyogij.jsonclientmasterdetailview.JsonDatas.Comment;
import com.hyogij.jsonclientmasterdetailview.R;

import java.util.ArrayList;
import java.util.Locale;

/**
 * Created by hyogij on 15. 12. 17..
 */
public class CommentItemRecycleViewAdapter extends RecyclerView.Adapter<CommentItemRecycleViewAdapter.ViewHolder> {
    private static final String CLASS_NAME = CommentItemRecycleViewAdapter.class.getCanonicalName();

    private ArrayList<Comment> items = null;
    private ArrayList<Comment> list = null; // Original post list
    private Context context = null;

    /**
     * Whether or not the activity is in two-pane mode, i.e. running on a tablet
     * device.
     */
    private boolean twoPane;

    public CommentItemRecycleViewAdapter(Context context, ArrayList<Comment> items, boolean twoPane) {
        this.context = context;
        this.items = items;

        this.list = new ArrayList<Comment>();
        this.list.addAll(items);
        this.twoPane = twoPane;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.comment_list_content, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder viewHolder, int position) {
        final Comment comment = items.get(position);

        viewHolder.comment = comment;
        viewHolder.postId.setText(context.getString(R.string.postId) + comment.getPostId());
        viewHolder.id.setText(context.getString(R.string.id) + comment.getId());
        viewHolder.name.setText(context.getString(R.string.name) + comment.getName());
        viewHolder.email.setText(context.getString(R.string.email) + comment.getEmail());
        viewHolder.body.setText(context.getString(R.string.email) + comment.getBody());
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
            for (Comment comment : list) {
                if (comment.toString().toLowerCase(Locale.getDefault()).contains(charText)) {
                    items.add(comment);
                }
            }
        }
        notifyDataSetChanged();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public final View view;
        public TextView postId;
        public TextView id;
        public TextView name;
        public TextView email;
        public TextView body;

        public Comment comment;

        public ViewHolder(View view) {
            super(view);
            this.view = view;
            id = (TextView) view.findViewById(R.id.id);
            postId = (TextView) view.findViewById(R.id.postId);
            name = (TextView) view.findViewById(R.id.name);
            body = (TextView) view.findViewById(R.id.body);
            email = (TextView) view.findViewById(R.id.email);
        }

        @Override
        public String toString() {
            return super.toString() + " '" + comment.toString() + "'";
        }
    }
}