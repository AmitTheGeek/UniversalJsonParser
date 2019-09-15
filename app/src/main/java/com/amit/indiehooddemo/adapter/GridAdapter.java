package com.amit.indiehooddemo.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.amit.indiehooddemo.R;
import com.amit.indiehooddemo.models.Item;

import java.util.ArrayList;

public class GridAdapter extends BaseAdapter {

    private final Context mContext;
    private final ArrayList<Item> items;

    //1
    public GridAdapter(Context context, ArrayList<Item> items) {
        this.mContext = context;
        this.items = items;
    }

    // 2
    @Override
    public int getCount() {
        return items.size();
    }

    // 3
    @Override
    public long getItemId(int position) {
        return 0;
    }

    // 4
    @Override
    public Object getItem(int position) {
        return null;
    }

    // 5
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final Item item = items.get(position);

        if (convertView == null) {
            final LayoutInflater layoutInflater = LayoutInflater.from(mContext);
            convertView = layoutInflater.inflate(R.layout.grid_item, null);
        }

        final TextView text1 = convertView.findViewById(R.id.text1);
        final TextView text2 = convertView.findViewById(R.id.text2);

        text1.setText(item.getKey());
        text2.setText(item.getValue());
        return convertView;
    }
}
