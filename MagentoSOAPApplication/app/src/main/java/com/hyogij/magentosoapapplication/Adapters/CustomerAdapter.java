package com.hyogij.magentosoapapplication.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.hyogij.magentosoapapplication.datas.Customer;
import com.hyogij.magentosoapapplication.R;

import java.util.ArrayList;

/**
 * An adapter class to display customer item.
 */
public class CustomerAdapter extends ArrayAdapter<Customer> {
    private static final String CLASS_NAME = CustomerAdapter.class
            .getCanonicalName();

    private ArrayList<Customer> items = null;
    private Context context = null;

    public CustomerAdapter(Context context, int textViewResourceId,
                           ArrayList<Customer> items) {
        super(context, textViewResourceId, items);
        this.context = context;
        this.items = items;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        CustomerViewHolder viewHolder = null;
        if (convertView == null) {
            LayoutInflater vi = (LayoutInflater) context.getSystemService
                    (Context.LAYOUT_INFLATER_SERVICE);
            convertView = vi.inflate(R.layout.customer_item, null);

            viewHolder = new CustomerViewHolder();
            viewHolder.created_at = (TextView) convertView.findViewById(R.id.created_at);
            viewHolder.email = (TextView) convertView.findViewById(R.id.email);


            convertView.setTag(viewHolder);
        } else {
            viewHolder = (CustomerViewHolder) convertView.getTag();
        }

        Customer customer = items.get(position);
        viewHolder.created_at.setText(customer.getCreated_at());
        viewHolder.email.setText(customer.getEmail());

        return convertView;
    }

    public class CustomerViewHolder {
        public TextView created_at;
        public TextView email;
    }
}
