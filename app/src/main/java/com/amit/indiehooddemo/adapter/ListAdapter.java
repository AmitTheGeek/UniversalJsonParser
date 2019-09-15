package com.amit.indiehooddemo.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.amit.indiehooddemo.DetailActivity;
import com.amit.indiehooddemo.MainActivity;
import com.amit.indiehooddemo.R;
import com.amit.indiehooddemo.models.Item;

import org.json.JSONObject;

import java.util.ArrayList;

public class ListAdapter extends BaseAdapter {

    private final Context mContext;
    private final ArrayList<String> items;
    private final JSONObject schemaJson;
    private final JSONObject dataJson;

    //1
    public ListAdapter(Context context, ArrayList<String> items, JSONObject schemaJson, JSONObject dataJson) {
        this.mContext = context;
        this.items = items;
        this.schemaJson  = schemaJson;
        this.dataJson = dataJson;
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
        final String item = items.get(position);

        if (convertView == null) {
            final LayoutInflater layoutInflater = LayoutInflater.from(mContext);
            convertView = layoutInflater.inflate(R.layout.list_item, null);
        }

        final TextView text1 = convertView.findViewById(R.id.type_name);

        text1.setText(item);

        convertView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mContext, DetailActivity.class);
                intent.putExtra("datajson", dataJson.toString());
                intent.putExtra("schemajson", schemaJson.toString());
                intent.putExtra("name", item);
                mContext.startActivity(intent);
            }
        });


        return convertView;
    }
}
