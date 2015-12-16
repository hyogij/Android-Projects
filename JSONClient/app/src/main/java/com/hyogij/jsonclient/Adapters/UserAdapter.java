package com.hyogij.jsonclient.Adapters;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.hyogij.jsonclient.JsonDatas.User;
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
    public View getView(int position, View convertView, ViewGroup parent) {
        View v = convertView;
        if (v == null) {
            LayoutInflater vi = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            v = vi.inflate(R.layout.user_item, null);
        }
        Log.d(CLASS_NAME, "getView " + items.size() + " / " + position);
        User user = items.get(position);
        if (user != null) {
            TextView id = (TextView) v.findViewById(R.id.id);
            TextView name = (TextView) v.findViewById(R.id.name);
            TextView username = (TextView) v.findViewById(R.id.username);
            TextView email = (TextView) v.findViewById(R.id.email);
            TextView address = (TextView) v.findViewById(R.id.address);
            TextView phone = (TextView) v.findViewById(R.id.phone);
            TextView website = (TextView) v.findViewById(R.id.website);
            TextView company = (TextView) v.findViewById(R.id.company);

            id.setText(context.getString(R.string.id) + user.getId());
            name.setText(context.getString(R.string.name) + user.getName());
            username.setText(context.getString(R.string.username) + user.getUsername());
            email.setText(context.getString(R.string.email) + user.getEmail());
            address.setText(context.getString(R.string.address) + user.getAddress().toString());
            phone.setText(context.getString(R.string.phone) + user.getPhone());
            website.setText(context.getString(R.string.website) + user.getWebsite());
            company.setText(context.getString(R.string.company) + user.getCompany().toString());
        }
        return v;
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
        Log.d(CLASS_NAME, "filter " + items.size());
        notifyDataSetChanged();
    }
}