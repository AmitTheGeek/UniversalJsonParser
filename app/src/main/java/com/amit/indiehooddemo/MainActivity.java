package com.amit.indiehooddemo;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.amit.indiehooddemo.adapter.ListAdapter;
import com.amit.indiehooddemo.adapter.SchemaAdapter;
import com.amit.indiehooddemo.models.SchemaDataType;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.ExclusionStrategy;
import com.google.gson.FieldAttributes;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Map;
import java.util.function.Consumer;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "MainActivity";

    private static final String DATA_URL = "https://ui-test-dot-indihood-dev-in.appspot.com/records";

    private static final String SCHEMA_URL = "https://ui-test-dot-indihood-dev-in.appspot.com/schema";

    RequestQueue requestQueue;
    private ListAdapter adapter;
    private ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        requestQueue = Volley.newRequestQueue(this);
        listView = findViewById(R.id.root_list_view);

        loadSchema();
    }

    private void loadSchema(){
        //getting the progressbar
        final ProgressBar progressBar = findViewById(R.id.progressBar);

        //making the progressbar visible
        progressBar.setVisibility(View.VISIBLE);

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest
                (Request.Method.GET, SCHEMA_URL, null, new Response.Listener<JSONObject>() {

                    @Override
                    public void onResponse(JSONObject response) {

                        loadData(response);
                    }
                }, new Response.ErrorListener() {

                    @Override
                    public void onErrorResponse(VolleyError error) {
                        progressBar.setVisibility(View.INVISIBLE);
                        Toast.makeText(getApplicationContext(), error.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });

        //creating a request queue

        //adding the string request to request queue
        requestQueue.add(jsonObjectRequest);
    }

    private void loadData(final JSONObject schemaJson){
        //getting the progressbar
        final ProgressBar progressBar = findViewById(R.id.progressBar);

        //making the progressbar visible
        progressBar.setVisibility(View.VISIBLE);

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest
                (Request.Method.GET, DATA_URL, null, new Response.Listener<JSONObject>() {

                    @Override
                    public void onResponse(JSONObject response) {
                        progressBar.setVisibility(View.INVISIBLE);
                        processData(schemaJson, response);
                    }
                }, new Response.ErrorListener() {

                    @Override
                    public void onErrorResponse(VolleyError error) {
                        progressBar.setVisibility(View.INVISIBLE);

                        Toast.makeText(getApplicationContext(), error.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });

        //creating a request queue

        //adding the string request to request queue
        requestQueue.add(jsonObjectRequest);
    }

    @SuppressLint("NewApi")
    private void processData(final JSONObject schemaJson, final JSONObject dataJson){
        GsonBuilder builder = new GsonBuilder();
        Gson gson = builder.create();
        try {
            final ArrayList<String> dataList = new ArrayList<>();
            dataJson.keys().forEachRemaining(new Consumer<String>() {
                @Override
                public void accept(String key) {
                    Object keyvalue = null;
                    try {
                        keyvalue = dataJson.get(key);
                        dataList.add(key);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    System.out.println("key: " + key + " value: " + keyvalue);
                }
            });

            ListAdapter listAdapter = new ListAdapter(this, dataList, schemaJson, dataJson);
            listView.setAdapter(listAdapter);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    ExclusionStrategy exclusionStrategy = new ExclusionStrategy() {
        public boolean shouldSkipField(FieldAttributes fieldAttributes) {
            if("type".equals(fieldAttributes.getName())){
                return true;
            }
            return false;
        }

        public boolean shouldSkipClass(Class aClass) {
            return false;
        }
    };

}
