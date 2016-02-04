package com.hyogij.transactionviewer.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.hyogij.transactionviewer.datas.Products;
import com.hyogij.transactionviewer.R;

import java.util.ArrayList;

/**
 * An adapter class to display Product item.
 */
public class ProductsAdapter extends ArrayAdapter<Products> {
    private static final String CLASS_NAME = ProductsAdapter.class
            .getCanonicalName();

    private ArrayList<Products> items = null;
    private Context context = null;

    public ProductsAdapter(Context context, int textViewResourceId,
                           ArrayList<Products> items) {
        super(context, textViewResourceId, items);
        this.context = context;
        this.items = items;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ProductViewHolder viewHolder = null;
        if (convertView == null) {
            LayoutInflater vi = (LayoutInflater) context.getSystemService
                    (Context.LAYOUT_INFLATER_SERVICE);
            convertView = vi.inflate(R.layout.product_item, null);

            viewHolder = new ProductViewHolder();
            viewHolder.sku = (TextView) convertView.findViewById(R.id.sku);
            viewHolder.size = (TextView) convertView.findViewById(R.id.size);

            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ProductViewHolder) convertView.getTag();
        }

        Products products = items.get(position);
        viewHolder.sku.setText(products.getSku());
        viewHolder.size.setText(String.valueOf(products.getSize()) + context
                .getString(R.string.transactions));

        return convertView;
    }

    public class ProductViewHolder {
        public TextView sku;
        public TextView size;
    }
}
