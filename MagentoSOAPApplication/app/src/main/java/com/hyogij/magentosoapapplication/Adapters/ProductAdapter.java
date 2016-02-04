package com.hyogij.magentosoapapplication.adapters;

import android.content.Context;
import android.graphics.Bitmap;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageRequest;
import com.hyogij.magentosoapapplication.datas.Product;
import com.hyogij.magentosoapapplication.helper.VolleyHelper;
import com.hyogij.magentosoapapplication.R;

import java.util.ArrayList;

/**
 * An adapter class to display product item.
 */
public class ProductAdapter extends ArrayAdapter<Product> {
    private static final String CLASS_NAME = ProductAdapter.class
            .getCanonicalName();

    private ArrayList<Product> items = null;
    private Context context = null;

    public ProductAdapter(Context context, int textViewResourceId,
                          ArrayList<Product> items) {
        super(context, textViewResourceId, items);
        this.context = context;
        this.items = items;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final ProductViewHolder viewHolder;
        if (convertView == null) {
            LayoutInflater vi = (LayoutInflater) context.getSystemService
                    (Context.LAYOUT_INFLATER_SERVICE);
            convertView = vi.inflate(R.layout.product_item, null);

            viewHolder = new ProductViewHolder();
            viewHolder.name = (TextView) convertView.findViewById(R.id.name);
            viewHolder.type = (TextView) convertView.findViewById(R.id.type);
            viewHolder.image = (ImageView) convertView.findViewById(R.id.image);

            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ProductViewHolder) convertView.getTag();
        }

        Product product = items.get(position);
        viewHolder.name.setText(product.getName());
        viewHolder.type.setText(product.getType());

        // Retrieves an image specified by the URL, displays it in the UI.
        ImageRequest imageRequest = new ImageRequest(product.getImage(),
                new Response.Listener<Bitmap>() {
                    @Override
                    public void onResponse(Bitmap bitmap) {
                        viewHolder.image.setImageBitmap(bitmap);
                    }
                }, 0, 0, ImageView.ScaleType.CENTER_CROP, null,
                new Response.ErrorListener() {
                    public void onErrorResponse(VolleyError error) {
                        Log.d(CLASS_NAME, error.getMessage());
                    }
                });

        VolleyHelper.getInstance(context).addToRequestQueue(imageRequest);

        return convertView;
    }

    public class ProductViewHolder {
        public ImageView image;
        public TextView name;
        public TextView type;
    }
}
