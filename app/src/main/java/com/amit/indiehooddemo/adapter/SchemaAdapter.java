package com.amit.indiehooddemo.adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.amit.indiehooddemo.R;
import com.amit.indiehooddemo.models.Item;
import com.amit.indiehooddemo.models.SchemaDataType;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.google.android.gms.maps.MapView;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Map;

public class SchemaAdapter extends RecyclerView.Adapter<SchemaAdapter.MyViewHolder> {
    private static final String TAG = "SchemaAdapter";
    private ArrayList<String> dataSet = new ArrayList<>();
    JSONObject schemaJson;
    JSONObject dataJson;
    Map<String, SchemaDataType> map;
    private Context context;

    public SchemaAdapter(JSONObject schemaJson, JSONObject dataJson,
                         Map<String, SchemaDataType> map, Context context) {
        this.schemaJson = schemaJson;
        this.dataJson = dataJson;
        this.map = map;
        this.context = context;
        for (Map.Entry<String, SchemaDataType> entry : map.entrySet()) {
            dataSet.add(entry.getKey());
        }

    }


    public static class MyViewHolder extends RecyclerView.ViewHolder {

        MapView mapView;
        ImageView imageView;
        GridView gridView;
        TextView textView;

        public MyViewHolder(View itemView) {
            super(itemView);
            this.mapView = itemView.findViewById(R.id.mapView);
            this.imageView = itemView.findViewById(R.id.imageView);
            this.gridView = itemView.findViewById(R.id.gridview);
            this.textView = itemView.findViewById(R.id.gridName);
        }
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent,
                                           int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.card_items, parent, false);

        MyViewHolder myViewHolder = new MyViewHolder(view);
        return myViewHolder;
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int listPosition) {
        String name = dataSet.get(listPosition);
        SchemaDataType d = map.get(name);
        try {

            JSONObject data = null;
            JSONArray array = null;
            holder.textView.setText(name);

            if (d.isPrimitive()) {

            } else if (d.isImageType()) {
                data = dataJson.getJSONObject(name); //Booking Keeping
                String value = data.getString("url");
                String label = data.getString("label");
                Glide.with(context).load(value).apply(new RequestOptions().centerCrop()).into(holder.imageView);
                holder.imageView.setVisibility(View.VISIBLE);
                holder.textView.setText(label);
            } else {

                if(d.isArray()){
                    array = dataJson.getJSONArray(name);
                }else{
                    data = dataJson.getJSONObject(name);
                }

                Map<String, SchemaDataType> gridMap = getData(d.getType());
                ArrayList<Item> items = new ArrayList<>();

                for (Map.Entry<String, SchemaDataType> entry : gridMap.entrySet()) {
                    if (!d.isArray()) {
                        String value = data.getString(entry.getKey());
                        items.add(new Item(entry.getKey(), value));
                    } else {
                        for (int i = 0; i < array.length(); i++) {
                            JSONObject obj = array.getJSONObject(i);
                            String value = obj.getString(entry.getKey());
                            items.add(new Item(entry.getKey(), value));
                        }
                    }
                }

                GridAdapter adapter = new GridAdapter(context, items);
                holder.gridView.setAdapter(adapter);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public int getItemCount() {
        return dataSet.size();
    }

    private Map<String, SchemaDataType> getData(String key) {
        GsonBuilder builder = new GsonBuilder();
        Gson gson = builder.create();
        try {
            JSONObject keyData = schemaJson.getJSONObject(key);
            keyData.remove("type");
            Type mapType = new TypeToken<Map<String, SchemaDataType>>() {
            }.getType();
            Map<String, SchemaDataType> map = gson.fromJson(keyData.toString(), mapType);
            Log.d(TAG, "response map == " + map);
            return map;
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;
    }
}
