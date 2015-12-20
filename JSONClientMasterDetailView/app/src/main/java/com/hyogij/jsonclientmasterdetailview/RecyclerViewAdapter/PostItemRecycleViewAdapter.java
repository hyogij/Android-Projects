package com.hyogij.jsonclientmasterdetailview.RecyclerViewAdapter;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.hyogij.jsonclientmasterdetailview.CommentListActivity;
import com.hyogij.jsonclientmasterdetailview.CommentListFragment;
import com.hyogij.jsonclientmasterdetailview.Const.Constants;
import com.hyogij.jsonclientmasterdetailview.JsonDatas.Post;
import com.hyogij.jsonclientmasterdetailview.R;

import java.util.ArrayList;
import java.util.Locale;

/**
 * An adapter class to display Post item.
 */
public class PostItemRecycleViewAdapter extends RecyclerView
        .Adapter<PostItemRecycleViewAdapter.ViewHolder> {
    private static final String CLASS_NAME = PostItemRecycleViewAdapter.class
            .getCanonicalName();

    private ArrayList<Post> items = null;
    private ArrayList<Post> list = null; // Original Post list
    private Context context = null;

    /**
     * Whether or not the activity is in two-pane mode, i.e. running on a tablet
     * device.
     */
    private boolean twoPane;

    public PostItemRecycleViewAdapter(Context context, ArrayList<Post> items,
                                      boolean twoPane) {
        this.context = context;
        this.items = items;

        this.list = new ArrayList<Post>();
        this.list.addAll(items);
        this.twoPane = twoPane;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.post_list_content, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder viewHolder, int position) {
        final Post post = items.get(position);

        viewHolder.post = post;
        viewHolder.userId.setText(context.getString(R.string.userId) + post
                .getUserId());
        viewHolder.id.setText(context.getString(R.string.id) + post.getId());
        viewHolder.title.setText(context.getString(R.string.title) + post
                .getTitle());
        viewHolder.body.setText(context.getString(R.string.body) + post
                .getBody());

        viewHolder.view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String postId = viewHolder.post.getId();
                if (twoPane) {
                    Bundle arguments = new Bundle();
                    arguments.putString(Constants.TAG_POSTID,
                            postId);
                    CommentListFragment fragment = new CommentListFragment();
                    fragment.setArguments(arguments);
                    ((FragmentActivity) context).getSupportFragmentManager()
                            .beginTransaction()
                            .replace(R.id.post_detail_container, fragment)
                            .commit();
                } else {
                    Intent intent = new Intent(context, CommentListActivity
                            .class);
                    intent.putExtra(Constants.TAG_POSTID, postId);
                    context.startActivity(intent);
                }
            }
        });
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
            for (Post post : list) {
                if (post.toString().toLowerCase(Locale.getDefault()).contains
                        (charText)) {
                    items.add(post);
                }
            }
        }
        notifyDataSetChanged();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public final View view;
        public TextView userId;
        public TextView id;
        public TextView title;
        public TextView body;

        public Post post;

        public ViewHolder(View view) {
            super(view);
            this.view = view;
            id = (TextView) view.findViewById(R.id.id);
            userId = (TextView) view.findViewById(R.id.userId);
            title = (TextView) view.findViewById(R.id.title);
            body = (TextView) view.findViewById(R.id.body);
        }

        @Override
        public String toString() {
            return super.toString() + " '" + post.toString() + "'";
        }
    }
}