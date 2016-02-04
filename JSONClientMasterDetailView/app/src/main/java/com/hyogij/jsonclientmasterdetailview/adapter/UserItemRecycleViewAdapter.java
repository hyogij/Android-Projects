package com.hyogij.jsonclientmasterdetailview.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.hyogij.jsonclientmasterdetailview.AlbumListActivity;
import com.hyogij.jsonclientmasterdetailview.Constants;
import com.hyogij.jsonclientmasterdetailview.json.User;
import com.hyogij.jsonclientmasterdetailview.PostListActivity;
import com.hyogij.jsonclientmasterdetailview.R;

import java.util.ArrayList;
import java.util.Locale;

/**
 * An adapter class to display User item.
 */
public class UserItemRecycleViewAdapter extends RecyclerView
        .Adapter<UserItemRecycleViewAdapter.ViewHolder> {
    private static final String CLASS_NAME = UserItemRecycleViewAdapter.class
            .getCanonicalName();

    private ArrayList<User> items = null;
    private ArrayList<User> list = null; // Original User list
    private Context context = null;

    /**
     * Whether or not the activity is in two-pane mode, i.e. running on a tablet
     * device.
     */
    private boolean twoPane;

    public UserItemRecycleViewAdapter(Context context, ArrayList<User> items,
                                      boolean twoPane) {
        this.context = context;
        this.items = items;

        this.list = new ArrayList<User>();
        this.list.addAll(items);
        this.twoPane = twoPane;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.user_list_content, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder viewHolder, int position) {
        final User user = items.get(position);

        viewHolder.user = user;
        viewHolder.id.setText(context.getString(R.string.id) + user.getId());
        viewHolder.name.setText(context.getString(R.string.name) + user
                .getName());
        viewHolder.username.setText(context.getString(R.string.username) +
                user.getUsername());
        viewHolder.email.setText(context.getString(R.string.email) + user
                .getEmail());
        viewHolder.address.setText(context.getString(R.string.address) + user
                .getAddress().toString());
        viewHolder.phone.setText(context.getString(R.string.phone) + user
                .getPhone());
        viewHolder.website.setText(context.getString(R.string.website) + user
                .getWebsite());
        viewHolder.company.setText(context.getString(R.string.company) + user
                .getCompany().toString());

        viewHolder.btnAlbum.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(user.getId(), true);
            }
        });

        viewHolder.btnPost.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(user.getId(), false);
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
            for (User user : list) {
                if (user.toString().toLowerCase(Locale.getDefault()).contains
                        (charText)) {
                    items.add(user);
                }
            }
        }
        notifyDataSetChanged();
    }

    private void startActivity(String userId, boolean isAlbumsActivity) {
        Intent intent = null;
        if (isAlbumsActivity == true) {
            intent = new Intent(context, AlbumListActivity.class);
        } else {
            intent = new Intent(context, PostListActivity.class);
        }
        intent.putExtra(Constants.TAG_USERID, userId);
        context.startActivity(intent);
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public final View mView;
        public TextView id;
        public TextView name;
        public TextView username;
        public TextView email;
        public TextView address;
        public TextView phone;
        public TextView website;
        public TextView company;

        public Button btnAlbum;
        public Button btnPost;

        public User user;

        public ViewHolder(View view) {
            super(view);
            mView = view;
            id = (TextView) view.findViewById(R.id.id);
            name = (TextView) view.findViewById(R.id.name);
            username = (TextView) view.findViewById(R.id.username);
            email = (TextView) view.findViewById(R.id.email);
            address = (TextView) view.findViewById(R.id.address);
            phone = (TextView) view.findViewById(R.id.phone);
            website = (TextView) view.findViewById(R.id.website);
            company = (TextView) view.findViewById(R.id.company);

            btnAlbum = (Button) view.findViewById(R.id.btnAlbum);
            btnPost = (Button) view.findViewById(R.id.btnPost);
        }

        @Override
        public String toString() {
            return super.toString() + " '" + user.toString() + "'";
        }
    }
}