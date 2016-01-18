package com.hyogij.transactionviewer.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.hyogij.transactionviewer.Constants;
import com.hyogij.transactionviewer.Datas.Transaction;
import com.hyogij.transactionviewer.R;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.ArrayList;

/**
 * An adapter class to display Transaction item.
 */
public class TransactionsAdapter extends ArrayAdapter<Transaction> {
    private static final String CLASS_NAME = TransactionsAdapter.class
            .getCanonicalName();
    private ArrayList<Transaction> items = null;
    private Context context = null;

    public TransactionsAdapter(Context context, int textViewResourceId,
                               ArrayList<Transaction> items) {
        super(context, textViewResourceId, items);
        this.context = context;
        this.items = items;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        TransactionViewHolder viewHolder = null;
        NumberFormat formatter = new DecimalFormat(Constants.DECIMAL_FORMAT);

        if (convertView == null) {
            LayoutInflater vi = (LayoutInflater) context.getSystemService
                    (Context.LAYOUT_INFLATER_SERVICE);
            convertView = vi.inflate(R.layout.transaction_item, null);

            viewHolder = new TransactionViewHolder();
            viewHolder.currency = (TextView) convertView.findViewById(R.id
                    .currency);
            viewHolder.gbp = (TextView) convertView.findViewById(R.id.gbp);

            convertView.setTag(viewHolder);
        } else {
            viewHolder = (TransactionViewHolder) convertView.getTag();
        }

        Transaction transaction = items.get(position);
        viewHolder.currency.setText(transaction.getCurrency() + " " + String
                .valueOf(formatter.format(transaction.getAmount())));
        viewHolder.gbp.setText(Constants.GBP_CURRENCY + " " + String
                .valueOf(formatter.format(transaction.getGbpValue())));

        return convertView;
    }

    public class TransactionViewHolder {
        public TextView currency;
        public TextView gbp;
    }
}
