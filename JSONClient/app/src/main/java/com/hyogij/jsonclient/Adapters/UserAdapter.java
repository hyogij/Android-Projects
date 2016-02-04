package com.hyogij.jsonclient.adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;

import com.hyogij.jsonclient.activities.AlbumsActivity;
import com.hyogij.jsonclient.Constants;
import com.hyogij.jsonclient.data.User;
import com.hyogij.jsonclient.activities.PostsActivity;
import com.hyogij.jsonclient.R;

import java.util.ArrayList;
import java.util.Locale;

/**
 * Created by hyogij on 15. 12. 15..
 */
public class UserAdapter extends ArrayAdapter<User> {
    private static final String CLASS_NAME = UserAdapter.class.getCanonicalName();

    private ArrayList<User> items = null;
    private ArrayList<User> userList = null; // Original user list
    private Context context = null;

    public UserAdapter(Context context, int textViewResourceId, ArrayList<User> items) {
        super(context, textViewResourceId, items);
        this.context = context;
        this.items = items;

        this.userList = new ArrayList<User>();
        this.userList.addAll(items);
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        UserViewHolder viewHolder = null;
        if (convertView == null) {
            LayoutInflater vi = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = vi.inflate(R.layout.user_item, null);

            viewHolder = new UserViewHolder();
            viewHolder.id = (TextView) convertView.findViewById(R.id.id);
            viewHolder.name = (TextView) convertView.findViewById(R.id.name);
            viewHolder.username = (TextView) convertView.findViewById(R.id.username);
            viewHolder.email = (TextView) convertView.findViewById(R.id.email);
            viewHolder.address = (TextView) convertView.findViewById(R.id.address);
            viewHolder.phone = (TextView) convertView.findViewById(R.id.phone);
            viewHolder.website = (TextView) convertView.findViewById(R.id.website);
            viewHolder.company = (TextView) convertView.findViewById(R.id.company);

            viewHolder.btnAlbum = (Button) convertView.findViewById(R.id.btnAlbum);
            viewHolder.btnPost = (Button) convertView.findViewById(R.id.btnPost);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (UserViewHolder) convertView.getTag();
        }

        final User user = items.get(position);

        viewHolder.id.setText(context.getString(R.string.id) + user.getId());
        viewHolder.name.setText(context.getString(R.string.name) + user.getName());
        viewHolder.username.setText(context.getString(R.string.username) + user.getUsername());
        viewHolder.email.setText(context.getString(R.string.email) + user.getEmail());
        viewHolder.address.setText(context.getString(R.string.address) + user.getAddress().toString());
        viewHolder.phone.setText(context.getString(R.string.phone) + user.getPhone());
        viewHolder.website.setText(context.getString(R.string.website) + user.getWebsite());
        viewHolder.company.setText(context.getString(R.string.company) + user.getCompany().toString());

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
        return convertView;
    }

    // Filter Class
    public void filter(String charText) {
        charText = charText.toLowerCase(Locale.getDefault());
        items.clear();
        if (charText.length() == 0) {
            items.addAll(userList);
        } else {
            for (User user : userList) {
                if (user.toString().toLowerCase(Locale.getDefault()).contains(charText)) {
                    items.add(user);
                }
            }
        }
        notifyDataSetChanged();
    }

    private void startActivity(String userId, boolean isAlbumsActivity) {
        Intent intent = null;
        if (isAlbumsActivity == true) {
            intent = new Intent(context, AlbumsActivity.class);
        } else {
            intent = new Intent(context, PostsActivity.class);
        }
        intent.putExtra(Constants.TAG_USERID, userId);
        context.startActivity(intent);
    }

    public class UserViewHolder {
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
    }
}